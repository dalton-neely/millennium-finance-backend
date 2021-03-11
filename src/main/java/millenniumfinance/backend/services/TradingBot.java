package millenniumfinance.backend.services;

import millenniumfinance.backend.clients.BinanceClient;
import millenniumfinance.backend.configuration.ApiKeysConfiguration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TradingBot {
  private final ApiKeysConfiguration apiKeysConfiguration;
  private final BinanceClient binanceClient;
  
  public TradingBot(ApiKeysConfiguration apiKeysConfiguration, BinanceClient binanceClient) {
    this.apiKeysConfiguration = apiKeysConfiguration;
    this.binanceClient = binanceClient;
  }
  
  @Scheduled(fixedRate = 60_000)
  public void trade() {
//    Date date = new Date();
//    System.out.println(binanceClient.getServerTime());

//    System.out.println(binanceClient.testOrder().getBody());
//    System.out.println(binanceClient.testClient().getBody());
//    System.out.println(binanceClient.testMarketOrder().getBody());
    System.out.println(binanceClient.testOrder2());

//    String query = "symbol=LTCBTC&side=BUY&type=LIMIT&timeInForce=GTC&quantity=1&price=0.1&recvWindow=5000&timestamp=1499827319559";
//    byte[] hmac = HMAC.calcHmacSha256("NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j".getBytes(StandardCharsets.UTF_8), query.getBytes(StandardCharsets.UTF_8));
//    String signature = String.format("Hex: %032x", new BigInteger(1, hmac));
//    System.out.println(signature);
//    System.out.println(binanceClient.order().getBody());
  }
}
