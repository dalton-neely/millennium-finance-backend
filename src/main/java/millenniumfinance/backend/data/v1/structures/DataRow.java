package millenniumfinance.backend.data.v1.structures;

import millenniumfinance.backend.data.v1.classes.CandlestickContainer;

public final class DataRow {
    private final CandlestickContainer candlestickContainer;

    private final Double exponentialMovingAverageTwelvePeriods;
    private final Double exponentialMovingAverageTwentySixPeriods;
    private final Double movingAverageConvergenceDivergence;
    private final Double movingAverageConvergenceDivergenceSignal;

    private final Double oneMinuteLongTermMovingAverageTwentyFivePeriods;
    private final Double oneMinuteMovingAverageFifteenPeriods;
    private final Double oneMinuteStandardDeviationFifteenPeriods;
    private final Double oneMinuteUpperBollingerBandFifteenPeriods;
    private final Double oneMinuteLowerBollingerBandFifteenPeriods;
    private final Double oneMinuteRelativeStrengthIndexSixPeriods;

    private final Double fifteenMinuteLongTermMovingAverage375Periods;
    private final Double fifteenMinuteMovingAverage225Periods;
    private final Double fifteenMinuteStandDeviation225Periods;
    private final Double fifteenMinuteUpperBollingerBand225Periods;
    private final Double fifteenMinuteLowerBollingerBand222Periods;
    private final Double fifteenMinuteRelativeStrengthIndex90Periods;

    public DataRow(DataRowBuilder builder) {
        this.candlestickContainer = builder.candlestickContainer;
        this.exponentialMovingAverageTwelvePeriods = builder.exponentialMovingAverageTwelvePeriods;
        this.exponentialMovingAverageTwentySixPeriods = builder.exponentialMovingAverageTwentySixPeriods;
        this.movingAverageConvergenceDivergence = builder.movingAverageConvergenceDivergence;
        this.movingAverageConvergenceDivergenceSignal = builder.movingAverageConvergenceDivergenceSignal;
        this.oneMinuteLongTermMovingAverageTwentyFivePeriods = builder.oneMinuteLongTermMovingAverageTwentyFivePeriods;
        this.oneMinuteMovingAverageFifteenPeriods = builder.oneMinuteMovingAverageFifteenPeriods;
        this.oneMinuteStandardDeviationFifteenPeriods = builder.oneMinuteStandardDeviationFifteenPeriods;
        this.oneMinuteUpperBollingerBandFifteenPeriods = builder.oneMinuteUpperBollingerBandFifteenPeriods;
        this.oneMinuteLowerBollingerBandFifteenPeriods = builder.oneMinuteLowerBollingerBandFifteenPeriods;
        this.oneMinuteRelativeStrengthIndexSixPeriods = builder.oneMinuteRelativeStrengthIndexSixPeriods;
        this.fifteenMinuteLongTermMovingAverage375Periods = builder.fifteenMinuteLongTermMovingAverage375Periods;
        this.fifteenMinuteMovingAverage225Periods = builder.fifteenMinuteMovingAverage225Periods;
        this.fifteenMinuteStandDeviation225Periods = builder.fifteenMinuteStandDeviation225Periods;
        this.fifteenMinuteUpperBollingerBand225Periods = builder.fifteenMinuteUpperBollingerBand225Periods;
        this.fifteenMinuteLowerBollingerBand222Periods = builder.fifteenMinuteLowerBollingerBand222Periods;
        this.fifteenMinuteRelativeStrengthIndex90Periods = builder.fifteenMinuteRelativeStrengthIndex90Periods;
    }

    public CandlestickContainer getCandlestickContainer() {
        return candlestickContainer;
    }

    public Double getExponentialMovingAverageTwelvePeriods() {
        return exponentialMovingAverageTwelvePeriods;
    }

    public Double getExponentialMovingAverageTwentySixPeriods() {
        return exponentialMovingAverageTwentySixPeriods;
    }

    public Double getMovingAverageConvergenceDivergence() {
        return movingAverageConvergenceDivergence;
    }

    public Double getMovingAverageConvergenceDivergenceSignal() {
        return movingAverageConvergenceDivergenceSignal;
    }

    public Double getOneMinuteLongTermMovingAverageTwentyFivePeriods() {
        return oneMinuteLongTermMovingAverageTwentyFivePeriods;
    }

    public Double getOneMinuteMovingAverageFifteenPeriods() {
        return oneMinuteMovingAverageFifteenPeriods;
    }

    public Double getOneMinuteStandardDeviationFifteenPeriods() {
        return oneMinuteStandardDeviationFifteenPeriods;
    }

