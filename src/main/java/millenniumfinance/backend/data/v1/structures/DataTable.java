package millenniumfinance.backend.data.v1.structures;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static millenniumfinance.backend.data.v1.structures.DataRow.fromComputedData;
import static millenniumfinance.backend.utilities.FinancialCalculations.calculateAllExponentialMovingAverages;
import static millenniumfinance.backend.utilities.FinancialCalculations.calculateAllLowerBollingerBands;
import static millenniumfinance.backend.utilities.FinancialCalculations.calculateAllMovingAverageConvergenceDivergence;
import static millenniumfinance.backend.utilities.FinancialCalculations.calculateAllRollingMean;
import static millenniumfinance.backend.utilities.FinancialCalculations.calculateAllRollingStandardDeviation;
import static millenniumfinance.backend.utilities.FinancialCalculations.calculateAllUpperBollingerBands;
import static millenniumfinance.backend.utilities.RelativeStrengthIndexCalculation.calculateRsi;

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
    List<BigDecimal> closePrices = candlestickContainers
        .stream()
        .map(Candlestick::getClosePrice)
        .map(BigDecimal::valueOf)
        .collect(Collectors.toList());
    
    // Get the exponential moving averages
    List<BigDecimal> exponentialMovingAveragesShortTerm = calculateAllExponentialMovingAverages(closePrices, TWELVE_PERIODS, 0);
    List<BigDecimal> exponentialMovingAveragesLongTerm = calculateAllExponentialMovingAverages(closePrices, TWENTY_SIX_PERIODS, 0);
    
    // Get the moving average convergence divergence for all the entries
    List<BigDecimal> movingAverageConvergenceDivergence =
        calculateAllMovingAverageConvergenceDivergence(
            exponentialMovingAveragesShortTerm,
            exponentialMovingAveragesLongTerm
        );
    
    // Get the signal for all the entries
    List<BigDecimal> signals =
        calculateAllExponentialMovingAverages(
            movingAverageConvergenceDivergence,
            NINE_PERIODS,
            TWENTY_SIX_PERIODS
        );
    
    // Calculate means and standard deviations
    List<BigDecimal> longTermMovingAverages = calculateAllRollingMean(closePrices, WINDOW_SIZE_25);
    List<BigDecimal> movingAverages = calculateAllRollingMean(closePrices, WINDOW_SIZE_15);
    List<BigDecimal> standardDeviations = calculateAllRollingStandardDeviation(closePrices, WINDOW_SIZE_15);
    List<BigDecimal> smoothedLongTermMovingAverages = calculateAllRollingMean(closePrices, WINDOW_SIZE_375);
    List<BigDecimal> smoothedMovingAverages = calculateAllRollingMean(closePrices, WINDOW_SIZE_225);
    List<BigDecimal> smoothedStandDeviations = calculateAllRollingStandardDeviation(closePrices, WINDOW_SIZE_225);
    
    // Calculate Bollinger Bands
    List<BigDecimal> upperBollingerBands =
        calculateAllUpperBollingerBands(
            movingAverages,
            standardDeviations,
            valueOf(2.25D)
        );
    List<BigDecimal> lowerBollingerBands =
        calculateAllLowerBollingerBands(
            movingAverages,
            standardDeviations,
            valueOf(2.5D)
        );
    
    List<BigDecimal> smoothedUpperBollingerBands =
        calculateAllUpperBollingerBands(
            smoothedMovingAverages,
            smoothedStandDeviations,
            valueOf(2.25D)
        );
    List<BigDecimal> smoothedLowerBollingerBands =
        calculateAllLowerBollingerBands(
            smoothedMovingAverages,
            smoothedStandDeviations,
            valueOf(2.5D)
        );

//        // Calculate RSI
//        List<BigDecimal> relativeStrengthIndices =
//                calculateAllRelativeStrengthIndex(closePrices, 6);
//
//        List<BigDecimal> smoothedRelativeStrengthIndices =
//                calculateAllRelativeStrengthIndex(closePrices, 90);
    // Calculate RSI
    List<BigDecimal> relativeStrengthIndices =
        calculateRsi(closePrices, 6);
    
    List<BigDecimal> smoothedRelativeStrengthIndices =
        calculateRsi(closePrices, 90);
    
    // Shove all computed data into a list of smart containers with indexes
    List<DataRow> dataRows = new ArrayList<>();
    for (int i = 0; i < candlestickContainers.size(); i++) {
      dataRows.add(fromComputedData(
          candlestickContainers.get(i),
          exponentialMovingAveragesShortTerm.get(i).doubleValue(),
          exponentialMovingAveragesLongTerm.get(i).doubleValue(),
          movingAverageConvergenceDivergence.get(i).doubleValue(),
          signals.get(i).doubleValue(),
          longTermMovingAverages.get(i).doubleValue(),
          movingAverages.get(i).doubleValue(),
          standardDeviations.get(i).doubleValue(),
          upperBollingerBands.get(i).doubleValue(),
          lowerBollingerBands.get(i).doubleValue(),
          relativeStrengthIndices.get(i).doubleValue(),
          smoothedLongTermMovingAverages.get(i).doubleValue(),
          smoothedMovingAverages.get(i).doubleValue(),
          smoothedStandDeviations.get(i).doubleValue(),
          smoothedUpperBollingerBands.get(i).doubleValue(),
          smoothedLowerBollingerBands.get(i).doubleValue(),
          smoothedRelativeStrengthIndices.get(i).doubleValue(),
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
