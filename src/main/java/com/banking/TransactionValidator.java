package com.banking;

/**
 * Transaction Validator for validating banking transactions
 */
public class TransactionValidator {
    
    private Calculator calculator;
    private static final double MAX_TRANSACTION_LIMIT = 500000;
    private static final double DAILY_LIMIT = 200000;
    
    public TransactionValidator() {
        this.calculator = new Calculator();
    }
    
    /**
     * Validates if transaction amount is within limits
     */
    public boolean isValidAmount(double amount) {
        if (amount <= 0) {
            return false;
        }
        
        if (amount > MAX_TRANSACTION_LIMIT) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Validates account number format
     */
    public boolean isValidAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            return false;
        }
        
        if (accountNumber.length() < 10 || accountNumber.length() > 16) {
            return false;
        }
        
        // Check if all characters are digits
        for (char c : accountNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Validates IFSC code format
     */
    public boolean isValidIFSC(String ifsc) {
        if (ifsc == null || ifsc.length() != 11) {
            return false;
        }
        
        // First 4 characters should be letters
        for (int i = 0; i < 4; i++) {
            if (!Character.isLetter(ifsc.charAt(i))) {
                return false;
            }
        }
        
        // 5th character should be 0
        if (ifsc.charAt(4) != '0') {
            return false;
        }
        
        // Last 6 characters should be alphanumeric
        for (int i = 5; i < 11; i++) {
            if (!Character.isLetterOrDigit(ifsc.charAt(i))) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Checks if daily transaction limit is exceeded
     */
    public boolean isDailyLimitExceeded(double currentDayTotal, double newAmount) {
        if (currentDayTotal < 0 || newAmount < 0) {
            throw new IllegalArgumentException("Amounts cannot be negative");
        }
        
        double totalAmount = currentDayTotal + newAmount;
        return totalAmount > DAILY_LIMIT;
    }
    
    /**
     * Validates transaction timing (banking hours)
     */
    public boolean isValidTransactionTime(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Invalid hour");
        }
        
        // Banking hours: 9 AM to 6 PM
        if (hour >= 9 && hour < 18) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Detects suspicious transaction patterns
     */
    public boolean isSuspiciousTransaction(double amount, int frequency, int timeWindowMinutes) {
        if (frequency < 0 || timeWindowMinutes < 0) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        
        // Suspicious if amount is very high
        if (amount > 400000) {
            return true;
        }
        
        // Suspicious if too many transactions in short time
        if (frequency > 5 && timeWindowMinutes < 10) {
            return true;
        }
        
        // Suspicious if round amounts above certain threshold
        if (amount >= 100000 && amount % 10000 == 0) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Validates PIN format
     */
    public boolean isValidPIN(String pin) {
        if (pin == null || pin.length() != 4 && pin.length() != 6) {
            return false;
        }
        
        for (char c : pin.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        
        // Check for sequential or repeated digits
        if (hasSequentialDigits(pin) || hasRepeatedDigits(pin)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Checks if PIN has sequential digits
     */
    private boolean hasSequentialDigits(String pin) {
        for (int i = 0; i < pin.length() - 1; i++) {
            int current = Character.getNumericValue(pin.charAt(i));
            int next = Character.getNumericValue(pin.charAt(i + 1));
            
            if (calculator.abs(calculator.subtract(next, current)) == 1) {
                if (i < pin.length() - 2) {
                    int nextNext = Character.getNumericValue(pin.charAt(i + 2));
                    if (calculator.abs(calculator.subtract(nextNext, next)) == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Checks if PIN has all repeated digits
     */
    private boolean hasRepeatedDigits(String pin) {
        char firstDigit = pin.charAt(0);
        for (int i = 1; i < pin.length(); i++) {
            if (pin.charAt(i) != firstDigit) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Validates beneficiary details for transfer
     */
    public boolean isValidBeneficiary(String accountNumber, String ifsc, String name) {
        if (!isValidAccountNumber(accountNumber)) {
            return false;
        }
        
        if (!isValidIFSC(ifsc)) {
            return false;
        }
        
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        
        if (name.length() < 3 || name.length() > 50) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Calculates risk score for transaction
     */
    public int calculateRiskScore(double amount, boolean isInternational, 
                                   boolean isFirstTime, int accountAge) {
        int riskScore = 0;
        
        if (amount > 100000) {
            riskScore += 30;
        } else if (amount > 50000) {
            riskScore += 15;
        }
        
        if (isInternational) {
            riskScore += 25;
        }
        
        if (isFirstTime) {
            riskScore += 20;
        }
        
        if (accountAge < 6) { // months
            riskScore += 15;
        }
        
        return min(riskScore, 100);
    }
    
    /**
     * Helper method to find minimum
     */
    private int min(int a, int b) {
        if (a < b) {
            return a;
        }
        return b;
    }
    
    /**
     * Determines if transaction requires additional verification
     */
    public boolean requiresAdditionalVerification(int riskScore) {
        if (riskScore < 0 || riskScore > 100) {
            throw new IllegalArgumentException("Risk score must be between 0 and 100");
        }
        
        return riskScore >= 50;
    }
    
    /**
     * Validates transaction description
     */
    public boolean isValidDescription(String description) {
        if (description == null) {
            return false;
        }
        
        if (description.trim().isEmpty()) {
            return false;
        }
        
        if (description.length() > 200) {
            return false;
        }
        
        // Check for special characters that might indicate injection attacks
        String[] suspiciousPatterns = {"<SCRIPT>", "DROP TABLE", "SELECT *", "--"};
        for (String pattern : suspiciousPatterns) {
            if (description.toUpperCase().contains(pattern)) {
                return false;
            }
        }
        
        return true;
    }
}
