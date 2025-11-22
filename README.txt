================================================================================
CSE731 SOFTWARE TESTING - MUTATION TESTING PROJECT
================================================================================

PROJECT TITLE: Banking System Mutation Testing Study
SUBMISSION DATE: November 25, 2025
INSTITUTION: IIT Bangalore

================================================================================
1. PROJECT DESCRIPTION
================================================================================

This project implements a comprehensive mutation testing study on a Java-based
banking application. The system consists of 5 main classes with 1063 lines of
production code, tested by 168 unit tests achieving 79% mutation score and 96%
line coverage.

The application provides banking functionalities including:
- Basic arithmetic calculations (Calculator class)
- Loan EMI calculations and eligibility checks (LoanCalculator class)
- Investment calculations including SIP and compound interest (InvestmentCalculator)
- Account management with deposits, withdrawals, and transfers (AccountManager)
- Transaction validation with security checks (TransactionValidator)

================================================================================
2. MUTATION OPERATORS USED
================================================================================

The project demonstrates 9 mutation operators, with detailed analysis of 6:

UNIT-LEVEL OPERATORS (3):
--------------------------
1. ConditionalsBoundaryMutator
   - Changes boundary conditions (< to <=, > to >=)
   - Generated: 102 mutations, Killed: 26 (25%)
   - Example: if (value < 0) → if (value <= 0)

2. MathMutator
   - Replaces arithmetic operators (+, -, *, /)
   - Generated: 135 mutations, Killed: 113 (84%)
   - Example: a + b → a - b, a * b → a / b

3. NegateConditionalsMutator
   - Inverts conditional expressions
   - Generated: 158 mutations, Killed: 153 (97%)
   - Example: if (x > 0) → if (x <= 0)


INTEGRATION-LEVEL OPERATORS (3):
---------------------------------
4. PrimitiveReturnsMutator (Integration Context)
   - Replaces return values with defaults (0, false)
   - Generated: 41 mutations, Killed: 40 (98%)
   - Tests integration when Calculator.add() returns 0 to LoanCalculator
   - Example: return a + b; → return 0;

5. BooleanReturnsMutators (Integration Context)
   - Inverts boolean return values
   - Generated: 70 mutations, Killed: 65 (93%)
   - Tests integration when withdraw() return affects transfer()
   - Example: return true; → return false;

6. MathMutator (Integration Context)
   - Arithmetic mutations propagating across classes
   - Tests Calculator methods used by TransactionValidator
   - Example: Calculator.subtract() mutation affects PIN validation
   - Integration points: 50+ mutations across 4 class pairs


ADDITIONAL OPERATORS:
---------------------
7. IncrementsMutator - 6 generated, 5 killed (83%)
8. InvertNegsMutator - 1 generated, 1 killed (100%)
9. EmptyObjectReturnValsMutator - 5 generated, 5 killed (100%)

================================================================================
3. HOW TO RUN THE PROJECT
================================================================================

PREREQUISITES:
--------------
- Java Development Kit (JDK) 11 or higher
- Apache Maven 3.6 or higher
- Terminal/Command Prompt access


STEP 1: Navigate to Project Directory
--------------------------------------
cd <project-directory>


STEP 2: Clean and Compile the Project
--------------------------------------
mvn clean compile


STEP 3: Run All Unit Tests
---------------------------
mvn test

Expected Output:
- Tests run: 168
- Failures: 0
- Errors: 0
- Success Rate: 100%


STEP 4: Run Mutation Testing
-----------------------------
mvn pitest:mutationCoverage

Expected Output:
- Mutations Generated: 518
- Mutations Killed: 408 (79%)
- Line Coverage: 96%
- Test Strength: 80%

This command will generate an HTML report in:
target/pit-reports/index.html


STEP 5: View Mutation Testing Report
-------------------------------------
Open target/pit-reports/index.html in a web browser

The report shows:
- Overall mutation score and coverage statistics
- Per-class mutation analysis
- Per-method mutation details
- Survived vs killed mutations
- Line-by-line mutation coverage


OPTIONAL: Run Specific Test Class
----------------------------------
mvn test -Dtest=CalculatorTest
mvn test -Dtest=LoanCalculatorTest
mvn test -Dtest=InvestmentCalculatorTest
mvn test -Dtest=AccountManagerTest
mvn test -Dtest=TransactionValidatorTest

