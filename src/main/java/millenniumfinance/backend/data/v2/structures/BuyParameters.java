package millenniumfinance.backend.data.v2.structures;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static millenniumfinance.backend.genetics.Randomizers.OFF_GENE;
import static millenniumfinance.backend.genetics.Randomizers.activeRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.chooseRandom;
import static millenniumfinance.backend.genetics.Randomizers.percentageRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.rsiRandomizer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyParameters {
  private Parameter<BigDecimal> rsiCeiling;
  private Parameter<BigDecimal> percentageOfLowerBollingerBand;
  private Parameter<BigDecimal> targetAmount;
  
  public static BuyParameters randomizeBuy() {
    BuyParametersBuilder builder = builder();
    Parameter<BigDecimal> percentageOfLowerBollingerBand = new Parameter<>(percentageRandomizer(), activeRandomizer());
    Parameter<BigDecimal> rsiCeiling = new Parameter<>(rsiRandomizer(), activeRandomizer());
    
    builder.percentageOfLowerBollingerBand(percentageOfLowerBollingerBand)
        .rsiCeiling(rsiCeiling)
        .targetAmount(OFF_GENE);
    
    return builder.build();
  }
  
  public static BuyParameters crossoverBuy(BuyParameters mother, BuyParameters father) {
    BuyParametersBuilder builder = builder();
    
    builder.percentageOfLowerBollingerBand(
        chooseRandom(mother.getPercentageOfLowerBollingerBand(), father.getPercentageOfLowerBollingerBand()
        ))
        .rsiCeiling(chooseRandom(mother.getRsiCeiling(), father.getRsiCeiling()))
        .targetAmount(chooseRandom(mother.getTargetAmount(), father.getTargetAmount()));
    
    return builder.build();
  }
}
