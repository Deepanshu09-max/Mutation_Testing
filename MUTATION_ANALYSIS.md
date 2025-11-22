# Mutation Testing Analysis

This document contains analysis of mutation testing performed on the banking application.
The project has 1063 lines of code across 5 classes with 168 unit tests. PIT generated
518 mutations using 9 different operators, achieving 79% mutation score and 96% line coverage.

---

## 1. Mutation Operators Identified

### Unit-Level Mutation Operators (3 operators)

#### 1.1 ConditionalsBoundaryMutator

**Description:** Changes boundary conditions in relational operators (e.g., `<` to `<=`, `>` to `>=`)

**Example from Calculator.java (Line 44):**

```java
// Original code:
if (value < 0) {
    return -value;
}

// Mutated code:
if (value <= 0) {  // Changed < to <=
    return -value;
}
```

**Test Coverage:**

- Generated: 102 mutations
- Killed: 26 (25%)
- Survived: 75
- No Coverage: 1

**Why Some Survived:** Many boundary mutations survived because tests didn't specifically check edge cases where the boundary condition matters (e.g., testing exactly at value = 0).

---

#### 1.2 MathMutator

**Description:** Replaces arithmetic operators (e.g., `+` to `-`, `*` to `/`, `/` to `*`)

**Example from Calculator.java (Line 12):**

```java
// Original code:
public int add(int a, int b) {
    return a + b;
}

// Mutated code:
public int add(int a, int b) {
    return a - b;  // Changed + to -
}
```

**Example from LoanCalculator.java (Line 41):**

```java
// Original code:
double numerator = principal * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths);

// Mutated code:
double numerator = principal / monthlyRate * Math.pow(1 + monthlyRate, tenureMonths);  // * to /
```

**Test Coverage:**

- Generated: 135 mutations
- Killed: 113 (84%)
- Survived: 22
- No Coverage: 0

**Why Some Survived:** Complex mathematical formulas where the mutation produces a result that's still within acceptable ranges or where intermediate calculations mask the mutation effect.

---

#### 1.3 NegateConditionalsMutator

**Description:** Inverts conditional expressions (e.g., `==` to `!=`, `<` to `>=`, `>` to `<=`)

**Example from LoanCalculator.java (Line 26):**

```java
// Original code:
if (annualRate <= 0 || principal <= 0 || tenureMonths <= 0) {
    return -1;
}

// Mutated code:
if (annualRate > 0 || principal <= 0 || tenureMonths <= 0) {  // <= to >
    return -1;
}
```

**Example from TransactionValidator.java (Line 28):**

```java
// Original code:
if (amount > TRANSACTION_LIMIT) {
    return false;
}

// Mutated code:
if (amount <= TRANSACTION_LIMIT) {  // > to <=
    return false;
}
```

**Test Coverage:**

- Generated: 158 mutations
- Killed: 153 (97%)
- Survived: 4
- No Coverage: 1

**Strength:** This operator had the highest kill rate (97%), indicating excellent test coverage of conditional logic.

---

### Integration-Level Mutation Operators (3 operators)

#### 2.1 PrimitiveReturnsMutator (Integration Context)

**Description:** Replaces primitive return values with default values (0 for numeric, false for boolean)

**Integration Example from Calculator.java used by LoanCalculator:**

```java
// Calculator.java (Line 12)
public int add(int a, int b) {
    return a + b;  // Mutated to: return 0;
}

// LoanCalculator.java (Line 73) - Integration point
public boolean isEligible(double monthlyIncome, double emi, double existingEMI) {
    // ...
    double totalEMI = calculator.add((int)emi, (int)existingEMI);  // Uses Calculator
    // ...
}
```

**Impact:** When `Calculator.add()` returns 0 instead of the correct sum, `LoanCalculator.isEligible()` fails because the total EMI calculation is incorrect. This tests the integration between the two classes.

**Test Coverage:**

- Generated: 41 mutations
- Killed: 40 (98%)
- Survived: 0
- No Coverage: 1

**Strength:** Near-perfect kill rate (98%) shows that return values are thoroughly tested, including at integration points.

---

#### 2.2 BooleanReturnsMutators (Integration Context)

**Combined:** BooleanTrueReturnValsMutator + BooleanFalseReturnValsMutator

