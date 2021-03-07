package millenniumfinance.backend.data.v2.structures;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static millenniumfinance.backend.genetics.Randomizers.OFF_GENE;
import static millenniumfinance.backend.genetics.Randomizers.activeRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.amountRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.chooseRandom;
import static millenniumfinance.backend.genetics.Randomizers.percentageRandomizer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StopLossParameters {
  private Parameter<BigDecimal> amountBelowCostBasis;
  private Parameter<BigDecimal> percentageOfLoss;
  private Parameter<BigDecimal> targetAssetPrice;
  
  public static StopLossParameters randomizeStopLoss(Double maxAmountBelowCostBasis) {
    StopLossParametersBuilder builder = builder();
    Parameter<BigDecimal> percentageOfLoss = new Parameter<>(percentageRandomizer(), activeRandomizer());
    Parameter<BigDecimal> amountBelowCostBasis = new Parameter<>(amountRandomizer(maxAmountBelowCostBasis), activeRandomizer());
    
    builder.percentageOfLoss(percentageOfLoss)
        .amountBelowCostBasis(amountBelowCostBasis)
        .targetAssetPrice(OFF_GENE);
    
    return builder.build();
  }
  
  public static StopLossParameters crossover(StopLossParameters mother, StopLossParameters father) {
    StopLossParametersBuilder builder = builder();
    
    builder.percentageOfLoss(chooseRandom(mother.getPercentageOfLoss(), father.getPercentageOfLoss()))
        .amountBelowCostBasis(chooseRandom(mother.getAmountBelowCostBasis(), father.getAmountBelowCostBasis()))
        .targetAssetPrice(chooseRandom(mother.getTargetAssetPrice(), father.getTargetAssetPrice()));
    
    return builder.build();
  }
}