================================================================================
4. PROJECT STRUCTURE
================================================================================

Testing/
├── pom.xml                           # Maven configuration
├── README.txt                        # This file
├── MUTATION_ANALYSIS.md              # Detailed mutation analysis report
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── banking/
│   │               ├── Calculator.java              (58 lines)
│   │               ├── LoanCalculator.java          (197 lines)
│   │               ├── InvestmentCalculator.java   (231 lines)
│   │               ├── AccountManager.java          (288 lines)
│   │               └── TransactionValidator.java    (289 lines)
│   └── test/
│       └── java/
│           └── com/
│               └── banking/
│                   ├── CalculatorTest.java          (7 tests)
│                   ├── LoanCalculatorTest.java      (30 tests)
│                   ├── InvestmentCalculatorTest.java (40 tests)
│                   ├── AccountManagerTest.java      (38 tests)
│                   └── TransactionValidatorTest.java (53 tests)
└── target/
    └── pit-reports/
        └── index.html                # Mutation testing report

Total Production Code: 1063 lines
Total Test Code: 168 tests
Total Files: 10 Java files

================================================================================
5. TEST RESULTS SUMMARY
================================================================================

UNIT TEST RESULTS:
------------------
CalculatorTest:            7/7 tests passed
LoanCalculatorTest:        30/30 tests passed
InvestmentCalculatorTest:  40/40 tests passed
AccountManagerTest:        38/38 tests passed
TransactionValidatorTest:  53/53 tests passed
------------------
TOTAL:                     168/168 tests passed (100%)


MUTATION TESTING RESULTS:
-------------------------
Line Coverage:               96% (373/387 lines)
Mutation Score:              79% (408/518 mutants killed)
Test Strength:               80%
Average Tests per Mutation:  1.64 tests
Total Test Executions:       848 runs

Mutations by Status:
- KILLED:      408 (79%)
- SURVIVED:    102 (20%)
- NO_COVERAGE:   8 (1%)


INTEGRATION TESTING:
--------------------
Integration Points Tested:
1. LoanCalculator → Calculator (add method)
2. TransactionValidator → Calculator (subtract, abs methods)
3. AccountManager → Calculator (abs method)
4. AccountManager → AccountManager (transfer → withdraw/deposit chain)

Integration Mutations: ~168 (32% of total mutations)
Integration Kill Rate: ~82%

================================================================================
6. KEY FEATURES
================================================================================

1. Test Coverage: 96% line coverage with 168 unit tests
2. Mutation Testing: 79% mutation score with 9 operators
3. Integration Testing: Cross-class method calls tested
4. Banking Domain: Loan calculations, account management, transaction validation

================================================================================
7. TEAM MEMBER CONTRIBUTIONS
================================================================================

Team Member 1: Nakul Siwach (MT2024096)
- Responsibilities: Source code development (Calculator, LoanCalculator, InvestmentCalculator classes)
- Test case design and implementation for Calculator and LoanCalculator
- PIT mutation testing configuration and execution
- Documentation (README.txt and MUTATION_ANALYSIS.md)

Team Member 2: Deepanshu Saini (MT2024039)
- Responsibilities: Source code development (AccountManager, TransactionValidator classes)
- Test case design and implementation for AccountManager and TransactionValidator
- Bug fixes and test debugging
- Screenshots and final submission preparation

================================================================================
8. TOOLS AND TECHNOLOGIES
================================================================================

Programming Language:    Java 11
Build Tool:             Apache Maven 3.9.10
Testing Framework:      JUnit 5.9.3
Mutation Tool:          PIT (PITest) 1.15.3 with JUnit 5 Plugin 1.2.1
IDE:                    Visual Studio Code
Version Control:        Git (optional)

Maven Plugins Used:
- maven-compiler-plugin: 3.11.0
- maven-surefire-plugin: 3.0.0
- pitest-maven: 1.15.3

Dependencies:
- junit-jupiter-api: 5.9.3
- junit-jupiter-engine: 5.9.3

================================================================================
9. MUTATION SCORE ANALYSIS
================================================================================