**Description:** Replaces boolean returns with opposite values (true to false, false to true)

**Integration Example from AccountManager.java:**

```java
// AccountManager.java (Line 60) - withdraw method
public boolean withdraw(double amount) {
    // ...
    balance -= amount;
    transactionCount++;
    return true;  // Mutated to: return false;
}

// AccountManager.java (Line 85) - transfer method uses withdraw
public boolean transfer(AccountManager targetAccount, double amount) {
    if (this.withdraw(amount)) {  // Integration point
        return targetAccount.deposit(amount);
    }
    return false;
}
```

**Impact:** If `withdraw()` returns false when it should return true, the `transfer()` method fails even though the withdrawal succeeded. This tests the integration contract between methods.

**Test Coverage:**

- BooleanTrueReturnValsMutator: 46 generated, 42 killed (91%)
- BooleanFalseReturnValsMutator: 24 generated, 23 killed (96%)
- Combined: 70 mutations, 65 killed (93%)

---

#### 2.3 MathMutator (Integration Context)

**Description:** Arithmetic mutations that propagate across method calls

**Integration Example - Calculator used by TransactionValidator:**

```java
// Calculator.java (Line 19)
public int subtract(int a, int b) {
    return a - b;  // Mutated to: return a + b;
}

// Calculator.java (Line 44)
public int abs(int value) {
    if (value < 0) {
        return -value;
    }
    return value;
}

// TransactionValidator.java (Line 167) - Integration usage
private boolean hasSequentialDigits(String pin) {
    for (int i = 0; i < pin.length() - 1; i++) {
        int current = Character.getNumericValue(pin.charAt(i));
        int next = Character.getNumericValue(pin.charAt(i + 1));

        // Uses both Calculator methods - integration point
        if (calculator.abs(calculator.subtract(next, current)) == 1) {
            // Check for three sequential digits
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
```

**Impact:** When `Calculator.subtract()` is mutated from `-` to `+`, the PIN validation logic in `TransactionValidator` fails because the sequential digit detection becomes inverted. This demonstrates integration-level mutation testing.

**Integration-Specific Mutations Killed:**

- Calculator.subtract() used by TransactionValidator: 5 tests kill these mutations
- Calculator.abs() used by TransactionValidator: 7 tests kill these mutations
- Calculator.add() used by LoanCalculator: 3 tests kill these mutations
- Calculator.abs() used by AccountManager: 2 tests kill these mutations

---

## 2. Additional Mutation Operators

### 2.4 IncrementsMutator

**Description:** Modifies increment/decrement operations (e.g., `i++` to `i--`, `i++` to `i`)

**Test Coverage:**

- Generated: 6 mutations
- Killed: 5 (83%)

---

### 2.5 InvertNegsMutator

**Description:** Removes or adds negation operators

**Example from Calculator.java (Line 45):**

```java
// Original: return -value;
// Mutated:  return value;
```

**Test Coverage:**

- Generated: 1 mutation
- Killed: 1 (100%)

---

### 2.6 EmptyObjectReturnValsMutator

**Description:** Replaces object returns with empty/null values

**Test Coverage:**

- Generated: 5 mutations
- Killed: 5 (100%)

---

## 3. Integration Testing Evidence

### 3.1 Cross-Class Integration Points

**Integration Point 1: LoanCalculator → Calculator**

```java
Location: LoanCalculator.java:73
Method: isEligible()
Integration: Uses Calculator.add()
Tests that kill integration mutations:
  - testIsEligibleWithHighEMI()
  - testIsEligibleWithValidIncome()
```

**Integration Point 2: TransactionValidator → Calculator**

```java
Location: TransactionValidator.java:167, 170
Method: hasSequentialDigits()
Integration: Uses Calculator.subtract() and Calculator.abs()
Tests that kill integration mutations:
  - testIsValidPINSequential()
  - testIsValidPINRepeated()
  - testIsValidPINFourDigits()
  - testIsValidPINSixDigits()
```

**Integration Point 3: AccountManager → Calculator**

```java
Location: AccountManager.java:236
Method: calculateOverdraftFee()
Integration: Uses Calculator.abs()
Tests that kill integration mutations:
  - testCalculateOverdraftFeeLargeOverdraft()
  - testCalculateOverdraftFeeSmallOverdraft()
```

