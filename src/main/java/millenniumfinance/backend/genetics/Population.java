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
import millenniumfinance.backend.data.v2.structures.BotSimulationInput;
import millenniumfinance.backend.data.v2.structures.GeneticAlgorithmInput;
import millenniumfinance.backend.services.SimulationBot;
import millenniumfinance.backend.utilities.BigDecimalHelpers;
import static millenniumfinance.backend.data.v2.structures.MarketParameters.crossoverMarket;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.isEqualTo;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Population {
  private List<Generation> generations = new ArrayList<>();
  private Integer generationSize;
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
  
  public void runAllGenerations(GeneticAlgorithmInput input) {
    initialGeneration(input);
    for (int index = 0; index < generationSize - 1; index++) {
      System.out.println("running generation: " + index);
      Generation current = generations.get(index);
      current.runSimulation(dataTable, bot);
      List<Phenotype> winners = current.getWinners();
//      System.out.println(current.getWinners().stream().map(winner -> max(winner.getReport().getRealizedGainLoss(), winner.getReport().getUnrealizedGainLoss())).collect(Collectors.toList()));
      List<Phenotype> children = crossover(winners);
      System.out.println("made " + children.size() + " children");
      Generation nextGeneration = new Generation();
      nextGeneration.setPopulationSize(input.getPopulationSize());
      nextGeneration.seedNew(children, input);
      generations.add(nextGeneration);
    }
    System.out.println("finished");
    System.out.println(generations.get(generations.size() - 2).getWinners().get(0).getReport().toString());
  }
  
  public void initialGeneration(GeneticAlgorithmInput input) {
    Generation generation = new Generation();
    generation.setPopulationSize(input.getPopulationSize());
    generation.randomize(input);
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
  
  private Phenotype crossover(BotSimulationInput mother, BotSimulationInput father) {
    Phenotype child = new Phenotype();
    Genotype newGenes = new Genotype();
    BotSimulationInput input = new BotSimulationInput();
    
    input.setBullMarket(crossoverMarket(mother.getBullMarket(), father.getBullMarket()));
    input.setBearMarket(crossoverMarket(mother.getBearMarket(), father.getBearMarket()));
    input.setMarketIndicators(mother.getMarketIndicators());
    input.setData(mother.getData());
    input.setStarting(mother.getStarting());
    
    newGenes.setGenes(input);
    child.setGenotype(newGenes);
    
    return child;
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
