package millenniumfinance.backend.classes.binance.responses.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAcknowledgementResponse {
  private String symbol;
  private int orderId;
  private int orderListId;
  private String clientOrderId;
  private long transactionTime;
}
