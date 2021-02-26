package millenniumfinance.backend.services;

import millenniumfinance.backend.data.v1.structures.*;
import millenniumfinance.backend.data.v1.structures.GainLossReport.GainLossReportBuilder;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static millenniumfinance.backend.data.v1.enumerations.PositionState.BUY;
import static millenniumfinance.backend.data.v1.enumerations.PositionState.SELL;
import static millenniumfinance.backend.data.v1.enumerations.TradeMethod.*;
import static millenniumfinance.backend.data.v1.structures.TransactionRecord.TransactionRecordBuilder;
import static millenniumfinance.backend.utilities.FinancialCalculations.calculateGainOrLoss;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public final class SimulationBot {
    // Start at 224 because you need the 15 minute lower bollinger band to start buying
    public static final int PERIOD_START_OFFSET = 224;
    public static final double BINANCE_TRANSACTION_FEE_BITCOIN = 0.001;
    private final Logger logger = getLogger(SimulationBot.class);

    private Double binanceTransactionFee(Double amountOfSymbol) {
        return BINANCE_TRANSACTION_FEE_BITCOIN * amountOfSymbol;
    }

    private Double buy(Double securityPrice, Double balance) {
        double amountOfSymbol = balance / securityPrice;
        double transactionFee = binanceTransactionFee(amountOfSymbol);
        return amountOfSymbol - transactionFee;
    }

    private Double sell(Double securityPrice, Double balance) {
        double unitedStatesDollarTether = balance * securityPrice;
        double transactionFee = binanceTransactionFee(unitedStatesDollarTether);
        return unitedStatesDollarTether - transactionFee;
    }

    public GainLossReport runSimulation(DataTable dataTable, BotSimulationInput botSimulationInput) {
        UUID simulationId = UUID.randomUUID();
        logger.debug("Running Simulation: " + simulationId.toString());
        List<DataRow> dataRows = dataTable.getDataRows();
        Position position = new Position();
        Double unitedStatesDollarTetherBalance = 3000.00;
        Double cryptocurrencyBalance = 0.00;
        Double lastBuyPrice = 0.00;
        List<TransactionRecord> transactionRecords = new ArrayList<>();
        int amountOfWins = 0;
        int amountOfLosses = 0;

        for (int index = PERIOD_START_OFFSET; index < dataRows.size(); index++) {
            // Current data for current period
            DataRow dataRow = dataRows.get(index);
            Candlestick candlestick = dataRow.getCandlestick();
            Double price = candlestick.getClosePrice();
            long time = candlestick.getCloseTime().getTime();
            Double relativeStrengthIndex = dataRow.getSmoothedRelativeStrengthIndex();
            Double upperBollingerBand = dataRow.getSmoothedUpperBollingerBand();
            Double lowerBollingerBand = dataRow.getSmoothedLowerBollingerBand();
            Double signal = dataRow.getSignal();

            if (position.isReady() && !signal.isNaN()) {
                WhenToSellForLongTrade longTradeSell = botSimulationInput.getWhenToSellForLongTrade();
                WhenToSellForShortTrade shortTradeSell = botSimulationInput.getWhenToSellForShortTrade();
                WhenToBuyParameters buyParameters = botSimulationInput.getWhenToBuyParameters();
                WhenToSellForALoss sellForALoss = botSimulationInput.getWhenToSellForALoss();

                if (price > shortTradeSell.getPriceFloorMultiplier() * lastBuyPrice &&
                        price > upperBollingerBand &&
                        position.isInShortHold()
                ) {
                    amountOfWins++;
                    unitedStatesDollarTetherBalance = sell(price, cryptocurrencyBalance);
                    cryptocurrencyBalance = 0D;
                    position.sellPositionsAtMarket();
                    position.win();
                    transactionRecords.add(new TransactionRecordBuilder()
                            .setPositionState(SELL)
                            .setTimestamp(new Date(time))
                            .setSecurityPrice(price)
                            .setUnitedStatesDollarTetherBalance(unitedStatesDollarTetherBalance)
                            .setCryptocurrencyBalance(cryptocurrencyBalance)
                            .setTradeMethod(SELL_FOR_SHORT)
                            .setRelativeStrengthIndex(relativeStrengthIndex)
                            .build());
                } else if (price < buyParameters.getLowerBollingerCeilingMultiplier() * lowerBollingerBand &&
                        relativeStrengthIndex < buyParameters.getRsiCeiling() &&
                        position.isOutOfMarket()
                ) {
                    cryptocurrencyBalance = buy(price, unitedStatesDollarTetherBalance);
                    lastBuyPrice = price;
                    unitedStatesDollarTetherBalance = 0D;
                    position.marketBuyForLong();
                    transactionRecords.add(new TransactionRecordBuilder()
                            .setPositionState(BUY)
                            .setTimestamp(new Date(time))
                            .setSecurityPrice(price)
                            .setUnitedStatesDollarTetherBalance(unitedStatesDollarTetherBalance)
                            .setCryptocurrencyBalance(cryptocurrencyBalance)
                            .setTradeMethod(BUY_NORMALLY)
                            .setRelativeStrengthIndex(relativeStrengthIndex)
                            .build());
                } else if (price > longTradeSell.getUpperBollingerFloorMultiplier() * upperBollingerBand &&
                        price > shortTradeSell.getPriceFloorMultiplier() * lastBuyPrice &&
                        relativeStrengthIndex > longTradeSell.getRsiFloor() &&
                        position.isInLongHold()
                ) {
                    amountOfWins++;
                    unitedStatesDollarTetherBalance = sell(price, cryptocurrencyBalance);
                    cryptocurrencyBalance = 0D;
                    position.sellPositionsAtMarket();
                    position.win();
                    transactionRecords.add(new TransactionRecordBuilder()
                            .setPositionState(SELL)
                            .setTimestamp(new Date(time))
                            .setSecurityPrice(price)
                            .setUnitedStatesDollarTetherBalance(unitedStatesDollarTetherBalance)
                            .setCryptocurrencyBalance(cryptocurrencyBalance)
                            .setTradeMethod(SELL_FOR_LONG)
                            .setRelativeStrengthIndex(relativeStrengthIndex)
                            .build());
                } else if (calculateGainOrLoss(price, lastBuyPrice) < sellForALoss.getFloorGainLossPercentage() &&
                        position.isInMarket()
                ) {
                    amountOfLosses++;
                    unitedStatesDollarTetherBalance = sell(price, cryptocurrencyBalance);
                    cryptocurrencyBalance = 0D;
                    position.sellPositionsAtMarket();
                    position.loss();
                    transactionRecords.add(new TransactionRecordBuilder()
                            .setPositionState(SELL)
                            .setTimestamp(new Date(time))
                            .setSecurityPrice(price)
                            .setUnitedStatesDollarTetherBalance(unitedStatesDollarTetherBalance)
                            .setCryptocurrencyBalance(cryptocurrencyBalance)
                            .setTradeMethod(SELL_FOR_LOSS)
                            .setRelativeStrengthIndex(relativeStrengthIndex)
                            .build());
                }
            } else if (position.isFailed()) {
                WhenToDelayABuyAfterALossParameters delayParameters = botSimulationInput.getWhenToDelayABuyAfterALossParameters();
                WhenToBuyAfterALossParameters buyParameters = botSimulationInput.getWhenToBuyAfterALossParameters();

                if (relativeStrengthIndex >= delayParameters.getRsiCeiling() && position.isOutOfMarket()) {
                    position.delayTheBuy();
                } else if (price < buyParameters.getPriceCeilingMultiplier() * lowerBollingerBand &&
                        relativeStrengthIndex < buyParameters.getRsiCeiling() &&
                        position.isOutOfMarketButDelaying()
                ) {
                    lastBuyPrice = price;
                    cryptocurrencyBalance = buy(lastBuyPrice, unitedStatesDollarTetherBalance);
                    unitedStatesDollarTetherBalance = 0D;
                    position.marketBuyForShort();
                    transactionRecords.add(new TransactionRecordBuilder()
                            .setPositionState(BUY)
                            .setTimestamp(new Date(time))
                            .setSecurityPrice(price)
                            .setUnitedStatesDollarTetherBalance(unitedStatesDollarTetherBalance)
                            .setCryptocurrencyBalance(cryptocurrencyBalance)
                            .setTradeMethod(BUY_AFTER_LOSS_AND_DELAY)
                            .setRelativeStrengthIndex(relativeStrengthIndex)
                            .build());
                }
            }
        }

        Double lastSecurityPrice = dataRows
                .get(dataRows.size() - 1)
                .getCandlestick()
                .getClosePrice();
        logger.debug("Finished Simulation: " + simulationId.toString());
        return new GainLossReportBuilder()
                .setAmountOfWins(amountOfWins)
                .setAmountOfLosses(amountOfLosses)
                .setUnitedStatesDollarTetherBalance(unitedStatesDollarTetherBalance)
                .setCryptocurrencyBalance(cryptocurrencyBalance)
                .setLastSecurityPrice(lastSecurityPrice)
                .setTransactionRecords(transactionRecords)
                .setSimulationId(simulationId)
                .build();
    }
}
