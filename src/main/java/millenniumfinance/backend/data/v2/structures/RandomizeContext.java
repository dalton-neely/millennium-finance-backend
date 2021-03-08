package millenniumfinance.backend.data.v2.structures;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RandomizeContext {
  private Double maxAmountAboveCostBasis;
  private Double maxAmountBelowCostBasis;
  private PercentageWindow buyBollingerPercentage;
  private PercentageWindow sellBollingerPercentage;
  private PercentageWindow sellGainPercentage;
  private PercentageWindow stopLossPercentage;
  private Integer minPeriod;
  private Integer maxPeriod;
  private RsiWindow sellRsi;
  private RsiWindow buyRsi;
}
