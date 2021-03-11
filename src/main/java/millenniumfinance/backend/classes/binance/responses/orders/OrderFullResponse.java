package millenniumfinance.backend.classes.binance.responses.orders;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class OrderFullResponse extends OrderResultResponse {
  private List<FillResponse> fills;
}
