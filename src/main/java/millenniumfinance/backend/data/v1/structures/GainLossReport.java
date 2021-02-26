package millenniumfinance.backend.data.v1.structures;

public class GainLossReport {
    private Integer wins;
    private Integer losses;
    private Double usdtBalance;
    private Double btcBalance;
    private Double lastPrice;

    public GainLossReport(){}

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Double getUsdtBalance() {
        return usdtBalance;
    }

    public void setUsdtBalance(Double usdtBalance) {
        this.usdtBalance = usdtBalance;
    }

    public Double getBtcBalance() {
        return btcBalance;
    }

    public void setBtcBalance(Double btcBalance) {
        this.btcBalance = btcBalance;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    @Override
    public String toString() {
        return "GainLossReport{" +
                "wins=" + wins +
                ", losses=" + losses +
                ", usdtBalance=" + usdtBalance +
                ", btcBalance=" + btcBalance +
                ", lastPrice=" + lastPrice +
                '}';
    }
}
