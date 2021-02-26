package millenniumfinance.backend.data.v1.classes;

import millenniumfinance.backend.data.v1.structures.Candlestick;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static millenniumfinance.backend.data.v1.classes.CandlestickContainer.fromBinanceApiString;
import static org.junit.jupiter.api.Assertions.*;

class CandlestickContainerTest {
    @Test
    public void fromBinanceApiStringTest() {
        final String input = "[1613908680000,\"57298.1800\",\"57315.5900\",\"57271.4900\",\"57297.5800\",\"0.04704000\",1613908739999,\"2694.9437\",9,\"0.01788600\",\"1024.5837\",\"0\"]";
        final CandlestickContainer candlestickContainer = fromBinanceApiString(input);
        final Candlestick candlestick = candlestickContainer.getCandlestick();

        assertEquals(candlestick.getOpenTime(), new Date(1613908680000L));
        assertEquals(candlestick.getOpenPrice(), 57298.1800D);
        assertEquals(candlestick.getHighestPrice(), 57315.5900D);
        assertEquals(candlestick.getLowestPrice(), 57271.4900D);
        assertEquals(candlestick.getClosePrice(), 57297.5800D);
        assertEquals(candlestick.getVolume(), 0.04704000D);
        assertEquals(candlestick.getCloseTime(), new Date(1613908739999L));
        assertEquals(candlestick.getQuoteAssetVolume(), 2694.9437D);
        assertEquals(candlestick.getNumberOfTrades(), 9);
        assertEquals(candlestick.getTakerBuyBaseAssetVolume(), 0.01788600D);
        assertEquals(candlestick.getTakerBuyQuoteAssetVolume(), 1024.5837D);
    }
}