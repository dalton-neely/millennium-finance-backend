package millenniumfinance.backend.data.v2.structures;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static millenniumfinance.backend.genetics.Randomizers.OFF_GENE;
import static millenniumfinance.backend.genetics.Randomizers.activeRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.amountRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.percentageRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.rsiRandomizer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellParameters {
  private Parameter<BigDecimal> rsiFloor;
  private Parameter<BigDecimal> percentageOfUpperBollingerBand;
  private Parameter<BigDecimal> percentageGain;
  private Parameter<BigDecimal> amountAboveCostBasis;
  private Parameter<BigDecimal> targetAmount;
  
  public static SellParameters randomizeSell(Double maxAmountAboveCostBasis) {
    SellParameters.SellParametersBuilder builder = SellParameters.builder();
    Parameter<BigDecimal> amountAboveCostBasis = new Parameter<>(amountRandomizer(maxAmountAboveCostBasis), activeRandomizer());
    Parameter<BigDecimal> percentageGain = new Parameter<>(percentageRandomizer(), activeRandomizer());
    Parameter<BigDecimal> rsiFloor = new Parameter<>(rsiRandomizer(), activeRandomizer());
    Parameter<BigDecimal> percentageOfUpperBollingerBand = new Parameter<>(percentageRandomizer(), activeRandomizer());
    
    builder.amountAboveCostBasis(amountAboveCostBasis)
        .percentageGain(percentageGain)
        .rsiFloor(rsiFloor)
        .percentageOfUpperBollingerBand(percentageOfUpperBollingerBand)
        .targetAmount(OFF_GENE);
    
    return builder.build();
  }
}
