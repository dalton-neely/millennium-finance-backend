package millenniumfinance.backend.utilities;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_UP;
import static java.util.stream.Collectors.toList;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.MATH_CONTEXT;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.ZEROS;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.divide;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.fromNumber;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.subtract;
import static millenniumfinance.backend.utilities.MathCalculations.standardDeviation;

public final class FinancialCalculations {
  public static final Logger logger = LoggerFactory.getLogger(FinancialCalculations.class);
  public static MathContext mathContext = new MathContext(10, HALF_UP);
  public static BinaryOperator<BigDecimal> accumulate = (accumulator, current) -> accumulator.add(current, mathContext);
  
  public static List<BigDecimal> calculateAllExponentialMovingAverages(
      List<BigDecimal> closingPrices,
      Integer periods,
      Integer offset
  ) {
    List<BigDecimal> exponentialMovingAverages = new ArrayList<>();
    BigDecimal weightedMultiplier = calculateWeightedMultiplier(periods);
    for (int i = 0; i < closingPrices.size(); i++) {
      int target = periods + offset;
      int modifiedIndex = i + 1;
      if (modifiedIndex < target) {
        exponentialMovingAverages.add(ZEROS);
      } else if (modifiedIndex == target) {
        exponentialMovingAverages.add(calculateSimpleMovingAverage(closingPrices, periods));
      } else {
        exponentialMovingAverages.add(
            calculateExponentialMovingAverage(
                closingPrices.get(i),
                closingPrices.get(i - 1),
                weightedMultiplier
            )
        );
      }
    }
    return exponentialMovingAverages;
  }
  
  public static BigDecimal calculateWeightedMultiplier(Integer periods) {
    return fromNumber(2).divide(fromNumber(periods).add(ONE), MATH_CONTEXT);
  }
  
  public static BigDecimal calculateSimpleMovingAverage(List<BigDecimal> closingPrices, Integer periods) {
    List<BigDecimal> firstPeriods = closingPrices.subList(0, periods);
    BigDecimal sum = summation(firstPeriods);
    BigDecimal periodsConverted = fromNumber(periods);
    return fromNumber(sum.divide(periodsConverted, MATH_CONTEXT));
  }
  
  public static BigDecimal calculateExponentialMovingAverage(BigDecimal currentPeriodClose,
                                                             BigDecimal previousPeriodExponentialMovingAverage,
                                                             BigDecimal weightedMultiplier) {
    BigDecimal priceTimesMultiplier = currentPeriodClose.multiply(weightedMultiplier, MATH_CONTEXT);
    BigDecimal oneMinusMultiplier = fromNumber(1).subtract(weightedMultiplier, MATH_CONTEXT);
    BigDecimal previousEmaTimesMultiplier = previousPeriodExponentialMovingAverage.multiply(oneMinusMultiplier, MATH_CONTEXT);
    return fromNumber(priceTimesMultiplier.add(previousEmaTimesMultiplier, MATH_CONTEXT));
  }
  
  public static BigDecimal summation(List<BigDecimal> toSum) {
    return fromNumber(toSum.stream().reduce(ZEROS, accumulate));
  }
  
  public static List<BigDecimal> calculateAllMovingAverageConvergenceDivergence(
      List<BigDecimal> smallerPeriod,
      List<BigDecimal> largerPeriod
  ) {
    List<BigDecimal> movingAverageConvergenceDivergence = new ArrayList<>();
    for (int i = 0; i < smallerPeriod.size(); i++) {
      BigDecimal smaller = smallerPeriod.get(i);
      BigDecimal larger = largerPeriod.get(i);
      
      if (smaller.compareTo(ZEROS) != 0 && larger.compareTo(ZEROS) != 0) {
        movingAverageConvergenceDivergence.add(fromNumber(smaller.subtract(larger, MATH_CONTEXT)));
      } else {
        movingAverageConvergenceDivergence.add(ZEROS);
      }
    }
    return movingAverageConvergenceDivergence;
  }
  
  public static List<BigDecimal> calculateAllRollingMean(List<BigDecimal> numbers, int rollingWindowSize) {
    List<BigDecimal> rollingMean = new ArrayList<>();
    for (int i = 1; i <= numbers.size(); i++) {
      if (i >= rollingWindowSize) {
        rollingMean.add(calculateMeanInWindow(numbers, i - rollingWindowSize, i));
      } else {
        rollingMean.add(ZEROS);
      }
    }
    return rollingMean;
  }
  
