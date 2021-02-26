package millenniumfinance.backend.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static millenniumfinance.backend.utilities.MathCalculations.standardDeviation;

public final class FinancialCalculations {
    public static BinaryOperator<Double> accumulate = (accumulator, currentDouble) -> accumulator += currentDouble;

    public static Double summation(List<Double> toSum) {
        Double identity = 0D;
        return toSum.stream().reduce(identity, accumulate);
    }

    public static Double calculateSimpleMovingAverage(List<Double> closingPrices, int periods) {
        List<Double> firstPeriods = closingPrices.subList(0, periods);
        return summation(firstPeriods) / periods;
    }

    public static Double calculateExponentialMovingAverage(Double currentPeriodClose,
                                                           Double previousPeriodExponentialMovingAverage,
                                                           Double weightedMultiplier) {
        return currentPeriodClose * weightedMultiplier + previousPeriodExponentialMovingAverage *
                (1 - weightedMultiplier);
    }

    public static List<Double> calculateAllExponentialMovingAverages(
            List<Double> closingPrices,
            int periods,
            int offset
    ) {
        List<Double> exponentialMovingAverages = new ArrayList<>();
        Double weightedMultiplier = 2D / (periods + 1D);
        for (int i = 0; i < closingPrices.size(); i++) {
            int target = periods + offset;
            if (i < target) {
                exponentialMovingAverages.add(0D);
            } else if (i == target) {
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

    public static List<Double> calculateAllMovingAverageConvergenceDivergence(
            List<Double> smallerPeriod,
            List<Double> largerPeriod
    ) {
        List<Double> movingAverageConvergenceDivergence = new ArrayList<>();
        for (int i = 0; i < smallerPeriod.size(); i++) {
            Double smaller = smallerPeriod.get(i);
            Double larger = largerPeriod.get(i);

            if (smaller != 0 && larger != 0) {
                movingAverageConvergenceDivergence.add(smaller - larger);
            } else {
                movingAverageConvergenceDivergence.add(0D);
            }
        }
        return movingAverageConvergenceDivergence;
    }

    public static List<Double> calculateAllRollingMean(List<Double> numbers, int rollingWindowSize){
        List<Double> rollingMean = new ArrayList<>();
        int startingIndex = rollingWindowSize;
        for(int i = 1; i <= numbers.size(); i++){
            if(i >= startingIndex) {
                rollingMean.add(calculateMeanInWindow(numbers, i - rollingWindowSize, i));
            } else {
                rollingMean.add(0D);
            }
        }
        return rollingMean;
    }

    public static Double calculateMeanInWindow(List<Double> numbers, int startIndex, int endIndex){
        int length = endIndex - startIndex;
        return summation(numbers.subList(startIndex, endIndex)) / length;
    }

    public static List<Double> calculateAllRollingStandardDeviation(List<Double> numbers, int rollingWindowSize){
        List<Double> rollingStandardDeviation = new ArrayList<>();
        int startingIndex = rollingWindowSize;
        for(int i = 1; i <= numbers.size(); i++){
            if(i >= startingIndex) {
                rollingStandardDeviation.add(calculateStandardDeviationInWindow(numbers, i - rollingWindowSize, i));
            } else {
                rollingStandardDeviation.add(0D);
            }
        }
        return rollingStandardDeviation;
    }

    public static Double calculateStandardDeviationInWindow(List<Double> numbers, int startIndex, int endIndex){
        return standardDeviation(numbers.subList(startIndex, endIndex));
    }

    public static List<Double> calculateAllUpperBollingerBands(
            List<Double> movingAverages,
            List<Double> standardDeviations,
            Double deviations
    ){
        List<Double> upperBollingerBands = new ArrayList<>();
        for(int i = 0; i < movingAverages.size(); i++){
            upperBollingerBands.add(calculateUpperBollingerBand(
                    movingAverages.get(i),
                    standardDeviations.get(i),
                    deviations
            ));
        }
        return upperBollingerBands;
    }

    public static Double calculateUpperBollingerBand(Double movingAverage, Double standardDeviation, Double deviations){
        return movingAverage + (standardDeviation * deviations);
    }

    public static List<Double> calculateAllLowerBollingerBands(
            List<Double> movingAverages,
            List<Double> standardDeviations,
            Double deviations
    ){
        List<Double> lowerBollingerBands = new ArrayList<>();
        for(int i = 0; i < movingAverages.size(); i++){
            lowerBollingerBands.add(calculateLowerBollingerBand(
                    movingAverages.get(i),
                    standardDeviations.get(i),
                    deviations
            ));
        }
        return lowerBollingerBands;
    }

    public static Double calculateLowerBollingerBand(Double movingAverage, Double standardDeviation, Double deviations){
        return movingAverage - (standardDeviation * deviations);
    }

    public static List<Double> calculateAllRelativeStrengthIndex(List<Double> closePrices, int windowSize){
        List<Double> relativeStrengthIndex = new ArrayList<>();
        List<Double> differenceInPrice = new ArrayList<>();
        for (int i = 0; i < closePrices.size(); i++){
            if(i == 0 || i == closePrices.size() - 1){
                differenceInPrice.add(0D);
            } else {
                differenceInPrice.add(calculateGainOrLoss(closePrices.get(i), closePrices.get(i + 1)));
            }
        }
        List<Double> positiveDifferences = differenceInPrice
                .stream()
                .map(difference -> {
                    if(difference > 0){
                        return difference;
                    } else {
                        return 0D;
                    }
                })
                .collect(Collectors.toList());
        List<Double> negativeDifferences = differenceInPrice
                .stream()
                .map(difference -> {
                    if(difference < 0) {
                        return difference;
                    } else {
                        return 0D;
                    }
                })
                .collect(Collectors.toList());

        List<Double> positiveDifferencesAverage = new ArrayList<>();
        for(int i = 1; i <= positiveDifferences.size(); i++){
            if(i < windowSize) {
                positiveDifferencesAverage.add(0D);
            } else {
                int startIndex = i - windowSize;
                int stopIndex = i;
                List<Double> subList = positiveDifferences.subList(startIndex, stopIndex);
                positiveDifferencesAverage.add(summation(subList) / windowSize);
            }
        }

        List<Double> negativeDifferenceAverages = new ArrayList<>();
        for(int i = 1; i <= negativeDifferences.size(); i++){
            if(i < windowSize) {
                negativeDifferenceAverages.add(0D);
            } else {
                int startIndex = i - windowSize;
                int stopIndex = i;
                List<Double> subList = negativeDifferences.subList(startIndex, stopIndex);
                negativeDifferenceAverages.add(summation(subList) / windowSize);
            }
        }

        for(int i = 0; i < closePrices.size(); i++){
            if(i < windowSize - 1) {
                relativeStrengthIndex.add(0D);
            } else if(i == windowSize - 1) {
                relativeStrengthIndex.add(100 - (100 / (1 + ((positiveDifferencesAverage.get(i)/windowSize)/(negativeDifferenceAverages.get(i)/windowSize)))));
            } else {
                relativeStrengthIndex.add(100 - (100 / (1 + (((positiveDifferencesAverage.get(i - 1) * (windowSize - 1)) + positiveDifferencesAverage.get(i))/-((negativeDifferenceAverages.get(i - 1) * (windowSize - 1)) + negativeDifferenceAverages.get(i))))));
            }
        }

        return relativeStrengthIndex;
    }

    public static Double calculateGainOrLoss(Double purchasePrice, Double soldPrice){
        return (soldPrice - purchasePrice) / purchasePrice;
    }
}
