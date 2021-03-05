package millenniumfinance.backend.services;

import millenniumfinance.backend.data.v1.structures.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public final class SimulationBot {
    // Start at 224 because you need the 15 minute lower bollinger band to start buying
    public static final int PERIOD_START_OFFSET = 224;
    public static final double BINANCE_TRANSACTION_FEE_BITCOIN = 0.001;
    private final Logger logger = getLogger(SimulationBot.class);

//    private Double binanceTransactionFee(Double amountOfSymbol) {
//        return BINANCE_TRANSACTION_FEE_BITCOIN * amountOfSymbol;
//    }
//
//    private Double buy(Double securityPrice, Double balance) {
//        double amountOfSymbol = balance / securityPrice;
//        double transactionFee = binanceTransactionFee(amountOfSymbol);
//        return amountOfSymbol - transactionFee;
//    }
//
//    private Double sell(Double securityPrice, Double balance) {
//        double unitedStatesDollarTether = balance * securityPrice;
//        double transactionFee = binanceTransactionFee(unitedStatesDollarTether);
//        return unitedStatesDollarTether - transactionFee;
//    }

    public GainLossReport runSimulation2(DataTable dataTable, BotSimulationInput input) {
        List<DataRow> dataRows = dataTable.getDataRows();
        BotState state = new BotState();
        state.reset(fromNumber(3000.00));

        for (DataRow dataRow : dataRows) {
            state.consume(dataRow);

            if (state.isConditionsForBuy()) {
                state.buy();
            } else if(state.isConditionsForStopLoss()) {
                state.stopLoss();
            } else if (state.isConditionsForSell()) {
                state.sell();
            }
        }

        return state.generateGainLossReport();
    }

//    public GainLossReport runSimulation(DataTable dataTable, BotSimulationInput botSimulationInput) {
//        UUID simulationId = UUID.randomUUID();
//        logger.debug("Running Simulation: " + simulationId.toString());
//        List<DataRow> dataRows = dataTable.getDataRows();
//        Position position = new Position();
//        Double unitedStatesDollarTetherBalance = 3000.00;
//        Double cryptocurrencyBalance = 0.00;
//        Double lastBuyPrice = 0.00;
//        List<TransactionRecord> transactionRecords = new ArrayList<>();
//        int amountOfWins = 0;
//        int amountOfLosses = 0;
//
//        for (int index = PERIOD_START_OFFSET; index < dataRows.size(); index++) {
//            // Current data for current period
//            DataRow dataRow = dataRows.get(index);
//            Candlestick candlestick = dataRow.getCandlestick();
//            Double price = candlestick.getClosePrice();
//            long time = candlestick.getCloseTime().getTime();
//            Double relativeStrengthIndex = dataRow.getSmoothedRelativeStrengthIndex();
//            Double upperBollingerBand = dataRow.getSmoothedUpperBollingerBand();
//            Double lowerBollingerBand = dataRow.getSmoothedLowerBollingerBand();
//            Double signal = dataRow.getSignal();
//
//            if (position.isReady() && !signal.isNaN()) {
//                WhenToSellForLongTrade longTradeSell = botSimulationInput.getWhenToSellForLongTrade();
//                WhenToSellForShortTrade shortTradeSell = botSimulationInput.getWhenToSellForShortTrade();
//                WhenToBuyParameters buyParameters = botSimulationInput.getWhenToBuyParameters();
//                WhenToSellForALoss sellForALoss = botSimulationInput.getWhenToSellForALoss();
//
//                if (price > shortTradeSell.getPriceFloorMultiplier() * lastBuyPrice &&
//                        price > upperBollingerBand &&
//                        position.isInShortHold()
//                ) {
//                    amountOfWins++;
//                    unitedStatesDollarTetherBalance = sell(price, cryptocurrencyBalance);
//                    cryptocurrencyBalance = 0D;
//                    position.sellPositionsAtMarket();
//                    position.win();
//                    TransactionRecord transactionRecord = TransactionRecord.builder()
//                            .positionState(SELL)
//                            .timestamp(new Date(time))
//                            .securityPrice(price)
//                            .setUnitedStatesDollarTetherBalance(unitedStatesDollarTetherBalance)
//                            .setCryptocurrencyBalance(cryptocurrencyBalance)
//                            .setTradeMethod(SELL_FOR_SHORT)
//                            .setRelativeStrengthIndex(relativeStrengthIndex)
//                            .build();
//                    transactionRecords.add(transactionRecord);
//                    logger.debug(transactionRecord.toString());
//                } else if (price < buyParameters.getLowerBollingerCeilingMultiplier() * lowerBollingerBand &&
//                        relativeStrengthIndex < buyParameters.getRsiCeiling() &&
//                        position.isOutOfMarket()
//                ) {
//                    cryptocurrencyBalance = buy(price, unitedStatesDollarTetherBalance);
//                    lastBuyPrice = price;
//                    unitedStatesDollarTetherBalance = 0D;
//                    position.marketBuyForLong();
//                    TransactionRecord transactionRecord = new TransactionRecordBuilder()
//                            .setPositionState(BUY)
//                            .setTimestamp(new Date(time))
//                            .setSecurityPrice(price)
//                            .setUnitedStatesDollarTetherBalance(unitedStatesDollarTetherBalance)
//                            .setCryptocurrencyBalance(cryptocurrencyBalance)
//                            .setTradeMethod(BUY_NORMALLY)
//                            .setRelativeStrengthIndex(relativeStrengthIndex)
//                            .build();
//                    transactionRecords.add(transactionRecord);
//                    logger.debug(transactionRecord.toString());
//                } else if (price > longTradeSell.getUpperBollingerFloorMultiplier() * upperBollingerBand &&
//                        price > shortTradeSell.getPriceFloorMultiplier() * lastBuyPrice &&
//                        relativeStrengthIndex > longTradeSell.getRsiFloor() &&
//                        position.isInLongHold()
//                ) {
//                    amountOfWins++;
//                    unitedStatesDollarTetherBalance = sell(price, cryptocurrencyBalance);
//                    cryptocurrencyBalance = 0D;
//                    position.sellPositionsAtMarket();
//                    position.win();
//                    TransactionRecord transactionRecord = new TransactionRecordBuilder()
//                            .setPositionState(SELL)
//                            .setTimestamp(new Date(time))
//                            .setSecurityPrice(price)
//                            .setUnitedStatesDollarTetherBalance(unitedStatesDollarTetherBalance)
//                            .setCryptocurrencyBalance(cryptocurrencyBalance)
//                            .setTradeMethod(SELL_FOR_LONG)
//                            .setRelativeStrengthIndex(relativeStrengthIndex)
//                            .build();
//                    transactionRecords.add(transactionRecord);
//                    logger.debug(transactionRecord.toString());
//                } else if (calculateGainOrLoss(valueOf(price), valueOf(lastBuyPrice)).doubleValue() < sellForALoss.getFloorGainLossPercentage() &&
//                        position.isInMarket()
//                ) {
//                    amountOfLosses++;
//                    unitedStatesDollarTetherBalance = sell(price, cryptocurrencyBalance);
//                    cryptocurrencyBalance = 0D;
//                    position.sellPositionsAtMarket();
//                    position.loss();
//                    TransactionRecord transactionRecord = new TransactionRecordBuilder()
//                            .setPositionState(SELL)
//                            .setTimestamp(new Date(time))
//                            .setSecurityPrice(price)
//                            .setUnitedStatesDollarTetherBalance(unitedStatesDollarTetherBalance)
//                            .setCryptocurrencyBalance(cryptocurrencyBalance)
//                            .setTradeMethod(SELL_FOR_LOSS)
//                            .setRelativeStrengthIndex(relativeStrengthIndex)
//                            .build();
//                    transactionRecords.add(transactionRecord);
//                    logger.debug(transactionRecord.toString());
//                }
//            } else if (position.isFailed()) {
//                WhenToDelayABuyAfterALossParameters delayParameters = botSimulationInput.getWhenToDelayABuyAfterALossParameters();
//                WhenToBuyAfterALossParameters buyParameters = botSimulationInput.getWhenToBuyAfterALossParameters();
//
//                if (relativeStrengthIndex >= delayParameters.getRsiCeiling() && position.isOutOfMarket()) {
//                    position.delayTheBuy();
//                } else if (price < buyParameters.getPriceCeilingMultiplier() * lowerBollingerBand &&
//                        relativeStrengthIndex < buyParameters.getRsiCeiling() &&
//                        position.isOutOfMarketButDelaying()
//                ) {
//                    lastBuyPrice = price;
//                    cryptocurrencyBalance = buy(lastBuyPrice, unitedStatesDollarTetherBalance);
//                    unitedStatesDollarTetherBalance = 0D;
//                    position.marketBuyForShort();
//                    TransactionRecord transactionRecord = new TransactionRecordBuilder()
//                            .setPositionState(BUY)
//                            .setTimestamp(new Date(time))
//                            .setSecurityPrice(price)
//                            .setUnitedStatesDollarTetherBalance(unitedStatesDollarTetherBalance)
//                            .setCryptocurrencyBalance(cryptocurrencyBalance)
//                            .setTradeMethod(BUY_AFTER_LOSS_AND_DELAY)
//                            .setRelativeStrengthIndex(relativeStrengthIndex)
//                            .build();
//                    transactionRecords.add(transactionRecord);
//                    logger.debug(transactionRecord.toString());
//                }
//            }
//        }
//
//        Double lastSecurityPrice = dataRows
//                .get(dataRows.size() - 1)
//                .getCandlestick()
//                .getClosePrice();
//        logger.debug("Finished Simulation: " + simulationId.toString());
//
//        return GainLossReport.builder().build();
////        return new GainLossReportBuilder()
////                .setAmountOfWins(amountOfWins)
////                .setAmountOfLosses(amountOfLosses)
////                .setUnitedStatesDollarTetherBalance(unitedStatesDollarTetherBalance)
////                .setCryptocurrencyBalance(cryptocurrencyBalance)
////                .setLastSecurityPrice(lastSecurityPrice)
////                .setTransactionRecords(transactionRecords)
////                .setSimulationId(simulationId)
////                .build();
//    }
}
