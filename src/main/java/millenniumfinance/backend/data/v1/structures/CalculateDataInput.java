package millenniumfinance.backend.data.v1.structures;

import static millenniumfinance.backend.Constants.INTERVAL_ONE_MINUTE;
import static millenniumfinance.backend.Constants.LIMIT_1;
import static millenniumfinance.backend.Constants.SYMBOL_BITCOIN;

public class CalculateDataInput {
  private String symbol;
  private String interval;
  private String limit;
  
  public CalculateDataInput() {
    this.symbol = SYMBOL_BITCOIN;
    this.interval = INTERVAL_ONE_MINUTE;
    this.limit = LIMIT_1;
  }
  
  public String getSymbol() {
    return symbol;
  }
  
  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }
  
  public String getInterval() {
    return interval;
  }
  
  public void setInterval(String interval) {
    this.interval = interval;
  }
  
  public String getLimit() {
    return limit;
  }
  
  public void setLimit(String limit) {
    this.limit = limit;
  }
  
  @Override
  public String toString() {
    return "CalculateDataInput{" +
        "symbol='" + symbol + '\'' +
        ", interval='" + interval + '\'' +
        ", limit='" + limit + '\'' +
        '}';
  }
}
