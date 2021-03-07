package millenniumfinance.backend.genetics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput.BotSimulationInputBuilder;
import millenniumfinance.backend.data.v2.structures.DataInput;
import millenniumfinance.backend.data.v2.structures.MarketIndicatorsInput;
import millenniumfinance.backend.data.v2.structures.MarketIndicatorsInput.MarketIndicatorsInputBuilder;
import millenniumfinance.backend.data.v2.structures.StartingParameters;
import millenniumfinance.backend.data.v2.structures.StartingParameters.StartingParametersBuilder;
import static millenniumfinance.backend.data.v2.structures.MarketParameters.randomizeMarketParameters;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Genotype {
  private BotSimulationInput genes;
  
  public static Genotype randomizeGenotype(
      DataInput input,
      Double maxAmountAboveCostBasis,
      Double maxAmountBelowCostBasis,
      Integer maxStdLowerBollingerBand,
      Integer minStdUpperBollingerBand,
      Integer minPeriod,
      Integer maxPeriod,
      Double startingBalance
  ) {
    BotSimulationInputBuilder builder = BotSimulationInput.builder();
    MarketIndicatorsInputBuilder marketBuilder = MarketIndicatorsInput.builder();
    StartingParametersBuilder startingBuilder = StartingParameters.builder();
    
    startingBuilder.startingBalance(fromNumber(startingBalance));
    
    builder.marketIndicators(marketBuilder.build())
        .starting(startingBuilder.build())
        .data(input)
        .bearMarket(randomizeMarketParameters(maxAmountAboveCostBasis, maxAmountBelowCostBasis))
        .bullMarket(randomizeMarketParameters(maxAmountAboveCostBasis, maxAmountBelowCostBasis));
    
    return new Genotype(builder.build());
  }
}
