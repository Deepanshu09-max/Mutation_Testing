package com.banking;

/**
 * Investment Calculator for compound interest, ROI, and investment analysis
 */
public class InvestmentCalculator {
    
    private Calculator calculator;
    
    public InvestmentCalculator() {
        this.calculator = new Calculator();
    }
    
    /**
     * Calculates compound interest
     * Formula: A = P(1 + r/n)^(nt)
     * @param principal Initial investment amount
     * @param annualRate Annual interest rate in percentage
     * @param years Investment period in years
     * @param compoundingFrequency Number of times interest is compounded per year
     */
    public double calculateCompoundInterest(double principal, double annualRate, 
                                            int years, int compoundingFrequency) {
        if (principal <= 0) {
            throw new IllegalArgumentException("Principal must be positive");
        }
        if (annualRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        if (years <= 0) {
            throw new IllegalArgumentException("Years must be positive");
        }
        if (compoundingFrequency <= 0) {
            throw new IllegalArgumentException("Compounding frequency must be positive");
        }
        
        double rate = annualRate / 100;
        double amount = principal * Math.pow(1 + rate / compoundingFrequency, 
                                             compoundingFrequency * years);
        
        return Math.round(amount * 100.0) / 100.0;
    }
    
    /**
     * Calculates simple interest
     */
    public double calculateSimpleInterest(double principal, double annualRate, int years) {
        if (principal <= 0 || annualRate < 0 || years <= 0) {
            throw new IllegalArgumentException("Invalid parameters for simple interest");
        }
        
        double interest = principal * (annualRate / 100) * years;
        return Math.round(interest * 100.0) / 100.0;
    }
    
    /**
     * Calculates Return on Investment (ROI)
     * ROI = (Final Value - Initial Investment) / Initial Investment * 100
     */
    public double calculateROI(double initialInvestment, double finalValue) {
        if (initialInvestment <= 0) {
            throw new IllegalArgumentException("Initial investment must be positive");
        }
        
        double gain = finalValue - initialInvestment;
        double roi = (gain / initialInvestment) * 100;
        
        return Math.round(roi * 100.0) / 100.0;
    }
    
    /**
     * Calculates future value of SIP (Systematic Investment Plan)
     * FV = P × ((1 + r)^n - 1) / r × (1 + r)
     */
    public double calculateSIPReturns(double monthlyInvestment, double annualRate, int months) {
        if (monthlyInvestment <= 0) {
            throw new IllegalArgumentException("Monthly investment must be positive");
        }
        if (months <= 0) {
            throw new IllegalArgumentException("Months must be positive");
        }
        
        if (annualRate == 0) {
            return monthlyInvestment * months;
        }
        
        double monthlyRate = annualRate / (12 * 100);
        double futureValue = monthlyInvestment * 
                            ((Math.pow(1 + monthlyRate, months) - 1) / monthlyRate) * 
                            (1 + monthlyRate);
        
        return Math.round(futureValue * 100.0) / 100.0;
    }
    
    /**
     * Calculates break-even point for an investment
     */
    public int calculateBreakEvenPeriod(double initialInvestment, double monthlyReturn) {
        if (initialInvestment <= 0 || monthlyReturn <= 0) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        
        int months = (int) Math.ceil(initialInvestment / monthlyReturn);
        return months;
    }
    
    /**
     * Determines if investment goal is achievable
     */
    public boolean isGoalAchievable(double currentAmount, double goalAmount, 
                                    double annualRate, int years) {
        if (currentAmount < 0 || goalAmount <= 0 || years <= 0) {
            return false;
        }
        
        double futureValue = calculateCompoundInterest(currentAmount, annualRate, years, 12);
        return futureValue >= goalAmount;
    }
    
    /**
     * Calculates required monthly investment to reach a goal
     */
    public double calculateRequiredMonthlySIP(double goalAmount, double annualRate, int months) {
        if (goalAmount <= 0 || months <= 0) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        
        if (annualRate == 0) {
            return goalAmount / months;
        }
        
        double monthlyRate = annualRate / (12 * 100);
        double requiredSIP = goalAmount * monthlyRate / 
                            ((Math.pow(1 + monthlyRate, months) - 1) * (1 + monthlyRate));
        
        return Math.round(requiredSIP * 100.0) / 100.0;
    }
    
    /**
     * Calculates portfolio value with multiple investments
     */
    public double calculatePortfolioValue(double[] investments, double[] returns) {
        if (investments == null || returns == null) {
            throw new IllegalArgumentException("Arrays cannot be null");
        }
        if (investments.length != returns.length) {
            throw new IllegalArgumentException("Arrays must have same length");
        }
        
        double totalValue = 0;
        for (int i = 0; i < investments.length; i++) {
            if (investments[i] < 0) {
                throw new IllegalArgumentException("Investment cannot be negative");
            }
            totalValue += investments[i] * (1 + returns[i] / 100);
        }
        
        return Math.round(totalValue * 100.0) / 100.0;
    }
    
    /**
     * Calculates diversification ratio
     */
    public double calculateDiversificationRatio(double[] investments) {
        if (investments == null || investments.length == 0) {
            throw new IllegalArgumentException("Invalid investments array");
        }
        
        double total = 0;
        double maxInvestment = 0;
        
        for (double investment : investments) {
            if (investment < 0) {
                throw new IllegalArgumentException("Investment cannot be negative");
            }
            total += investment;
            if (investment > maxInvestment) {
                maxInvestment = investment;
            }
        }
        
        if (total == 0) {
            return 0.0;
        }
        
        double ratio = (1 - (maxInvestment / total)) * 100;
        return Math.round(ratio * 100.0) / 100.0;
    }
    
    /**
     * Calculates inflation-adjusted returns
     */
    public double calculateRealReturn(double nominalReturn, double inflationRate) {
        if (inflationRate >= 100) {
            throw new IllegalArgumentException("Inflation rate too high");
        }
        
        double realReturn = ((1 + nominalReturn / 100) / (1 + inflationRate / 100) - 1) * 100;
        return Math.round(realReturn * 100.0) / 100.0;
    }
    
    /**
     * Determines investment risk level
     */
    public String assessRiskLevel(double volatility) {
        if (volatility < 0) {
            throw new IllegalArgumentException("Volatility cannot be negative");
        }
        
        if (volatility < 5) {
            return "Low";
        } else if (volatility < 15) {
            return "Medium";
        } else if (volatility < 25) {
            return "High";
        } else {
            return "Very High";
        }
    }
    
    /**
     * Calculates average annual growth rate (CAGR)
     */
    public double calculateCAGR(double initialValue, double finalValue, int years) {
        if (initialValue <= 0 || finalValue <= 0 || years <= 0) {
            throw new IllegalArgumentException("Invalid parameters for CAGR");
        }
        
        double cagr = (Math.pow(finalValue / initialValue, 1.0 / years) - 1) * 100;
        return Math.round(cagr * 100.0) / 100.0;
    }
}
