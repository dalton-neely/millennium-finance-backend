package millenniumfinance.backend.classes.binance.responses.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FillResponse {
  private String price;
  private String qty;
  private String commission;
  private String commissionAsset;
}
