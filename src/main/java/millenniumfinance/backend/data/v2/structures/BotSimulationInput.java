package millenniumfinance.backend.data.v2.structures;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BotSimulationInput {
  private MarketIndicatorsInput marketIndicators;
  private MarketParameters bearMarket;
  private MarketParameters bullMarket;
  private DataInput data;
  private StartingParameters starting;
}