    public Double getOneMinuteUpperBollingerBandFifteenPeriods() {
        return oneMinuteUpperBollingerBandFifteenPeriods;
    }

    public Double getOneMinuteLowerBollingerBandFifteenPeriods() {
        return oneMinuteLowerBollingerBandFifteenPeriods;
    }

    public Double getOneMinuteRelativeStrengthIndexSixPeriods() {
        return oneMinuteRelativeStrengthIndexSixPeriods;
    }

    public Double getFifteenMinuteLongTermMovingAverage375Periods() {
        return fifteenMinuteLongTermMovingAverage375Periods;
    }

    public Double getFifteenMinuteMovingAverage225Periods() {
        return fifteenMinuteMovingAverage225Periods;
    }

    public Double getFifteenMinuteStandDeviation225Periods() {
        return fifteenMinuteStandDeviation225Periods;
    }

    public Double getFifteenMinuteUpperBollingerBand225Periods() {
        return fifteenMinuteUpperBollingerBand225Periods;
    }

    public Double getFifteenMinuteLowerBollingerBand222Periods() {
        return fifteenMinuteLowerBollingerBand222Periods;
    }

    public Double getFifteenMinuteRelativeStrengthIndex90Periods() {
        return fifteenMinuteRelativeStrengthIndex90Periods;
    }

    @Override
    public String toString() {
        return "{" +
                "\"candlestickContainer\": " + candlestickContainer +
                ", \"exponentialMovingAverageTwelvePeriods\": " + exponentialMovingAverageTwelvePeriods +
                ", \"exponentialMovingAverageTwentySixPeriods\": " + exponentialMovingAverageTwentySixPeriods +
                ", \"movingAverageConvergenceDivergence\": " + movingAverageConvergenceDivergence +
                ", \"movingAverageConvergenceDivergenceSignal\": " + movingAverageConvergenceDivergenceSignal +
                ", \"oneMinuteLongTermMovingAverageTwentyFivePeriods\": " + oneMinuteLongTermMovingAverageTwentyFivePeriods +
                ", \"oneMinuteMovingAverageFifteenPeriods\": " + oneMinuteMovingAverageFifteenPeriods +
                ", \"oneMinuteStandardDeviationFifteenPeriods\": " + oneMinuteStandardDeviationFifteenPeriods +
                ", \"oneMinuteUpperBollingerBandFifteenPeriods\": " + oneMinuteUpperBollingerBandFifteenPeriods +
                ", \"oneMinuteLowerBollingerBandFifteenPeriods\": " + oneMinuteLowerBollingerBandFifteenPeriods +
                ", \"oneMinuteRelativeStrengthIndexSixPeriods\": " + oneMinuteRelativeStrengthIndexSixPeriods +
                ", \"fifteenMinuteLongTermMovingAverage375Periods\": " + fifteenMinuteLongTermMovingAverage375Periods +
                ", \"fifteenMinuteMovingAverage225Periods\": " + fifteenMinuteMovingAverage225Periods +
                ", \"fifteenMinuteStandDeviation225Periods\": " + fifteenMinuteStandDeviation225Periods +
                ", \"fifteenMinuteUpperBollingerBand225Periods\": " + fifteenMinuteUpperBollingerBand225Periods +
                ", \"fifteenMinuteLowerBollingerBand222Periods\": " + fifteenMinuteLowerBollingerBand222Periods +
                ", \"fifteenMinuteRelativeStrengthIndex90Periods\": " + fifteenMinuteRelativeStrengthIndex90Periods +
                '}';
    }

    public static class DataRowBuilder {
        private CandlestickContainer candlestickContainer;

        private Double exponentialMovingAverageTwelvePeriods;
        private Double exponentialMovingAverageTwentySixPeriods;
        private Double movingAverageConvergenceDivergence;
        private Double movingAverageConvergenceDivergenceSignal;

        private Double oneMinuteLongTermMovingAverageTwentyFivePeriods;
        private Double oneMinuteMovingAverageFifteenPeriods;
        private Double oneMinuteStandardDeviationFifteenPeriods;
        private Double oneMinuteUpperBollingerBandFifteenPeriods;
        private Double oneMinuteLowerBollingerBandFifteenPeriods;
        private Double oneMinuteRelativeStrengthIndexSixPeriods;

        private Double fifteenMinuteLongTermMovingAverage375Periods;
        private Double fifteenMinuteMovingAverage225Periods;
        private Double fifteenMinuteStandDeviation225Periods;
        private Double fifteenMinuteUpperBollingerBand225Periods;
        private Double fifteenMinuteLowerBollingerBand222Periods;
        private Double fifteenMinuteRelativeStrengthIndex90Periods;

