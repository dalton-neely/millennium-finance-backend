package millenniumfinance.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import millenniumfinance.backend.data.classes.DataTableContainer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static millenniumfinance.backend.data.classes.DataTableContainer.fromBinanceApiString;

@RestController
public class BackendControllerV1 {
    public static final String SYMBOL = "symbol";
    public static final String INTERVAL = "interval";
    public static final String LIMIT = "limit";
    public static final String SYMBOL_BITCOIN = "BTCUSD";
    public static final String INTERVAL_ONE_MINUTE = "1m";
    public static final String LIMIT_1000 = "1000";
    public static final String LIMIT_50 = "50";
    public static final String INTERVAL_FIFTEEN_MINUTE = "15m";
    public static final String API_BASE_URL = "https://api.binance.us";
    public static final String API_VERSION = "api/v3";
    public static final String K_LINES_PATH = "klines";
    public static final RestTemplate restTemplate = new RestTemplate();
    public static final ObjectMapper objectMapper = new ObjectMapper();

    private String getCandlestickData(String symbol, String interval, String limit) {
        return restTemplate
                .getForEntity(
                        API_BASE_URL + "/" +
                                API_VERSION + "/" +
                                K_LINES_PATH + "?" +
                                SYMBOL + "=" + symbol + "&" +
                                INTERVAL + "=" + interval + "&" +
                                LIMIT + "=" + limit,
                        String.class)
                .getBody();
    }

    @GetMapping(path = "candlestick-data")
    public String getCandleStickData3() {
        CandlestickDataSet dataSet =
                new CandlestickDataSet(getCandlestickData(SYMBOL_BITCOIN, INTERVAL_ONE_MINUTE, LIMIT_1000));
        dataSet.convert();
        SecuritiesIndicatorContainer securitiesIndicatorContainer = new SecuritiesIndicatorContainer(dataSet.getData());
        return securitiesIndicatorContainer.toStringFormatted();
    }

    @GetMapping(path = "candlestick-data-2")
    public String getCandleStickData2() throws JsonProcessingException {
        DataTableContainer dataTableContainer = fromBinanceApiString(
                getCandlestickData(SYMBOL_BITCOIN, INTERVAL_ONE_MINUTE, LIMIT_50),
                getCandlestickData(SYMBOL_BITCOIN, INTERVAL_FIFTEEN_MINUTE, LIMIT_50)
        );
        return objectMapper.writeValueAsString(dataTableContainer.getDataTable().getDataRows());
    }
}
