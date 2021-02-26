package millenniumfinance.backend.data.v1.enumerations;

public enum TradeMethod {
    BUY_NORMALLY("Buy the security under normal buying conditions"),
    BUY_BUT_DELAY("After a loss on a trade, delay buying for a future time"),
    BUY_AFTER_LOSS_AND_DELAY("After a loss on a trade and a delay, buy the security"),
    SELL_FOR_LONG("Sell for a long position"),
    SELL_FOR_SHORT("Sell for a short position"),
    SELL_FOR_LOSS("Sell for a loss to limit damage");

    private final String description;

    TradeMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
