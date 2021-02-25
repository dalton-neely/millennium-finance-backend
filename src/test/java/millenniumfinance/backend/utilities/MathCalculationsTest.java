package millenniumfinance.backend.utilities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static millenniumfinance.backend.utilities.MathCalculations.standardDeviation;
import static org.junit.jupiter.api.Assertions.*;

class MathCalculationsTest {
    @Test
    public void standardDeviationTest(){
        Double expectedStandardDeviation = 8.541662601625049D;
        List<Double> inputNumbers = new ArrayList<>();
        inputNumbers.add(10D);
        inputNumbers.add(12D);
        inputNumbers.add(22D);
        inputNumbers.add(34D);
        inputNumbers.add(21D);
        Double actualStandardDeviation = standardDeviation(inputNumbers);

        assertEquals(expectedStandardDeviation, actualStandardDeviation);
    }
}