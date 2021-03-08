package millenniumfinance.backend.genetics;

import java.math.BigDecimal;
import org.junit.jupiter.api.RepeatedTest;
import static millenniumfinance.backend.genetics.Randomizers.amountRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.percentageRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.periodRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.rsiRandomizer;
import static millenniumfinance.backend.genetics.Randomizers.stdRandomizer;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.isGreaterThan;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.isGreaterThanOrEqualTo;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.isLessThan;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.isLessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomizersTest {
  
  @RepeatedTest(value = 1000)
  void testPercentageRandomizer() {
    double min = 0.80;
    double max = 1.20;
    BigDecimal actual = percentageRandomizer(min, max);
    
    assertTrue(isLessThan(actual, fromNumber(max)));
    assertTrue(isGreaterThan(actual, fromNumber(min)));
  }
  
  @RepeatedTest(value = 1000)
  void testRsiRandomizer() {
    Double minRsi = 0.00;
    Double maxRsi = 30.00;
    BigDecimal actual = rsiRandomizer(minRsi, maxRsi);
    
    assertTrue(isLessThanOrEqualTo(actual, fromNumber(maxRsi)));
    assertTrue(isGreaterThanOrEqualTo(actual, fromNumber(minRsi)));
  }
  
  @RepeatedTest(value = 1000)
  void testAmountRandomizer() {
    Double maxAmount = 50.05;
    BigDecimal actual = amountRandomizer(maxAmount);
    
    assertTrue(isGreaterThan(actual, fromNumber(0)));
    assertTrue(isLessThan(actual, fromNumber(maxAmount)));
  }
  
  @RepeatedTest(value = 1000)
  void testPeriodRandomizer() {
    Integer min = 5;
    Integer max = 20;
    Integer actual = periodRandomizer(min, max);
    
    assertTrue(actual < max);
    assertTrue(actual > min);
  }
  
  @RepeatedTest(value = 1000)
  void testStdRandomizer() {
    double min = 1.0;
    double max = 2.5;
    BigDecimal actual = stdRandomizer(min, max);
    
    assertTrue(isLessThanOrEqualTo(actual, fromNumber(max)));
    assertTrue(isGreaterThanOrEqualTo(actual, fromNumber(min)));
  }
}