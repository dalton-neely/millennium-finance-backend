package millenniumfinance.backend.services;

import millenniumfinance.backend.data.v1.classes.DataRowContainer;
import millenniumfinance.backend.data.v1.classes.DataTableContainer;
import millenniumfinance.backend.data.v1.structures.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public final class SimulationBot {
    public static final int BUY_WHEN_RSI_LESS_THAN_PARAMETER = 50;
    public static final int PERCENTAGE_MULTIPLIER_TO_15_LOWER_TO_BUY_PARAMETER = 1;
    public static final double SELL_WHEN_CURRENT_PRICE_ABOVE_BUY_PRICE_MULTIPLIER = 1.035;
    public static final int BUY_WHEN_CURRENT_RSI_IS_BELOW_THIS_PERCENTAGE = 18;
    public static final int BUY_WHEN_CURRENT_PRICE_BELOW_LOWER_BOLLINGER_MULTIPLIER = 1;
    public static final double SELL_WHEN_CURRENT_CLOSE_IS_GREATER_THAN_BUY_PRICE_MULTIPLIER = 1.03;
    public static final int SELL_WHEN_CURRENT_RSI_IS_ABOVE_THIS_PERCENTAGE = 75;
    public static final double SELL_WHEN_LOSS_PERCENTAGE_IS_LESS_THAN_THIS = -0.04D;
    public static final int SELL_WHEN_CURRENT_CLOSE_IS_GREATER_THAN_UPPER_BOLLINGER_MULTIPLIER = 1;
    private final Logger logger = getLogger(SimulationBot.class);

    public void sayHi() {
        logger.debug("Hi, from the simulation bot!");
    }

    private Double buy(Double price, Double balance) {
        double amountOfSymbol = balance / price;
        double transactionFee = 0.001 * amountOfSymbol;
        return amountOfSymbol - transactionFee;
    }

    private Double sell(Double price, Double balance) {
        double usdt = balance * price;
        double transactionFee = 0.001 * usdt;
        return usdt - transactionFee;
    }

    public GainLossReport runSimulation(DataTableContainer dataTableContainer, BotSimulationInput botSimulationInput) {
        List<DataRowContainer> dataRows = dataTableContainer.getDataTable().getDataRows();
        Position position = new Position("OUT");
        Double usdtBalance = 3000D;
        Double btcBalance = 0D;
        Double buyPrice = 1D;
        List<Integer> buyTimes = new ArrayList<>();
        List<Double> buys = new ArrayList<>();
        List<Integer> sellTimesW = new ArrayList<>();
        List<Double> sellsW = new ArrayList<>();
        List<Integer> sellTimesL = new ArrayList<>();
        List<Double> sellsL = new ArrayList<>();
        List<Double> btc = new ArrayList<>();
        List<Double> usdt = new ArrayList<>();
        List<Long> timestamp = new ArrayList<>();
        int wins = 0;
        int losses = 0;

        // Start at 224 because you need the 15minute lower bollinger band to start buying
        for (int index = 224; index < dataRows.size(); index++) {
            DataRow currentDataRow = dataRows.get(index).getDataRow();
            Candlestick currentCandlestick = currentDataRow.getCandlestickContainer().getCandlestick();
            Double currentClose = currentCandlestick.getClosePrice();
            Long currentTime = currentCandlestick.getCloseTime().getTime();
            Double current15RSI = currentDataRow.getFifteenMinuteRelativeStrengthIndex90Periods();
            Double current15Upper = currentDataRow.getFifteenMinuteUpperBollingerBand225Periods();
            Double current15Lower = currentDataRow.getFifteenMinuteLowerBollingerBand222Periods();
            Double currentMACDSignal = currentDataRow.getMovingAverageConvergenceDivergenceSignal();
            if (position.getTrade().equals("READY")) {
                if (!currentMACDSignal.isNaN()) {
                    if (
                            currentClose > botSimulationInput.getWhenToSellForShortTrade().getPriceFloorMultiplier() * buyPrice &&
                                    currentClose > current15Upper &&
                                    position.getState().equals("IN") &&
                                    position.getAction().equals("SHORT TRADE")
                    ) {
                        wins++;
                        usdtBalance = sell(currentClose, btcBalance);
                        btcBalance = 0D;
                        sellTimesW.add(index);
                        sellsW.add(currentClose);
                        usdt.add(usdtBalance);
                        timestamp.add(currentTime);
                        position.marketSell();
                        position.win();
                        System.out.println("######################### SELL WIN ###################################");
                        System.out.println("SELL-Time: " + currentTime);
                        System.out.println("SELL-Price: " + currentClose);
                        System.out.println("USDT-Balance: " + usdtBalance);
                        System.out.println("PASS TRADE");
                        System.out.println("RSI: " + current15RSI);
                        System.out.println("######################################################################");
                    } else if (
                        currentClose < botSimulationInput.getWhenToBuyParameters().getLowerBollingerCeilingMultiplier() * current15Lower &&
                                current15RSI < botSimulationInput.getWhenToBuyParameters().getRsiCeiling() &&
                                position.getState().equals("OUT")
                    ) {
                        btcBalance = buy(currentClose, usdtBalance);
                        buyPrice = currentClose;
                        usdtBalance = 0D;
                        buyTimes.add(index);
                        buys.add(buyPrice);
                        btc.add(btcBalance);
                        position.marketBuy();
                        System.out.println("######################### BUY ###################################");
                        System.out.println("Buy-Time: " + currentTime);
                        System.out.println("Buy-Price: " + currentClose);
                        System.out.println("BTC-Balance: " + btc);
                        System.out.println("MARKET BUY");
                        System.out.println("RSI: " + current15RSI);
                        System.out.println("######################################################################");
                    } else if (
                        currentClose > botSimulationInput.getWhenToSellForLongTrade().getUpperBollingerFloorMultiplier() * current15Upper &&
                                position.getState().equals("IN") &&
                                position.getAction().equals("LONG TRADE")
                    ) {
                        if(
                             currentClose > botSimulationInput.getWhenToSellForLongTrade().getPriceFloorMultiplier() * buyPrice &&
                             current15RSI > botSimulationInput.getWhenToSellForLongTrade().getRsiFloor()
                        ){
                            wins++;
                            usdtBalance = sell(currentClose, btcBalance);
                            btcBalance = 0D;
                            sellTimesW.add(index);
                            sellsW.add(currentClose);
                            usdt.add(usdtBalance);
                            timestamp.add(currentTime);
                            position.marketSell();
                            position.win();
                            System.out.println("######################### SELL WIN ###################################");
                            System.out.println("SELL-Time: " + currentTime);
                            System.out.println("SELL-Price: " + currentClose);
                            System.out.println("USDT-Balance: " + usdtBalance);
                            System.out.println("SUCCESS");
                            System.out.println("RSI: " + current15RSI);
                            System.out.println("######################################################################");
                        }
                    } else if (
                            (currentClose - buyPrice) / buyPrice < botSimulationInput.getWhenToSellForALoss().getFloorGainLossPercentage() &&
                            position.getState().equals("IN")
                    ) {
                        losses++;
                        usdtBalance = sell(currentClose, btcBalance);
                        btcBalance = 0D;
                        sellTimesL.add(index);
                        sellsL.add(currentClose);
                        usdt.add(usdtBalance);
                        timestamp.add(currentTime);
                        position.marketSell();
                        position.loss();
                        System.out.println("######################### SELL LOSE ###################################");
                        System.out.println("SELL-Time: " + currentTime);
                        System.out.println("SELL-Price: " + currentClose);
                        System.out.println("USDT-Balance: " + usdtBalance);
                        System.out.println("FAIL");
                        System.out.println("RSI: " + current15RSI);
                        System.out.println("######################################################################");
                    }
                }
            } else if (position.getTrade().equals("FAILED")) {
                if (current15RSI >= botSimulationInput.getWhenToDelayABuyAfterALossParameters().getRsiCeiling() &&
                        position.getState().equals("OUT")) {
                    position.delay();
                } else if (
                        currentClose < botSimulationInput.getWhenToBuyAfterALossParameters().getPriceCeilingMultiplier() * current15Lower &&
                                current15RSI < botSimulationInput.getWhenToBuyAfterALossParameters().getRsiCeiling() &&
                                position.getState().equals("OUT") &&
                                position.getAction().equals("DELAY BUY")
                ) {
                    buyPrice = currentDataRow.getCandlestickContainer().getCandlestick().getClosePrice();
                    btcBalance = buy(buyPrice, usdtBalance);
                    usdtBalance = 0D;
                    buyTimes.add(index);
                    buys.add(buyPrice);
                    btc.add(btcBalance);
                    position.marketShort();
                    position.ready();
                    System.out.println("######################### BUY ###################################");
                    System.out.println("BUY-Time: " + currentTime);
                    System.out.println("BUY-Price: " + currentClose);
                    System.out.println("BTC-Balance: " + btcBalance);
                    System.out.println("DELAY BUY");
                    System.out.println("RSI: " + current15RSI);
                    System.out.println("#################################################################");
                }
            }
        }

        System.out.println();
        System.out.println("ENDING RESULT +++++++++++++++++++++++++++++++");
        System.out.println("USDT BALANCE: " + usdtBalance);
        System.out.println("BTC BALANCE: " + btcBalance);
        System.out.println("Wins: " + wins);
        System.out.println("Losses: " + losses);
        System.out.println("Last Price: " + dataRows.get(dataRows.size() - 1).getDataRow().getCandlestickContainer().getCandlestick().getClosePrice());

        GainLossReport gainLossReport = new GainLossReport();
        gainLossReport.setWins(wins);
        gainLossReport.setLosses(losses);
        gainLossReport.setUsdtBalance(usdtBalance);
        gainLossReport.setBtcBalance(btcBalance);
        gainLossReport.setLastPrice(dataRows.get(dataRows.size() - 1).getDataRow().getCandlestickContainer().getCandlestick().getClosePrice());
        return gainLossReport;
    }
}
