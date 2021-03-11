package millenniumfinance.backend.classes.binance.responses.filters.exchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.classes.binance.responses.filters.ExchangeFilterResponse;

/**
 * https://github.com/binance-us/binance-official-api-docs/blob/master/rest-api.md#exchange_max_num_algo_orders
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaxNumAlgoOrdersExchangeFilterResponse extends ExchangeFilterResponse {
  private int maxNumAlgoOrders;
}
