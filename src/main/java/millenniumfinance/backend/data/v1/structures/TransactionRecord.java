package millenniumfinance.backend.data.v1.structures;

import millenniumfinance.backend.data.v1.enumerations.PositionState;
import millenniumfinance.backend.data.v1.enumerations.TradeMethod;

import java.util.Date;

public final class TransactionRecord {
    private final PositionState positionState;
    private final Date timestamp;
    private final Double securityPrice;
    private final Double unitedStatesDollarTetherBalance;
    private final Double cryptocurrencyBalance;
    private final TradeMethod tradeMethod;
    private final Double relativeStrengthIndex;

    public TransactionRecord(
            PositionState positionState,
            Date timestamp,
            Double securityPrice,
            Double unitedStatesDollarTetherBalance,
            Double cryptocurrencyBalance,
            TradeMethod tradeMethod,
            Double relativeStrengthIndex
    ) {
        this.positionState = positionState;
        this.timestamp = timestamp;
        this.securityPrice = securityPrice;
        this.unitedStatesDollarTetherBalance = unitedStatesDollarTetherBalance;
        this.cryptocurrencyBalance = cryptocurrencyBalance;
        this.tradeMethod = tradeMethod;
        this.relativeStrengthIndex = relativeStrengthIndex;
    }

    public PositionState getPositionState() {
        return positionState;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Double getSecurityPrice() {
        return securityPrice;
    }

    public Double getUnitedStatesDollarTetherBalance() {
        return unitedStatesDollarTetherBalance;
    }

    public Double getCryptocurrencyBalance() {
        return cryptocurrencyBalance;
    }

    public TradeMethod getTradeMethod() {
        return tradeMethod;
    }

    public Double getRelativeStrengthIndex() {
        return relativeStrengthIndex;
    }

    public static class TransactionRecordBuilder {
        private PositionState positionState;
        private Date timestamp;
        private Double securityPrice;
        private Double unitedStatesDollarTetherBalance;
        private Double cryptocurrencyBalance;
        private TradeMethod tradeMethod;
        private Double relativeStrengthIndex;

        public TransactionRecordBuilder setPositionState(PositionState positionState) {
            this.positionState = positionState;
            return this;
        }

        public TransactionRecordBuilder setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public TransactionRecordBuilder setSecurityPrice(Double securityPrice) {
            this.securityPrice = securityPrice;
            return this;
        }

        public TransactionRecordBuilder setUnitedStatesDollarTetherBalance(Double unitedStatesDollarTetherBalance) {
            this.unitedStatesDollarTetherBalance = unitedStatesDollarTetherBalance;
            return this;
        }

        public TransactionRecordBuilder setCryptocurrencyBalance(Double cryptocurrencyBalance) {
            this.cryptocurrencyBalance = cryptocurrencyBalance;
            return this;
        }

        public TransactionRecordBuilder setTradeMethod(TradeMethod tradeMethod) {
            this.tradeMethod = tradeMethod;
            return this;
        }

        public TransactionRecordBuilder setRelativeStrengthIndex(Double relativeStrengthIndex) {
            this.relativeStrengthIndex = relativeStrengthIndex;
            return this;
        }

        public TransactionRecord build() {
            return new TransactionRecord(
                    this.positionState,
                    this.timestamp,
                    this.securityPrice,
                    this.unitedStatesDollarTetherBalance,
                    this.cryptocurrencyBalance,
                    this.tradeMethod,
                    this.relativeStrengthIndex
            );
        }
    }
}