  public static BigDecimal calculateMeanInWindow(List<BigDecimal> numbers, int startIndexInclusive, int endIndexNotInclusive) {
    int length = endIndexNotInclusive - startIndexInclusive;
    BigDecimal sum = summation(numbers.subList(startIndexInclusive, endIndexNotInclusive));
    return fromNumber(sum.divide(fromNumber(length), mathContext));
  }
  
  public static List<BigDecimal> calculateAllRollingStandardDeviation(List<BigDecimal> numbers, int rollingWindowSize) {
    List<BigDecimal> rollingStandardDeviation = new ArrayList<>();
    for (int i = 1; i <= numbers.size(); i++) {
      if (i >= rollingWindowSize) {
        rollingStandardDeviation.add(fromNumber(calculateStandardDeviationInWindow(numbers, i - rollingWindowSize, i)));
      } else {
        rollingStandardDeviation.add(ZEROS);
      }
    }
    return rollingStandardDeviation;
  }
  
  public static BigDecimal calculateStandardDeviationInWindow(List<BigDecimal> numbers, int startIndexInclusive, int endIndexNonInclusive) {
    return fromNumber(standardDeviation(numbers.subList(startIndexInclusive, endIndexNonInclusive)));
  }
  
  public static List<BigDecimal> calculateAllUpperBollingerBands(
      List<BigDecimal> movingAverages,
      List<BigDecimal> standardDeviations,
      BigDecimal deviations
  ) {
    List<BigDecimal> upperBollingerBands = new ArrayList<>();
    for (int i = 0; i < movingAverages.size(); i++) {
      upperBollingerBands.add(calculateUpperBollingerBand(
          movingAverages.get(i),
          standardDeviations.get(i),
          deviations
      ));
    }
    return upperBollingerBands;
  }
  
  public static BigDecimal calculateUpperBollingerBand(BigDecimal movingAverage, BigDecimal standardDeviation, BigDecimal deviations) {
    if (movingAverage.compareTo(ZEROS) == 0 || standardDeviation.compareTo(ZEROS) == 0) {
      return ZEROS;
    } else {
      return fromNumber(movingAverage.add(standardDeviation.multiply(deviations, MATH_CONTEXT), MATH_CONTEXT));
    }
  }
  
  public static List<BigDecimal> calculateAllLowerBollingerBands(
      List<BigDecimal> movingAverages,
      List<BigDecimal> standardDeviations,
      BigDecimal deviations
  ) {
    List<BigDecimal> lowerBollingerBands = new ArrayList<>();
    for (int i = 0; i < movingAverages.size(); i++) {
      lowerBollingerBands.add(calculateLowerBollingerBand(
          movingAverages.get(i),
          standardDeviations.get(i),
          deviations
      ));
    }
    return lowerBollingerBands;
  }
  
  public static BigDecimal calculateLowerBollingerBand(BigDecimal movingAverage, BigDecimal standardDeviation, BigDecimal deviations) {
    if (movingAverage.compareTo(ZEROS) == 0 || standardDeviation.compareTo(ZEROS) == 0) {
      return ZEROS;
    } else {
      return fromNumber(movingAverage.subtract(standardDeviation.multiply(deviations), MATH_CONTEXT));
    }
  }
  
