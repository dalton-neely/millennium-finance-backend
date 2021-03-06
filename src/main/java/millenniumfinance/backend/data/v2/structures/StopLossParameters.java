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
public class StopLossParameters {
  private Parameter<BigDecimal> amountBelowCostBasis;
  private Parameter<BigDecimal> percentageOfLoss;
  private Parameter<BigDecimal> targetAssetPrice;
}
