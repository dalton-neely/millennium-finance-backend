package millenniumfinance.backend.data.v1.structures;

public class WhenToSellForLongTrade {
  public static final Double DEFAULT_PRICE_FLOOR_MULTIPLIER = 1.03D;
  public static final Double DEFAULT_RSI_FLOOR = 75D;
  public static final Double DEFAULT_UPPER_BOLLINGER_FLOOR_MULTIPLIER = 1D;
  
  private Double upperBollingerFloorMultiplier;
  private Double priceFloorMultiplier;
  private Double rsiFloor;
  
  public WhenToSellForLongTrade() {
    this.upperBollingerFloorMultiplier = DEFAULT_UPPER_BOLLINGER_FLOOR_MULTIPLIER;
    this.priceFloorMultiplier = DEFAULT_PRICE_FLOOR_MULTIPLIER;
    this.rsiFloor = DEFAULT_RSI_FLOOR;
  }
  
  public Double getUpperBollingerFloorMultiplier() {
    return upperBollingerFloorMultiplier;
  }
  
  public void setUpperBollingerFloorMultiplier(Double upperBollingerFloorMultiplier) {
    this.upperBollingerFloorMultiplier = upperBollingerFloorMultiplier;
  }
  
  public Double getPriceFloorMultiplier() {
    return priceFloorMultiplier;
  }
  
  public void setPriceFloorMultiplier(Double priceFloorMultiplier) {
    this.priceFloorMultiplier = priceFloorMultiplier;
  }
  
  public Double getRsiFloor() {
    return rsiFloor;
  }
  
  public void setRsiFloor(Double rsiFloor) {
    this.rsiFloor = rsiFloor;
  }
  
  @Override
  public String toString() {
    return "WhenToSellForLongTrade{" +
        "upperBollingerFloorMultiplier=" + upperBollingerFloorMultiplier +
        ", priceFloorMultiplier=" + priceFloorMultiplier +
        ", rsiFloor=" + rsiFloor +
        '}';
  }
}
