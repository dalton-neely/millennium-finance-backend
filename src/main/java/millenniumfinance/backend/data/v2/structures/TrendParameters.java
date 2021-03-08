package millenniumfinance.backend.data.v2.structures;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static millenniumfinance.backend.data.v2.structures.BuyParameters.crossoverBuy;
import static millenniumfinance.backend.data.v2.structures.BuyParameters.randomizeBuy;
import static millenniumfinance.backend.data.v2.structures.SellParameters.crossoverSell;
import static millenniumfinance.backend.data.v2.structures.SellParameters.randomizeSell;
import static millenniumfinance.backend.data.v2.structures.StopLossParameters.crossoverStopLoss;
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
      GeneticAlgorithmInput input
  ) {
    return TrendParameters.builder()
        .buy(randomizeBuy(input))
        .sell(randomizeSell(input))
        .stopLoss(randomizeStopLoss(input))
        .build();
  }
  
  public static TrendParameters crossoverTrend(TrendParameters mother, TrendParameters father) {
    TrendParametersBuilder builder = builder();
    
    builder.buy(crossoverBuy(mother.getBuy(), father.getBuy()))
        .sell(crossoverSell(mother.getSell(), father.getSell()))
        .stopLoss(crossoverStopLoss(mother.getStopLoss(), father.getStopLoss()));
    
    return builder.build();
  }
}
