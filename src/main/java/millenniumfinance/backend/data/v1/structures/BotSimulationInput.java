package millenniumfinance.backend.data.v1.structures;

public final class BotSimulationInput {
    private CalculateDataInput calculateDataInput;
    private WhenToDelayABuyAfterALossParameters whenToDelayABuyAfterALossParameters;
    private WhenToBuyAfterALossParameters whenToBuyAfterALossParameters;
    private WhenToBuyParameters whenToBuyParameters;
    private WhenToSellForALoss whenToSellForALoss;
    private WhenToSellForLongTrade whenToSellForLongTrade;
    private WhenToSellForShortTrade whenToSellForShortTrade;

    public BotSimulationInput() {
    }

    public CalculateDataInput getCalculateDataInput() {
        return calculateDataInput;
    }

    public void setCalculateDataInput(CalculateDataInput calculateDataInput) {
        this.calculateDataInput = calculateDataInput;
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
                "calculateDataInput=" + calculateDataInput +
                ", whenToDelayABuyAfterALossParameters=" + whenToDelayABuyAfterALossParameters +
                ", whenToBuyAfterALossParameters=" + whenToBuyAfterALossParameters +
                ", whenToBuyParameters=" + whenToBuyParameters +
                ", whenToSellForALoss=" + whenToSellForALoss +
                ", whenToSellForLongTrade=" + whenToSellForLongTrade +
                ", whenToSellForShortTrade=" + whenToSellForShortTrade +
                '}';
    }
}
