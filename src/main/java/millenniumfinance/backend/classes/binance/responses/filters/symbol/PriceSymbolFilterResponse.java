package millenniumfinance.backend.classes.binance.responses.filters.symbol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.classes.binance.responses.filters.FilterResponse;

/**
 * https://github.com/binance-us/binance-official-api-docs/blob/master/rest-api.md#price_filter
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceSymbolFilterResponse extends FilterResponse {
  private String minPrice;
  private String maxPrice;
  private String tickSize;
}
