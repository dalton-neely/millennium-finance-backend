package millenniumfinance.backend.data.v1.structures;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.data.v1.enumerations.PositionState;
import millenniumfinance.backend.data.v1.enumerations.TradeMethod;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRecord {
  private PositionState positionState;
  private Date timestamp;
  private BigDecimal assetPrice;
  private BigDecimal cashBalance;
  private BigDecimal assetBalance;
  private TradeMethod tradeMethod;
  private BigDecimal rsi;
  private BigDecimal realizedGainLoss;
  private BigDecimal gain;
  private BigDecimal loss;
}
