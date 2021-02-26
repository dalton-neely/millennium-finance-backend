package millenniumfinance.backend.data.v1.enumerations;

public enum PositionState {
    BUY("buy"),
    SELL("sell"),
    HOLD("hold");

    private final String value;

    PositionState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TransactionTypes{" +
                "value='" + value + '\'' +
                '}';
    }
}
