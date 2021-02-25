package millenniumfinance.backend.data.structures;

import java.util.Date;

public final class Candlestick {
    private final Date openTime;
    private final Double openPrice;
    private final Double highestPrice;
    private final Double lowestPrice;
    private final Double closePrice;
    private final Double volume;
    private final Date closeTime;
    private final Double quoteAssetVolume;
    private final Integer numberOfTrades;
    private final Double takerBuyBaseAssetVolume;
    private final Double takerBuyQuoteAssetVolume;

    private Candlestick(CandlestickBuilder builder) {
        this.openTime = builder.openTime;
        this.openPrice = builder.openPrice;
        this.highestPrice = builder.highestPrice;
        this.lowestPrice = builder.lowestPrice;
        this.closePrice = builder.closePrice;
        this.volume = builder.volume;
        this.closeTime = builder.closeTime;
        this.quoteAssetVolume = builder.quoteAssetVolume;
        this.numberOfTrades = builder.numberOfTrades;
        this.takerBuyBaseAssetVolume = builder.takerBuyBaseAssetVolume;
        this.takerBuyQuoteAssetVolume = builder.takerBuyQuoteAssetVolume;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public Double getHighestPrice() {
        return highestPrice;
    }

    public Double getLowestPrice() {
        return lowestPrice;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public Double getVolume() {
        return volume;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public Double getQuoteAssetVolume() {
        return quoteAssetVolume;
    }

    public Integer getNumberOfTrades() {
        return numberOfTrades;
    }

    public Double getTakerBuyBaseAssetVolume() {
        return takerBuyBaseAssetVolume;
    }

    public Double getTakerBuyQuoteAssetVolume() {
        return takerBuyQuoteAssetVolume;
    }

    public static class CandlestickBuilder {
        private Date openTime;
        private Double openPrice;
        private Double highestPrice;
        private Double lowestPrice;
        private Double closePrice;
        private Double volume;
        private Date closeTime;
        private Double quoteAssetVolume;
        private Integer numberOfTrades;
        private Double takerBuyBaseAssetVolume;
        private Double takerBuyQuoteAssetVolume;

        public CandlestickBuilder openTime(Date openTime) {
            this.openTime = openTime;
            return this;
        }

        public CandlestickBuilder openPrice(Double openPrice) {
            this.openPrice = openPrice;
            return this;
        }

        public CandlestickBuilder highestPrice(Double highestPrice) {
            this.highestPrice = highestPrice;
            return this;
        }

        public CandlestickBuilder lowestPrice(Double lowestPrice) {
            this.lowestPrice = lowestPrice;
            return this;
        }

        public CandlestickBuilder closePrice(Double closePrice) {
            this.closePrice = closePrice;
            return this;
        }

        public CandlestickBuilder volume(Double volume) {
            this.volume = volume;
            return this;
        }

        public CandlestickBuilder closeTime(Date closeTime) {
            this.closeTime = closeTime;
            return this;
        }

        public CandlestickBuilder quoteAssetVolume(Double quoteAssetVolume) {
            this.quoteAssetVolume = quoteAssetVolume;
            return this;
        }

        public CandlestickBuilder numberOfTrades(Integer numberOfTrades) {
            this.numberOfTrades = numberOfTrades;
            return this;
        }

        public CandlestickBuilder takerBuyBaseAssetVolume(Double takerBuyBaseAssetVolume) {
            this.takerBuyBaseAssetVolume = takerBuyBaseAssetVolume;
            return this;
        }

        public CandlestickBuilder takerBuyQuoteAssetVolume(Double takerBuyQuoteAssetVolume) {
            this.takerBuyQuoteAssetVolume = takerBuyQuoteAssetVolume;
            return this;
        }

        public Candlestick build(){
            return new Candlestick(this);
        }
    }

    @Override
    public String toString() {
        return "Candlestick{" +
                "openTime=" + openTime +
                ", openPrice=" + openPrice +
                ", highestPrice=" + highestPrice +
                ", lowestPrice=" + lowestPrice +
                ", closePrice=" + closePrice +
                ", volume=" + volume +
                ", closeTime=" + closeTime +
                ", quoteAssetVolume=" + quoteAssetVolume +
                ", numberOfTrades=" + numberOfTrades +
                ", takerBuyBaseAssetVolume=" + takerBuyBaseAssetVolume +
                ", takerBuyQuoteAssetVolume=" + takerBuyQuoteAssetVolume +
                '}';
    }
}
