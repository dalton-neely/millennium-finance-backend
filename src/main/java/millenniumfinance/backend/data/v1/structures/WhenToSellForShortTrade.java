package millenniumfinance.backend.data.v1.structures;

public class WhenToSellForShortTrade {
  public static final Double DEFAULT_PRICE_FLOOR_MULTIPLIER = 1.035D;
  
  private Double priceFloorMultiplier;
  
  public WhenToSellForShortTrade() {
    this.priceFloorMultiplier = DEFAULT_PRICE_FLOOR_MULTIPLIER;
  }
  
  public Double getPriceFloorMultiplier() {
    return priceFloorMultiplier;
  }
  
  public void setPriceFloorMultiplier(Double priceFloorMultiplier) {
    this.priceFloorMultiplier = priceFloorMultiplier;
  }
  
  @Override
  public String toString() {
    return "WhenToSellForShortTrade{" +
        "priceFloorMultiplier=" + priceFloorMultiplier +
        '}';
  }
}
