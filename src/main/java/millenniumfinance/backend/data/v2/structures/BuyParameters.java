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
public class BuyParameters {
  private Parameter<BigDecimal> rsiCeiling;
  private Parameter<BigDecimal> percentageOfLowerBollingerBand;
  private Parameter<BigDecimal> targetAmount;
}
