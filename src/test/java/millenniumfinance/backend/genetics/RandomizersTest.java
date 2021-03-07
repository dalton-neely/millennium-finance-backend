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
    BigDecimal actual = percentageRandomizer();
    
    assertTrue(isLessThan(actual, fromNumber(1.51)));
    assertTrue(isGreaterThan(actual, fromNumber(0.49)));
  }
  
  @RepeatedTest(value = 1000)
  void testRsiRandomizer() {
    BigDecimal actual = rsiRandomizer();
    
    assertTrue(isLessThan(actual, fromNumber(100)));
    assertTrue(isGreaterThan(actual, fromNumber(0)));
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
    Integer max = 5;
    BigDecimal actual = stdRandomizer(max);
    
    assertTrue(isLessThanOrEqualTo(actual, fromNumber(max)));
    assertTrue(isGreaterThanOrEqualTo(actual, fromNumber(0)));
  }
}