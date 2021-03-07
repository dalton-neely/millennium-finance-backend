package millenniumfinance.backend.data.v2.structures;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static millenniumfinance.backend.data.v2.structures.TrendParameters.randomizeTrendParameters;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketParameters {
  private TrendParameters uptrend;
  private TrendParameters downtrend;
  
  public static MarketParameters randomizeMarketParameters(
      Double maxAmountAboveCostBasis,
      Double maxAmountBelowCostBasis
  ) {
    return MarketParameters.builder()
        .uptrend(randomizeTrendParameters(maxAmountAboveCostBasis, maxAmountBelowCostBasis))
        .downtrend(randomizeTrendParameters(maxAmountAboveCostBasis, maxAmountBelowCostBasis))
        .build();
  }
}
