package millenniumfinance.backend.data.v1.structures;

public final class DataRow {
  private final Candlestick candlestick;
  private final Integer index;
  
  private final Double exponentialMovingAverageShortTerm;
  private final Double exponentialMovingAverageLongTerm;
  private final Double movingAverageConvergenceDivergence;
  private final Double signal;
  
  private final Double longTermMovingAverage;
  private final Double movingAverage;
  private final Double standardDeviation;
  private final Double upperBollingerBand;
  private final Double lowerBollingerBand;
  private final Double relativeStrengthIndex;
  
  private final Double smoothedLongTermMovingAverage;
  private final Double smoothedMovingAverage;
  private final Double smoothedStandDeviation;
  private final Double smoothedUpperBollingerBand;
  private final Double smoothedLowerBollingerBand;
  private final Double smoothedRelativeStrengthIndex;
  
  public DataRow(DataRowBuilder builder) {
    this.candlestick = builder.candlestickContainer;
    this.exponentialMovingAverageShortTerm = builder.exponentialMovingAverageShortTerm;
    this.exponentialMovingAverageLongTerm = builder.exponentialMovingAverageLongTerm;
    this.movingAverageConvergenceDivergence = builder.movingAverageConvergenceDivergence;
    this.signal = builder.signal;
    this.longTermMovingAverage = builder.longTermMovingAverage;
    this.movingAverage = builder.movingAverage;
    this.standardDeviation = builder.standardDeviation;
    this.upperBollingerBand = builder.upperBollingerBand;
    this.lowerBollingerBand = builder.lowerBollingerBand;
    this.relativeStrengthIndex = builder.relativeStrengthIndex;
    this.smoothedLongTermMovingAverage = builder.smoothedLongTermMovingAverage;
    this.smoothedMovingAverage = builder.smoothedMovingAverage;
    this.smoothedStandDeviation = builder.smoothedStandDeviation;
    this.smoothedUpperBollingerBand = builder.smoothedUpperBollingerBand;
    this.smoothedLowerBollingerBand = builder.smoothedLowerBollingerBand;
    this.smoothedRelativeStrengthIndex = builder.smoothedRelativeStrengthIndex;
    this.index = builder.index;
  }
  
  public static DataRow fromComputedData(
      Candlestick candlestickContainer,
      Double exponentialMovingAverageShortTerm,
      Double exponentialMovingAverageLongTerm,
      Double movingAverageConvergenceDivergence,
      Double signal,
      Double longTermMovingAverage,
      Double movingAverage,
      Double standardDeviation,
      Double upperBollingerBand,
      Double lowerBollingerBand,
      Double relativeStrengthIndex,
      Double smoothedLongTermMovingAverage,
      Double smoothedMovingAverage,
      Double smoothedStandDeviation,
      Double smoothedUpperBollingerBand,
      Double smoothedLowerBollingerBand,
      Double smoothedRelativeStrengthIndex,
      int index
  ) {
    return new DataRow
        .DataRowBuilder()
        .setCandlestickContainer(candlestickContainer)
        .setExponentialMovingAverageShortTerm(exponentialMovingAverageShortTerm)
        .setExponentialMovingAverageLongTerm(exponentialMovingAverageLongTerm)
        .setMovingAverageConvergenceDivergence(movingAverageConvergenceDivergence)
        .setSignal(signal)
        .setLongTermMovingAverage(longTermMovingAverage)
        .setMovingAverage(movingAverage)
        .setStandardDeviation(standardDeviation)
        .setUpperBollingerBand(upperBollingerBand)
        .setLowerBollingerBand(lowerBollingerBand)
        .setRelativeStrengthIndex(relativeStrengthIndex)
        .setSmoothedLongTermMovingAverage(smoothedLongTermMovingAverage)
        .setSmoothedMovingAverage(smoothedMovingAverage)
        .setSmoothedStandDeviation(smoothedStandDeviation)
        .setSmoothedUpperBollingerBand(smoothedUpperBollingerBand)
        .setSmoothedLowerBollingerBand(smoothedLowerBollingerBand)
        .setSmoothedRelativeStrengthIndex(smoothedRelativeStrengthIndex)
        .setIndex(index)
        .build();
  }
  
  public Candlestick getCandlestick() {
    return candlestick;
  }
  
  public Double getExponentialMovingAverageShortTerm() {
    return exponentialMovingAverageShortTerm;
  }
  
  public Double getExponentialMovingAverageLongTerm() {
    return exponentialMovingAverageLongTerm;
  }
  
  public Double getMovingAverageConvergenceDivergence() {
    return movingAverageConvergenceDivergence;
  }
  
  public Double getSignal() {
    return signal;
  }
  
  public Double getLongTermMovingAverage() {
    return longTermMovingAverage;
  }
  
  public Double getMovingAverage() {
    return movingAverage;
  }
  
  public Double getStandardDeviation() {
    return standardDeviation;
  }
  
  public Double getUpperBollingerBand() {
    return upperBollingerBand;
  }
  
