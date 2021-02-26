package millenniumfinance.backend.data.v1.structures;

public class WhenToDelayABuyAfterALossParameters {
    public static final Double DEFAULT_RSI_CEILING = 30D;

    private Double rsiCeiling;

    public WhenToDelayABuyAfterALossParameters() {

    }

    public WhenToDelayABuyAfterALossParameters(Double rsiCeiling) {
        this.rsiCeiling = rsiCeiling;
    }

    public Double getRsiCeiling() {
        return rsiCeiling;
    }

    public void setRsiCeiling(Double rsiCeiling) {
        this.rsiCeiling = rsiCeiling;
    }

    @Override
    public String toString() {
        return "WhenToDelayABuyAfterALossParameters{" +
                "rsiCeiling=" + rsiCeiling +
                '}';
    }
}
