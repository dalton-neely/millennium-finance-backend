package millenniumfinance.backend.services;

import millenniumfinance.backend.clients.BinanceClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TradingBot {
  private final BinanceClient binanceClient;
  
  public TradingBot(BinanceClient binanceClient) {
    this.binanceClient = binanceClient;
  }
  
  @Scheduled(fixedRate = 60_000)
  public void trade() {
//    System.out.println(binanceClient.testMaticUsdOrder());
    System.out.println(binanceClient.canConnectToServer());
  }
}
