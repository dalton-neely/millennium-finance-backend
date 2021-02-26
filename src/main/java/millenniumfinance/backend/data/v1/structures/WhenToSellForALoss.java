package millenniumfinance.backend.data.v1.structures;

public class WhenToSellForALoss {
    public static final Double DEFAULT_FLOOR_GAIN_LOSS_PERCENTAGE = -0.04D;

    private Double floorGainLossPercentage;

    public WhenToSellForALoss() {
        this.floorGainLossPercentage = DEFAULT_FLOOR_GAIN_LOSS_PERCENTAGE;
    }

    public Double getFloorGainLossPercentage() {
        return floorGainLossPercentage;
    }

    public void setFloorGainLossPercentage(Double floorGainLossPercentage) {
        this.floorGainLossPercentage = floorGainLossPercentage;
    }

    @Override
    public String toString() {
        return "WhenToSellForALoss{" +
                "floorGainLossPercentage=" + floorGainLossPercentage +
                '}';
    }
}
