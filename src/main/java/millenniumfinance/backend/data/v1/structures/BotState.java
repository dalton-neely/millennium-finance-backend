package millenniumfinance.backend.data.v1.structures;

import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.data.v1.enumerations.PositionState;
import millenniumfinance.backend.data.v1.structures.TransactionRecord.TransactionRecordBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.math.RoundingMode.HALF_UP;
import static millenniumfinance.backend.data.v1.enumerations.PositionState.*;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.*;
import static millenniumfinance.backend.utilities.FinancialCalculations.gainLoss;

@Data
@NoArgsConstructor
public final class BotState {
    private BigDecimal startingBalance;
    private BigDecimal cashBalance;
    private BigDecimal assetBalance;
    private List<TransactionRecord> transactions;
    private String id;
    private Integer wins;
    private Integer losses;
    private Integer trades;
    private Boolean isHolding;
    private BigDecimal lastAssetPrice;
    private BigDecimal currentAssetPrice;
    private BigDecimal rsi;
    private BigDecimal upperBollinger;
    private BigDecimal lowerBollinger;
    private Date time;
    private BigDecimal portfolioMarketValue;
    private BigDecimal assetMarketValue;
    private BigDecimal potentialAmountOfAssetToBuy;
    private BigDecimal potentialCostOfAssetToBuy;
    private BigDecimal potentialLeftOverBalanceAfterBuy;
    private BigDecimal potentialRevenueFromSellOfAsset;
    private BigDecimal potentialGain;
    private BigDecimal potentialLoss;
    private BigDecimal potentialGainLoss;

    public void reset(BigDecimal capital) {
        this.startingBalance = capital;
        this.cashBalance = capital;
        this.isHolding = false;
        this.assetBalance = fromNumber(0);
        this.transactions = new ArrayList<>();
        this.id = UUID.randomUUID().toString();
        this.lastAssetPrice = fromNumber(0);
        this.wins = 0;
        this.losses = 0;
        this.trades = 0;
        this.rsi = fromNumber(0);
        this.upperBollinger = fromNumber(0);
        this.lowerBollinger = fromNumber(0);
        this.time = new Date();
    }

    public void consume(DataRow dataRow) {
        Candlestick candlestick = dataRow.getCandlestick();
        currentAssetPrice = fromNumber(candlestick.getClosePrice());
        rsi = fromNumber(dataRow.getRelativeStrengthIndex());
        upperBollinger = fromNumber(dataRow.getUpperBollingerBand());
        lowerBollinger = fromNumber(dataRow.getLowerBollingerBand());
        time = candlestick.getCloseTime();
        calculate();
    }

    private void calculate(){
        assetMarketValue = multiply(currentAssetPrice, assetBalance).setScale(2, HALF_UP);
        portfolioMarketValue = addition(assetMarketValue, cashBalance).setScale(2, HALF_UP);
        potentialAmountOfAssetToBuy = divide(cashBalance, currentAssetPrice);
        potentialCostOfAssetToBuy = multiply(potentialAmountOfAssetToBuy, currentAssetPrice).setScale(2, HALF_UP);
        potentialLeftOverBalanceAfterBuy = subtract(cashBalance, potentialCostOfAssetToBuy).setScale(2, HALF_UP);
        potentialRevenueFromSellOfAsset = multiply(currentAssetPrice, assetBalance).setScale(2, HALF_UP);
        if(isHolding) {
            BigDecimal purchasePrice = multiply(lastAssetPrice, assetBalance);
            potentialGainLoss = gainLoss(purchasePrice, assetMarketValue);
            if(isGreaterThanZero(potentialGainLoss)){
                potentialLoss = ZEROS;
                potentialGain = subtract(assetMarketValue, purchasePrice);
            } else {
                potentialGain = ZEROS;
                potentialLoss = subtract(purchasePrice, assetMarketValue);
            }
        }
    }

    public Boolean isConditionsForBuy() {
        return isReadyToBuy() &&
//                isLessThan(this.marketValue, multiply(this.lowerBollinger, fromNumber(1.1))) &&
                isLessThan(rsi, fromNumber(20));
    }

    public Boolean isConditionsForSell() {
        return isReadyToSell() &&
//                isGreaterThanZero(potentialGainLoss) &&
//                isGreaterThan(this.marketValue, multiply(this.upperBollinger, fromNumber(0.9))) &&
                isGreaterThan(rsi, fromNumber(80));
    }

    public Boolean isConditionsForStopLoss(){
        return isReadyToSell() &&
                isLessThanZero(potentialGainLoss) &&
                isGreaterThan(potentialLoss, fromNumber(50));
    }

    public Boolean isReadyToBuy() {
        return !isHolding;
    }

    public Boolean isReadyToSell() {
        return isHolding;
    }

    public void buy() {
        cashBalance = potentialLeftOverBalanceAfterBuy;
        assetBalance = potentialAmountOfAssetToBuy;
        isHolding = true;
        lastAssetPrice = currentAssetPrice;
        transact(BUY);
    }

    private void sellBase(PositionState state){
        cashBalance = addition(cashBalance, potentialRevenueFromSellOfAsset).setScale(2, HALF_UP);
        isHolding = false;
        assetBalance = fromNumber(0);
        if (isGreaterThanZero(subtract(currentAssetPrice, lastAssetPrice))) {
            this.wins++;
        } else {
            this.losses++;
        }
        this.trades += 2;
        transact(state);
    }

    public void sell() {
        sellBase(SELL);
    }

    public void stopLoss() {
        sellBase(STOP_LOSS);
    }

    private void transact(PositionState positionState){
        TransactionRecordBuilder builder = TransactionRecord.builder()
                .cashBalance(cashBalance)
                .assetBalance(assetBalance)
                .assetPrice(lastAssetPrice)
                .positionState(positionState)
                .timestamp(time)
                .rsi(rsi);

        if(positionState.equals(SELL) || positionState.equals(STOP_LOSS)){
            builder.realizedGainLoss(potentialGainLoss)
                    .gain(potentialGain)
                    .loss(potentialLoss);
        }

        transactions.add(builder.build());
    }

    private BigDecimal winLossRatio(){
        if(wins != 0 && losses != 0){
            return divide(fromNumber(wins), fromNumber(losses));
        } else {
            return fromNumber(0);
        }
    }

    private BigDecimal realizedGainLoss(){
        if(!isHolding){
            return gainLoss(startingBalance, cashBalance);
        } else {
            return ZEROS;
        }
    }

    private BigDecimal unrealizedGainLoss(){
        if(isHolding){
            return gainLoss(startingBalance, potentialRevenueFromSellOfAsset);
        } else {
            return ZEROS;
        }
    }

    public GainLossReport generateGainLossReport() {
        return GainLossReport.builder()
                .wins(wins)
                .losses(losses)
                .winLossRatio(winLossRatio())
                .cashBalance(cashBalance)
                .assetBalance(assetBalance)
                .lastAssetPrice(currentAssetPrice)
                .transactionRecords(transactions)
                .simulationId(id)
                .portfolioMarketValue(portfolioMarketValue)
                .realizedGainLoss(realizedGainLoss())
                .unrealizedGainLoss(unrealizedGainLoss())
                .build();
    }
}
