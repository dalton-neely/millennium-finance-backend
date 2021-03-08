package millenniumfinance.backend.genetics;

import java.math.BigDecimal;
import java.util.Random;
import millenniumfinance.backend.data.v2.structures.Parameter;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.addition;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;

public final class Randomizers {
  public static final Parameter<BigDecimal> OFF_GENE = new Parameter<>(null, false);
  private static final Random random = new Random();
  
  public static Integer periodRandomizer(Integer min, Integer max) {
    return random.nextInt(min) + (max - min);
  }
  
  public static BigDecimal stdRandomizer(Double min, Double max) {
    double delta = max - min;
    return addition(fromNumber(min), fromNumber(random.nextDouble() * delta));
  }
  
  public static BigDecimal percentageRandomizer(Double min, Double max) {
    double delta = max - min;
    // Values from 50% to 150%
    return addition(fromNumber(min), fromNumber(random.nextDouble() * delta));
  }
  
  public static Boolean activeRandomizer() {
    return random.nextBoolean();
  }
  
  public static BigDecimal rsiRandomizer(Double minRsi, Double maxRsi) {
    double delta = maxRsi - minRsi;
    return addition(fromNumber(minRsi), fromNumber(random.nextDouble() * delta));
  }
  
  public static BigDecimal amountRandomizer(Double maxAmount) {
    return fromNumber(random.nextDouble() * maxAmount);
  }
  
  public static <T> T chooseRandom(T firstChoice, T secondChoice) {
    if (random.nextBoolean()) {
      return firstChoice;
    } else {
      return secondChoice;
    }
  }
}
