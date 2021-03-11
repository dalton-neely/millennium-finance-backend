package millenniumfinance.backend.utilities;

import org.junit.jupiter.api.Test;
import static millenniumfinance.backend.utilities.HMAC.calculate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HMACTest {
  
  @Test
  void testCalculate() {
    final String apiKey = "vmPUZE6mv9SD5VNHk4HlWFsOr6aKE2zvsw0MuIgwCIPy6utIco14y7Ju91duEh8A";
    final String secretKey = "NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j";
    final String input = "symbol=LTCBTC&side=BUY&type=LIMIT&timeInForce=GTC&quantity=1&price=0.1&recvWindow=5000&timestamp=1499827319559";
    final String expected = "c8db56825ae71d6d79447849e617115f4a920fa2acdcab2b053c4b2838bd6b71";
    final String actual = calculate(secretKey, input);
    
    assertEquals(expected, actual);
  }
}