package millenniumfinance.backend.classes.binance.responses.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.classes.binance.enumerations.Side;
import millenniumfinance.backend.classes.binance.enumerations.SymbolStatus;
import millenniumfinance.backend.classes.binance.enumerations.TimeInForce;
import millenniumfinance.backend.classes.binance.enumerations.Type;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResultResponse extends OrderAcknowledgementResponse {
  private String price;
  private String origQty;
  private String executedQty;
  private String cummulativeQuoteQty;
  private SymbolStatus status;
  private TimeInForce timeInForce;
  private Type type;
  private Side side;
}
