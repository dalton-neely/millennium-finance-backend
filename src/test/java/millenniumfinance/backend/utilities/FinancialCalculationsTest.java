package millenniumfinance.backend.utilities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static java.math.BigDecimal.valueOf;
import static java.util.stream.Stream.*;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumbers;
import static millenniumfinance.backend.utilities.FinancialCalculations.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FinancialCalculationsTest {

    private static Stream<Arguments> summationTestInputs(){
        return of(
                Arguments.of(fromNumbers(5.80, 7.56, 2.45, 6.89, 2.23), fromNumber(24.93)),
                Arguments.of(fromNumbers(-3D, 0D, 4D, 5D, 3.45), fromNumber(9.45)),
                Arguments.of(fromNumbers(0.0, 0.0, 0.6, 0.0, 0.72, 0.50, 0.27, 0.33, 0.42, 0.24, 0.0, 0.14, 0.0, 0.67, 0.0), fromNumber(3.89))
        );
    }

    @ParameterizedTest
    @MethodSource("summationTestInputs")
    void summationTest(List<BigDecimal> input, BigDecimal expected) {
        logger.debug("Start");
        assertEquals(expected, summation(input));
    }

    private static Stream<Arguments> calculateSimpleMovingAverageTestInputs(){
        return of(
                Arguments.of(fromNumbers(5.80, 7.56, 2.45, 6.89, 2.23), fromNumber(4.986)),
                Arguments.of(fromNumbers(-3D, 0D, 4D, 5D, 3.45), fromNumber(1.89))
        );
    }

    @ParameterizedTest
    @MethodSource("calculateSimpleMovingAverageTestInputs")
    void calculateSimpleMovingAverageTest(List<BigDecimal> input, BigDecimal expected) {
        assertEquals(expected, calculateSimpleMovingAverage(input, 5));
    }

    private static Stream<Arguments> calculateWeightedMultiplierTestInputs(){
        return of(
                Arguments.of(13, fromNumber(0.1428571429)),
                Arguments.of(10, fromNumber(0.1818181818))
        );
    }

    @ParameterizedTest
    @MethodSource("calculateWeightedMultiplierTestInputs")
    void calculateWeightedMultiplierTest(Integer periods, BigDecimal expected) {
        assertEquals(expected, calculateWeightedMultiplier(periods));
    }

    @Test
    void calculateExponentialMovingAverageTest() {
        final BigDecimal currentClose = fromNumber(1.5560);
        final BigDecimal previousEma = fromNumber(1.5558);
        final BigDecimal weightedMultiplier = calculateWeightedMultiplier(4);
        final BigDecimal expected = fromNumber(1.55588);

        assertEquals(expected, calculateExponentialMovingAverage(currentClose, previousEma, weightedMultiplier));
    }

    @Test
    void calculateAllExponentialMovingAveragesTest() {
        List<BigDecimal> expectedEMA = fromNumbers(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 22.221, 22.264545457, 22.193636361);
        List<BigDecimal> closingPrices = fromNumbers(22.27, 22.19, 22.08, 22.17, 22.18, 22.13, 22.23, 22.43, 22.24, 22.29, 22.15, 22.39);
        List<BigDecimal> actualEMA = calculateAllExponentialMovingAverages(closingPrices, 10, 0);

        assertEquals(expectedEMA, actualEMA);
    }

    @Test
    void calculateAllMovingAverageConvergenceDivergenceTest() {
        List<BigDecimal> fiveEma = calculateAllExponentialMovingAverages(fromNumbers(3.34, 45.4, 32.3, 43.3, 45.3, 43.2, 12.3, 23.2, 76.32, 45.78, 4.56, 4.34, 0.34), 5, 0);
        List<BigDecimal> tenEma = calculateAllExponentialMovingAverages(fromNumbers(3.34, 45.4, 32.3, 43.3, 45.3, 43.2, 12.3, 23.2, 76.32, 45.78, 4.56, 4.34, 0.34), 10, 0);
        List<BigDecimal> expectedMACD = fromNumbers(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 29.0960000000, -6.2454545500, -0.0333333330, -0.6060606070);
        List<BigDecimal> actualMACD = calculateAllMovingAverageConvergenceDivergence(fiveEma, tenEma);

        assertEquals(expectedMACD, actualMACD);
    }

    @Test
    void calculateAllRollingMeanTest() {
        List<BigDecimal> numbers = fromNumbers(4D, 6D, 3D, 2D, 3D, 4D, 6D);
        int windowSize = 4;
        List<BigDecimal> expectedMeans = fromNumbers(0.0, 0.0, 0.0, 3.75, 3.5, 3.0, 3.75);
        List<BigDecimal> actualMeans = calculateAllRollingMean(numbers, windowSize);

        assertEquals(expectedMeans, actualMeans);
    }

    @Test
    void calculateMeanInWindowTest() {
        List<BigDecimal> window = fromNumbers(4D, 6D, 3D, 2D, 3D, 4D, 6D);
        int start = 1;
        int end = 3;
        BigDecimal expectedMean = fromNumber(4.5);
        BigDecimal actualMean = calculateMeanInWindow(window, start, end);

        assertEquals(expectedMean, actualMean);
    }

    @Test
    void calculateAllRollingStandardDeviationTest() {
        List<BigDecimal> expectedStds = fromNumbers(0.0, 0.0, 154.7983437, 178.8979287, 198.4563204, 192.9934532);
        List<BigDecimal> window = fromNumbers(3.45, 345.3, 32.3, 454.4, 34.53, 56.34);
        List<BigDecimal> actualStds = calculateAllRollingStandardDeviation(window, 3);

        assertEquals(expectedStds, actualStds);
    }

    @Test
    void calculateStandardDeviationInWindowTest() {
        BigDecimal expectedStd = fromNumber(154.7983437);
        List<BigDecimal> window = fromNumbers(3.45, 345.3, 32.3, 454.4);
        BigDecimal actualStd = calculateStandardDeviationInWindow(window, 0, 3);

        assertEquals(expectedStd, actualStd);
    }

    @Test
    void calculateAllUpperBollingerBandsTest() {
        List<BigDecimal> stds = fromNumbers(0.0, 0.0, 0.0, 124.90753750274641, 147.50254936356185, 144.30558079991224, 1859.0490585427674);
        List<BigDecimal> movingAverages = fromNumbers(0.0, 0.0, 0.0, 153.555, 160.82000000000002, 214.352, 1789.65);
        List<BigDecimal> expectedValues = fromNumbers(0.0, 0.0, 0.0, 434.5969594, 492.7007362, 539.0395568, 5972.510383);
        List<BigDecimal> actualValues = calculateAllUpperBollingerBands(movingAverages, stds, fromNumber(2.25));

        assertEquals(expectedValues, actualValues);
    }

    @Test
    void calculateUpperBollingerBandTest() {
        BigDecimal expectedValue = fromNumber(27.8080);
        BigDecimal actualValue = calculateUpperBollingerBand(fromNumber(26.6), fromNumber(0.604), fromNumber(2.0));

        assertEquals(expectedValue, actualValue);
    }

    @Test
    void calculateAllLowerBollingerBandsTest() {
        BigDecimal expectedValue = fromNumber(25.3920);
        BigDecimal actualValue = calculateLowerBollingerBand(fromNumber(26.6), fromNumber(0.604), fromNumber(2.0));

        assertEquals(expectedValue, actualValue);
    }

    @Test
    void calculateLowerBollingerBandTest() {
        List<BigDecimal> stds = fromNumbers(0.0, 0.0, 0.0, 124.90753750274641, 147.50254936356185, 144.30558079991224, 1859.0490585427674);
        List<BigDecimal> movingAverages = fromNumbers(0.0, 0.0, 0.0, 153.555, 160.82000000000002, 214.352, 1789.65);
        List<BigDecimal> expectedValues = fromNumbers(0.0, 0.0, 0.0, -127.4869594, -171.0607362, -110.3355568, -2393.210383);
        List<BigDecimal> actualValues = calculateAllLowerBollingerBands(movingAverages, stds, valueOf(2.25));

        assertEquals(expectedValues, actualValues);
    }

//    @Test
//    void calculateAllRelativeStrengthIndexTest() {
//        List<BigDecimal> closingPrices = fromNumbers(
//                34.33, 45.33, 45.66, 44.66, 33.43, 45.69, 66.77, 88.99
//        );
//        List<BigDecimal> expectedRsi = fromNumbers(0.0, 0.0, 0.0, 0.0, 0.0, 21.49, 17.95);
//        List<BigDecimal> actualRsi = calculateAllRelativeStrengthIndex(closingPrices, 6);
//
//        assertEquals(expectedRsi, actualRsi);
//    }

//    @Test
//    void calculateAverageGainsTest(){
//        List<BigDecimal> expected = fromNumbers(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.2392857143, 0.2221938776);
//        List<BigDecimal> input = fromNumbers(0.0, 0.0, 0.06, 0.0, 0.72, 0.50, 0.27, 0.33, 0.42, 0.24, 0.0, 0.14, 0.0, 0.67, 0.0, 0.0);
//        List<BigDecimal> actual = calculateAverageGains(input, 14);
//
//        assertEquals(expected, actual);
//    }

    @Test
    void calculateGainOrLossTest() {
        BigDecimal expectedGainOrLoss = fromNumber(0.0500000000);
        BigDecimal purchasePrice = fromNumber(14500.00);
        BigDecimal sellPrice = fromNumber(15225.00);
        BigDecimal actualGainOrLoss = gainLoss(purchasePrice, sellPrice);

        assertEquals(expectedGainOrLoss, actualGainOrLoss);
    }

    @Test
    void calculateAllGainOrLossTest() {
        List<BigDecimal> purchasePrices = fromNumbers(34.33, 223.334, 64.345, 45.345, 345.3);
        List<BigDecimal> soldPrice = fromNumbers(643.33, 234.33, 234.323, 24.22, 123.33);
        List<BigDecimal> expected = fromNumbers(17.73958637, 0.04923567392, 2.641666019, -0.4658727533, -0.6428323197);
        List<BigDecimal> actual = calculateAllGainOrLoss(purchasePrices, soldPrice);

        assertEquals(expected, actual);
    }

    @Test
    void calculateRsiStepOneTest(){
        BigDecimal expected = fromNumber(70.58823529);
        BigDecimal actual = calculateRsiStepOne(fromNumber(3.36), fromNumber(1.4), fromNumber(14));

        assertEquals(expected, actual);
    }

    @Test
    void calculateRsiStepTwoTest(){
        BigDecimal expected = fromNumber(66.19144604);
        BigDecimal actual = calculateRsiStepTwo(fromNumber(0.22), fromNumber(0.00), fromNumber(0.11), fromNumber(0.28), fromNumber(14));

        assertEquals(expected, actual);
    }

    @Test
    void separateLossesTest(){
        List<BigDecimal> expected = fromNumbers(0.0, 0.0, -45.45, 0.0, -56.34);
        List<BigDecimal> input = fromNumbers(44.44, 33.33, -45.45, 55.55, -56.34);
        List<BigDecimal> actual = separateLosses(input);

        assertEquals(expected, actual);
    }

    @Test
    void separateGainsTest(){
        List<BigDecimal> expected = fromNumbers(44.44, 33.33, 0.0, 55.55, 0.0);
        List<BigDecimal> input = fromNumbers(44.44, 33.33, -45.45, 55.55, -56.34);
        List<BigDecimal> actual = separateGains(input);

        assertEquals(expected, actual);
    }
}