package millenniumfinance.backend.data.v2.structures;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarketIndicatorsInput {
  private Integer rsiPeriodSize;
  private BigDecimal stdsOnLowerBollingerBand;
  private BigDecimal stdsOnUpperBollingerBand;
}
