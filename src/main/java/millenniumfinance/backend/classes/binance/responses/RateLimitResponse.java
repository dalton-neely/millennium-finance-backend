package millenniumfinance.backend.classes.binance.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.classes.binance.enumerations.Interval;
import millenniumfinance.backend.classes.binance.enumerations.RateLimitType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateLimitResponse {
  private RateLimitType rateLimitType;
  private Interval interval;
  private int intervalNum;
  private int limit;
}
