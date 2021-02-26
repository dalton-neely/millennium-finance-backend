package millenniumfinance.backend.data.v1.structures;

public final class BotSimulationInput {
    private String symbol;
    private String interval;
    private String limit;
    private WhenToDelayABuyAfterALossParameters whenToDelayABuyAfterALossParameters;
    private WhenToBuyAfterALossParameters whenToBuyAfterALossParameters;
    private WhenToBuyParameters whenToBuyParameters;
    private WhenToSellForALoss whenToSellForALoss;
    private WhenToSellForLongTrade whenToSellForLongTrade;
    private WhenToSellForShortTrade whenToSellForShortTrade;

    public BotSimulationInput() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public WhenToDelayABuyAfterALossParameters getWhenToDelayABuyAfterALossParameters() {
        return whenToDelayABuyAfterALossParameters;
    }

    public void setWhenToDelayABuyAfterALossParameters(WhenToDelayABuyAfterALossParameters whenToDelayABuyAfterALossParameters) {
        this.whenToDelayABuyAfterALossParameters = whenToDelayABuyAfterALossParameters;
    }

    public WhenToBuyAfterALossParameters getWhenToBuyAfterALossParameters() {
        return whenToBuyAfterALossParameters;
    }

    public void setWhenToBuyAfterALossParameters(WhenToBuyAfterALossParameters whenToBuyAfterALossParameters) {
        this.whenToBuyAfterALossParameters = whenToBuyAfterALossParameters;
    }

    public WhenToBuyParameters getWhenToBuyParameters() {
        return whenToBuyParameters;
    }

    public void setWhenToBuyParameters(WhenToBuyParameters whenToBuyParameters) {
        this.whenToBuyParameters = whenToBuyParameters;
    }

    public WhenToSellForALoss getWhenToSellForALoss() {
        return whenToSellForALoss;
    }

    public void setWhenToSellForALoss(WhenToSellForALoss whenToSellForALoss) {
        this.whenToSellForALoss = whenToSellForALoss;
    }

    public WhenToSellForLongTrade getWhenToSellForLongTrade() {
        return whenToSellForLongTrade;
    }

    public void setWhenToSellForLongTrade(WhenToSellForLongTrade whenToSellForLongTrade) {
        this.whenToSellForLongTrade = whenToSellForLongTrade;
    }

    public WhenToSellForShortTrade getWhenToSellForShortTrade() {
        return whenToSellForShortTrade;
    }

    public void setWhenToSellForShortTrade(WhenToSellForShortTrade whenToSellForShortTrade) {
        this.whenToSellForShortTrade = whenToSellForShortTrade;
    }

    @Override
    public String toString() {
        return "BotSimulationInput{" +
                "symbol='" + symbol + '\'' +
                ", interval='" + interval + '\'' +
                ", limit='" + limit + '\'' +
                ", whenToDelayABuyAfterALossParameters=" + whenToDelayABuyAfterALossParameters +
                ", whenToBuyAfterALossParameters=" + whenToBuyAfterALossParameters +
                ", whenToBuyParameters=" + whenToBuyParameters +
                ", whenToSellForALoss=" + whenToSellForALoss +
                ", whenToSellForLongTrade=" + whenToSellForLongTrade +
                ", whenToSellForShortTrade=" + whenToSellForShortTrade +
                '}';
    }
}
