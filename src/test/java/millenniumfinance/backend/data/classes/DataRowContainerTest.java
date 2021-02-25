package millenniumfinance.backend.data.classes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static millenniumfinance.backend.data.classes.DataTableContainer.TWELVE_PERIODS;
import static millenniumfinance.backend.utilities.FinancialCalculations.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DataRowContainerTest {
    @Test
    public void calculateSimpleMovingAverageTest() {
        List<Double> closePrices = new ArrayList<>();

        closePrices.add(789.234D);
        closePrices.add(5345.243D);
        closePrices.add(879.324D);
        closePrices.add(2342.34D);
        closePrices.add(989.234D);
        closePrices.add(2342.3D);
        closePrices.add(23423.4D);
        closePrices.add(4534.8797D);
        closePrices.add(89834.234D);
        closePrices.add(789.234D);
        closePrices.add(3749.234D);
        closePrices.add(3478.234D);

        final Double expectedSimpleMovingAverage = 11541.407558333332D;
        final Double actualSimpleMovingAverage = calculateSimpleMovingAverage(closePrices, TWELVE_PERIODS);

        assertEquals(12, closePrices.size());
        assertEquals(expectedSimpleMovingAverage, actualSimpleMovingAverage);
    }

    @Test
    public void calculateExponentialMovingAverageTest() {
        Double expectedExponentialMovingAverage = 55.09615384615385D;
        Double currentClosePrice = 45.56D;
        Double previousPeriodExponentialMovingAverage = 56.83D;

        Double actualExponentialMovingAverage = calculateExponentialMovingAverage(
                currentClosePrice,
                previousPeriodExponentialMovingAverage,
                2D / 13D
        );

        assertEquals(expectedExponentialMovingAverage, actualExponentialMovingAverage);
    }
}