TARGET SCORE: 75-80%
ACHIEVED SCORE: 79%

SCORE BREAKDOWN BY OPERATOR:
-----------------------------
InvertNegsMutator:              100% (1/1)
EmptyObjectReturnValsMutator:   100% (5/5)
PrimitiveReturnsMutator:         98% (40/41)
NegateConditionalsMutator:       97% (153/158)
BooleanFalseReturnValsMutator:   96% (23/24)
BooleanTrueReturnValsMutator:    91% (42/46)
MathMutator:                     84% (113/135)
IncrementsMutator:               83% (5/6)
ConditionalsBoundaryMutator:     25% (26/102)


SCORE BREAKDOWN BY CLASS:
-------------------------
Calculator:               ~88% (16/18 mutations killed)
LoanCalculator:           ~75% (estimated from logs)
InvestmentCalculator:     ~78% (estimated from logs)
AccountManager:           ~81% (estimated from logs)
TransactionValidator:     ~82% (estimated from logs)


WHY SOME MUTATIONS SURVIVED:
-----------------------------
1. Boundary Conditions (75 survived)
   - Tests didn't check exact boundary values (= 0, = limit)
   - Improvement: Add more edge case tests

2. Mathematical Operations (22 survived)
   - Complex formulas where mutations produce acceptable results
   - Floating-point precision masks some mutations
   - Improvement: Use stricter assertion tolerances

3. Conditional Logic (4 survived)
   - Some branches in nested conditions not fully tested
   - Improvement: Achieve 100% branch coverage

4. No Coverage (8 mutations)
   - Getter/setter methods not directly tested
   - Private helper methods tested indirectly
   - Acceptable for this project scope

================================================================================
10. GITHUB REPOSITORY
================================================================================

Repository URL: https://github.com/Deepanshu09-max/Mutation_Testing

The repository contains source code, tests, Maven configuration, documentation,
and screenshots of PIT reports.

================================================================================
11. SCREENSHOTS LOCATION
================================================================================

Mutation testing screenshots can be found in:
- screenshots/pit-report-overview.png    (Overall mutation score)
- screenshots/pit-report-calculator.png  (Calculator class details)
- screenshots/pit-report-mutations.png   (Sample mutation details)

Alternatively, the full HTML report is available at:
target/pit-reports/index.html

================================================================================
12. ADDITIONAL NOTES
================================================================================

The 79% mutation score indicates good test quality. Integration testing was
verified through cross-class mutations. The low boundary mutation kill rate
(25%) is common in mutation testing projects.

Some mutations survived due to complex formulas and floating-point precision.
This is acceptable for the project scope.

================================================================================
13. NOTES ON MUTATION SCORE
================================================================================

The ConditionalsBoundaryMutator has a lower score (25%) which is typical for
mutation testing projects. Boundary mutations are harder to detect without
extensive edge case testing.

Unit-level mutations affect logic within a single method, while integration-level
mutations test interactions between classes.

Mutation testing took approximately 40-45 seconds for 518 mutations.

================================================================================
14. CONTACT INFORMATION
================================================================================

Team Member 1:
Student Name: Nakul Siwach
Roll Number: MT2024096
Program: MTech in Computer Science
Course: CSE731 Software Testing

Team Member 2:
Student Name: Deepanshu Saini
Roll Number: MT2024039
Program: MTech in Computer Science
Course: CSE731 Software Testing

Institution: IIT Bangalore
Project Submission Date: November 25, 2025, 6:00 PM

================================================================================
15. REFERENCES
================================================================================

1. PITest Documentation: https://pitest.org/
2. JUnit 5 User Guide: https://junit.org/junit5/docs/current/user-guide/
3. Maven PIT Plugin: https://pitest.org/quickstart/maven/
4. Mutation Testing Introduction: Jia, Y., & Harman, M. (2011). "An Analysis
   and Survey of the Development of Mutation Testing." IEEE Transactions on
   Software Engineering.
5. Java Best Practices: Effective Java by Joshua Bloch

================================================================================
END OF README
================================================================================

For detailed mutation analysis, see MUTATION_ANALYSIS.md
For source code, see src/main/java/com/banking/
For test code, see src/test/java/com/banking/
For HTML report, open target/pit-reports/index.html
