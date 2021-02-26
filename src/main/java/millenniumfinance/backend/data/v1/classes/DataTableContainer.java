package millenniumfinance.backend.data.v1.classes;

import millenniumfinance.backend.data.v1.structures.DataTable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static millenniumfinance.backend.data.v1.classes.DataRowContainer.fromComputedData;
import static millenniumfinance.backend.utilities.FinancialCalculations.*;

public class DataTableContainer {
    public final static int NINE_PERIODS = 9;
    public final static int TWELVE_PERIODS = 12;
    public final static int TWENTY_SIX_PERIODS = 26;
    private final DataTable dataTable;

    private DataTableContainer(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public static DataTableContainer fromBinanceApiString(String inputString) {
        // Format and split string for serialization
        final String withoutLeadingAndTrailing = inputString.substring(1, inputString.length() - 1);
        final String replaceWithDifferentSeparator = withoutLeadingAndTrailing.replaceAll("],\\[", "]@[");
        final List<String> splitByCommas = asList(replaceWithDifferentSeparator.split("@"));

        // Create object list from strings
        List<CandlestickContainer> candlestickContainers = splitByCommas.stream()
                .map(CandlestickContainer::fromBinanceApiString)
                .collect(Collectors.toList());

        // Get the close prices list for further calculations
        List<Double> closePrices = candlestickContainers
                .stream()
                .map(candlestickContainer ->
                        candlestickContainer
                                .getCandlestick()
                                .getClosePrice()
                ).collect(Collectors.toList());

        // Get the EMA for the 12 periods
        List<Double> exponentialMovingAverageTwelvePeriods =
                calculateAllExponentialMovingAverages(closePrices, TWELVE_PERIODS, 0);

        // Get the EMA for the 26 periods
        List<Double> exponentialMovingAverageTwentySixPeriods =
                calculateAllExponentialMovingAverages(closePrices, TWENTY_SIX_PERIODS, 0);

        // Get the MACD for all the entries
        List<Double> movingAverageConvergenceDivergence =
                calculateAllMovingAverageConvergenceDivergence(
                        exponentialMovingAverageTwelvePeriods,
                        exponentialMovingAverageTwentySixPeriods
                );

        // Get the MACD Signal for all the entries
        List<Double> movingAverageConvergenceDivergenceSignal =
                calculateAllExponentialMovingAverages(
                        movingAverageConvergenceDivergence,
                        NINE_PERIODS,
                        TWENTY_SIX_PERIODS
                );

        // Calculate means and standard deviations
        List<Double> oneMinuteLongTermMovingAverageTwentyFivePeriods =
                calculateAllRollingMean(closePrices, 25);
        List<Double> oneMinuteMovingAverageFifteenPeriods =
                calculateAllRollingMean(closePrices, 15);
        List<Double> oneMinuteStandardDeviationFifteenPeriods =
                calculateAllRollingStandardDeviation(closePrices, 15);

        List<Double> fifteenMinuteLongTermMovingAverage375Periods =
                calculateAllRollingMean(closePrices, 375);
        List<Double> fifteenMinuteMovingAverage225Periods =
                calculateAllRollingMean(closePrices, 225);
        List<Double> fifteenMinuteStandDeviation225Periods =
                calculateAllRollingStandardDeviation(closePrices, 225);

        // Calculate Bollinger Bands
        List<Double> oneMinuteUpperBollingerBandFifteenPeriods =
                calculateAllUpperBollingerBands(
                        oneMinuteMovingAverageFifteenPeriods,
                        oneMinuteStandardDeviationFifteenPeriods,
                        2.25D
                );
        List<Double> oneMinuteLowerBollingerBandFifteenPeriods =
                calculateAllLowerBollingerBands(
                        oneMinuteMovingAverageFifteenPeriods,
                        oneMinuteStandardDeviationFifteenPeriods,
                        2.5D
                );

        List<Double> fifteenMinuteUpperBollingerBand225Periods =
                calculateAllUpperBollingerBands(
                        fifteenMinuteMovingAverage225Periods,
                        fifteenMinuteStandDeviation225Periods,
                        2.25D
                );
        List<Double> fifteenMinuteLowerBollingerBand222Periods =
                calculateAllLowerBollingerBands(
                        fifteenMinuteMovingAverage225Periods,
                        fifteenMinuteStandDeviation225Periods,
                        2.5D
                );

        // Calculate RSI
        List<Double> oneMinuteRelativeStrengthIndexSixPeriods =
                calculateAllRelativeStrengthIndex(closePrices, 6);

        List<Double> fifteenMinuteRelativeStrengthIndex90Periods =
                calculateAllRelativeStrengthIndex(closePrices, 90);

        // Shove all computed data into a list of smart containers with indexes
        List<DataRowContainer> dataRowContainerList = new ArrayList<>();
        for (int i = 0; i < candlestickContainers.size(); i++) {
            dataRowContainerList.add(fromComputedData(
                    candlestickContainers.get(i),
                    exponentialMovingAverageTwelvePeriods.get(i),
                    exponentialMovingAverageTwentySixPeriods.get(i),
                    movingAverageConvergenceDivergence.get(i),
                    movingAverageConvergenceDivergenceSignal.get(i),
                    oneMinuteLongTermMovingAverageTwentyFivePeriods.get(i),
                    oneMinuteMovingAverageFifteenPeriods.get(i),
                    oneMinuteStandardDeviationFifteenPeriods.get(i),
                    oneMinuteUpperBollingerBandFifteenPeriods.get(i),
                    oneMinuteLowerBollingerBandFifteenPeriods.get(i),
                    oneMinuteRelativeStrengthIndexSixPeriods.get(i),
                    fifteenMinuteLongTermMovingAverage375Periods.get(i),
                    fifteenMinuteMovingAverage225Periods.get(i),
                    fifteenMinuteStandDeviation225Periods.get(i),
                    fifteenMinuteUpperBollingerBand225Periods.get(i),
                    fifteenMinuteLowerBollingerBand222Periods.get(i),
                    fifteenMinuteRelativeStrengthIndex90Periods.get(i),
                    i
            ));
        }

        // Construct smart container back for manipulation by other code
        DataTable dataTable = new DataTable.DataTableBuilder()
                .dataRows(dataRowContainerList)
                .build();
        return new DataTableContainer(dataTable);
    }

    public DataTable getDataTable() {
        return dataTable;
    }

    public String toJavaString() {
        return "DataTableContainer{" +
                "dataTable=" + dataTable +
                '}';
    }

    @Override
    public String toString() {
        return this.toJavaString()
                .replace("DataTableContainer", "")
                .replaceAll(",", ": ");
    }
}
