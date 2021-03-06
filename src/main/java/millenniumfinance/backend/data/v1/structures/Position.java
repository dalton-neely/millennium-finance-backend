package millenniumfinance.backend.data.v1.structures;

public class Position {
  private MarketInvolvementState state;
  private PositionTradeState trade;
  private PositionTradeStrategy action;
  
  public Position() {
    this.state = MarketInvolvementState.OUT;
    this.trade = PositionTradeState.READY;
    this.action = PositionTradeStrategy.GET_IN;
  }
  
  public boolean isReady() {
    return this.trade == PositionTradeState.READY;
  }
  
  public boolean isFailed() {
    return this.trade == PositionTradeState.FAILED;
  }
  
  public boolean isInShortHold() {
    return this.state == MarketInvolvementState.IN && this.action == PositionTradeStrategy.SHORT_TRADE;
  }
  
  public boolean isInLongHold() {
    return this.state == MarketInvolvementState.IN && this.action == PositionTradeStrategy.LONG_TRADE;
  }
  
  public boolean isOutOfMarket() {
    return this.state == MarketInvolvementState.OUT;
  }
  
  public boolean isInMarket() {
    return this.state == MarketInvolvementState.IN;
  }
  
  public boolean isOutOfMarketButDelaying() {
    return this.action == PositionTradeStrategy.DELAY_BUY;
  }
  
  public void marketBuyForLong() {
    this.state = MarketInvolvementState.IN;
    this.action = PositionTradeStrategy.LONG_TRADE;
  }
  
  public void marketBuyForShort() {
    this.state = MarketInvolvementState.IN;
    this.action = PositionTradeStrategy.SHORT_TRADE;
    this.trade = PositionTradeState.READY;
  }
  
  public void sellPositionsAtMarket() {
    this.state = MarketInvolvementState.OUT;
  }
  
  public void win() {
    this.state = MarketInvolvementState.OUT;
    this.trade = PositionTradeState.READY;
    this.action = PositionTradeStrategy.LONG_TRADE;
  }
  
  public void loss() {
    this.state = MarketInvolvementState.OUT;
    this.trade = PositionTradeState.FAILED;
  }
  
  public void delayTheBuy() {
    this.action = PositionTradeStrategy.DELAY_BUY;
  }
  
  public enum MarketInvolvementState {IN, OUT}
  
  public enum PositionTradeState {READY, FAILED}
  
  public enum PositionTradeStrategy {DELAY_BUY, LONG_TRADE, SHORT_TRADE, GET_IN}
}
