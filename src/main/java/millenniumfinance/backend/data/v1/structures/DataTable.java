package millenniumfinance.backend.data.v1.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static millenniumfinance.backend.data.v1.structures.DataRow.fromComputedData;
import static millenniumfinance.backend.utilities.FinancialCalculations.*;

public final class DataTable {
    public final static int NINE_PERIODS = 9;
    public final static int TWELVE_PERIODS = 12;
    public final static int TWENTY_SIX_PERIODS = 26;
    public static final int WINDOW_SIZE_25 = 25;
    public static final int WINDOW_SIZE_15 = 15;
    public static final int WINDOW_SIZE_375 = 375;
    public static final int WINDOW_SIZE_225 = 225;
    private final List<DataRow> dataRows;

    private DataTable(DataTableBuilder builder) {
        this.dataRows = builder.dataRows;
    }

    public static DataTable fromBinanceApiString(String inputString) {
        // Format and split string for serialization
        final String withoutLeadingAndTrailing = inputString.substring(1, inputString.length() - 1);
        final String replaceWithDifferentSeparator = withoutLeadingAndTrailing.replaceAll("],\\[", "]@[");
        final List<String> splitByCommas = asList(replaceWithDifferentSeparator.split("@"));

        // Create object list from strings
        List<Candlestick> candlestickContainers = splitByCommas.stream()
                .map(Candlestick::fromBinanceApiString)
                .collect(Collectors.toList());

        // Get the close prices list for further calculations
        List<Double> closePrices = candlestickContainers
                .stream()
                .map(Candlestick::getClosePrice)
                .collect(Collectors.toList());

        // Get the exponential moving averages
        List<Double> exponentialMovingAveragesShortTerm = calculateAllExponentialMovingAverages(closePrices, TWELVE_PERIODS, 0);
        List<Double> exponentialMovingAveragesLongTerm = calculateAllExponentialMovingAverages(closePrices, TWENTY_SIX_PERIODS, 0);

        // Get the moving average convergence divergence for all the entries
        List<Double> movingAverageConvergenceDivergence =
                calculateAllMovingAverageConvergenceDivergence(
                        exponentialMovingAveragesShortTerm,
                        exponentialMovingAveragesLongTerm
                );

        // Get the signal for all the entries
        List<Double> signals =
                calculateAllExponentialMovingAverages(
                        movingAverageConvergenceDivergence,
                        NINE_PERIODS,
                        TWENTY_SIX_PERIODS
                );

        // Calculate means and standard deviations
        List<Double> longTermMovingAverages = calculateAllRollingMean(closePrices, WINDOW_SIZE_25);
        List<Double> movingAverages = calculateAllRollingMean(closePrices, WINDOW_SIZE_15);
        List<Double> standardDeviations = calculateAllRollingStandardDeviation(closePrices, WINDOW_SIZE_15);
        List<Double> smoothedLongTermMovingAverages = calculateAllRollingMean(closePrices, WINDOW_SIZE_375);
        List<Double> smoothedMovingAverages = calculateAllRollingMean(closePrices, WINDOW_SIZE_225);
        List<Double> smoothedStandDeviations = calculateAllRollingStandardDeviation(closePrices, WINDOW_SIZE_225);

        // Calculate Bollinger Bands
        List<Double> upperBollingerBands =
                calculateAllUpperBollingerBands(
                        movingAverages,
                        standardDeviations,
                        2.25D
                );
        List<Double> lowerBollingerBands =
                calculateAllLowerBollingerBands(
                        movingAverages,
                        standardDeviations,
                        2.5D
                );

        List<Double> smoothedUpperBollingerBands =
                calculateAllUpperBollingerBands(
                        smoothedMovingAverages,
                        smoothedStandDeviations,
                        2.25D
                );
        List<Double> smoothedLowerBollingerBands =
                calculateAllLowerBollingerBands(
                        smoothedMovingAverages,
                        smoothedStandDeviations,
                        2.5D
                );

        // Calculate RSI
        List<Double> relativeStrengthIndices =
                calculateAllRelativeStrengthIndex(closePrices, 6);

        List<Double> smoothedRelativeStrengthIndices =
                calculateAllRelativeStrengthIndex(closePrices, 90);

        // Shove all computed data into a list of smart containers with indexes
        List<DataRow> dataRows = new ArrayList<>();
        for (int i = 0; i < candlestickContainers.size(); i++) {
            dataRows.add(fromComputedData(
                    candlestickContainers.get(i),
                    exponentialMovingAveragesShortTerm.get(i),
                    exponentialMovingAveragesLongTerm.get(i),
                    movingAverageConvergenceDivergence.get(i),
                    signals.get(i),
                    longTermMovingAverages.get(i),
                    movingAverages.get(i),
                    standardDeviations.get(i),
                    upperBollingerBands.get(i),
                    lowerBollingerBands.get(i),
                    relativeStrengthIndices.get(i),
                    smoothedLongTermMovingAverages.get(i),
                    smoothedMovingAverages.get(i),
                    smoothedStandDeviations.get(i),
                    smoothedUpperBollingerBands.get(i),
                    smoothedLowerBollingerBands.get(i),
                    smoothedRelativeStrengthIndices.get(i),
                    i
            ));
        }

        return new DataTableBuilder()
                .dataRows(dataRows)
                .build();
    }

    public String toJavaString() {
        return "DataTable{" +
                "dataRows=" + dataRows +
                '}';
    }

    @Override
    public String toString() {
        return this.getDataRows().toString()
                .replace("DataTable", "")
                .replaceAll("=", ": ");
    }

    public List<DataRow> getDataRows() {
        return dataRows;
    }

    public static class DataTableBuilder {
        private List<DataRow> dataRows;

        public DataTableBuilder dataRows(List<DataRow> dataRows) {
            this.dataRows = dataRows;
            return this;
        }

        public DataTable build() {
            return new DataTable(this);
        }
    }
}