        public DataRowBuilder candlestickContainer(CandlestickContainer candlestickContainer) {
            this.candlestickContainer = candlestickContainer;
            return this;
        }

        public DataRowBuilder exponentialMovingAverageTwelvePeriods(Double exponentialMovingAverageTwelvePeriods) {
            this.exponentialMovingAverageTwelvePeriods = exponentialMovingAverageTwelvePeriods;
            return this;
        }

        public DataRowBuilder exponentialMovingAverageTwentySixPeriods(Double exponentialMovingAverageTwentySixPeriods) {
            this.exponentialMovingAverageTwentySixPeriods = exponentialMovingAverageTwentySixPeriods;
            return this;
        }

        public DataRowBuilder movingAverageConvergenceDivergence(Double movingAverageConvergenceDivergence) {
            this.movingAverageConvergenceDivergence = movingAverageConvergenceDivergence;
            return this;
        }

        public DataRowBuilder movingAverageConvergenceDivergenceSignal(Double movingAverageConvergenceDivergenceSignal) {
            this.movingAverageConvergenceDivergenceSignal = movingAverageConvergenceDivergenceSignal;
            return this;
        }

        public DataRowBuilder oneMinuteLongTermMovingAverageTwentyFivePeriods(Double oneMinuteLongTermMovingAverageTwentyFivePeriods) {
            this.oneMinuteLongTermMovingAverageTwentyFivePeriods = oneMinuteLongTermMovingAverageTwentyFivePeriods;
            return this;
        }

        public DataRowBuilder oneMinuteMovingAverageFifteenPeriods(Double oneMinuteMovingAverageFifteenPeriods) {
            this.oneMinuteMovingAverageFifteenPeriods = oneMinuteMovingAverageFifteenPeriods;
            return this;
        }

        public DataRowBuilder oneMinuteStandardDeviationFifteenPeriods(Double oneMinuteStandardDeviationFifteenPeriods) {
            this.oneMinuteStandardDeviationFifteenPeriods = oneMinuteStandardDeviationFifteenPeriods;
            return this;
        }

        public DataRowBuilder oneMinuteUpperBollingerBandFifteenPeriods(Double oneMinuteUpperBollingerBandFifteenPeriods) {
            this.oneMinuteUpperBollingerBandFifteenPeriods = oneMinuteUpperBollingerBandFifteenPeriods;
            return this;
        }

        public DataRowBuilder oneMinuteLowerBollingerBandFifteenPeriods(Double oneMinuteLowerBollingerBandFifteenPeriods) {
            this.oneMinuteLowerBollingerBandFifteenPeriods = oneMinuteLowerBollingerBandFifteenPeriods;
            return this;
        }

        public DataRowBuilder oneMinuteRelativeStrengthIndexSixPeriods(Double oneMinuteRelativeStrengthIndexSixPeriods) {
            this.oneMinuteRelativeStrengthIndexSixPeriods = oneMinuteRelativeStrengthIndexSixPeriods;
            return this;
        }

        public DataRowBuilder fifteenMinuteLongTermMovingAverage375Periods(Double fifteenMinuteLongTermMovingAverage375Periods) {
            this.fifteenMinuteLongTermMovingAverage375Periods = fifteenMinuteLongTermMovingAverage375Periods;
            return this;
        }

        public DataRowBuilder fifteenMinuteMovingAverage225Periods(Double fifteenMinuteMovingAverage225Periods) {
            this.fifteenMinuteMovingAverage225Periods = fifteenMinuteMovingAverage225Periods;
            return this;
        }

        public DataRowBuilder fifteenMinuteStandDeviation225Periods(Double fifteenMinuteStandDeviation225Periods) {
            this.fifteenMinuteStandDeviation225Periods = fifteenMinuteStandDeviation225Periods;
            return this;
        }

        public DataRowBuilder fifteenMinuteUpperBollingerBand225Periods(Double fifteenMinuteUpperBollingerBand225Periods) {
            this.fifteenMinuteUpperBollingerBand225Periods = fifteenMinuteUpperBollingerBand225Periods;
            return this;
        }

        public DataRowBuilder fifteenMinuteLowerBollingerBand222Periods(Double fifteenMinuteLowerBollingerBand222Periods) {
            this.fifteenMinuteLowerBollingerBand222Periods = fifteenMinuteLowerBollingerBand222Periods;
            return this;
        }

        public DataRowBuilder fifteenMinuteRelativeStrengthIndex90Periods(Double fifteenMinuteRelativeStrengthIndex90Periods) {
            this.fifteenMinuteRelativeStrengthIndex90Periods = fifteenMinuteRelativeStrengthIndex90Periods;
            return this;
        }

        public DataRow build() {
            return new DataRow(this);
        }
    }
}
