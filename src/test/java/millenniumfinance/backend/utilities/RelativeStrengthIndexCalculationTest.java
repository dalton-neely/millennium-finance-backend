package millenniumfinance.backend.utilities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumbers;
import static millenniumfinance.backend.utilities.RelativeStrengthIndexCalculation.*;
import static org.junit.jupiter.api.Assertions.*;

class RelativeStrengthIndexCalculationTest {
    @Test
    public void testIsUpwardTrend(){
        assertTrue(isUpwardTrend(TEN, ONE));
        assertFalse(isUpwardTrend(ONE, TEN));
        assertFalse(isUpwardTrend(ONE, ONE));
    }

    @Test
    public void testIsDownwardTrend() {
        assertTrue(isDownwardTrend(ONE, TEN));
        assertFalse(isDownwardTrend(TEN, ONE));
        assertFalse(isDownwardTrend(TEN, TEN));
    }

    @Test
    public void testSummation(){
        List<BigDecimal> listToSum = fromNumbers(1D, 1D, 1D);
        assertEquals("3.0000000000", summation(listToSum).toString());
    }

    @Test
    public void testSimpleMovingAverage(){
        List<BigDecimal> prices = fromNumbers(1D, 1D, 1D);
        assertEquals("1.0000000000", simpleMovingAverage(prices).toString());
    }

    @Test
    public void testSmoothedMovingAverageFirst(){
        List<BigDecimal> prices = fromNumbers(60.4, 59.4, 59.8);
        assertEquals("59.8444444500", smoothedMovingAverageFirst(prices).toString());
    }

    @Test
    public void testSmoothedMovingAverageSecond(){
        List<BigDecimal> prices = fromNumbers(59.4, 59.8, 58.2);
        assertEquals("59.3333333400", smoothedMovingAverageSecond(prices, fromNumber(59.9)).toString());
    }

    @Test
    public void testFilterUpwardTrends(){
        List<BigDecimal> prices = fromNumbers(34.33, 33.56, 33.32, 34.56, 36.54, 32.45);
        assertEquals("[0E-10, 0E-10, 0E-10, 1.2400000000, 1.9800000000, 0E-10]", filterUpwardTrends(prices).toString());
    }

    @Test
    public void testFilterDownwardTrend(){
        List<BigDecimal> prices = fromNumbers(34.33, 33.56, 33.32, 34.56, 36.54, 32.45);
        assertEquals("[0E-10, 0.7700000000, 0.2400000000, 0E-10, 0E-10, 4.0900000000]", filterDownwardTrends(prices).toString());
    }

    @Test
    public void testRollingSma(){
        List<BigDecimal> numbers = fromNumbers(0D, 0D, 0D, 0D, 1.23, 1D, 0D, 0D, 0.89D);
        assertEquals("[0E-10, 0E-10, 0E-10, 0E-10, 0E-10, 0E-10, 0.3716666667, 0.3716666667, 0.5200000000]", rollingSma(numbers, 6).toString());
    }

    @Test
    public void testCalculateAverageGain(){
        List<BigDecimal> prices = fromNumbers(38.99, 36.77, 35.69, 33.43, 34.66, 35.66, 35.33, 34.33, 35.22);
        assertEquals("[0E-10, 0E-10, 0E-10, 0E-10, 0E-10, 0E-10, 0.3716666667, 0.3716666667, 0.5200000000]", calculateAverageGain(prices, 6).toString());
    }

    @Test
    public void testCalculateAverageLoss(){
        List<BigDecimal> prices = fromNumbers(38.99, 36.77, 35.69, 33.43, 34.66, 35.66, 35.33, 34.33, 35.22);
        assertEquals("[0E-10, 0E-10, 0E-10, 0E-10, 0E-10, 0E-10, 0.9816666667, 0.7783333333, 0.5983333333]", calculateAverageLoss(prices, 6).toString());
    }

    @Test
    public void testCalculateRs() {
        List<BigDecimal> averageGain = fromNumbers(0D, 0D, 0D, 0D, 0D, 0D, 0.3716666667, 0.3716666667, 0.5200000000);
        List<BigDecimal> averageLoss = fromNumbers(0D, 0D, 0D, 0D, 0D, 0D, 0.9816666667, 0.7783333333, 0.5983333333);
        assertEquals("[0E-10, 0E-10, 0E-10, 0E-10, 0E-10, 0E-10, 0.3786078099, 0.4775160600, 0.8690807800]", calculateRs(averageGain, averageLoss).toString());
    }

    @Test
    public void testCalculateRsi() {
        List<BigDecimal> prices = fromNumbers(38.99, 36.77, 35.69, 33.43, 34.66, 35.66, 35.33, 34.33, 35.22);
        assertEquals("[0E-10, 0E-10, 0E-10, 0E-10, 0E-10, 0E-10, 27.4630542000, 32.3188405800, 46.4977645300]", calculateRsi(prices, 6).toString());
    }
}