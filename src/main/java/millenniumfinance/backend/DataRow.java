package millenniumfinance.backend;

public class DataRow {
    CandlestickData baseData;
    Float ema_12;
    Float ema_26;

    public DataRow(CandlestickData baseData){
        this.baseData = baseData;
    }

    public CandlestickData getBaseData() {
        return baseData;
    }

    public void setBaseData(CandlestickData baseData) {
        this.baseData = baseData;
    }

    public Float getEma_12() {
        return ema_12;
    }

    public void setEma_12(Float ema_12) {
        this.ema_12 = ema_12;
    }

    public Float getEma_26() {
        return ema_26;
    }

    public void setEma_26(Float ema_26) {
        this.ema_26 = ema_26;
    }
}
