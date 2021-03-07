package millenniumfinance.backend.genetics;

import java.math.BigDecimal;
import java.util.Random;
import lombok.Data;
import millenniumfinance.backend.data.v2.structures.BearMarketParameters;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput.BotSimulationInputBuilder;
import millenniumfinance.backend.data.v2.structures.BullMarketParameters;
import millenniumfinance.backend.data.v2.structures.BuyParameters;
import millenniumfinance.backend.data.v2.structures.DataInput;
import millenniumfinance.backend.data.v2.structures.DowntrendParameters;
import millenniumfinance.backend.data.v2.structures.MarketIndicatorsInput;
import millenniumfinance.backend.data.v2.structures.MarketIndicatorsInput.MarketIndicatorsInputBuilder;
import millenniumfinance.backend.data.v2.structures.Parameter;
import millenniumfinance.backend.data.v2.structures.SellParameters;
import millenniumfinance.backend.data.v2.structures.StartingParameters;
import millenniumfinance.backend.data.v2.structures.StopLossParameters;
import millenniumfinance.backend.data.v2.structures.UptrendParameters;
import static java.math.BigDecimal.ZERO;
import static millenniumfinance.backend.genetics.Randomizers.activeRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.amountRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.percentageRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.rsiRandomizer;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;

@Data
public class Genotype {
  private BotSimulationInput genes;
  private Random random = new Random();
  
  public void randomize() {
    BotSimulationInputBuilder builder = BotSimulationInput.builder();
    MarketIndicatorsInputBuilder marketBuilder = MarketIndicatorsInput.builder();
    StartingParameters.StartingParametersBuilder startingBuilder = StartingParameters.builder();
    DataInput.DataInputBuilder dataBuilder = DataInput.builder();
    BearMarketParameters.BearMarketParametersBuilder bearBuilder = BearMarketParameters.builder();
    BullMarketParameters.BullMarketParametersBuilder bullBuilder = BullMarketParameters.builder();
    DowntrendParameters.DowntrendParametersBuilder bullDowntrendBuilder = DowntrendParameters.builder();
    DowntrendParameters.DowntrendParametersBuilder bearDowntrendBuilder = DowntrendParameters.builder();
    UptrendParameters.UptrendParametersBuilder bullUptrendBuilder = UptrendParameters.builder();
    UptrendParameters.UptrendParametersBuilder bearUptrendBuilder = UptrendParameters.builder();
    
    dataBuilder.symbol("BTCUSD")
        .interval("1m")
        .limit("1000")
        .startDate(null)
        .stopDate(null);
    
    startingBuilder.startingBalance(fromNumber(3000.00));
    
    marketBuilder.rsiPeriodSize(periodRandomizer())
        .stdsOnLowerBollingerBand(stdRandomizer())
        .stdsOnUpperBollingerBand(stdRandomizer());
    
    bearDowntrendBuilder
        .buy(randomizeBuy())
        .sell(randomizeSell())
        .stopLoss(randomizeStopLoss());
    bearUptrendBuilder
        .buy(randomizeBuy())
        .sell(randomizeSell())
        .stopLoss(randomizeStopLoss());
    bearBuilder.downtrend(bearDowntrendBuilder.build())
        .uptrend(bearUptrendBuilder.build());
    
    bullDowntrendBuilder
        .buy(randomizeBuy())
        .sell(randomizeSell())
        .stopLoss(randomizeStopLoss());
    bullUptrendBuilder
        .buy(randomizeBuy())
        .sell(randomizeSell())
        .stopLoss(randomizeStopLoss());
    bullBuilder.downtrend(bullDowntrendBuilder.build())
        .uptrend(bullUptrendBuilder.build());
    
    builder.marketIndicators(marketBuilder.build())
        .starting(startingBuilder.build())
        .data(dataBuilder.build())
        .bearMarket(bearBuilder.build())
        .bullMarket(bullBuilder.build());
    
    
    genes = builder.build();
  }
  
  private Integer periodRandomizer() {
    return random.nextInt(8) + 6;
  }
  
  private BigDecimal stdRandomizer() {
    return fromNumber(random.nextDouble() * random.nextInt(5));
  }
  
  private BuyParameters randomizeBuy() {
    BuyParameters.BuyParametersBuilder builder = BuyParameters.builder();
    
    builder
        .percentageOfLowerBollingerBand(new Parameter<>(percentageRandomizer(), activeRandomizer()))
        .rsiCeiling(new Parameter<>(rsiRandomizer(), activeRandomizer()))
        .targetAmount(new Parameter<>(ZERO, false));
    
    return builder.build();
  }
  
  private SellParameters randomizeSell() {
    SellParameters.SellParametersBuilder builder = SellParameters.builder();
    
    builder
        .amountAboveCostBasis(new Parameter<>(amountRandomizer(50.00), activeRandomizer()))
        .percentageGain(new Parameter<>(percentageRandomizer(), activeRandomizer()))
        .rsiFloor(new Parameter<>(rsiRandomizer(), activeRandomizer()))
        .percentageOfUpperBollingerBand(new Parameter<>(percentageRandomizer(), activeRandomizer()))
        .targetAmount(new Parameter<>(ZERO, false));
    
    return builder.build();
  }
  
  private StopLossParameters randomizeStopLoss() {
    StopLossParameters.StopLossParametersBuilder builder = StopLossParameters.builder();
    
    builder
        .percentageOfLoss(new Parameter<>(percentageRandomizer(), activeRandomizer()))
        .amountBelowCostBasis(new Parameter<>(amountRandomizer(50.00), activeRandomizer()))
        .targetAssetPrice(new Parameter<>(ZERO, false));
    
    return builder.build();
  }
}
