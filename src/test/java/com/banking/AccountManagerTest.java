package com.banking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AccountManager
 */
public class AccountManagerTest {
    
    private AccountManager savingsAccount;
    private AccountManager premiumAccount;
    
    @BeforeEach
    public void setUp() {
        savingsAccount = new AccountManager("Savings", 10000);
        premiumAccount = new AccountManager("Premium", 50000);
    }
    
    @Test
    public void testAccountCreation() {
        assertEquals(10000, savingsAccount.getBalance(), 0.01);
        assertEquals("Savings", savingsAccount.getAccountType());
        assertTrue(savingsAccount.isActive());
    }
    
    @Test
    public void testAccountCreationNegativeBalance() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AccountManager("Savings", -1000);
        });
    }
    
    @Test
    public void testDepositValid() {
        boolean result = savingsAccount.deposit(5000);
        assertTrue(result);
        assertEquals(15000, savingsAccount.getBalance(), 0.01);
    }
    
    @Test
    public void testDepositZero() {
        boolean result = savingsAccount.deposit(0);
        assertFalse(result);
        assertEquals(10000, savingsAccount.getBalance(), 0.01);
    }
    
    @Test
    public void testDepositNegative() {
        boolean result = savingsAccount.deposit(-1000);
        assertFalse(result);
    }
    
    @Test
    public void testDepositExceedingLimit() {
        assertThrows(IllegalArgumentException.class, () -> {
            savingsAccount.deposit(1500000);
        });
    }
    
    @Test
    public void testDepositInactiveAccount() {
        savingsAccount.closeAccount();
        assertThrows(IllegalStateException.class, () -> {
            savingsAccount.deposit(5000);
        });
    }
    
    @Test
    public void testWithdrawValid() {
        boolean result = savingsAccount.withdraw(5000);
        assertTrue(result);
        assertEquals(5000, savingsAccount.getBalance(), 0.01);
    }
    
    @Test
    public void testWithdrawExceedingBalance() {
        boolean result = savingsAccount.withdraw(15000);
        assertFalse(result);
    }
    
    @Test
    public void testWithdrawWithOverdraft() {
        AccountManager regularAccount = new AccountManager("Regular", 5000);
        boolean result = regularAccount.withdraw(12000);
        assertTrue(result);
        assertEquals(-7000, regularAccount.getBalance(), 0.01);
    }
    
    @Test
    public void testWithdrawSavingsLimit() {
        boolean result = savingsAccount.withdraw(60000);
        assertFalse(result); // Should fail due to insufficient balance
    }
    
    @Test
    public void testWithdrawInactiveAccount() {
        savingsAccount.closeAccount();
        assertThrows(IllegalStateException.class, () -> {
            savingsAccount.withdraw(5000);
        });
    }
    
    @Test
    public void testTransferValid() {
        AccountManager targetAccount = new AccountManager("Savings", 5000);
        boolean result = savingsAccount.transfer(targetAccount, 3000);
        assertTrue(result);
        assertEquals(7000, savingsAccount.getBalance(), 0.01);
        assertEquals(8000, targetAccount.getBalance(), 0.01);
    }
    
    @Test
    public void testTransferInsufficientFunds() {
        AccountManager targetAccount = new AccountManager("Savings", 5000);
        boolean result = savingsAccount.transfer(targetAccount, 20000);
        assertFalse(result);
    }
    
    @Test
    public void testTransferInactiveAccount() {
        AccountManager targetAccount = new AccountManager("Savings", 5000);
        savingsAccount.closeAccount();
        boolean result = savingsAccount.transfer(targetAccount, 3000);
        assertFalse(result);
    }
    
    @Test
    public void testCalculateMonthlyInterest() {
        double interest = savingsAccount.calculateMonthlyInterest(6);
        assertTrue(interest > 0);
        assertEquals(50.0, interest, 0.01);
    }
    
    @Test
    public void testCalculateMonthlyInterestNonSavings() {
        double interest = premiumAccount.calculateMonthlyInterest(6);
        assertEquals(0.0, interest, 0.01);
    }
    
    @Test
    public void testCalculateMonthlyInterestNegativeRate() {
        assertThrows(IllegalArgumentException.class, () -> {
            savingsAccount.calculateMonthlyInterest(-5);
        });
    }
    
    @Test
    public void testApplyInterest() {
        double initialBalance = savingsAccount.getBalance();
        savingsAccount.applyInterest(6);
        assertTrue(savingsAccount.getBalance() > initialBalance);
    }
    
    @Test
    public void testCalculateMaintenanceChargeSavingsLowBalance() {
        AccountManager lowBalanceAccount = new AccountManager("Savings", 5000);
        double charge = lowBalanceAccount.calculateMaintenanceCharge();
        assertEquals(500.0, charge, 0.01);
    }
    
    @Test
    public void testCalculateMaintenanceChargeSavingsHighBalance() {
        double charge = savingsAccount.calculateMaintenanceCharge();
        assertEquals(0.0, charge, 0.01);
    }
    
    @Test
    public void testCalculateMaintenanceChargePremium() {
        double charge = premiumAccount.calculateMaintenanceCharge();
        assertEquals(1000.0, charge, 0.01);
    }
    
    @Test
    public void testDeductMaintenanceCharge() {
        double initialBalance = premiumAccount.getBalance();
        boolean result = premiumAccount.deductMaintenanceCharge();
        assertTrue(result);
        assertEquals(initialBalance - 1000, premiumAccount.getBalance(), 0.01);
    }
    
    @Test
    public void testDeductMaintenanceChargeInsufficientFunds() {
        AccountManager lowBalanceAccount = new AccountManager("Savings", 200);
        boolean result = lowBalanceAccount.deductMaintenanceCharge();
        assertFalse(result);
    }
    
    @Test
    public void testCanUpgradeEligible() {
        AccountManager eligibleAccount = new AccountManager("Savings", 150000);
        for (int i = 0; i < 20; i++) {
            eligibleAccount.deposit(100);
        }
        assertTrue(eligibleAccount.canUpgrade());
    }
    
    @Test
    public void testCanUpgradeInsufficientBalance() {
        assertFalse(savingsAccount.canUpgrade());
    }
    
    @Test
    public void testCanUpgradeNonSavings() {
        assertFalse(premiumAccount.canUpgrade());
    }
    
    @Test
    public void testUpgradeAccountSuccess() {
        AccountManager eligibleAccount = new AccountManager("Savings", 150000);
        for (int i = 0; i < 20; i++) {
            eligibleAccount.deposit(100);
        }
        boolean result = eligibleAccount.upgradeAccount("Premium");
        assertTrue(result);
        assertEquals("Premium", eligibleAccount.getAccountType());
        assertEquals(50000, eligibleAccount.getOverdraftLimit(), 0.01);
    }
    
    @Test
    public void testUpgradeAccountNotEligible() {
        boolean result = savingsAccount.upgradeAccount("Premium");
        assertFalse(result);
    }
    
    @Test
    public void testCloseAccountWithPositiveBalance() {
        boolean result = savingsAccount.closeAccount();
        assertTrue(result);
        assertFalse(savingsAccount.isActive());
    }
    
    @Test
    public void testCloseAccountWithNegativeBalance() {
        savingsAccount.withdraw(5000);
        AccountManager overdraftAccount = new AccountManager("Regular", 5000);
        overdraftAccount.withdraw(15000);
        boolean result = overdraftAccount.closeAccount();
        assertFalse(result);
    }
    
    @Test
    public void testIsOverdrawn() {
        assertFalse(savingsAccount.isOverdrawn());
        AccountManager regularAccount = new AccountManager("Regular", 5000);
        regularAccount.withdraw(7000);
        assertTrue(regularAccount.isOverdrawn());
    }
    
    @Test
    public void testCalculateOverdraftFeeNoOverdraft() {
        double fee = savingsAccount.calculateOverdraftFee();
        assertEquals(0.0, fee, 0.01);
    }
    
    @Test
    public void testCalculateOverdraftFeeSmallOverdraft() {
        AccountManager regularAccount = new AccountManager("Regular", 5000);
        regularAccount.withdraw(10000);
        double fee = regularAccount.calculateOverdraftFee();
        assertEquals(500.0, fee, 0.01);
    }
    
    @Test
    public void testCalculateOverdraftFeeLargeOverdraft() {
        AccountManager premiumAccount = new AccountManager("Premium", 5000);
        premiumAccount.withdraw(16000);
        double fee = premiumAccount.calculateOverdraftFee();
        assertEquals(1000.0, fee, 0.01);
    }
    
    @Test
    public void testGetAvailableBalance() {
        double available = savingsAccount.getAvailableBalance();
        assertEquals(10000, available, 0.01);
        
        double availablePremium = premiumAccount.getAvailableBalance();
        assertEquals(100000, availablePremium, 0.01);
    }
    
    @Test
    public void testTransactionCount() {
        assertEquals(0, savingsAccount.getTransactionCount());
        savingsAccount.deposit(1000);
        assertEquals(1, savingsAccount.getTransactionCount());
        savingsAccount.withdraw(500);
        assertEquals(2, savingsAccount.getTransactionCount());
    }
    
    @Test
    public void testOverdraftLimitBySavingsType() {
        assertEquals(0, savingsAccount.getOverdraftLimit(), 0.01);
        assertEquals(50000, premiumAccount.getOverdraftLimit(), 0.01);
        
        AccountManager regularAccount = new AccountManager("Regular", 5000);
        assertEquals(10000, regularAccount.getOverdraftLimit(), 0.01);
    }
}
