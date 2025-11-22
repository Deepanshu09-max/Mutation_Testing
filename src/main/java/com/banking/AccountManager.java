package com.banking;

/**
 * Account Manager for handling bank account operations
 */
public class AccountManager {
    
    private double balance;
    private String accountType;
    private double overdraftLimit;
    private Calculator calculator;
    private int transactionCount;
    private boolean isActive;
    
    public AccountManager(String accountType, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        
        this.accountType = accountType;
        this.balance = initialBalance;
        this.calculator = new Calculator();
        this.transactionCount = 0;
        this.isActive = true;
        
        // Set overdraft limit based on account type
        if (accountType.equals("Premium")) {
            this.overdraftLimit = 50000;
        } else if (accountType.equals("Savings")) {
            this.overdraftLimit = 0;
        } else {
            this.overdraftLimit = 10000;
        }
    }
    
    /**
     * Deposits amount into account
     */
    public boolean deposit(double amount) {
        if (!isActive) {
            throw new IllegalStateException("Account is not active");
        }
        
        if (amount <= 0) {
            return false;
        }
        
        if (amount > 1000000) {
            throw new IllegalArgumentException("Single deposit cannot exceed 10 lakhs");
        }
        
        balance += amount;
        transactionCount++;
        return true;
    }
    
    /**
     * Withdraws amount from account
     */
    public boolean withdraw(double amount) {
        if (!isActive) {
            throw new IllegalStateException("Account is not active");
        }
        
        if (amount <= 0) {
            return false;
        }
        
        if (amount > balance + overdraftLimit) {
            return false;
        }
        
        if (accountType.equals("Savings") && amount > 50000) {
            throw new IllegalArgumentException("Withdrawal limit exceeded for savings account");
        }
        
        balance -= amount;
        transactionCount++;
        return true;
    }
    
    /**
     * Transfers amount to another account
     */
    public boolean transfer(AccountManager targetAccount, double amount) {
        if (!isActive || !targetAccount.isActive) {
            return false;
        }
        
        if (amount <= 0) {
            return false;
        }
        
        if (this.withdraw(amount)) {
            targetAccount.deposit(amount);
            return true;
        }
        
        return false;
    }
    
    /**
     * Calculates monthly interest for savings account
     */
    public double calculateMonthlyInterest(double annualRate) {
        if (annualRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        
        if (!accountType.equals("Savings")) {
            return 0.0;
        }
        
        if (balance < 0) {
            return 0.0; // No interest on negative balance
        }
        
        double monthlyRate = annualRate / 12 / 100;
        double interest = balance * monthlyRate;
        
        return Math.round(interest * 100.0) / 100.0;
    }
    
    /**
     * Applies monthly interest to account
     */
    public void applyInterest(double annualRate) {
        double interest = calculateMonthlyInterest(annualRate);
        if (interest > 0) {
            balance += interest;
        }
    }
    
    /**
     * Calculates and deducts maintenance charges
     */
    public double calculateMaintenanceCharge() {
        if (!isActive) {
            return 0.0;
        }
        
        double charge = 0.0;
        
        if (accountType.equals("Savings")) {
            if (balance < 10000) {
                charge = 500;
            }
        } else if (accountType.equals("Premium")) {
            charge = 1000;
        } else {
            if (balance < 5000) {
                charge = 300;
            }
        }
        
        return charge;
    }
    
    /**
     * Deducts maintenance charges from account
     */
    public boolean deductMaintenanceCharge() {
        double charge = calculateMaintenanceCharge();
        
        if (charge == 0) {
            return true;
        }
        
        if (balance >= charge) {
            balance -= charge;
            return true;
        }
        
        return false;
    }
    
    /**
     * Checks if account qualifies for upgrade
     */
    public boolean canUpgrade() {
        if (!accountType.equals("Savings")) {
            return false;
        }
        
        if (balance >= 100000 && transactionCount >= 20) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Upgrades account type
     */
    public boolean upgradeAccount(String newType) {
        if (!canUpgrade()) {
            return false;
        }
        
        if (newType.equals("Premium")) {
            accountType = newType;
            overdraftLimit = 50000;
            return true;
        }
        
        return false;
    }
    
    /**
     * Closes the account
     */
    public boolean closeAccount() {
        if (balance < 0) {
            return false; // Cannot close account with negative balance
        }
        
        isActive = false;
        return true;
    }
    
    /**
     * Checks if account is overdrawn
     */
    public boolean isOverdrawn() {
        return balance < 0;
    }
    
    /**
     * Calculates overdraft fee
     */
    public double calculateOverdraftFee() {
        if (balance >= 0) {
            return 0.0;
        }
        
        double overdraftAmount = calculator.abs((int)balance);
        double fee = 0.0;
        
        if (overdraftAmount > 0 && overdraftAmount <= 10000) {
            fee = 500;
        } else if (overdraftAmount > 10000) {
            fee = 1000;
        }
        
        return fee;
    }
    
    /**
     * Gets current balance
     */
    public double getBalance() {
        return Math.round(balance * 100.0) / 100.0;
    }
    
    /**
     * Gets account type
     */
    public String getAccountType() {
        return accountType;
    }
    
    /**
     * Gets transaction count
     */
    public int getTransactionCount() {
        return transactionCount;
    }
    
    /**
     * Checks if account is active
     */
    public boolean isActive() {
        return isActive;
    }
    
    /**
     * Gets overdraft limit
     */
    public double getOverdraftLimit() {
        return overdraftLimit;
    }
    
    /**
     * Gets available balance including overdraft
     */
    public double getAvailableBalance() {
        return balance + overdraftLimit;
    }
}
