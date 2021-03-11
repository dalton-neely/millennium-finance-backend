package millenniumfinance.backend.classes.binance.responses;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.classes.binance.enumerations.OrderType;
import millenniumfinance.backend.classes.binance.responses.filters.SymbolFilterResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SymbolResponse {
  private String symbol;
  private String status;
  private String baseAsset;
  private int baseAssetPrecision;
  private String quoteAsset;
  private int quotePrecision;
  private int quoteAssetPrecision;
  private int baseCommissionPrecision;
  private int quoteCommissionPrecision;
  private List<OrderType> orderTypes;
  private boolean icebergAllowed;
  private boolean ocoAllowed;
  private boolean quoteOrderQtyMarketAllowed;
  private boolean isSpotTradingAllowed;
  private boolean isMarginTradingAllowed;
  private List<SymbolFilterResponse> filters;
}
