package millenniumfinance.backend.genetics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.data.v1.structures.DataTable;
import millenniumfinance.backend.data.v2.structures.BearMarketParameters;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput;
import millenniumfinance.backend.data.v2.structures.BullMarketParameters;
import millenniumfinance.backend.data.v2.structures.BuyParameters;
import millenniumfinance.backend.data.v2.structures.DowntrendParameters;
import millenniumfinance.backend.data.v2.structures.MarketIndicatorsInput;
import millenniumfinance.backend.data.v2.structures.SellParameters;
import millenniumfinance.backend.data.v2.structures.StopLossParameters;
import millenniumfinance.backend.data.v2.structures.UptrendParameters;
import millenniumfinance.backend.services.SimulationBot;
import millenniumfinance.backend.utilities.BigDecimalHelpers;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.isEqualTo;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Population {
  private List<Generation> generations = new ArrayList<>();
  private Integer rounds;
  private Integer generationsRun;
  private DataTable dataTable;
  private SimulationBot bot;
  private Random random = new Random();
  
  public void loadDataInput(DataTable dataInput) {
    this.dataTable = dataInput;
  }
  
  public void loadBot(SimulationBot bot) {
    this.bot = bot;
  }
  
  public void getResult() {
    System.out.println(generations.size());
  }
  
  public void runAllGenerations() {
    initialGeneration();
    for (int index = 0; index < rounds - 1; index++) {
      System.out.println("running generation: " + index);
      Generation current = generations.get(index);
      current.runSimulation(dataTable, bot);
      List<Phenotype> winners = current.getWinners();
//      System.out.println(current.getWinners().stream().map(winner -> max(winner.getReport().getRealizedGainLoss(), winner.getReport().getUnrealizedGainLoss())).collect(Collectors.toList()));
      List<Phenotype> children = crossover(winners);
      System.out.println("made " + children.size() + " children");
      Generation nextGeneration = new Generation();
      nextGeneration.setGenerationSize(100);
      nextGeneration.seedNew(children);
      generations.add(nextGeneration);
    }
    System.out.println("finished");
    System.out.println(generations.get(generations.size() - 2).getWinners().get(0).getReport().toString());
  }
  
  public void initialGeneration() {
    Generation generation = new Generation();
    generation.setGenerationSize(100);
    generation.randomize();
    generations.add(generation);
  }
  
  public List<Phenotype> crossover(List<Phenotype> winners) {
    System.out.println("starting crossover");
    List<Phenotype> children = new ArrayList<>();
    int size = 20;
    
    for (int index = 0; index < size; index += 2) {
      System.out.println("crossing parent " + index + " with parent " + index + 1);
      Phenotype current = winners.get(index);
      Phenotype next = winners.get(index + 1);
      children.add(crossover(current.getGenotype().getGenes(), next.getGenotype().getGenes()));
    }
    
    return children;
  }
  
  private Phenotype crossover(BotSimulationInput mom, BotSimulationInput dad) {
    Phenotype child = new Phenotype();
    Genotype newGenes = new Genotype();
    BotSimulationInput input = new BotSimulationInput();
    MarketIndicatorsInput marketInput = new MarketIndicatorsInput();
    BearMarketParameters bearMarketParameters = new BearMarketParameters();
    UptrendParameters bearUptrend = new UptrendParameters();
    DowntrendParameters bearDowntrend = new DowntrendParameters();
    BullMarketParameters bullMarketParameters = new BullMarketParameters();
    DowntrendParameters bullDowntrend = new DowntrendParameters();
    UptrendParameters bullUptrend = new UptrendParameters();
    
    bullDowntrend.setBuy(crossover(mom.getBullMarket().getDowntrend().getBuy(), dad.getBullMarket().getDowntrend().getBuy()));
    bullDowntrend.setSell(crossover(mom.getBullMarket().getDowntrend().getSell(), dad.getBullMarket().getDowntrend().getSell()));
    bullDowntrend.setStopLoss(crossover(mom.getBullMarket().getDowntrend().getStopLoss(), dad.getBullMarket().getDowntrend().getStopLoss()));
    
    bullUptrend.setBuy(crossover(mom.getBullMarket().getUptrend().getBuy(), dad.getBullMarket().getUptrend().getBuy()));
    bullUptrend.setSell(crossover(mom.getBullMarket().getUptrend().getSell(), dad.getBullMarket().getUptrend().getSell()));
    bullUptrend.setStopLoss(crossover(mom.getBullMarket().getUptrend().getStopLoss(), dad.getBullMarket().getUptrend().getStopLoss()));
    
    bullMarketParameters.setDowntrend(bullDowntrend);
    bullMarketParameters.setUptrend(bullUptrend);
    
    bearUptrend.setBuy(crossover(mom.getBearMarket().getUptrend().getBuy(), dad.getBearMarket().getUptrend().getBuy()));
    bearUptrend.setSell(crossover(mom.getBearMarket().getUptrend().getSell(), dad.getBearMarket().getUptrend().getSell()));
    bearUptrend.setStopLoss(crossover(mom.getBearMarket().getUptrend().getStopLoss(), dad.getBearMarket().getUptrend().getStopLoss()));
    
    bearDowntrend.setBuy(crossover(mom.getBearMarket().getDowntrend().getBuy(), dad.getBearMarket().getDowntrend().getBuy()));
    bearDowntrend.setSell(crossover(mom.getBearMarket().getDowntrend().getSell(), dad.getBearMarket().getDowntrend().getSell()));
    bearDowntrend.setStopLoss(crossover(mom.getBearMarket().getDowntrend().getStopLoss(), dad.getBearMarket().getDowntrend().getStopLoss()));
    
    bearMarketParameters.setDowntrend(bearDowntrend);
    bearMarketParameters.setUptrend(bearUptrend);
    
    marketInput.setRsiPeriodSize(chooseRandom(mom.getMarketIndicators().getRsiPeriodSize(), dad.getMarketIndicators().getRsiPeriodSize()));
    marketInput.setStdsOnLowerBollingerBand(chooseRandom(mom.getMarketIndicators().getStdsOnLowerBollingerBand(), dad.getMarketIndicators().getStdsOnLowerBollingerBand()));
    marketInput.setStdsOnUpperBollingerBand(chooseRandom(mom.getMarketIndicators().getStdsOnUpperBollingerBand(), dad.getMarketIndicators().getStdsOnUpperBollingerBand()));
    
    input.setBullMarket(bullMarketParameters);
    input.setBearMarket(bearMarketParameters);
    input.setMarketIndicators(marketInput);
    input.setData(mom.getData());
    input.setStarting(mom.getStarting());
    
    newGenes.setGenes(input);
    child.setGenotype(newGenes);
    
    return child;
  }
  
  private BuyParameters crossover(BuyParameters mom, BuyParameters dad) {
    BuyParameters child = new BuyParameters();
    
    child.setRsiCeiling(chooseRandom(mom.getRsiCeiling(), dad.getRsiCeiling()));
    child.setTargetAmount(chooseRandom(mom.getTargetAmount(), dad.getTargetAmount()));
    child.setPercentageOfLowerBollingerBand(chooseRandom(mom.getPercentageOfLowerBollingerBand(), dad.getPercentageOfLowerBollingerBand()));
    
    return child;
  }
  
  private SellParameters crossover(SellParameters mom, SellParameters dad) {
    SellParameters child = new SellParameters();
    
    child.setPercentageGain(chooseRandom(mom.getPercentageGain(), dad.getPercentageGain()));
    child.setAmountAboveCostBasis(chooseRandom(mom.getAmountAboveCostBasis(), dad.getAmountAboveCostBasis()));
    child.setTargetAmount(chooseRandom(mom.getTargetAmount(), dad.getTargetAmount()));
    child.setRsiFloor(chooseRandom(mom.getRsiFloor(), dad.getRsiFloor()));
    child.setPercentageOfUpperBollingerBand(chooseRandom(mom.getPercentageOfUpperBollingerBand(), dad.getPercentageOfUpperBollingerBand()));
    
    return child;
  }
  
  private StopLossParameters crossover(StopLossParameters mom, StopLossParameters dad) {
    StopLossParameters child = new StopLossParameters();
    
    child.setPercentageOfLoss(chooseRandom(mom.getPercentageOfLoss(), dad.getPercentageOfLoss()));
    child.setAmountBelowCostBasis(chooseRandom(mom.getAmountBelowCostBasis(), dad.getAmountBelowCostBasis()));
    child.setTargetAssetPrice(chooseRandom(mom.getTargetAssetPrice(), dad.getTargetAssetPrice()));
    
    return child;
  }
  
  private <T> T chooseRandom(T a, T b) {
    if (random.nextBoolean()) {
      return a;
    } else {
      return b;
    }
  }
  
  private BigDecimal max(BigDecimal a, BigDecimal b) {
    if (isEqualTo(a, fromNumber(0))) {
      return b;
    }
    if (isEqualTo(b, fromNumber(0))) {
      return a;
    }
    if (BigDecimalHelpers.isGreaterThan(a, b)) {
      return a;
    } else {
      return b;
    }
  }
}
