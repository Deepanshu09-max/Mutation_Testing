# Screenshots Guide for PIT Report

## What Screenshots to Capture

Please capture the following screenshots from the PIT HTML report (target/pit-reports/index.html):

### 1. Overview Screenshot (pit-report-overview.png)

**Location:** Main index.html page

**Should show:**

- Overall mutation score: 79% (408/518)
- Line coverage: 96% (373/387)
- Test strength: 80%
- Number of classes tested
- Mutation operators summary table

**How to capture:**

1. Open target/pit-reports/index.html in browser
2. Take screenshot of the top section showing statistics
3. Save as: screenshots/pit-report-overview.png

### 2. Mutation Operators Screenshot (pit-report-operators.png)

**Location:** Main index.html page (scroll down)

**Should show:**

- Table of all 9 mutation operators
- Generated, Killed, Survived counts for each
- Kill percentages
- Visual bars showing mutation distribution

**How to capture:**

1. Scroll to the "Mutators" section
2. Take screenshot of the complete operators table
3. Save as: screenshots/pit-report-operators.png

### 3. Calculator Class Detail (pit-report-calculator.png)

**Location:** Click on "com.banking.Calculator" in the report

**Should show:**

- Calculator class mutation score (~88%)
- List of methods with their mutation scores
- Number of mutations per method
- Color-coded mutation status

**How to capture:**

1. Click on "Calculator" class in the package list
2. Take screenshot showing all methods
3. Save as: screenshots/pit-report-calculator.png

### 4. Sample Mutation Detail (pit-report-mutation-detail.png)

**Location:** Click on any method, then click on a line number with mutations

**Should show:**

- Original source code
- Mutated code (highlighted)
- Mutation operator used
- Test that killed the mutation (or "SURVIVED")
- Line numbers and context

**How to capture:**

1. Click on "Calculator.add" method
2. Click on a line number (e.g., line 12)
3. Take screenshot showing mutation details
4. Save as: screenshots/pit-report-mutation-detail.png

### 5. Integration Example (pit-report-integration.png)

**Location:** Navigate to LoanCalculator or TransactionValidator

**Should show:**

- Class with integration points to Calculator
- Methods that call Calculator methods
- Mutation results for integrated methods

**How to capture:**

1. Click on "LoanCalculator" class
2. Take screenshot showing methods like isEligible()
3. Save as: screenshots/pit-report-integration.png

### 6. Survived Mutations (pit-report-survived.png)

**Location:** Any class with survived mutations

**Should show:**

- Example of a mutation that survived
- The mutated code highlighted in red/orange
- "SURVIVED" status indicator
- Which mutation operator was applied

**How to capture:**

1. Look for methods with red/orange indicators
2. Click on a line with survived mutation
3. Take screenshot showing why it survived
4. Save as: screenshots/pit-report-survived.png

## Optional Screenshots

### 7. Package Summary (pit-report-package.png)

- Shows com.banking package with all 5 classes
- Overall package statistics
- Save as: screenshots/pit-report-package.png

### 8. Timeline/Performance (pit-report-timeline.png)

- If available, shows mutation testing execution time
- Save as: screenshots/pit-report-timeline.png

## Tips for Good Screenshots

1. **Use Full Window:** Maximize browser window for clear screenshots
2. **High Resolution:** Use at least 1920x1080 resolution if possible
3. **Crop Appropriately:** Remove unnecessary browser chrome/toolbars
4. **Readable Text:** Ensure all text is legible in the screenshot
5. **File Format:** PNG format preferred (better quality than JPG for text)
6. **File Size:** Keep under 5MB per screenshot for easy sharing

## Screenshot Tools

**Windows:**

- Snipping Tool (Win + Shift + S)
- Snip & Sketch (built-in)
- Alt + PrtScn (capture active window)

**Mac:**

- Cmd + Shift + 4 (select area)
- Cmd + Shift + 3 (full screen)

**Linux:**

- Screenshot utility (varies by distro)
- gnome-screenshot
- Spectacle (KDE)

**Browser Extensions:**

- Awesome Screenshot
- Nimbus Screenshot
- Fireshot

## After Capturing

Place all screenshots in the `screenshots/` directory:

```
Testing/
└── screenshots/
    ├── pit-report-overview.png
    ├── pit-report-operators.png
    ├── pit-report-calculator.png
    ├── pit-report-mutation-detail.png
    ├── pit-report-integration.png
    └── pit-report-survived.png
```

These screenshots will be included in the final submission tar.gz file.

## Important Notes

- The HTML report is already in target/pit-reports/index.html
- The report is self-contained and can be opened offline
- You can also submit the entire pit-reports folder if screenshots are difficult
- Screenshots help evaluators quickly see your results without running code
- Make sure screenshots show the 79% mutation score achievement!

## Quick Checklist

□ Overview with 79% score visible
□ Mutation operators table showing all 9 operators
□ At least one class detail view (Calculator recommended)
□ At least one mutation detail showing killed mutation
□ At least one example of survived mutation
□ Integration testing evidence (optional but recommended)

Minimum required: 3-4 screenshots
Recommended: 5-6 screenshots
Complete set: 6-8 screenshots
