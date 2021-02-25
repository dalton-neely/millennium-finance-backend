package millenniumfinance.backend.data.classes;

import millenniumfinance.backend.data.structures.Candlestick;

import java.util.Date;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.Arrays.asList;

public final class CandlestickContainer {
    private final Candlestick candlestick;

    private CandlestickContainer(Candlestick candlestick) {
        this.candlestick = candlestick;
    }

    /**
     * Example String Input from Binance
     *
     *     [
     *         1613908800000, // 0 - open time in milliseconds since epoch
     *         "57315.5900",  // 1 - open price
     *         "57359.5500",  // 2 - highest price
     *         "57315.5900",  // 3 - lowest price
     *         "57358.3600",  // 4 - close price
     *         "1.03935300",  // 5 - volume
     *         1613908859999, // 6 - close time in milliseconds since epoch
     *         "59586.5483",  // 7 - quote asset volume
     *         46,            // 8 - number of trades
     *         "0.84456200",  // 9 - taker buy base asset volume
     *         "48418.6341",  // 10 - taker buy quote asset volume
     *         "0"            // 11 - ignore this value
     *     ]
     */
    public static CandlestickContainer fromBinanceApiString(String input) {
        final String leftBracket = "\\[";
        final String rightBracket = "]";
        final String nothing = "";
        final String comma = ",";
        final String quotes = "\"";

        final String withoutBrackets = input.replaceAll(leftBracket, nothing).replaceAll(rightBracket, nothing);
        final String withoutQuotes = withoutBrackets.replaceAll(quotes, nothing);
        final List<String> dataPoints = asList(withoutQuotes.split(comma));

        Candlestick candlestick = new Candlestick.CandlestickBuilder()
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
        return new CandlestickContainer(candlestick);
    }

    public Candlestick getCandlestick() {
        return candlestick;
    }

    @Override
    public String toString() {
        return "CandlestickContainer{" +
                "candlestick=" + candlestick +
                '}';
    }
}
