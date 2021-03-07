package millenniumfinance.backend.genetics;

import java.math.BigDecimal;
import java.util.Random;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;

public final class Randomizers {
  private static final Random random = new Random();
  
  public static BigDecimal percentageRandomizer() {
    // Values from 50% to 150%
    return fromNumber(random.nextDouble() + 0.5);
  }
  
  public static Boolean activeRandomizer() {
    return random.nextBoolean();
  }
  
  public static BigDecimal rsiRandomizer() {
    return fromNumber(random.nextDouble() * 100);
  }
  
  public static BigDecimal amountRandomizer(Double maxAmount) {
    return fromNumber(random.nextDouble() * maxAmount);
  }
}
