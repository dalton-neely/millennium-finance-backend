package millenniumfinance.backend.data.v1.classes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.BigDecimal.valueOf;
import static java.util.stream.Collectors.toList;
import static millenniumfinance.backend.data.v1.structures.DataTable.TWELVE_PERIODS;
import static millenniumfinance.backend.utilities.FinancialCalculations.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DataRowContainerTest {
//    @Test
//    public void calculateSimpleMovingAverageTest() {
//        List<Double> closePrices = new ArrayList<>();
//
//        closePrices.add(789.234D);
//        closePrices.add(5345.243D);
//        closePrices.add(879.324D);
//        closePrices.add(2342.34D);
//        closePrices.add(989.234D);
//        closePrices.add(2342.3D);
//        closePrices.add(23423.4D);
//        closePrices.add(4534.8797D);
//        closePrices.add(89834.234D);
//        closePrices.add(789.234D);
//        closePrices.add(3749.234D);
//        closePrices.add(3478.234D);
//
//        final BigDecimal expectedSimpleMovingAverage = valueOf(11541.40756D);
//        final BigDecimal actualSimpleMovingAverage = calculateSimpleMovingAverage(closePrices.stream().map(BigDecimal::valueOf).collect(toList()), TWELVE_PERIODS);
//
//        assertEquals(12, closePrices.size());
//        assertEquals(expectedSimpleMovingAverage, actualSimpleMovingAverage);
//    }

//    @Test
//    public void calculateExponentialMovingAverageTest() {
//        BigDecimal expectedExponentialMovingAverage = valueOf(55.096153849D);
//        BigDecimal currentClosePrice = valueOf(45.56D);
//        BigDecimal previousPeriodExponentialMovingAverage = valueOf(56.83D);
//
//        BigDecimal actualExponentialMovingAverage = calculateExponentialMovingAverage(
//                currentClosePrice,
//                previousPeriodExponentialMovingAverage,
//                valueOf(2D / 13D)
//        );
//
//        assertEquals(expectedExponentialMovingAverage, actualExponentialMovingAverage);
//    }
}