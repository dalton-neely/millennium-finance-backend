package millenniumfinance.backend.data.v1.structures;

public class WhenToBuyAfterALossParameters {
    public static final Double DEFAULT_RSI_CEILING = 18D;
    public static final Double DEFAULT_PRICE_CEILING_MULTIPLIER = 1D;

    private Double priceCeilingMultiplier;
    private Double rsiCeiling;

    public WhenToBuyAfterALossParameters() {
        this.priceCeilingMultiplier = DEFAULT_PRICE_CEILING_MULTIPLIER;
        this.rsiCeiling = DEFAULT_RSI_CEILING;
    }

    public Double getPriceCeilingMultiplier() {
        return priceCeilingMultiplier;
    }

    public void setPriceCeilingMultiplier(Double priceCeilingMultiplier) {
        this.priceCeilingMultiplier = priceCeilingMultiplier;
    }

    public Double getRsiCeiling() {
        return rsiCeiling;
    }

    public void setRsiCeiling(Double rsiCeiling) {
        this.rsiCeiling = rsiCeiling;
    }

    @Override
    public String toString() {
        return "WhenToBuyAfterALossParameters{" +
                "priceCeilingMultiplier=" + priceCeilingMultiplier +
                ", rsiCeiling=" + rsiCeiling +
                '}';
    }
}
