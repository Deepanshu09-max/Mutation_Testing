package com.banking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for InvestmentCalculator
 */
public class InvestmentCalculatorTest {
    
    private InvestmentCalculator investmentCalculator;
    
    @BeforeEach
    public void setUp() {
        investmentCalculator = new InvestmentCalculator();
    }
    
    @Test
    public void testCalculateCompoundInterest() {
        double amount = investmentCalculator.calculateCompoundInterest(10000, 10, 5, 12);
        assertTrue(amount > 10000);
        assertEquals(16453.09, amount, 0.01);
    }
    
    @Test
    public void testCalculateCompoundInterestInvalidPrincipal() {
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculateCompoundInterest(-1000, 10, 5, 12);
        });
    }
    
    @Test
    public void testCalculateCompoundInterestNegativeRate() {
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculateCompoundInterest(10000, -5, 5, 12);
        });
    }
    
    @Test
    public void testCalculateCompoundInterestInvalidYears() {
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculateCompoundInterest(10000, 10, 0, 12);
        });
    }
    
    @Test
    public void testCalculateCompoundInterestInvalidFrequency() {
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculateCompoundInterest(10000, 10, 5, 0);
        });
    }
    
    @Test
    public void testCalculateSimpleInterest() {
        double interest = investmentCalculator.calculateSimpleInterest(10000, 10, 5);
        assertEquals(5000.0, interest, 0.01);
    }
    
    @Test
    public void testCalculateSimpleInterestInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculateSimpleInterest(-10000, 10, 5);
        });
    }
    
    @Test
    public void testCalculateROIProfit() {
        double roi = investmentCalculator.calculateROI(10000, 15000);
        assertEquals(50.0, roi, 0.01);
    }
    
    @Test
    public void testCalculateROILoss() {
        double roi = investmentCalculator.calculateROI(10000, 8000);
        assertEquals(-20.0, roi, 0.01);
    }
    
    @Test
    public void testCalculateROIInvalidInvestment() {
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculateROI(0, 15000);
        });
    }
    
    @Test
    public void testCalculateSIPReturns() {
        double returns = investmentCalculator.calculateSIPReturns(5000, 12, 24);
        assertTrue(returns > 120000);
    }
    
    @Test
    public void testCalculateSIPReturnsZeroRate() {
        double returns = investmentCalculator.calculateSIPReturns(5000, 0, 24);
        assertEquals(120000.0, returns, 0.01);
    }
    
    @Test
    public void testCalculateSIPReturnsInvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculateSIPReturns(-5000, 12, 24);
        });
    }
    
    @Test
    public void testCalculateSIPReturnsInvalidMonths() {
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculateSIPReturns(5000, 12, 0);
        });
    }
    
    @Test
    public void testCalculateBreakEvenPeriod() {
        int months = investmentCalculator.calculateBreakEvenPeriod(100000, 5000);
        assertEquals(20, months);
    }
    
    @Test
    public void testCalculateBreakEvenPeriodInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculateBreakEvenPeriod(-100000, 5000);
        });
    }
    
    @Test
    public void testIsGoalAchievableTrue() {
        boolean achievable = investmentCalculator.isGoalAchievable(10000, 15000, 10, 5);
        assertTrue(achievable);
    }
    
    @Test
    public void testIsGoalAchievableFalse() {
        boolean achievable = investmentCalculator.isGoalAchievable(10000, 50000, 5, 3);
        assertFalse(achievable);
    }
    
    @Test
    public void testIsGoalAchievableInvalidParameters() {
        boolean achievable = investmentCalculator.isGoalAchievable(-10000, 50000, 5, 3);
        assertFalse(achievable);
    }
    
    @Test
    public void testCalculateRequiredMonthlySIP() {
        double sip = investmentCalculator.calculateRequiredMonthlySIP(100000, 10, 24);
        assertTrue(sip > 0);
    }
    
    @Test
    public void testCalculateRequiredMonthlySIPZeroRate() {
        double sip = investmentCalculator.calculateRequiredMonthlySIP(120000, 0, 24);
        assertEquals(5000.0, sip, 0.01);
    }
    
    @Test
    public void testCalculateRequiredMonthlySIPInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculateRequiredMonthlySIP(-100000, 10, 24);
        });
    }
    
    @Test
    public void testCalculatePortfolioValue() {
        double[] investments = {10000, 20000, 30000};
        double[] returns = {10, 15, 20};
        double value = investmentCalculator.calculatePortfolioValue(investments, returns);
        assertEquals(70000.0, value, 0.01);
    }
    
    @Test
    public void testCalculatePortfolioValueNullArray() {
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculatePortfolioValue(null, new double[]{10});
        });
    }
    
    @Test
    public void testCalculatePortfolioValueMismatchedArrays() {
        double[] investments = {10000, 20000};
        double[] returns = {10};
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculatePortfolioValue(investments, returns);
        });
    }
    
    @Test
    public void testCalculatePortfolioValueNegativeInvestment() {
        double[] investments = {-10000, 20000};
        double[] returns = {10, 15};
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculatePortfolioValue(investments, returns);
        });
    }
    
    @Test
    public void testCalculateDiversificationRatio() {
        double[] investments = {20000, 30000, 50000};
        double ratio = investmentCalculator.calculateDiversificationRatio(investments);
        assertEquals(50.0, ratio, 0.01);
    }
    
    @Test
    public void testCalculateDiversificationRatioSingleInvestment() {
        double[] investments = {100000};
        double ratio = investmentCalculator.calculateDiversificationRatio(investments);
        assertEquals(0.0, ratio, 0.01);
    }
    
    @Test
    public void testCalculateDiversificationRatioNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculateDiversificationRatio(null);
        });
    }
    
    @Test
    public void testCalculateDiversificationRatioEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculateDiversificationRatio(new double[]{});
        });
    }
    
    @Test
    public void testCalculateDiversificationRatioNegative() {
        double[] investments = {20000, -10000};
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculateDiversificationRatio(investments);
        });
    }
    
    @Test
    public void testCalculateRealReturn() {
        double realReturn = investmentCalculator.calculateRealReturn(15, 5);
        assertTrue(realReturn > 0);
        assertEquals(9.52, realReturn, 0.01);
    }
    
    @Test
    public void testCalculateRealReturnHighInflation() {
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculateRealReturn(10, 100);
        });
    }
    
    @Test
    public void testAssessRiskLevelLow() {
        String risk = investmentCalculator.assessRiskLevel(3);
        assertEquals("Low", risk);
    }
    
    @Test
    public void testAssessRiskLevelMedium() {
        String risk = investmentCalculator.assessRiskLevel(10);
        assertEquals("Medium", risk);
    }
    
    @Test
    public void testAssessRiskLevelHigh() {
        String risk = investmentCalculator.assessRiskLevel(20);
        assertEquals("High", risk);
    }
    
    @Test
    public void testAssessRiskLevelVeryHigh() {
        String risk = investmentCalculator.assessRiskLevel(30);
        assertEquals("Very High", risk);
    }
    
    @Test
    public void testAssessRiskLevelNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.assessRiskLevel(-5);
        });
    }
    
    @Test
    public void testCalculateCAGR() {
        double cagr = investmentCalculator.calculateCAGR(10000, 20000, 5);
        assertTrue(cagr > 0);
        assertEquals(14.87, cagr, 0.01);
    }
    
    @Test
    public void testCalculateCAGRInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> {
            investmentCalculator.calculateCAGR(-10000, 20000, 5);
        });
    }
}
