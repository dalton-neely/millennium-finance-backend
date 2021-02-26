package millenniumfinance.backend.data.v1.structures;

import java.util.Date;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.Arrays.asList;

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

    /**
     * Example String Input from Binance
     * <p>
     * [
     * 1613908800000, // 0 - open time in milliseconds since epoch
     * "57315.5900",  // 1 - open price
     * "57359.5500",  // 2 - highest price
     * "57315.5900",  // 3 - lowest price
     * "57358.3600",  // 4 - close price
     * "1.03935300",  // 5 - volume
     * 1613908859999, // 6 - close time in milliseconds since epoch
     * "59586.5483",  // 7 - quote asset volume
     * 46,            // 8 - number of trades
     * "0.84456200",  // 9 - taker buy base asset volume
     * "48418.6341",  // 10 - taker buy quote asset volume
     * "0"            // 11 - ignore this value
     * ]
     */
    public static Candlestick fromBinanceApiString(String input) {
        final String leftBracket = "\\[";
        final String rightBracket = "]";
        final String nothing = "";
        final String comma = ",";
        final String quotes = "\"";

        final String withoutBrackets = input.replaceAll(leftBracket, nothing).replaceAll(rightBracket, nothing);
        final String withoutQuotes = withoutBrackets.replaceAll(quotes, nothing);
        final List<String> dataPoints = asList(withoutQuotes.split(comma));

        return new CandlestickBuilder()
                .openTime(new Date(parseLong(dataPoints.get(0))))
                .openPrice(parseDouble(dataPoints.get(1)))
                .highestPrice(parseDouble(dataPoints.get(2)))
                .lowestPrice(parseDouble(dataPoints.get(3)))
                .closePrice(parseDouble(dataPoints.get(4)))
                .volume(parseDouble(dataPoints.get(5)))
                .closeTime(new Date(parseLong(dataPoints.get(6))))
                .quoteAssetVolume(parseDouble(dataPoints.get(7)))
                .numberOfTrades(parseInt(dataPoints.get(8)))
                .takerBuyBaseAssetVolume(parseDouble(dataPoints.get(9)))
                .takerBuyQuoteAssetVolume(parseDouble(dataPoints.get(10)))
                .build();
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

        public Candlestick build() {
            return new Candlestick(this);
        }
    }
}
