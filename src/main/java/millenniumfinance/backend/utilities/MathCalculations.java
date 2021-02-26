package millenniumfinance.backend.utilities;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static millenniumfinance.backend.utilities.FinancialCalculations.summation;

public final class MathCalculations {
    public static List<Double> squaredDifference(List<Double> numbers, Double mean) {
        List<Double> squaredDifferences = new ArrayList<>();
        for (Double number : numbers) {
            squaredDifferences.add(pow(number - mean, 2D));
        }
        return squaredDifferences;
    }

    public static Double standardDeviation(List<Double> numbers) {
        Double sumOfNumbers = summation(numbers);
        int numberOfElements = numbers.size();
        Double meanOfNumbers = sumOfNumbers / numberOfElements;
        List<Double> differences = squaredDifference(numbers, meanOfNumbers);
        double meanOfDifferences = summation(differences) / numberOfElements;
        return sqrt(meanOfDifferences);
    }
}
