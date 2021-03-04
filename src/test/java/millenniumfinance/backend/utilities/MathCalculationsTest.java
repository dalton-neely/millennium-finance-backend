package millenniumfinance.backend.utilities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.math.BigDecimal.valueOf;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static millenniumfinance.backend.utilities.MathCalculations.standardDeviation;
import static org.junit.jupiter.api.Assertions.*;

class MathCalculationsTest {
    @Test
    public void standardDeviationTest(){
        BigDecimal expectedStandardDeviation = valueOf(8.935323161D);
        List<BigDecimal> inputNumbers = of(10D, 10D, 22D, 34D, 21D).map(BigDecimal::valueOf).collect(toList());
        BigDecimal actualStandardDeviation = standardDeviation(inputNumbers);

        assertEquals(expectedStandardDeviation, actualStandardDeviation);
    }
}