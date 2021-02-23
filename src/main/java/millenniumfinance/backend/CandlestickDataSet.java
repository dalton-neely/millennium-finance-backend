package millenniumfinance.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class CandlestickDataSet {
    private String inputString;
    private List<CandlestickData> data;

    public CandlestickDataSet(String inputString) {
        this.inputString = inputString;
        this.data = new ArrayList<>();
    }

    public void convert(){
        inputString = inputString.substring(1, inputString.length() - 2);
        List<String> rows = asList(inputString.split("],"));
        rows = rows.stream().map(row -> row.substring(1)).collect(Collectors.toList());
        data = rows.stream().map(row -> {
            List<String> rowSplit = asList(row.split(","));
            CandlestickData data = new CandlestickData();

            data.setOpenTime(Long.parseLong(rowSplit.get(0)));
            data.setOpen(rowSplit.get(1));
            data.setHigh(rowSplit.get(2));
            data.setLow(rowSplit.get(3));
            data.setClose(rowSplit.get(4));
            data.setVolume(rowSplit.get(5));
            data.setCloseTime(Long.parseLong(rowSplit.get(6)));
            data.setQuoteAssetVolume(rowSplit.get(7));
            data.setNumberOfTrades(Long.parseLong(rowSplit.get(8)));
            data.setTakerBuyBaseAssetVolume(rowSplit.get(9));
            data.setTakerBuyQuoteAssetVolume(rowSplit.get(10));
            data.setIgnore(rowSplit.get(11));

            return data;
        }).collect(Collectors.toList());
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public List<CandlestickData> getData() {
        return data;
    }

    public void setData(List<CandlestickData> data) {
        this.data = data;
    }
}
