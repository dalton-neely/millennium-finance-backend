package millenniumfinance.backend.utilities;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import static java.math.RoundingMode.HALF_UP;
import static millenniumfinance.backend.utilities.FinancialCalculations.summation;

public final class MathCalculations {
  public static MathContext mathContext = new MathContext(10, HALF_UP);
  
  public static BigDecimal standardDeviation(List<BigDecimal> numbers) {
    BigDecimal sumOfNumbers = summation(numbers);
    BigDecimal numberOfElements = new BigDecimal(numbers.size());
    BigDecimal meanOfNumbers = sumOfNumbers.divide(numberOfElements, mathContext);
    List<BigDecimal> differences = squaredDifference(numbers, meanOfNumbers);
    BigDecimal meanOfDifferences = summation(differences).divide(numberOfElements, mathContext);
    return meanOfDifferences.sqrt(mathContext);
  }
  
  public static List<BigDecimal> squaredDifference(List<BigDecimal> numbers, BigDecimal mean) {
    List<BigDecimal> squaredDifferences = new ArrayList<>();
    for (BigDecimal number : numbers) {
      squaredDifferences.add(number.subtract(mean).pow(2, mathContext));
    }
    return squaredDifferences;
  }
}
