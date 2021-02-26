package millenniumfinance.backend.data.v1.structures;

public class WhenToBuyParameters {
    public static final Double DEFAULT_RSI_CEILING = 50D;
    public static final Double DEFAULT_LOWER_BOLLINGER_CEILING_MULTIPLIER = 1D;

    private Double rsiCeiling;
    private Double lowerBollingerCeilingMultiplier;

    public WhenToBuyParameters() {
        this.rsiCeiling = DEFAULT_RSI_CEILING;
        this.lowerBollingerCeilingMultiplier = DEFAULT_LOWER_BOLLINGER_CEILING_MULTIPLIER;
    }

    public Double getRsiCeiling() {
        return rsiCeiling;
    }

    public void setRsiCeiling(Double rsiCeiling) {
        this.rsiCeiling = rsiCeiling;
    }

    public Double getLowerBollingerCeilingMultiplier() {
        return lowerBollingerCeilingMultiplier;
    }

    public void setLowerBollingerCeilingMultiplier(Double lowerBollingerCeilingMultiplier) {
        this.lowerBollingerCeilingMultiplier = lowerBollingerCeilingMultiplier;
    }

    @Override
    public String toString() {
        return "WhenToBuyParameters{" +
                "rsiCeiling=" + rsiCeiling +
                ", lowerBollingerCeilingMultiplier=" + lowerBollingerCeilingMultiplier +
                '}';
    }
}
