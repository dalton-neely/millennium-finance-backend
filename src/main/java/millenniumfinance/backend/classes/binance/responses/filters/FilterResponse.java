package millenniumfinance.backend.classes.binance.responses.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.classes.binance.enumerations.FilterType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterResponse {
  private FilterType filterType;
}