  public static List<BigDecimal> calculateAllRelativeStrengthIndex(List<BigDecimal> closePrices, int windowSize) {
//        List<BigDecimal> relativeStrengthIndex = new ArrayList<>();
//        List<BigDecimal> differenceInPrice = new ArrayList<>();
//        for (int i = 0; i < closePrices.size(); i++) {
//            if (i == 0 || i == closePrices.size() - 1) {
//                differenceInPrice.add(0D);
//            } else {
//                differenceInPrice.add(calculateGainOrLoss(closePrices.get(i), closePrices.get(i + 1)));
//            }
//        }
//        List<BigDecimal> positiveDifferences = differenceInPrice
//                .stream()
//                .map(difference -> {
//                    if (difference > 0) {
//                        return difference;
//                    } else {
//                        return 0D;
//                    }
//                })
//                .collect(Collectors.toList());
//        List<BigDecimal> negativeDifferences = differenceInPrice
//                .stream()
//                .map(difference -> {
//                    if (difference < 0) {
//                        return difference;
//                    } else {
//                        return 0D;
//                    }
//                })
//                .collect(Collectors.toList());
//
//        List<BigDecimal> positiveDifferencesAverage = new ArrayList<>();
//        for (int i = 1; i <= positiveDifferences.size(); i++) {
//            if (i < windowSize) {
//                positiveDifferencesAverage.add(0D);
//            } else {
//                int startIndex = i - windowSize;
//                int stopIndex = i;
//                List<BigDecimal> subList = positiveDifferences.subList(startIndex, stopIndex);
//                positiveDifferencesAverage.add(summation(subList) / windowSize);
//            }
//        }
//
//        List<BigDecimal> negativeDifferenceAverages = new ArrayList<>();
//        for (int i = 1; i <= negativeDifferences.size(); i++) {
//            if (i < windowSize) {
//                negativeDifferenceAverages.add(0D);
//            } else {
//                int startIndex = i - windowSize;
//                int stopIndex = i;
//                List<BigDecimal> subList = negativeDifferences.subList(startIndex, stopIndex);
//                negativeDifferenceAverages.add(summation(subList) / windowSize);
//            }
//        }
//
//        for (int i = 0; i < closePrices.size(); i++) {
//            if (i < windowSize - 1) {
//                relativeStrengthIndex.add(0D);
//            } else if (i == windowSize - 1) {
//                relativeStrengthIndex.add(100 - (100 / (1 + ((positiveDifferencesAverage.get(i) / windowSize) / (negativeDifferenceAverages.get(i) / windowSize)))));
//            } else {
//                relativeStrengthIndex.add(100 - (100 / (1 + (((positiveDifferencesAverage.get(i - 1) * (windowSize - 1)) + positiveDifferencesAverage.get(i)) / -((negativeDifferenceAverages.get(i - 1) * (windowSize - 1)) + negativeDifferenceAverages.get(i))))));
//            }
//        }
//
//        return relativeStrengthIndex;
    System.out.println(closePrices);
    List<BigDecimal> soldPrices = closePrices.subList(1, closePrices.size());
//        soldPrices.add(0.0);
    System.out.println(soldPrices);
    List<BigDecimal> gainOrLoss = calculateAllGainOrLoss(closePrices.subList(0, closePrices.size() - 1), soldPrices);
    List<BigDecimal> gains = separateGains(gainOrLoss);
    List<BigDecimal> averageGains = calculateAverageGains(gains, windowSize);
    List<BigDecimal> losses = separateLosses(gainOrLoss);
    List<BigDecimal> averageLosses = calculateAverageGains(losses, windowSize);
    System.out.println(gainOrLoss);
    List<BigDecimal> rsi = new ArrayList<>();
    
    for (int index = 0; index < averageGains.size(); index++) {
      if (index >= windowSize - 2) {
        BigDecimal rs;
        if (averageGains.get(index).compareTo(ZEROS) == 0 || averageLosses.get(index).compareTo(ZEROS) == 0) {
          rs = fromNumber(0);
        } else {
          rs = averageGains.get(index).divide(averageLosses.get(index), MATH_CONTEXT);
        }
        System.out.println("average gain: " + averageGains.get(index));
        System.out.println("average loss: " + averageLosses.get(index));
        System.out.println("rs: " + rs);
        BigDecimal plusOne = fromNumber(1).add(rs, MATH_CONTEXT);
        BigDecimal hundredOver = fromNumber(100).divide(plusOne, MATH_CONTEXT);
        BigDecimal hundredMinus = fromNumber(100).subtract(hundredOver, MATH_CONTEXT);
        rsi.add(fromNumber(hundredMinus));
//                rsi.add(calculateRsiStepTwo(averageGains.get(index - 1), gains.get(index), averageLosses.get(index - 1), losses.get(index), fromNumber(windowSize)));
      } else {
        rsi.add(ZEROS);
      }
    }
    return rsi;
  }
  
  public static List<BigDecimal> calculateAllGainOrLoss(List<BigDecimal> purchasePrices, List<BigDecimal> soldPrices) {
    List<BigDecimal> gainOrLosses = new ArrayList<>();
    for (int index = 0; index < purchasePrices.size(); index++) {
      gainOrLosses.add(gainLoss(purchasePrices.get(index), soldPrices.get(index)));
    }
    return gainOrLosses;
  }
  
