package com.banking;

/**
 * Loan Calculator for calculating EMI, total interest, and amortization schedules
 */
public class LoanCalculator {
    
    private Calculator calculator;
    
    public LoanCalculator() {
        this.calculator = new Calculator();
    }
    
    /**
     * Calculates monthly EMI (Equated Monthly Installment)
     * Formula: EMI = P * r * (1 + r)^n / ((1 + r)^n - 1)
     * @param principal Loan amount
     * @param annualRate Annual interest rate in percentage
     * @param tenureMonths Loan tenure in months
     * @return Monthly EMI amount
     */
    public double calculateEMI(double principal, double annualRate, int tenureMonths) {
        if (principal <= 0) {
            throw new IllegalArgumentException("Principal must be positive");
        }
        if (annualRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        if (tenureMonths <= 0) {
            throw new IllegalArgumentException("Tenure must be positive");
        }
        
        if (annualRate == 0) {
            return principal / tenureMonths;
        }
        
        double monthlyRate = annualRate / (12 * 100);
        double power = Math.pow(1 + monthlyRate, tenureMonths);
        double emi = principal * monthlyRate * power / (power - 1);
        
        return Math.round(emi * 100.0) / 100.0;
    }
    
    /**
     * Calculates total interest payable over loan tenure
     */
    public double calculateTotalInterest(double principal, double annualRate, int tenureMonths) {
        double emi = calculateEMI(principal, annualRate, tenureMonths);
        double totalPayment = emi * tenureMonths;
        return Math.round((totalPayment - principal) * 100.0) / 100.0;
    }
    
    /**
     * Calculates total amount payable (principal + interest)
     */
    public double calculateTotalAmount(double principal, double annualRate, int tenureMonths) {
        double totalInterest = calculateTotalInterest(principal, annualRate, tenureMonths);
        return principal + totalInterest;
    }
    
    /**
     * Determines loan eligibility based on income and EMI ratio
     * @param monthlyIncome Applicant's monthly income
     * @param emi Proposed EMI amount
     * @param existingEMI Existing EMI obligations
     * @return true if eligible, false otherwise
     */
    public boolean isEligible(double monthlyIncome, double emi, double existingEMI) {
        if (monthlyIncome <= 0) {
            return false;
        }
        
        double totalEMI = calculator.add((int)emi, (int)existingEMI);
        double emiRatio = (totalEMI / monthlyIncome) * 100;
        
        // EMI should not exceed 50% of monthly income
        if (emiRatio > 50) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Calculates maximum loan amount based on income
     */
    public double calculateMaxLoanAmount(double monthlyIncome, double annualRate, int tenureMonths) {
        if (monthlyIncome <= 0) {
            throw new IllegalArgumentException("Income must be positive");
        }
        
        double maxEMI = monthlyIncome * 0.5; // 50% of income
        
        if (annualRate == 0) {
            return maxEMI * tenureMonths;
        }
        
        double monthlyRate = annualRate / (12 * 100);
        double power = Math.pow(1 + monthlyRate, tenureMonths);
        double maxPrincipal = maxEMI * (power - 1) / (monthlyRate * power);
        
        return Math.round(maxPrincipal * 100.0) / 100.0;
    }
    
    /**
     * Calculates remaining principal after a certain number of payments
     */
    public double calculateRemainingPrincipal(double principal, double annualRate, 
                                               int tenureMonths, int paidMonths) {
        if (paidMonths < 0 || paidMonths > tenureMonths) {
            throw new IllegalArgumentException("Invalid paid months");
        }
        
        if (paidMonths == 0) {
            return principal;
        }
        
        if (paidMonths == tenureMonths) {
            return 0.0;
        }
        
        double emi = calculateEMI(principal, annualRate, tenureMonths);
        double monthlyRate = annualRate / (12 * 100);
        double remaining = principal;
        
        for (int i = 0; i < paidMonths; i++) {
            double interest = remaining * monthlyRate;
            double principalPaid = emi - interest;
            remaining = remaining - principalPaid;
            
            if (remaining < 0) {
                remaining = 0;
                break;
            }
        }
        
        return Math.round(remaining * 100.0) / 100.0;
    }
    
    /**
     * Calculates prepayment penalty based on remaining principal
     */
    public double calculatePrepaymentPenalty(double remainingPrincipal, double penaltyPercent) {
        if (remainingPrincipal < 0) {
            throw new IllegalArgumentException("Remaining principal cannot be negative");
        }
        
        if (penaltyPercent < 0 || penaltyPercent > 10) {
            throw new IllegalArgumentException("Penalty percent must be between 0 and 10");
        }
        
        double penalty = remainingPrincipal * (penaltyPercent / 100);
        return Math.round(penalty * 100.0) / 100.0;
    }
    
    /**
     * Determines if refinancing is beneficial
     */
    public boolean shouldRefinance(double currentRate, double newRate, double remainingPrincipal, 
                                   int remainingMonths, double refinancingCost) {
        if (newRate >= currentRate) {
            return false;
        }
        
        double currentEMI = calculateEMI(remainingPrincipal, currentRate, remainingMonths);
        double newEMI = calculateEMI(remainingPrincipal, newRate, remainingMonths);
        
        double monthlySavings = currentEMI - newEMI;
        double totalSavings = monthlySavings * remainingMonths;
        
        return totalSavings > refinancingCost;
    }
    
    /**
     * Calculates interest component in a specific EMI
     */
    public double calculateInterestInEMI(double remainingPrincipal, double annualRate) {
        if (remainingPrincipal <= 0) {
            return 0.0;
        }
        
        double monthlyRate = annualRate / (12 * 100);
        double interest = remainingPrincipal * monthlyRate;
        
        return Math.round(interest * 100.0) / 100.0;
    }
    
    /**
     * Calculates principal component in a specific EMI
     */
    public double calculatePrincipalInEMI(double emi, double interestComponent) {
        if (emi < interestComponent) {
            throw new IllegalArgumentException("EMI cannot be less than interest component");
        }
        
        return Math.round((emi - interestComponent) * 100.0) / 100.0;
    }
}
