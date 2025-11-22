package com.banking;

/**
 * Simple Calculator class for demonstration
 */
public class Calculator {
    
    /**
     * Adds two numbers
     */
    public int add(int a, int b) {
        return a + b;
    }
    
    /**
     * Subtracts b from a
     */
    public int subtract(int a, int b) {
        return a - b;
    }
    
    /**
     * Multiplies two numbers
     */
    public int multiply(int a, int b) {
        return a * b;
    }
    
    /**
     * Divides a by b
     * @throws ArithmeticException if b is zero
     */
    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }
    
    /**
     * Returns absolute value
     */
    public int abs(int a) {
        if (a < 0) {
            return -a;
        }
        return a;
    }
    
    /**
     * Returns maximum of two numbers
     */
    public int max(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }
}
