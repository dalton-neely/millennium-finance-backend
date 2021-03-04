package millenniumfinance.backend.utilities;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.util.stream.Collectors.toList;

public final class BigDecimalHelpers {
    public static final Integer PRECISION = 10;
    public static final BigDecimal ZEROS = new BigDecimal("0.0000000000");
    public static final MathContext MATH_CONTEXT = new MathContext(PRECISION, HALF_UP);

    public static List<BigDecimal> fromNumbers(Double... values) {
        return Arrays.stream(values)
                .map(BigDecimalHelpers::fromNumber)
                .collect(toList());
    }

    public static BigDecimal fromNumber(BigDecimal value){
        return format(value);
    }

    public static BigDecimal fromNumber(Double value){
        return format(new BigDecimal(value, MATH_CONTEXT));
    }

    public static BigDecimal fromNumber(Integer value){
        return format(new BigDecimal(value, MATH_CONTEXT));
    }

    public static BigDecimal format(BigDecimal bigDecimal){
        return bigDecimal.setScale(PRECISION, HALF_UP);
    }

    public static BigDecimal addition(BigDecimal first, BigDecimal second){
        return format(first.add(second, MATH_CONTEXT));
    }

    public static BigDecimal subtract(BigDecimal first, BigDecimal second){
        return format(first.subtract(second, MATH_CONTEXT));
    }

    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor){
        return format(dividend.divide(divisor, MATH_CONTEXT));
    }

    public static BigDecimal multiply(BigDecimal multiplicand, BigDecimal multiplier){
        return format(multiplicand.multiply(multiplier, MATH_CONTEXT));
    }

    public static boolean isLessThanZero(BigDecimal bigDecimal){
        return bigDecimal.compareTo(ZERO) < 0;
    }

    public static boolean isGreaterThanZero(BigDecimal bigDecimal){
        return bigDecimal.compareTo(ZERO) > 0;
    }

    public static boolean isEqualToZero(BigDecimal bigDecimal){
        return bigDecimal.compareTo(ZERO) == 0;
    }
}
