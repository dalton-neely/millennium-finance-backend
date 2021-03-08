package millenniumfinance.backend.data.v2.structures;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static millenniumfinance.backend.data.v2.structures.TrendParameters.crossoverTrend;
import static millenniumfinance.backend.data.v2.structures.TrendParameters.randomizeTrendParameters;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketParameters {
  private TrendParameters uptrend;
  private TrendParameters downtrend;
  
  public static MarketParameters randomizeMarketParameters(
      GeneticAlgorithmInput input
  ) {
    return builder()
        .uptrend(randomizeTrendParameters(input))
        .downtrend(randomizeTrendParameters(input))
        .build();
  }
  
  public static MarketParameters crossoverMarket(MarketParameters mother, MarketParameters father) {
    MarketParametersBuilder builder = builder();
    
    builder.downtrend(crossoverTrend(mother.getDowntrend(), father.getDowntrend()))
        .uptrend(crossoverTrend(mother.getUptrend(), father.getUptrend()));
    
    return builder.build();
  }
}
