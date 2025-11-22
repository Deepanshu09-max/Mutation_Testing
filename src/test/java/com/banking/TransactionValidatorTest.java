package com.banking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for TransactionValidator
 */
public class TransactionValidatorTest {
    
    private TransactionValidator validator;
    
    @BeforeEach
    public void setUp() {
        validator = new TransactionValidator();
    }
    
    @Test
    public void testIsValidAmountPositive() {
        assertTrue(validator.isValidAmount(10000));
    }
    
    @Test
    public void testIsValidAmountZero() {
        assertFalse(validator.isValidAmount(0));
    }
    
    @Test
    public void testIsValidAmountNegative() {
        assertFalse(validator.isValidAmount(-1000));
    }
    
    @Test
    public void testIsValidAmountExceedingLimit() {
        assertFalse(validator.isValidAmount(600000));
    }
    
    @Test
    public void testIsValidAccountNumberValid() {
        assertTrue(validator.isValidAccountNumber("1234567890"));
        assertTrue(validator.isValidAccountNumber("12345678901234"));
    }
    
    @Test
    public void testIsValidAccountNumberNull() {
        assertFalse(validator.isValidAccountNumber(null));
    }
    
    @Test
    public void testIsValidAccountNumberEmpty() {
        assertFalse(validator.isValidAccountNumber(""));
    }
    
    @Test
    public void testIsValidAccountNumberTooShort() {
        assertFalse(validator.isValidAccountNumber("123456789"));
    }
    
    @Test
    public void testIsValidAccountNumberTooLong() {
        assertFalse(validator.isValidAccountNumber("12345678901234567"));
    }
    
    @Test
    public void testIsValidAccountNumberWithLetters() {
        assertFalse(validator.isValidAccountNumber("123ABC7890"));
    }
    
    @Test
    public void testIsValidIFSCValid() {
        assertTrue(validator.isValidIFSC("SBIN0001234"));
        assertTrue(validator.isValidIFSC("HDFC0AB1234"));
    }
    
    @Test
    public void testIsValidIFSCNull() {
        assertFalse(validator.isValidIFSC(null));
    }
    
    @Test
    public void testIsValidIFSCWrongLength() {
        assertFalse(validator.isValidIFSC("SBIN001234"));
    }
    
    @Test
    public void testIsValidIFSCInvalidFifthChar() {
        assertFalse(validator.isValidIFSC("SBIN1001234"));
    }
    
    @Test
    public void testIsValidIFSCInvalidFirstChars() {
        assertFalse(validator.isValidIFSC("12IN0001234"));
    }
    
    @Test
    public void testIsDailyLimitExceededFalse() {
        assertFalse(validator.isDailyLimitExceeded(100000, 50000));
    }
    
    @Test
    public void testIsDailyLimitExceededTrue() {
        assertTrue(validator.isDailyLimitExceeded(150000, 100000));
    }
    
    @Test
    public void testIsDailyLimitExceededExact() {
        assertFalse(validator.isDailyLimitExceeded(100000, 100000));
    }
    
