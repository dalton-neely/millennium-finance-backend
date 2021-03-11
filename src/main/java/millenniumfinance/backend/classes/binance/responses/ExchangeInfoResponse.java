package millenniumfinance.backend.classes.binance.responses;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.classes.binance.enumerations.SymbolType;
import millenniumfinance.backend.classes.binance.responses.filters.ExchangeFilterResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeInfoResponse {
  private String timezone;
  private long serverTime;
  private List<RateLimitResponse> rateLimits;
  private List<ExchangeFilterResponse> exchangeFilters;
  private List<SymbolResponse> symbols;
  private SymbolType permissions;
}
