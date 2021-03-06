package millenniumfinance.backend.data.v2.structures;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
