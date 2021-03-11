package millenniumfinance.backend.clients;

import com.binance.api.client.BinanceApiRestClient;
import millenniumfinance.backend.classes.binance.enumerations.Side;
import millenniumfinance.backend.classes.binance.enumerations.Symbol;
import millenniumfinance.backend.classes.binance.enumerations.Type;
import millenniumfinance.backend.classes.binance.responses.ExchangeInfoResponse;
import millenniumfinance.backend.classes.binance.responses.ServerTime;
import millenniumfinance.backend.configuration.ApiKeysConfiguration;
import millenniumfinance.backend.data.v1.structures.CalculateDataInput;
import millenniumfinance.backend.data.v2.structures.DataInput;
import millenniumfinance.backend.utilities.HMAC;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import static java.util.Objects.requireNonNull;
import static millenniumfinance.backend.classes.binance.enumerations.Side.BUY;
import static millenniumfinance.backend.classes.binance.enumerations.Symbol.BTCUSD;
import static millenniumfinance.backend.classes.binance.enumerations.Symbol.MATICUSD;
import static millenniumfinance.backend.classes.binance.enumerations.Type.MARKET;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.POST;

@Service
public final class BinanceClient {
  public static final String SYMBOL = "symbol";
  public static final String INTERVAL = "interval";
  public static final String LIMIT = "limit";
  public static final String BINANCE_API_BASE_URL = "https://api.binance.us";
  public static final String BINANCE_API_VERSION = "api/v3";
  public static final String FORWARD_SLASH = "/";
  public static final String VERSION_3_BASE = BINANCE_API_BASE_URL + FORWARD_SLASH + BINANCE_API_VERSION + FORWARD_SLASH;
  public static final String K_LINES_PATH = "klines";
  public static final String API_KEY_HEADER = "X-MBX-APIKEY";
  public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
  private final RestTemplate restTemplate;
  private final ApiKeysConfiguration apiKeysConfiguration;
  private final BinanceApiRestClient binanceApiRestClient;
  
  
  public BinanceClient(
      RestTemplate restTemplate,
      ApiKeysConfiguration apiKeysConfiguration,
      BinanceApiRestClient binanceApiRestClient
  ) {
    this.restTemplate = restTemplate;
    this.apiKeysConfiguration = apiKeysConfiguration;
    this.binanceApiRestClient = binanceApiRestClient;
  }
  
  public boolean canConnectToServer() {
    ResponseEntity<String> response = restTemplate.getForEntity(VERSION_3_BASE + "ping", String.class);
    return HttpStatus.OK.value() == response.getStatusCodeValue();
  }
  
  public ExchangeInfoResponse getExchangeInfo() {
    return restTemplate.getForEntity(VERSION_3_BASE + "exchangeInfo", ExchangeInfoResponse.class).getBody();
  }
  
  public String testOrder2() {
    return constructBinanceApiRequestPost("order/test", orderBuilder(BTCUSD, BUY, MARKET, "1"));
  }
  
  private String constructBinanceApiRequestPost(String path, String formData) {
    String timestamp = getServerTime();
    StringBuilder modifiedBody = new StringBuilder(formData);
    modifiedBody.append("&timestamp=")
        .append(timestamp);
    String signature = HMAC.calculate(apiKeysConfiguration.getSecretKey(), modifiedBody.toString());
    modifiedBody.append("&signature=")
        .append(signature);
    HttpHeaders headers = new HttpHeaders();
    headers.add(API_KEY_HEADER, apiKeysConfiguration.getApiKey());
    headers.add(CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED);
    HttpEntity<String> entity = new HttpEntity<>(modifiedBody.toString(), headers);
    String url = BINANCE_API_BASE_URL + FORWARD_SLASH +
        BINANCE_API_VERSION +
        FORWARD_SLASH +
        path;
    return restTemplate.exchange(url, POST, entity, String.class).getBody();
  }
  
  private String orderBuilder(Symbol symbol, Side side, Type type, String quantity) {
    return "symbol=" + symbol +
        "&side=" + side +
        "&quantity=" + quantity +
        "&type=" + type;
  }
  
  public String getServerTime() {
    return requireNonNull(restTemplate
        .getForEntity(BINANCE_API_BASE_URL + FORWARD_SLASH + BINANCE_API_VERSION + FORWARD_SLASH + "time", ServerTime.class)
        .getBody())
        .getServerTime();
  }
  
  public String testMaticUsdOrder() {
    return constructBinanceApiRequestPost("order", orderBuilder(MATICUSD, BUY, MARKET, "35"));
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
