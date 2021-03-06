package millenniumfinance.backend.clients;

import millenniumfinance.backend.data.v1.structures.CalculateDataInput;
import millenniumfinance.backend.data.v2.structures.DataInput;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public final class BinanceClient {
  public static final String SYMBOL = "symbol";
  public static final String INTERVAL = "interval";
  public static final String LIMIT = "limit";
  public static final String BINANCE_API_BASE_URL = "https://api.binance.us";
  public static final String BINANCE_API_VERSION = "api/v3";
  public static final String K_LINES_PATH = "klines";
  private final RestTemplate restTemplate;
  
  
  public BinanceClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }
  
  public String getCandlestickData(CalculateDataInput input) {
    return restTemplate
        .getForEntity(
            BINANCE_API_BASE_URL + "/" +
                BINANCE_API_VERSION + "/" +
                K_LINES_PATH + "?" +
                SYMBOL + "=" + input.getSymbol() + "&" +
                INTERVAL + "=" + input.getInterval() + "&" +
                LIMIT + "=" + input.getLimit(),
            String.class)
        .getBody();
  }
  
  public String getCandlestickData(DataInput input) {
    return restTemplate
        .getForEntity(
            BINANCE_API_BASE_URL + "/" +
                BINANCE_API_VERSION + "/" +
                K_LINES_PATH + "?" +
                SYMBOL + "=" + input.getSymbol() + "&" +
                INTERVAL + "=" + input.getInterval() + "&" +
                LIMIT + "=" + input.getLimit(),
            String.class)
        .getBody();
  }
}
