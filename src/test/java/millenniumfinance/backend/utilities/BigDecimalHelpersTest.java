package millenniumfinance.backend.utilities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.*;
import static millenniumfinance.backend.utilities.BigDecimalHelpers.*;
import static org.junit.jupiter.api.Assertions.*;

class BigDecimalHelpersTest {
    @Test
    public void testFromNumber(){
        assertEquals("10.0000000000", fromNumber(TEN).toString());
        assertEquals("10.0000000000", fromNumber(10).toString());
        assertEquals("10.0000000000", fromNumber(10D).toString());
    }

    @Test
    public void testFormat(){
        assertEquals("0E-10", format(ZERO).toString());
        assertEquals("1.0000000000", format(ONE).toString());
        assertEquals("10.0000000000", format(TEN).toString());
    }

    @Test
    public void testSubtract(){
        assertEquals("9.0000000000", subtract(TEN, ONE).toString());
    }

    @Test
    public void testAddition(){
        assertEquals("11.0000000000", addition(TEN, ONE).toString());
    }

    @Test
    public void testDivide(){
        assertEquals("10.0000000000", divide(TEN, ONE).toString());
    }

    @Test
    public void testMultiply(){
        assertEquals("100.0000000000", multiply(TEN, TEN).toString());
    }

    @Test
    public void testIsLessThanZero(){
        assertTrue(isLessThanZero(fromNumber(-1)));
        assertFalse(isLessThanZero(ONE));
        assertFalse(isLessThanZero(ZERO));
    }

    @Test
    public void testIsGreaterThanZero(){
        assertTrue(isGreaterThanZero(ONE));
        assertFalse(isGreaterThanZero(fromNumber(-1)));
        assertFalse(isGreaterThanZero(ZERO));
    }

    @Test
    public void testIsEqualToZero(){
        assertTrue(isEqualToZero(ZERO));
        assertFalse(isEqualToZero(ONE));
        assertFalse(isEqualToZero(fromNumber(-1)));
    }

    @Test
    public void testIsGreaterThan(){
        assertTrue(isGreaterThan(TEN, ONE));
        assertFalse(isGreaterThan(ONE, TEN));
        assertFalse(isGreaterThan(ZERO, ONE));
    }

    @Test
    public void testIsLessThan(){
        assertTrue(isLessThan(ONE, TEN));
        assertFalse(isLessThan(TEN, ONE));
        assertFalse(isLessThan(TEN, ZERO));
    }

    @Test
    public void testIsEqualTo(){
        assertTrue(isEqualTo(ONE, ONE));
        assertTrue(isEqualTo(ZERO, ZERO));
        assertFalse(isEqualTo(ZERO, TEN));
        assertFalse(isEqualTo(TEN, ZERO));
        assertFalse(isEqualTo(TEN, ONE));
        assertFalse(isEqualTo(ONE, TEN));
    }

    @Test
    public void testFormatTwoPlaces(){
        assertEquals("10.00", formatTwoPlaces(TEN).toString());
    }

    @Test
    public void testConstants(){
        assertEquals("0E-10", ZEROS.toString());
        assertEquals("1.0000000000", ONES.toString());
        assertEquals("10.0000000000", TENS.toString());
        assertEquals("100.0000000000", ONE_HUNDREDS.toString());
    }
}