  public Double getLowerBollingerBand() {
    return lowerBollingerBand;
  }
  
  public Double getRelativeStrengthIndex() {
    return relativeStrengthIndex;
  }
  
  public Double getSmoothedLongTermMovingAverage() {
    return smoothedLongTermMovingAverage;
  }
  
  public Double getSmoothedMovingAverage() {
    return smoothedMovingAverage;
  }
  
  public Double getSmoothedStandDeviation() {
    return smoothedStandDeviation;
  }
  
  public Double getSmoothedUpperBollingerBand() {
    return smoothedUpperBollingerBand;
  }
  
  public Double getSmoothedLowerBollingerBand() {
    return smoothedLowerBollingerBand;
  }
  
  public Double getSmoothedRelativeStrengthIndex() {
    return smoothedRelativeStrengthIndex;
  }
  
  public Integer getIndex() {
    return this.index;
  }
  
  public static class DataRowBuilder {
    private Candlestick candlestickContainer;
    private Integer index;
    
    private Double exponentialMovingAverageShortTerm;
    private Double exponentialMovingAverageLongTerm;
    private Double movingAverageConvergenceDivergence;
    private Double signal;
    
    private Double longTermMovingAverage;
    private Double movingAverage;
    private Double standardDeviation;
    private Double upperBollingerBand;
    private Double lowerBollingerBand;
    private Double relativeStrengthIndex;
    
    private Double smoothedLongTermMovingAverage;
    private Double smoothedMovingAverage;
    private Double smoothedStandDeviation;
    private Double smoothedUpperBollingerBand;
    private Double smoothedLowerBollingerBand;
    private Double smoothedRelativeStrengthIndex;
    
    public DataRowBuilder setCandlestickContainer(Candlestick candlestickContainer) {
      this.candlestickContainer = candlestickContainer;
      return this;
    }
    
    public DataRowBuilder setExponentialMovingAverageShortTerm(Double exponentialMovingAverageShortTerm) {
      this.exponentialMovingAverageShortTerm = exponentialMovingAverageShortTerm;
      return this;
    }
    
    public DataRowBuilder setExponentialMovingAverageLongTerm(Double exponentialMovingAverageLongTerm) {
      this.exponentialMovingAverageLongTerm = exponentialMovingAverageLongTerm;
      return this;
    }
    
    public DataRowBuilder setMovingAverageConvergenceDivergence(Double movingAverageConvergenceDivergence) {
      this.movingAverageConvergenceDivergence = movingAverageConvergenceDivergence;
      return this;
    }
    
    public DataRowBuilder setSignal(Double signal) {
      this.signal = signal;
      return this;
    }
    
    public DataRowBuilder setLongTermMovingAverage(Double longTermMovingAverage) {
      this.longTermMovingAverage = longTermMovingAverage;
      return this;
    }
    
    public DataRowBuilder setMovingAverage(Double movingAverage) {
      this.movingAverage = movingAverage;
      return this;
    }
    
    public DataRowBuilder setStandardDeviation(Double standardDeviation) {
      this.standardDeviation = standardDeviation;
      return this;
    }
    
    public DataRowBuilder setUpperBollingerBand(Double upperBollingerBand) {
      this.upperBollingerBand = upperBollingerBand;
      return this;
    }
    
    public DataRowBuilder setLowerBollingerBand(Double lowerBollingerBand) {
      this.lowerBollingerBand = lowerBollingerBand;
      return this;
    }
    
    public DataRowBuilder setRelativeStrengthIndex(Double relativeStrengthIndex) {
      this.relativeStrengthIndex = relativeStrengthIndex;
      return this;
    }
    
    public DataRowBuilder setSmoothedLongTermMovingAverage(Double smoothedLongTermMovingAverage) {
      this.smoothedLongTermMovingAverage = smoothedLongTermMovingAverage;
      return this;
    }
    
    public DataRowBuilder setSmoothedMovingAverage(Double smoothedMovingAverage) {
      this.smoothedMovingAverage = smoothedMovingAverage;
      return this;
    }
    
    public DataRowBuilder setSmoothedStandDeviation(Double smoothedStandDeviation) {
      this.smoothedStandDeviation = smoothedStandDeviation;
      return this;
    }
    
    public DataRowBuilder setSmoothedUpperBollingerBand(Double smoothedUpperBollingerBand) {
      this.smoothedUpperBollingerBand = smoothedUpperBollingerBand;
      return this;
    }
    
    public DataRowBuilder setSmoothedLowerBollingerBand(Double smoothedLowerBollingerBand) {
      this.smoothedLowerBollingerBand = smoothedLowerBollingerBand;
      return this;
    }
    
    public DataRowBuilder setSmoothedRelativeStrengthIndex(Double smoothedRelativeStrengthIndex) {
      this.smoothedRelativeStrengthIndex = smoothedRelativeStrengthIndex;
      return this;
    }
    
    public DataRowBuilder setIndex(Integer index) {
      this.index = index;
      return this;
    }
    
    public DataRow build() {
      return new DataRow(this);
    }
  }
}
