package millenniumfinance.backend.genetics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput.BotSimulationInputBuilder;
import millenniumfinance.backend.data.v2.structures.GeneticAlgorithmInput;
import millenniumfinance.backend.data.v2.structures.MarketIndicatorsInput;
import millenniumfinance.backend.data.v2.structures.StartingParameters;
import static millenniumfinance.backend.data.v2.structures.MarketParameters.randomizeMarketParameters;
import static millenniumfinance.backend.genetics.Randomizers.randomInt;
import static millenniumfinance.backend.genetics.Randomizers.toMutate;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Genotype {
  private BotSimulationInput genes;
  
  public static Genotype fromSeed(GeneticAlgorithmInput input) {
    return new Genotype(input.getSeed());
  }
  
  public static Genotype randomizeGenotype(GeneticAlgorithmInput input) {
    BotSimulationInputBuilder builder = BotSimulationInput.builder();
    
    builder.marketIndicators(new MarketIndicatorsInput())
        .starting(new StartingParameters(fromNumber(input.getStartingBalance())))
        .data(input.getDataFetchParameters())
        .bearMarket(randomizeMarketParameters(input))
        .bullMarket(randomizeMarketParameters(input));
    
    return new Genotype(builder.build());
  }
  
  public void mutateChance(GeneticAlgorithmInput input) {
    if (toMutate(input.getMutationPercentage())) {
      int randomInt = randomInt(2);
      switch (randomInt) {
        case 0:
          genes.setBearMarket(randomizeMarketParameters(input));
          break;
        case 1:
          genes.setBullMarket(randomizeMarketParameters(input));
          break;
      }
    }
  }
}
