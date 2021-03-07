package millenniumfinance.backend.data.v2.structures;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static millenniumfinance.backend.data.v2.structures.BuyParameters.randomizeBuy;
import static millenniumfinance.backend.data.v2.structures.SellParameters.randomizeSell;
import static millenniumfinance.backend.data.v2.structures.StopLossParameters.randomizeStopLoss;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrendParameters {
  private BuyParameters buy;
  private SellParameters sell;
  private StopLossParameters stopLoss;
  
  public static TrendParameters randomizeTrendParameters(
      Double maxAmountAboveCostBasis,
      Double maxAmountBelowCostBasis
  ) {
    return TrendParameters.builder()
        .buy(randomizeBuy())
        .sell(randomizeSell(maxAmountAboveCostBasis))
        .stopLoss(randomizeStopLoss(maxAmountBelowCostBasis))
        .build();
  }
}
