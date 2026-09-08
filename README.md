# SelfHealingInSelenium

A Selenium-based test automation framework that uses a three-level locator fallback strategy to make automated tests more resilient to UI locator changes.

The project demonstrates how Selenium tests can automatically recover when a primary locator becomes invalid, while also capturing detailed execution and self-healing information for reporting and debugging.

---

## Why This Project?

Traditional Selenium automation depends heavily on fixed locators.

When the UI changes, even a small change to an attribute, text, or DOM structure can cause a test to fail.

For example:

Locator 1 → fails  
→ Test normally stops

This project introduces a fallback approach:

Locator 1 → Locator 2 → Locator 3

If the first locator fails, the framework automatically attempts the next available locator.

The objective is not to hide Selenium failures.

The objective is to:

- Detect locator failures.
- Automatically recover when an alternative locator works.
- Generate diagnostic WARN logs.
- Identify exactly which locator failed.
- Identify which fallback locator successfully recovered the action.
- Capture test execution logs.
- Generate a local HTML report for every test execution.
- Keep the test case itself simple and readable.

---

# Methodology

The project follows a simple three-level locator strategy.

### Locator Levels

- **L1 – Very Specific**
- **L2 – Slightly Specific**
- **L3 – Generic / Reliable Fallback**

Example:

L1 → Most specific locator  
L2 → Alternative locator  
L3 → Known reliable locator

The framework attempts them sequentially.

### Self-Healing Flow

```text
             Start
               |
               v
          Try Locator 1
               |
        +------+------+
        |             |
     Success        Failure
        |             |
        v             v
      Continue      WARN
                      |
                      v
                Try Locator 2
                      |
               +------+------+
               |             |
            Success        Failure
               |             |
               v             v
             WARN          WARN
             Continue        |
                             v
                       Try Locator 3
                             |
                      +------+------+
                      |             |
                   Success        Failure
                      |             |
                      v             v
                    WARN         ERROR
                    Continue     Test fails

# Project Structure

```text
SelfHealingInSelenium/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── elements/
│   │       │   └── PageElements.java
│   │       │
│   │       └── utilities/
│   │           ├── Common.java
│   │           ├── SelfHealingLocator.java
│   │           ├── SelfHealingReport.java
│   │           └── TestLogAppender.java
│   │
│   └── test/
│       └── java/
│           └── tests/
│               ├── ParentClass.java
│               └── EndToEnd.java
│
├── reports/
│   ├── self-healing-report01.html
│   ├── self-healing-report02.html
│   └── ...
│
├── pom.xml
└── README.md

Class Overview
PageElements.java

Contains all Selenium locators used by the tests, including the L1, L2 and L3 locators required for self-healing.

Common.java

Provides reusable Selenium actions such as click, type, clear, wait and element validation. It also connects the test cases with the self-healing locator engine.

SelfHealingLocator.java

Implements the three-level self-healing strategy.

L1 → L2 → L3

If L1 fails, L2 is attempted. If L2 also fails, L3 is attempted. Each failure and fallback success is captured for reporting.

It also supports the faster findFast() strategy using a shorter timeout for each locator attempt.

SelfHealingReport.java

Generates the HTML self-healing report and automatically creates the next report number.

Example:

reports/self-healing-report01.html
reports/self-healing-report02.html
reports/self-healing-report03.html

The report contains:

Test execution logs
Self-healing strategy details
Failed locators
Successful fallback locators
Exception details
TestLogAppender.java

Captures the SLF4J/Logback logs generated during a test execution so they can be embedded directly into the HTML report.

ParentClass.java

Provides the common TestNG setup and teardown.

Starts the Chrome WebDriver.
Opens the OrangeHRM application.
Initializes Common.
Starts test log capture.
Generates the self-healing report after the test.
Closes the log appender.
Quits the browser.
EndToEnd.java

Contains the end-to-end Selenium test scenarios covering the employee lifecycle.

The tests demonstrate both:

Normal self-healing execution
Fast self-healing execution
Reporting Structure

Each test execution generates a separate HTML report inside the reports directory.

Example:

reports/
├── self-healing-report01.html
├── self-healing-report02.html
└── self-healing-report03.html

The report contains the test name and two expandable sections:

Test Case: completeEmployeeLifecycle

▼ Test Execution Logs
   ├── Test start
   ├── Login actions
   ├── Navigation actions
   ├── Employee creation
   ├── Employee editing
   └── Other test logs

▼ Self-Healing Strategy
   ├── Locator 1 → FAILED
   ├── Locator 2 → FAILED
   ├── Locator 3 → FALLBACK SUCCESS
   └── ...

This keeps the classic test execution logs separate from the self-healing locator analysis, while both remain available in the same report.

Report Numbering

Before creating a new report, the framework checks the existing files inside the reports directory.

For example:

self-healing-report01.html
self-healing-report02.html
self-healing-report05.html

The framework identifies 05 as the highest existing number and creates:

self-healing-report06.html

This prevents existing reports from being overwritten.