package millenniumfinance.backend.genetics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput.BotSimulationInputBuilder;
import millenniumfinance.backend.data.v2.structures.GeneticAlgorithmInput;
import millenniumfinance.backend.data.v2.structures.MarketIndicatorsInput;
import millenniumfinance.backend.data.v2.structures.RandomizeContext;
import millenniumfinance.backend.data.v2.structures.StartingParameters;
import static millenniumfinance.backend.data.v2.structures.MarketParameters.randomizeMarketParameters;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Genotype {
  private BotSimulationInput genes;
  
  public static Genotype randomizeGenotype(GeneticAlgorithmInput input) {
    BotSimulationInputBuilder builder = BotSimulationInput.builder();
    RandomizeContext context = input.getRandomizeContext();
    Double maxAmountAboveCostBasis = context.getMaxAmountAboveCostBasis();
    Double maxAmountBelowCostBasis = context.getMaxAmountBelowCostBasis();
    
    builder.marketIndicators(new MarketIndicatorsInput())
        .starting(new StartingParameters(fromNumber(input.getStartingBalance())))
        .data(input.getDataFetchParameters())
        .bearMarket(randomizeMarketParameters(maxAmountAboveCostBasis, maxAmountBelowCostBasis))
        .bullMarket(randomizeMarketParameters(maxAmountAboveCostBasis, maxAmountBelowCostBasis));
    
    return new Genotype(builder.build());
  }
}
