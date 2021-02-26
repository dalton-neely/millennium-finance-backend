package millenniumfinance.backend.data.v1.structures;

public class Position {
    private String state;
    private String trade;
    private String action;

    public Position(String initialState) {
        this.state = initialState;
        this.trade = "READY";
        this.action = initialState;
    }

    public String getState() {
        return state;
    }

    public Position setState(String state) {
        this.state = state;
        return this;
    }

    public String getTrade() {
        return trade;
    }

    public Position setTrade(String trade) {
        this.trade = trade;
        return this;
    }

    public String getAction() {
        return action;
    }

    public Position setAction(String action) {
        this.action = action;
        return this;
    }

    public void marketBuy(){
        this.state = "IN";
        this.action = "LONG TRADE";
    }

    public void marketShort(){
        this.state = "IN";
        this.action = "SHORT TRADE";
    }

    public void marketSell(){
        this.state = "OUT";
    }

    public void win(){
        this.state = "OUT";
        this.trade = "READY";
        this.action = "LONG TRADE";
    }

    public void loss(){
        this.state = "OUT";
        this.trade = "FAILED";
    }

    public void ready(){
        this.trade = "READY";
    }

    public void delay(){
        this.action = "DELAY BUY";
    }
}
