package millenniumfinance.backend.data.classes;

import millenniumfinance.backend.data.structures.DataRow;

import static millenniumfinance.backend.utilities.FinancialCalculations.calculateAllRollingMean;

public class DataRowContainer {
    private final DataRow dataRow;
    private final int index;

    private DataRowContainer(DataRow dataRow, int index) {
        this.dataRow = dataRow;
        this.index = index;
    }

    public static DataRowContainer fromComputedData(
            CandlestickContainer candlestickContainer,
            Double exponentialMovingAverageTwelvePeriods,
            Double exponentialMovingAverageTwentySixPeriods,
            Double movingAverageConvergenceDivergence,
            Double movingAverageConvergenceDivergenceSignal,
            Double oneMinuteLongTermMovingAverageTwentyFivePeriods,
            Double oneMinuteMovingAverageFifteenPeriods,
            Double oneMinuteStandardDeviationFifteenPeriods,
            Double oneMinuteUpperBollingerBandFifteenPeriods,
            Double oneMinuteLowerBollingerBandFifteenPeriods,
            Double oneMinuteRelativeStrengthIndexSixPeriods,
            Double fifteenMinuteLongTermMovingAverage375Periods,
            Double fifteenMinuteMovingAverage225Periods,
            Double fifteenMinuteStandDeviation225Periods,
            Double fifteenMinuteUpperBollingerBand225Periods,
            Double fifteenMinuteLowerBollingerBand222Periods,
            Double fifteenMinuteRelativeStrengthIndex90Periods,
            int index
            ) {
        DataRow dataRow = new DataRow
                .DataRowBuilder()
                .candlestickContainer(candlestickContainer)
                .exponentialMovingAverageTwelvePeriods(exponentialMovingAverageTwelvePeriods)
                .exponentialMovingAverageTwentySixPeriods(exponentialMovingAverageTwentySixPeriods)
                .movingAverageConvergenceDivergence(movingAverageConvergenceDivergence)
                .movingAverageConvergenceDivergenceSignal(movingAverageConvergenceDivergenceSignal)
                .oneMinuteLongTermMovingAverageTwentyFivePeriods(oneMinuteLongTermMovingAverageTwentyFivePeriods)
                .oneMinuteMovingAverageFifteenPeriods(oneMinuteMovingAverageFifteenPeriods)
                .oneMinuteStandardDeviationFifteenPeriods(oneMinuteStandardDeviationFifteenPeriods)
                .oneMinuteUpperBollingerBandFifteenPeriods(oneMinuteUpperBollingerBandFifteenPeriods)
                .oneMinuteLowerBollingerBandFifteenPeriods(oneMinuteLowerBollingerBandFifteenPeriods)
                .oneMinuteRelativeStrengthIndexSixPeriods(oneMinuteRelativeStrengthIndexSixPeriods)
                .fifteenMinuteLongTermMovingAverage375Periods(fifteenMinuteLongTermMovingAverage375Periods)
                .fifteenMinuteMovingAverage225Periods(fifteenMinuteMovingAverage225Periods)
                .fifteenMinuteStandDeviation225Periods(fifteenMinuteStandDeviation225Periods)
                .fifteenMinuteUpperBollingerBand225Periods(fifteenMinuteUpperBollingerBand225Periods)
                .fifteenMinuteLowerBollingerBand222Periods(fifteenMinuteLowerBollingerBand222Periods)
                .fifteenMinuteRelativeStrengthIndex90Periods(fifteenMinuteRelativeStrengthIndex90Periods)
                .build();
        return new DataRowContainer(dataRow, index);
    }

    public DataRow getDataRow() {
        return dataRow;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "DataRowContainer{" +
                "dataRow=" + dataRow +
                ", index=" + index +
                '}';
    }
}