  public static List<BigDecimal> separateGains(List<BigDecimal> gainOrLosses) {
    return gainOrLosses
        .stream()
        .map(gainOrLoss -> gainOrLoss.compareTo(ZEROS) > 0 ? gainOrLoss : ZEROS)
        .collect(toList());
  }
  
  public static List<BigDecimal> calculateAverageGains(List<BigDecimal> numbers, Integer windowSize) {
    List<BigDecimal> averageGains = new ArrayList<>();
    for (int index = 0; index < numbers.size(); index++) {
      if (index < windowSize - 1) {
        averageGains.add(ZEROS);
      } else if (index == windowSize - 1) {
        List<BigDecimal> newList = numbers.subList(0, index);
        System.out.println(newList.toString());
        averageGains.add(fromNumber(summation(newList).divide(fromNumber(windowSize), MATH_CONTEXT)));
      } else {
        averageGains.add(fromNumber(
            averageGains.get(averageGains.size() - 1)
                .multiply(fromNumber(windowSize - 1), MATH_CONTEXT)
                .add(numbers.get(index), MATH_CONTEXT)
                .divide(fromNumber(windowSize), MATH_CONTEXT)
        ));
      }
    }
    return averageGains.stream().map(BigDecimal::abs).collect(Collectors.toList());
  }
  
  public static List<BigDecimal> separateLosses(List<BigDecimal> gainOrLosses) {
    return gainOrLosses
        .stream()
        .map(gainOrLoss -> gainOrLoss.compareTo(ZEROS) < 0 ? gainOrLoss : ZEROS)
        .collect(toList());
  }
  
  public static BigDecimal gainLoss(BigDecimal purchasePrice, BigDecimal soldPrice) {
    return divide(subtract(soldPrice, purchasePrice), purchasePrice);
  }
  
  public static BigDecimal calculateRsiStepOne(BigDecimal averageGainSum, BigDecimal averageLossSum, BigDecimal periods) {
    BigDecimal averageGain = averageGainSum.divide(periods, MATH_CONTEXT);
    BigDecimal averageLoss = averageLossSum.divide(periods, MATH_CONTEXT);
    BigDecimal gainOverLoss = averageGain.divide(averageLoss, MATH_CONTEXT);
    BigDecimal gainOverLossPlusOne = gainOverLoss.add(fromNumber(1));
    BigDecimal hundredOverGainLoss = fromNumber(100).divide(gainOverLossPlusOne, MATH_CONTEXT);
    return fromNumber(100).subtract(hundredOverGainLoss);
  }
  
  public static BigDecimal calculateRsiStepTwo(BigDecimal previousAverageGain, BigDecimal currentGain, BigDecimal previousAverageLoss, BigDecimal currentLoss, BigDecimal periods) {
    BigDecimal adjustedPeriods = periods.subtract(fromNumber(1), MATH_CONTEXT);
    BigDecimal adjustedAverageGain = adjustedPeriods.add(currentGain, MATH_CONTEXT);
    BigDecimal adjustedAverageLoss = adjustedPeriods.add(currentLoss, MATH_CONTEXT);
    BigDecimal gainStepOne = adjustedAverageGain.multiply(previousAverageGain, MATH_CONTEXT);
    BigDecimal lossStepOne = adjustedAverageLoss.multiply(previousAverageLoss, MATH_CONTEXT);
    BigDecimal gainStepTwo = gainStepOne.divide(periods, MATH_CONTEXT);
    BigDecimal lossStepTwo = lossStepOne.divide(periods, MATH_CONTEXT);
    if (gainStepTwo.compareTo(ZEROS) == 0 || lossStepTwo.compareTo(ZEROS) == 0) {
      return fromNumber(ZEROS);
    }
    BigDecimal gainOverLoss = gainStepTwo.divide(lossStepTwo, MATH_CONTEXT);
    BigDecimal gainOverLossPlusOne = gainOverLoss.add(fromNumber(1));
    BigDecimal hundredOverGainLoss = fromNumber(100).divide(gainOverLossPlusOne, MATH_CONTEXT);
    return fromNumber(fromNumber(100).subtract(hundredOverGainLoss, MATH_CONTEXT));
  }
}