    @Test
    public void testIsDailyLimitExceededNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.isDailyLimitExceeded(-1000, 50000);
        });
    }
    
    @Test
    public void testIsValidTransactionTimeValid() {
        assertTrue(validator.isValidTransactionTime(9));
        assertTrue(validator.isValidTransactionTime(12));
        assertTrue(validator.isValidTransactionTime(17));
    }
    
    @Test
    public void testIsValidTransactionTimeInvalid() {
        assertFalse(validator.isValidTransactionTime(8));
        assertFalse(validator.isValidTransactionTime(18));
        assertFalse(validator.isValidTransactionTime(22));
    }
    
    @Test
    public void testIsValidTransactionTimeInvalidHour() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.isValidTransactionTime(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            validator.isValidTransactionTime(24);
        });
    }
    
    @Test
    public void testIsSuspiciousTransactionHighAmount() {
        assertTrue(validator.isSuspiciousTransaction(450000, 3, 30));
    }
    
    @Test
    public void testIsSuspiciousTransactionHighFrequency() {
        assertTrue(validator.isSuspiciousTransaction(50000, 6, 5));
    }
    
    @Test
    public void testIsSuspiciousTransactionRoundAmount() {
        assertTrue(validator.isSuspiciousTransaction(200000, 2, 30));
    }
    
    @Test
    public void testIsSuspiciousTransactionNormal() {
        assertFalse(validator.isSuspiciousTransaction(50000, 3, 30));
    }
    
    @Test
    public void testIsSuspiciousTransactionInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.isSuspiciousTransaction(50000, -1, 30);
        });
    }
    
    @Test
    public void testIsValidPINFourDigits() {
        assertTrue(validator.isValidPIN("2580"));
        assertTrue(validator.isValidPIN("9753"));
    }
    
    @Test
    public void testIsValidPINSixDigits() {
        assertTrue(validator.isValidPIN("258036"));
    }
    
    @Test
    public void testIsValidPINNull() {
        assertFalse(validator.isValidPIN(null));
    }
    
    @Test
    public void testIsValidPINWrongLength() {
        assertFalse(validator.isValidPIN("123"));
        assertFalse(validator.isValidPIN("12345"));
    }
    
    @Test
    public void testIsValidPINWithLetters() {
        assertFalse(validator.isValidPIN("12AB"));
    }
    
    @Test
    public void testIsValidPINSequential() {
        assertFalse(validator.isValidPIN("1234"));
        assertFalse(validator.isValidPIN("4321"));
    }
    
    @Test
    public void testIsValidPINRepeated() {
        assertFalse(validator.isValidPIN("1111"));
        assertFalse(validator.isValidPIN("9999"));
    }
    
    @Test
    public void testIsValidBeneficiaryValid() {
        assertTrue(validator.isValidBeneficiary("1234567890", "SBIN0001234", "John Doe"));
    }
    
    @Test
    public void testIsValidBeneficiaryInvalidAccount() {
        assertFalse(validator.isValidBeneficiary("123", "SBIN0001234", "John Doe"));
    }
    
    @Test
    public void testIsValidBeneficiaryInvalidIFSC() {
        assertFalse(validator.isValidBeneficiary("1234567890", "INVALID", "John Doe"));
    }
    
    @Test
    public void testIsValidBeneficiaryNullName() {
        assertFalse(validator.isValidBeneficiary("1234567890", "SBIN0001234", null));
    }
    
    @Test
    public void testIsValidBeneficiaryEmptyName() {
        assertFalse(validator.isValidBeneficiary("1234567890", "SBIN0001234", ""));
    }
    
    @Test
    public void testIsValidBeneficiaryNameTooShort() {
        assertFalse(validator.isValidBeneficiary("1234567890", "SBIN0001234", "AB"));
    }
    
    @Test
    public void testIsValidBeneficiaryNameTooLong() {
        String longName = "A".repeat(51);
        assertFalse(validator.isValidBeneficiary("1234567890", "SBIN0001234", longName));
    }
    
    @Test
    public void testCalculateRiskScoreLowRisk() {
        int score = validator.calculateRiskScore(10000, false, false, 12);
        assertTrue(score < 50);
    }
    
    @Test
    public void testCalculateRiskScoreMediumRisk() {
        int score = validator.calculateRiskScore(60000, false, true, 12);
        assertTrue(score >= 15 && score < 50);
    }
    
    @Test
    public void testCalculateRiskScoreHighRisk() {
        int score = validator.calculateRiskScore(150000, true, true, 3);
        assertTrue(score >= 50);
    }
    
    @Test
    public void testCalculateRiskScoreMaximum() {
        int score = validator.calculateRiskScore(200000, true, true, 2);
        assertTrue(score <= 100);
    }
    
    @Test
    public void testRequiresAdditionalVerificationTrue() {
        assertTrue(validator.requiresAdditionalVerification(75));
        assertTrue(validator.requiresAdditionalVerification(50));
    }
    
    @Test
    public void testRequiresAdditionalVerificationFalse() {
        assertFalse(validator.requiresAdditionalVerification(30));
        assertFalse(validator.requiresAdditionalVerification(49));
    }
    
    @Test
    public void testRequiresAdditionalVerificationInvalidScore() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.requiresAdditionalVerification(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            validator.requiresAdditionalVerification(101);
        });
    }
    
    @Test
    public void testIsValidDescriptionValid() {
        assertTrue(validator.isValidDescription("Payment for services"));
        assertTrue(validator.isValidDescription("Salary transfer"));
    }
    
    @Test
    public void testIsValidDescriptionNull() {
        assertFalse(validator.isValidDescription(null));
    }
    
    @Test
    public void testIsValidDescriptionEmpty() {
        assertFalse(validator.isValidDescription(""));
        assertFalse(validator.isValidDescription("   "));
    }
    
    @Test
    public void testIsValidDescriptionTooLong() {
        String longDesc = "A".repeat(201);
        assertFalse(validator.isValidDescription(longDesc));
    }
    
    @Test
    public void testIsValidDescriptionSuspiciousContent() {
        assertFalse(validator.isValidDescription("Payment <script>alert('xss')</script>"));
        assertFalse(validator.isValidDescription("DROP TABLE users"));
        assertFalse(validator.isValidDescription("SELECT * FROM accounts"));
        assertFalse(validator.isValidDescription("Transfer --comment"));
    }
}
