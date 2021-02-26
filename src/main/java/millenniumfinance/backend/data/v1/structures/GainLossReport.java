package millenniumfinance.backend.data.v1.structures;

import java.util.List;
import java.util.UUID;

public class GainLossReport {
    private Integer amountOfWins;
    private Integer amountOfLosses;
    private Double unitedStatesDollarTetherBalance;
    private Double cryptocurrencyBalance;
    private Double lastSecurityPrice;
    private List<TransactionRecord> transactionRecords;
    private UUID simulationId;

    public GainLossReport() {
    }

    public GainLossReport(
            Integer amountOfWins,
            Integer amountOfLosses,
            Double unitedStatesDollarTetherBalance,
            Double cryptocurrencyBalance,
            Double lastSecurityPrice,
            List<TransactionRecord> transactionRecords,
            UUID simulationId
    ) {
        this.amountOfWins = amountOfWins;
        this.amountOfLosses = amountOfLosses;
        this.unitedStatesDollarTetherBalance = unitedStatesDollarTetherBalance;
        this.cryptocurrencyBalance = cryptocurrencyBalance;
        this.lastSecurityPrice = lastSecurityPrice;
        this.transactionRecords = transactionRecords;
        this.simulationId = simulationId;
    }

    public Integer getAmountOfWins() {
        return amountOfWins;
    }

    public void setAmountOfWins(Integer amountOfWins) {
        this.amountOfWins = amountOfWins;
    }

    public Integer getAmountOfLosses() {
        return amountOfLosses;
    }

    public void setAmountOfLosses(Integer amountOfLosses) {
        this.amountOfLosses = amountOfLosses;
    }

    public Double getUnitedStatesDollarTetherBalance() {
        return unitedStatesDollarTetherBalance;
    }

    public void setUnitedStatesDollarTetherBalance(Double unitedStatesDollarTetherBalance) {
        this.unitedStatesDollarTetherBalance = unitedStatesDollarTetherBalance;
    }

    public Double getCryptocurrencyBalance() {
        return cryptocurrencyBalance;
    }

    public void setCryptocurrencyBalance(Double cryptocurrencyBalance) {
        this.cryptocurrencyBalance = cryptocurrencyBalance;
    }

    public Double getLastSecurityPrice() {
        return lastSecurityPrice;
    }

    public void setLastSecurityPrice(Double lastSecurityPrice) {
        this.lastSecurityPrice = lastSecurityPrice;
    }

    public List<TransactionRecord> getTransactionRecords() {
        return transactionRecords;
    }

    public GainLossReport setTransactionRecords(List<TransactionRecord> transactionRecords) {
        this.transactionRecords = transactionRecords;
        return this;
    }

    public UUID getSimulationId() {
        return simulationId;
    }

    public GainLossReport setSimulationId(UUID simulationId) {
        this.simulationId = simulationId;
        return this;
    }

    public static class GainLossReportBuilder {
        private Integer amountOfWins;
        private Integer amountOfLosses;
        private Double unitedStatesDollarTetherBalance;
        private Double cryptocurrencyBalance;
        private Double lastSecurityPrice;
        private List<TransactionRecord> transactionRecords;
        private UUID simulationId;

        public GainLossReportBuilder setAmountOfWins(Integer amountOfWins) {
            this.amountOfWins = amountOfWins;
            return this;
        }

        public GainLossReportBuilder setAmountOfLosses(Integer amountOfLosses) {
            this.amountOfLosses = amountOfLosses;
            return this;
        }

        public GainLossReportBuilder setUnitedStatesDollarTetherBalance(Double unitedStatesDollarTetherBalance) {
            this.unitedStatesDollarTetherBalance = unitedStatesDollarTetherBalance;
            return this;
        }

        public GainLossReportBuilder setCryptocurrencyBalance(Double cryptocurrencyBalance) {
            this.cryptocurrencyBalance = cryptocurrencyBalance;
            return this;
        }

        public GainLossReportBuilder setLastSecurityPrice(Double lastSecurityPrice) {
            this.lastSecurityPrice = lastSecurityPrice;
            return this;
        }

        public GainLossReportBuilder setTransactionRecords(List<TransactionRecord> transactionRecords) {
            this.transactionRecords = transactionRecords;
            return this;
        }

        public GainLossReportBuilder setSimulationId(UUID simulationId) {
            this.simulationId = simulationId;
            return this;
        }

        public GainLossReport build() {
            return new GainLossReport(
                    this.amountOfWins,
                    this.amountOfLosses,
                    this.unitedStatesDollarTetherBalance,
                    this.cryptocurrencyBalance,
                    this.lastSecurityPrice,
                    this.transactionRecords,
                    this.simulationId
            );
        }
    }
}
