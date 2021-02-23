package millenniumfinance.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BackendControllerV1 {
    private final String API_BASE_URL = "https://api.binance.us";
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping(path = "candlestick-data")
    public String getCandleStickData(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(API_BASE_URL + "/api/v3/klines?symbol=BTCUSD&interval=1m&limit=5000", String.class);
        CandlestickDataSet dataSet = new CandlestickDataSet(responseEntity.getBody());
        dataSet.convert();
        SecuritiesIndicatorContainer securitiesIndicatorContainer = new SecuritiesIndicatorContainer(dataSet.getData());
        return securitiesIndicatorContainer.toStringFormatted();
//        return dataSet.getData().toString();
    }
}
