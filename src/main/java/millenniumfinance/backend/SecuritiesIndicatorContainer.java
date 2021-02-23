package millenniumfinance.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SecuritiesIndicatorContainer {
    private List<DataRow> dataRows;
    private List<Float> ema_12;
    private List<Float> ema_26;
    private List<Float> macd;
    private List<Float> macd_signal;

    public SecuritiesIndicatorContainer(List<CandlestickData> candlestickData) {
        this.ema_12 = new ArrayList<>();
        this.ema_26 = new ArrayList<>();
        this.macd = new ArrayList<>();
        this.macd_signal = new ArrayList<>();
        this.dataRows = calculateDataRows(candlestickData);
    }

    private Float sma(List<Float> periods) {
        return periods.stream().reduce(0f, Float::sum) / periods.size();
    }

    private Float ema(Float current, Float previous, Float multiplier) {
        return (float) Math.round((current * multiplier + previous * (1 - multiplier)));
    }

    private List<DataRow> calculateDataRows(List<CandlestickData> data) {
        AtomicInteger index = new AtomicInteger();
        ema_12.add(sma(data.subList(0, 11).stream().map(row -> Float.parseFloat(row.getClose().replace("\"", ""))).collect(Collectors.toList())));
        ema_26.add(sma(data.subList(0, 25).stream().map(row -> Float.parseFloat(row.getClose().replace("\"", ""))).collect(Collectors.toList())));

        return data.stream().map((row) -> {
            DataRow dataRow = new DataRow(row);

            if (index.get() >= 12) {
                ema_12.add(ema(Float.parseFloat(row.getClose().replace("\"", "")), ema_12.get(ema_12.size() - 1), 2 / 13f));
            }
            if (index.get() >= 26) {
                ema_26.add(ema(Float.parseFloat(row.getClose().replace("\"", "")), ema_26.get(ema_26.size() - 1), 2 / 27f));
                macd.add(ema_12.get(index.get() - 12) - ema_26.get(index.get() - 26));
            }
            if (index.get() >= 35) {
                if (macd_signal.isEmpty()) {
                    macd_signal.add(sma(macd.subList(0, 8)));
                } else {
                    macd_signal.add(ema(macd.get(index.get() - 26), macd_signal.get(macd_signal.size() - 1), 2 / 10f));
                }
            }


//            dataRow.setEma_12();
//            dataRow.setEma_26();
            index.getAndIncrement();
            return dataRow;
        }).collect(Collectors.toList());
    }

    public List<DataRow> getDataRows() {
        return dataRows;
    }

    public void setDataRows(List<DataRow> dataRows) {
        this.dataRows = dataRows;
    }

    public List<Float> getEma_12() {
        return ema_12;
    }

    public void setEma_12(List<Float> ema_12) {
        this.ema_12 = ema_12;
    }

    public List<Float> getEma_26() {
        return ema_26;
    }

    public void setEma_26(List<Float> ema_26) {
        this.ema_26 = ema_26;
    }

    @Override
    public String toString() {
        return "SecuritiesIndicatorContainer{" +
                "dataRows=" + dataRows +
                ", ema_12=" + ema_12 +
                ", ema_26=" + ema_26 +
                ", macd=" + macd +
                ", macd_signal=" + macd_signal +
                '}';
    }

    public String toStringFormatted() {
        StringBuilder endValue = new StringBuilder("[");
        int length = dataRows.size();
        int index = 0;
        for (DataRow dataRow : this.dataRows) {
            Float ema12 = 0f;
            Float ema26 = 0f;
            Float macd = 0f;
            Float macd_signal = 0f;

            if(index >= 12 && this.ema_12.get(index - 12) != null){
                ema12 = this.ema_12.get(index - 12);
            }
            if(index >= 26 && this.ema_26.get(index - 26) != null){
                ema26 = this.ema_26.get(index - 26);
                macd = this.macd.get(index - 26);
            }
            if(index >= 35) {
                macd_signal = this.macd_signal.get(index - 35);
            }
            endValue
                    .append("{\"index\": ")
                    .append(index)
                    .append(",")
                    .append("\"openTime\": ")
                    .append(dataRow.getBaseData().getOpenTime())
                    .append(",")
                    .append("\"open\": ")
                    .append(dataRow.getBaseData().getOpen())
                    .append(",")
                    .append("\"high\": ")
                    .append(dataRow.getBaseData().getHigh())
                    .append(",")
                    .append("\"low\": ")
                    .append(dataRow.getBaseData().getLow())
                    .append(",")
                    .append("\"close\": ")
                    .append(dataRow.getBaseData().getClose())
                    .append(",")
                    .append("\"volume\": ")
                    .append(dataRow.getBaseData().getVolume())
                    .append(",")
                    .append("\"closeTime\": ")
                    .append(dataRow.getBaseData().getCloseTime())
                    .append(",")
                    .append("\"quoteAssetVolume\": ")
                    .append(dataRow.getBaseData().getQuoteAssetVolume())
                    .append(",")
                    .append("\"numberOfTrades\": ")
                    .append(dataRow.getBaseData().getNumberOfTrades())
                    .append(",")
                    .append("\"takerBuyBaseAssetVolume\": ")
                    .append(dataRow.getBaseData().getTakerBuyBaseAssetVolume())
                    .append(",")
                    .append("\"takerBuyQuoteAssetVolume\": ")
                    .append(dataRow.getBaseData().getTakerBuyQuoteAssetVolume())
                    .append(",")
                    .append("\"ignore\": ")
                    .append(dataRow.getBaseData().getIgnore())
                    .append(",")
                    .append("\"ema12\": ")
                    .append(ema12)
                    .append(",")
                    .append("\"ema26\": ")
                    .append(ema26)
                    .append(",")
                    .append("\"macd\": ")
                    .append(macd)
                    .append(",")
                    .append("\"macdSignal\": ")
                    .append(macd_signal)
                    .append("}");

            if (index != length - 1) {
                endValue.append(",");
            }
            index++;
        }
        return endValue + "]";
    }
}
