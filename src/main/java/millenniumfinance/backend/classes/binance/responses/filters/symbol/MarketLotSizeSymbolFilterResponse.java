package millenniumfinance.backend.classes.binance.responses.filters.symbol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.classes.binance.responses.filters.SymbolFilterResponse;

/**
 * https://github.com/binance-us/binance-official-api-docs/blob/master/rest-api.md#market_lot_size
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketLotSizeSymbolFilterResponse extends SymbolFilterResponse {
  private String minQty;
  private String maxQty;
  private String stepSize;
}