**Integration Point 4: AccountManager → AccountManager**

```java
Location: AccountManager.java:85
Method: transfer()
Integration: Calls withdraw() and deposit() across two instances
Tests that kill integration mutations:
  - testTransferBetweenAccounts()
  - testTransferInsufficientFunds()
```

### 3.2 Integration Mutation Examples

**Example 1: Method Call Chain**
When `Calculator.add()` is mutated in the context of `LoanCalculator.isEligible()`:

- Mutation: Return 0 instead of actual sum
- Impact: Total EMI calculation becomes 0
- Result: Loan eligibility check fails incorrectly
- Killed by: testIsEligibleWithHighEMI()

**Example 2: Nested Method Calls**
When `Calculator.subtract()` is mutated in `TransactionValidator.hasSequentialDigits()`:

- Mutation: Change subtraction to addition
- Impact: Sequential digit detection logic inverts
- Result: Valid PINs are rejected, invalid PINs are accepted
- Killed by: testIsValidPINSequential()

---

## 4. Mutation Score Analysis

### 4.1 Overall Statistics

```
Total Mutations Generated: 518
Mutations Killed: 408 (79%)
Mutations Survived: 102 (20%)
No Coverage: 8 (1%)

Line Coverage: 373/387 (96%)
Test Strength: 80%
Tests Executed: 848 (1.64 tests per mutation)
```

### 4.2 Per-Operator Performance

| Operator                      | Generated | Killed | Kill Rate |
| ----------------------------- | --------- | ------ | --------- |
| InvertNegsMutator             | 1         | 1      | 100%      |
| EmptyObjectReturnValsMutator  | 5         | 5      | 100%      |
| PrimitiveReturnsMutator       | 41        | 40     | 98%       |
| NegateConditionalsMutator     | 158       | 153    | 97%       |
| BooleanFalseReturnValsMutator | 24        | 23     | 96%       |
| BooleanTrueReturnValsMutator  | 46        | 42     | 91%       |
| MathMutator                   | 135       | 113    | 84%       |
| IncrementsMutator             | 6         | 5      | 83%       |
| ConditionalsBoundaryMutator   | 102       | 26     | 25%       |

### 4.3 Why Mutations Survived

**ConditionalsBoundaryMutator (75 survived):**

- Tests didn't check exact boundary values (= 0, exact limits)
- Tests used ranges like `> 0` but didn't test `== 0` separately
- More edge case tests needed

**MathMutator (22 survived):**

- Complex formulas where mutations still produce acceptable results
- Floating-point precision masks some mutations
- Tighter assertion tolerances could help

**NegateConditionalsMutator (4 survived):**

- Some conditional branches not fully tested
- Rare edge cases in nested conditionals

---

## 6. Integration vs Unit Level Analysis

### 6.1 Unit-Level Mutations

**Definition:** Mutations that affect a single method's internal logic without crossing class boundaries.

**Count:** ~350 mutations (68%)

**Examples:**

- Arithmetic changes within a single method
- Conditional logic within a method
- Return value changes not propagated to other classes
- Local variable mutations

---

### 6.2 Integration-Level Mutations

**Definition:** Mutations that affect method calls crossing class boundaries or method chains within the same class.

**Count:** ~168 mutations (32%)

**Examples:**

- Calculator methods called by LoanCalculator (17 mutations)
- Calculator methods called by TransactionValidator (28 mutations)
- Calculator methods called by AccountManager (5 mutations)
- AccountManager.transfer() calling withdraw() and deposit() (12 mutations)
- LoanCalculator.shouldRefinance() calling calculateEMI() (8 mutations)

**Integration Mutation Detection:**
Tests that kill integration mutations verify:

1. Correct parameter passing between classes
2. Correct handling of return values from integrated methods
3. Proper error propagation across class boundaries
4. Contract compliance between integrated components

---

## 7. Conclusion

The mutation testing achieved a 79% mutation score with 96% line coverage. The project
demonstrates both unit-level and integration-level mutation testing with 9 different
mutation operators. Integration mutations account for approximately 32% of the total,
showing cross-class testing. Boundary condition testing could be improved to increase
the overall score.

---
