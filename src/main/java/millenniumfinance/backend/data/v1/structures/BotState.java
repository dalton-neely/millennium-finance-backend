package millenniumfinance.backend.data.v1.structures;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import millenniumfinance.backend.data.v1.enumerations.PositionState;
import millenniumfinance.backend.data.v1.structures.TransactionRecord.TransactionRecordBuilder;
import millenniumfinance.backend.data.v2.structures.BotSimulationInput;
import millenniumfinance.backend.data.v2.structures.StopLossParameters;
import static java.math.RoundingMode.HALF_UP;
import static millenniumfinance.backend.data.v1.enumerations.PositionState.BUY;
import static millenniumfinance.backend.data.v1.enumerations.PositionState.SELL;
import static millenniumfinance.backend.data.v1.enumerations.PositionState.STOP_LOSS;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.ZEROS;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.addition;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.divide;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.formatTwoPlaces;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.isGreaterThan;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.isGreaterThanZero;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.isLessThan;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.isLessThanZero;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.multiply;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.subtract;
import static millenniumfinance.backend.utilities.FinancialCalculations.gainLoss;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
  private BigDecimal transactionFee;
  private BotSimulationInput input;
  
  public void reset() {
    this.startingBalance = input.getStarting().getStartingBalance();
    this.cashBalance = startingBalance;
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
  
  private void calculate() {
    assetMarketValue = formatTwoPlaces(multiply(currentAssetPrice, assetBalance));
    portfolioMarketValue = formatTwoPlaces(addition(assetMarketValue, cashBalance));
    potentialAmountOfAssetToBuy = divide(cashBalance, currentAssetPrice);
    potentialCostOfAssetToBuy = formatTwoPlaces(multiply(potentialAmountOfAssetToBuy, currentAssetPrice));
    potentialLeftOverBalanceAfterBuy = formatTwoPlaces(transactionFee(subtract(cashBalance, potentialCostOfAssetToBuy)));
    potentialRevenueFromSellOfAsset = formatTwoPlaces(transactionFee(multiply(currentAssetPrice, assetBalance)));
    if (isHolding) {
      BigDecimal purchasePrice = multiply(lastAssetPrice, assetBalance);
      potentialGainLoss = gainLoss(purchasePrice, assetMarketValue);
      if (isGreaterThanZero(potentialGainLoss)) {
        potentialLoss = ZEROS;
        potentialGain = subtract(assetMarketValue, purchasePrice);
      } else {
        potentialGain = ZEROS;
        potentialLoss = subtract(purchasePrice, assetMarketValue);
      }
    }
  }
  
  private BigDecimal transactionFee(BigDecimal amount) {
    return multiply(amount, transactionFee);
  }
  
  public Boolean isConditionsForBuy() {
    return isReadyToBuy() &&
//                isLessThan(this.marketValue, multiply(this.lowerBollinger, fromNumber(1.1))) &&
        isLessThan(rsi, input.getBullMarket().getUptrend().getBuy().getRsiCeiling().getValue());
  }
  
  public Boolean isReadyToBuy() {
    return !isHolding;
  }
  
  public Boolean isConditionsForSell() {
    return isReadyToSell() &&
//                isGreaterThanZero(potentialGainLoss) &&
//                isGreaterThan(this.marketValue, multiply(this.upperBollinger, fromNumber(0.9))) &&
        isGreaterThan(rsi, input.getBullMarket().getUptrend().getSell().getRsiFloor().getValue());
  }
  
  public Boolean isReadyToSell() {
    return isHolding;
  }
  
  public Boolean isConditionsForStopLoss() {
    StopLossParameters parameters = input.getBearMarket().getUptrend().getStopLoss();
    Boolean isAmountBelowCostBasis = parameters.getAmountBelowCostBasis().getActive();
    isAmountBelowCostBasis = isAmountBelowCostBasis && isGreaterThanZero(parameters.getAmountBelowCostBasis().getValue());
    
    return isReadyToSell() &&
        isLessThanZero(potentialGainLoss) &&
        isAmountBelowCostBasis;
  }
  
  public void buy() {
    cashBalance = potentialLeftOverBalanceAfterBuy;
    assetBalance = potentialAmountOfAssetToBuy;
    isHolding = true;
    lastAssetPrice = currentAssetPrice;
    transact(BUY);
  }
  
  private void transact(PositionState positionState) {
    TransactionRecordBuilder builder = TransactionRecord.builder()
        .cashBalance(cashBalance)
        .assetBalance(assetBalance)
        .assetPrice(lastAssetPrice)
        .positionState(positionState)
        .timestamp(time)
        .rsi(rsi);
    
    if (positionState.equals(SELL) || positionState.equals(STOP_LOSS)) {
      builder.realizedGainLoss(potentialGainLoss)
          .gain(potentialGain)
          .loss(potentialLoss);
    }
    
    transactions.add(builder.build());
  }
  
  public void sell() {
    sellBase(SELL);
  }
  
  private void sellBase(PositionState state) {
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
  
  public void stopLoss() {
    sellBase(STOP_LOSS);
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
  
  private BigDecimal winLossRatio() {
    if (wins != 0 && losses != 0) {
      return divide(fromNumber(wins), fromNumber(losses));
    } else {
      return fromNumber(0);
    }
  }
  
  private BigDecimal realizedGainLoss() {
    if (!isHolding) {
      return gainLoss(startingBalance, cashBalance);
    } else {
      return ZEROS;
    }
  }
  
  private BigDecimal unrealizedGainLoss() {
    if (isHolding) {
      return gainLoss(startingBalance, potentialRevenueFromSellOfAsset);
    } else {
      return ZEROS;
    }
  }
}
