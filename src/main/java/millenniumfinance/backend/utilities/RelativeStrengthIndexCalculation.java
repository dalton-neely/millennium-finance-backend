package millenniumfinance.backend.utilities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.addition;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.divide;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.isEqualToZero;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.isGreaterThanZero;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.multiply;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.subtract;

public final class RelativeStrengthIndexCalculation {
  public static boolean isUpwardTrend(BigDecimal closeNow, BigDecimal closePrevious) {
    return isGreaterThanZero(subtract(closeNow, closePrevious));
  }
  
  public static boolean isDownwardTrend(BigDecimal closeNow, BigDecimal closePrevious) {
    return isGreaterThanZero(subtract(closePrevious, closeNow));
  }
  
  // price * weight + SMA * (1.0 – weight)
  public static BigDecimal smoothedMovingAverageFirst(List<BigDecimal> prices) {
    BigDecimal weight = divide(ONE, fromNumber(prices.size()));
    BigDecimal inverseWeight = subtract(ONE, weight);
    BigDecimal sma = simpleMovingAverage(prices);
    BigDecimal lastPrice = prices.get(prices.size() - 1);
    return addition(multiply(lastPrice, weight), multiply(sma, inverseWeight));
  }
  
  public static BigDecimal simpleMovingAverage(List<BigDecimal> prices) {
    return divide(summation(prices), fromNumber(prices.size()));
  }
  
  public static BigDecimal summation(List<BigDecimal> numbers) {
    BigDecimal sum = ZERO;
    for (BigDecimal number : numbers) {
      sum = addition(sum, number);
    }
    return sum;
  }
  
  // price * weight + previous SMA * (1.0 – weight)
  public static BigDecimal smoothedMovingAverageSecond(List<BigDecimal> prices, BigDecimal previousSma) {
    BigDecimal weight = divide(ONE, fromNumber(prices.size()));
    BigDecimal inverseWeight = subtract(ONE, weight);
    BigDecimal lastPrice = prices.get(prices.size() - 1);
    return addition(multiply(lastPrice, weight), multiply(previousSma, inverseWeight));
  }
  
  public static List<BigDecimal> filterUpwardTrends(List<BigDecimal> prices) {
    List<BigDecimal> upwardTrends = new ArrayList<>();
    for (int index = 0; index < prices.size(); index++) {
      if (index == 0) {
        upwardTrends.add(fromNumber(0));
      } else {
        BigDecimal closeNow = prices.get(index);
        BigDecimal closePrevious = prices.get(index - 1);
        if (isUpwardTrend(closeNow, closePrevious)) {
          upwardTrends.add(subtract(closeNow, closePrevious));
        } else {
          upwardTrends.add(fromNumber(0));
        }
      }
    }
    return upwardTrends;
  }
  
  public static List<BigDecimal> filterDownwardTrends(List<BigDecimal> prices) {
    List<BigDecimal> downwardTrends = new ArrayList<>();
    for (int index = 0; index < prices.size(); index++) {
      if (index == 0) {
        downwardTrends.add(fromNumber(0));
      } else {
        BigDecimal closeNow = prices.get(index);
        BigDecimal closePrevious = prices.get(index - 1);
        if (isDownwardTrend(closeNow, closePrevious)) {
          downwardTrends.add(subtract(closePrevious, closeNow));
        } else {
          downwardTrends.add(fromNumber(0));
        }
      }
    }
    return downwardTrends;
  }
  
  public static List<BigDecimal> rollingSma(List<BigDecimal> number, Integer window) {
    List<BigDecimal> rollingSma = new ArrayList<>();
    for (int index = 0; index < number.size(); index++) {
      if (index >= window) {
        List<BigDecimal> currentSet = number.subList(index - window + 1, index + 1);
        rollingSma.add(divide(summation(currentSet), fromNumber(window)));
      } else {
        rollingSma.add(fromNumber(0));
      }
    }
    return rollingSma;
  }
  
  public static List<BigDecimal> calculateAverageGain(List<BigDecimal> prices, Integer periods) {
    List<BigDecimal> upwardTrends = filterUpwardTrends(prices);
    return rollingSma(upwardTrends, periods);
  }
  
  public static List<BigDecimal> calculateAverageLoss(List<BigDecimal> prices, Integer periods) {
    List<BigDecimal> downwardTrends = filterDownwardTrends(prices);
    return rollingSma(downwardTrends, periods);
  }
  
  public static List<BigDecimal> calculateRs(List<BigDecimal> averageGain, List<BigDecimal> averageLoss) {
    List<BigDecimal> rs = new ArrayList<>();
    for (int index = 0; index < averageGain.size(); index++) {
      BigDecimal currentAverageGain = averageGain.get(index);
      BigDecimal currentAverageLoss = averageLoss.get(index);
      if (!isEqualToZero(currentAverageGain) && !isEqualToZero(currentAverageLoss)) {
        rs.add(divide(currentAverageGain, currentAverageLoss));
      } else {
        rs.add(fromNumber(0));
      }
    }
    return rs;
  }
  
  public static List<BigDecimal> calculateRsi(List<BigDecimal> prices, Integer periods) {
    List<BigDecimal> rsi = new ArrayList<>();
    List<BigDecimal> averageGain = calculateAverageGain(prices, periods);
    List<BigDecimal> averageLoss = calculateAverageLoss(prices, periods);
    List<BigDecimal> rs = calculateRs(averageGain, averageLoss);
    BigDecimal hundred = fromNumber(100);
    for (BigDecimal currentRs : rs) {
      if (!isEqualToZero(currentRs)) {
        rsi.add(subtract(hundred, divide(hundred, addition(fromNumber(1), currentRs))));
      } else {
        rsi.add(fromNumber(0));
      }
    }
    return rsi;
  }
}
