package com.banking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Calculator
 */
public class CalculatorTest {
    
    private Calculator calculator;
    
    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
    }
    
    @Test
    public void testAdd() {
        assertEquals(5, calculator.add(2, 3));
        assertEquals(0, calculator.add(-1, 1));
    }
    
    @Test
    public void testSubtract() {
        assertEquals(1, calculator.subtract(3, 2));
        assertEquals(-2, calculator.subtract(-1, 1));
    }
    
    @Test
    public void testMultiply() {
        assertEquals(6, calculator.multiply(2, 3));
        assertEquals(0, calculator.multiply(0, 5));
    }
    
    @Test
    public void testDivide() {
        assertEquals(2, calculator.divide(6, 3));
        assertEquals(-2, calculator.divide(-6, 3));
    }
    
    @Test
    public void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> {
            calculator.divide(5, 0);
        });
    }
    
    @Test
    public void testAbs() {
        assertEquals(5, calculator.abs(-5));
        assertEquals(5, calculator.abs(5));
        assertEquals(0, calculator.abs(0));
    }
    
    @Test
    public void testMax() {
        assertEquals(5, calculator.max(3, 5));
        assertEquals(5, calculator.max(5, 3));
        assertEquals(5, calculator.max(5, 5));
    }
}
