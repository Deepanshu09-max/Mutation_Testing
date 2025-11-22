package com.banking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for LoanCalculator
 */
public class LoanCalculatorTest {
    
    private LoanCalculator loanCalculator;
    
    @BeforeEach
    public void setUp() {
        loanCalculator = new LoanCalculator();
    }
    
    @Test
    public void testCalculateEMIWithPositiveValues() {
        double emi = loanCalculator.calculateEMI(100000, 10, 12);
        assertTrue(emi > 0);
        assertEquals(8791.59, emi, 0.01);
    }
    
    @Test
    public void testCalculateEMIWithZeroInterest() {
        double emi = loanCalculator.calculateEMI(120000, 0, 12);
        assertEquals(10000.0, emi, 0.01);
    }
    
    @Test
    public void testCalculateEMIInvalidPrincipal() {
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculator.calculateEMI(-1000, 10, 12);
        });
    }
    
    @Test
    public void testCalculateEMINegativeRate() {
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculator.calculateEMI(100000, -5, 12);
        });
    }
    
    @Test
    public void testCalculateEMIInvalidTenure() {
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculator.calculateEMI(100000, 10, 0);
        });
    }
    
    @Test
    public void testCalculateTotalInterest() {
        double interest = loanCalculator.calculateTotalInterest(100000, 10, 12);
        assertTrue(interest > 0);
        assertEquals(5499.08, interest, 0.01);
    }
    
    @Test
    public void testCalculateTotalAmount() {
        double total = loanCalculator.calculateTotalAmount(100000, 10, 12);
        assertEquals(105499.08, total, 0.01);
    }
    
    @Test
    public void testIsEligibleWithValidIncome() {
        boolean eligible = loanCalculator.isEligible(50000, 20000, 5000);
        assertTrue(eligible);
    }
    
    @Test
    public void testIsEligibleWithHighEMI() {
        boolean eligible = loanCalculator.isEligible(50000, 30000, 5000);
        assertFalse(eligible);
    }
    
    @Test
    public void testIsEligibleWithZeroIncome() {
        boolean eligible = loanCalculator.isEligible(0, 10000, 0);
        assertFalse(eligible);
    }
    
    @Test
    public void testIsEligibleWithNegativeIncome() {
        boolean eligible = loanCalculator.isEligible(-1000, 10000, 0);
        assertFalse(eligible);
    }
    
    @Test
    public void testCalculateMaxLoanAmount() {
        double maxLoan = loanCalculator.calculateMaxLoanAmount(50000, 10, 60);
        assertTrue(maxLoan > 0);
    }
    
    @Test
    public void testCalculateMaxLoanAmountZeroRate() {
        double maxLoan = loanCalculator.calculateMaxLoanAmount(50000, 0, 24);
        assertEquals(600000.0, maxLoan, 0.01);
    }
    
    @Test
    public void testCalculateMaxLoanInvalidIncome() {
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculator.calculateMaxLoanAmount(-1000, 10, 60);
        });
    }
    
    @Test
    public void testCalculateRemainingPrincipalZeroPayments() {
        double remaining = loanCalculator.calculateRemainingPrincipal(100000, 10, 12, 0);
        assertEquals(100000.0, remaining, 0.01);
    }
    
    @Test
    public void testCalculateRemainingPrincipalAllPaid() {
        double remaining = loanCalculator.calculateRemainingPrincipal(100000, 10, 12, 12);
        assertEquals(0.0, remaining, 0.01);
    }
    
    @Test
    public void testCalculateRemainingPrincipalMidTerm() {
        double remaining = loanCalculator.calculateRemainingPrincipal(100000, 10, 12, 6);
        assertTrue(remaining > 0 && remaining < 100000);
    }
    
    @Test
    public void testCalculateRemainingPrincipalInvalidMonths() {
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculator.calculateRemainingPrincipal(100000, 10, 12, -1);
        });
    }
    
    @Test
    public void testCalculateRemainingPrincipalExceedingMonths() {
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculator.calculateRemainingPrincipal(100000, 10, 12, 15);
        });
    }
    
    @Test
    public void testCalculatePrepaymentPenalty() {
        double penalty = loanCalculator.calculatePrepaymentPenalty(50000, 2);
        assertEquals(1000.0, penalty, 0.01);
    }
    
    @Test
    public void testCalculatePrepaymentPenaltyZeroPercent() {
        double penalty = loanCalculator.calculatePrepaymentPenalty(50000, 0);
        assertEquals(0.0, penalty, 0.01);
    }
    
    @Test
    public void testCalculatePrepaymentPenaltyInvalidPrincipal() {
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculator.calculatePrepaymentPenalty(-1000, 2);
        });
    }
    
    @Test
    public void testCalculatePrepaymentPenaltyInvalidPercent() {
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculator.calculatePrepaymentPenalty(50000, 15);
        });
    }
    
    @Test
    public void testShouldRefinanceHigherRate() {
        boolean shouldRefinance = loanCalculator.shouldRefinance(12, 15, 100000, 24, 5000);
        assertFalse(shouldRefinance);
    }
    
    @Test
    public void testShouldRefinanceLowerRateWithBenefit() {
        boolean shouldRefinance = loanCalculator.shouldRefinance(12, 8, 100000, 24, 1000);
        assertTrue(shouldRefinance);
    }
    
    @Test
    public void testShouldRefinanceLowerRateNoBenefit() {
        boolean shouldRefinance = loanCalculator.shouldRefinance(12, 11, 100000, 12, 10000);
        assertFalse(shouldRefinance);
    }
    
    @Test
    public void testCalculateInterestInEMI() {
        double interest = loanCalculator.calculateInterestInEMI(50000, 12);
        assertTrue(interest > 0);
        assertEquals(500.0, interest, 0.01);
    }
    
    @Test
    public void testCalculateInterestInEMIZeroBalance() {
        double interest = loanCalculator.calculateInterestInEMI(0, 12);
        assertEquals(0.0, interest, 0.01);
    }
    
    @Test
    public void testCalculatePrincipalInEMI() {
        double principal = loanCalculator.calculatePrincipalInEMI(5000, 500);
        assertEquals(4500.0, principal, 0.01);
    }
    
    @Test
    public void testCalculatePrincipalInEMIInvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculator.calculatePrincipalInEMI(500, 1000);
        });
    }
}
