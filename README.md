Exactly — you want **the entire README, including every sentence, code block, heading, and structure, inside one single copyable Markdown code block**. Nothing from the README should appear outside that block.

Here is the complete version:

````markdown
# SelfHealingInSelenium

A Selenium-based test automation framework that uses a three-level locator fallback strategy to make automated tests more resilient to UI locator changes.

The project demonstrates how Selenium tests can automatically recover when a primary locator becomes invalid, while also capturing detailed execution and self-healing information for reporting and debugging.

---

## Why This Project?

Traditional Selenium automation depends heavily on fixed locators.

When the UI changes, even a small change to an attribute, text, or DOM structure can cause a test to fail.

For example:

```text
Locator 1 → fails
          ↓
     Test normally stops
```

This project introduces a fallback approach:

```text
Locator 1 → Locator 2 → Locator 3
```

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

## Methodology

The project follows a simple three-level locator strategy.

### Locator Levels

- **L1 – Very Specific**
- **L2 – Slightly Specific**
- **L3 – Generic / Reliable Fallback**

Example:

```text
L1 → Most specific locator
L2 → Alternative locator
L3 → Known reliable locator
```

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
            Continue        WARN
                              |
                              v
                        Try Locator 2
                              |
                       +------+------+
                       |             |
                    Success        Failure
                       |             |
                       v             v
                    Continue        WARN
                                      |
                                      v
                                Try Locator 3
                                      |
                               +------+------+
                               |             |
                            Success        Failure
                               |             |
                               v             v
                            Continue       ERROR
                                           Test fails
```

---

## Project Structure

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
```

---

## Technologies Used

- **Java 22**
- **Selenium WebDriver 4.48.0**
- **TestNG 7.11.0**
- **Logback 1.5.18**
- **Maven**
- **Chrome WebDriver**
- **SLF4J**

---

## Framework Components

### PageElements.java

Contains all Selenium locators used by the tests.

For elements participating in the self-healing mechanism, three locator levels are defined:

```text
L1 → Primary / specific locator
L2 → Alternative locator
L3 → Fallback locator
```

Example:

```java
public static final By USERNAME_L1 = By.name("usernam");
public static final By USERNAME_L2 = By.xpath("//input[@name='usernam']");
public static final By USERNAME_L3 = By.name("username");
```

The intentionally incorrect L1 and L2 locators allow the self-healing mechanism to demonstrate recovery using L3.

---

### Common.java

Provides reusable Selenium operations used throughout the framework.

The class provides functionality for:

- Waiting for elements.
- Waiting for elements to become clickable.
- Clicking elements.
- Entering text.
- Clearing fields.
- Retrieving text.
- Checking element visibility.
- Scrolling to elements.
- Waiting for collections of elements.
- Waiting for page loading to complete.
- Waiting for the OrangeHRM form loader to disappear.
- Invoking the self-healing locator mechanism.
- Performing fast self-healing locator attempts.
- Recording self-healing events.

The class acts as the reusable Selenium utility layer between the test cases and the self-healing engine.

---

### SelfHealingLocator.java

Implements the core three-level self-healing strategy.

The standard strategy is:

```text
Locator 1
    ↓
if failed
    ↓
Locator 2
    ↓
if failed
    ↓
Locator 3
```

If Locator 1 succeeds, it is immediately returned.

If Locator 1 fails, the framework records the failure and attempts Locator 2.

If Locator 2 also fails, Locator 3 is attempted.

If Locator 3 succeeds, the framework reports that a fallback locator successfully recovered the action.

If all three locators fail, the final exception is propagated and the test fails.

The class also maintains a list of healing events that are later included in the HTML report.

---

## Fast Self-Healing Strategy

The framework also provides a faster locator resolution method through:

```java
findFast()
```

Instead of using the standard configured wait for every locator, each locator is given a shorter timeout.

The current implementation uses:

```text
Locator 1 → 2 seconds
Locator 2 → 2 seconds
Locator 3 → 2 seconds
```

This is useful when the test is expected to recover quickly and long waits between fallback attempts are undesirable.

The fast strategy is exposed through:

```java
selfHealingClickFast()
```

This allows the test to choose between the standard self-healing strategy and the faster fallback strategy.

---

## Form Loader Handling

OrangeHRM displays a form loader while certain operations are being processed.

The framework therefore provides:

```java
waitForFormLoaderToDisappear()
```

The method repeatedly checks whether the OrangeHRM form loader has disappeared before continuing with an interaction.

The current implementation uses repeated checks rather than relying on one long fixed wait.

This helps reduce failures caused by attempting to interact with the page while the application is still processing an operation.

---

## Self-Healing Events

Every locator attempt is recorded.

Examples include:

```text
Username | Locator 1 | FAILED | TimeoutException
Username | Locator 2 | FAILED | TimeoutException
Username | Locator 3 | FALLBACK SUCCESS
```

This makes it possible to determine exactly how the framework recovered from a locator failure.

The events are stored by:

```java
SelfHealingLocator
```

and exposed through:

```java
getHealingEvents()
```

---

## TestLogAppender.java

Captures Logback logging events generated during test execution.

The captured logs are stored in memory and later passed to the reporting component.

This allows normal test execution logs and self-healing events to be included in the same HTML report.

Example captured messages include:

```text
Starting employee lifecycle test
Starting employee creation
Employee created successfully
Starting edit of first employee record
Deleting selected employee records
```

---

## SelfHealingReport.java

Generates an HTML report after each test execution.

The report contains two primary sections.

### Test Execution Logs

Contains the normal application/test execution logs captured during the test.

Example:

```text
Test start
Login actions
Navigation actions
Employee creation
Employee editing
Employee deletion
```

### Self-Healing Strategy

Contains the locator-level diagnostic information.

Example:

```text
Locator 1 → FAILED
Locator 2 → FAILED
Locator 3 → FALLBACK SUCCESS
```

The report uses different visual styles to distinguish:

- Failed locator attempts.
- Successful fallback attempts.
- Successful locator resolutions.

---

## Report Structure

Each test execution generates a separate HTML report inside the `reports` directory.

Example:

```text
reports/
├── self-healing-report01.html
├── self-healing-report02.html
├── self-healing-report03.html
└── ...
```

The report contains the test name and two expandable sections:

```text
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
```

This keeps the classic test execution logs separate from the self-healing locator analysis while keeping both available in a single report.

---

## Report Numbering

Before creating a new report, the framework checks the existing files inside the `reports` directory.

For example:

```text
self-healing-report01.html
self-healing-report02.html
self-healing-report05.html
```

The framework identifies `05` as the highest existing report number and creates:

```text
self-healing-report06.html
```

This prevents existing reports from being overwritten.

---

## ParentClass.java

Provides the common TestNG setup and teardown functionality.

### Test Setup

Before each test:

1. A Logback test appender is created.
2. The appender is attached to the root logger.
3. Chrome WebDriver is initialized.
4. The browser window is maximized.
5. The OrangeHRM application is opened.
6. The `Common` utility class is initialized.
7. The framework waits for the page to finish loading.

### Test Teardown

After each test:

1. The test method name is obtained.
2. A new self-healing report is created.
3. Captured test logs are passed to the report.
4. Self-healing events are passed to the report.
5. The HTML report is generated.
6. The test log appender is detached.
7. The browser session is closed.

This ensures that reporting is performed regardless of whether the test passes or fails.

---

## EndToEnd.java

Contains the end-to-end Selenium test scenarios.

The tests use the OrangeHRM employee lifecycle to demonstrate the framework.

### Employee Lifecycle

The complete lifecycle includes:

```text
Login
  ↓
Navigate to PIM
  ↓
Add Employee
  ↓
Enter Employee Details
  ↓
Save Employee
  ↓
Navigate to Employee List
  ↓
Edit Employee
  ↓
Update Employee Details
  ↓
Save Changes
  ↓
Delete Employee Records
  ↓
Validate Success Message
```

The test demonstrates self-healing during navigation and form interactions.

It also demonstrates the use of the dedicated first-employee edit self-healing method.

---

## Locator Strategy Example

The framework intentionally defines multiple locator levels for selected elements.

For example:

```java
public static final By ADD_EMPLOYEE_L1 =
        By.xpath("//a[text()='Add Employe']");

public static final By ADD_EMPLOYEE_L2 =
        By.xpath("//a[contains(text(),'Add Employe')]");

public static final By ADD_EMPLOYEE_L3 =
        By.xpath("//a[text()='Add Employee']");
```

If the first locator does not match the current page:

```text
L1 → FAILED
```

the framework attempts:

```text
L2 → FAILED
```

and finally:

```text
L3 → SUCCESS
```

The test can therefore continue without manually changing the test code.

---

## First Employee Edit Strategy

The first employee edit action uses three alternative locator strategies.

```java
public static final By FIRST_EMPLOYEE_EDIT_L1 =
        By.xpath("(//div[@role='row'])[2]//button[1]");

public static final By FIRST_EMPLOYEE_EDIT_L2 =
        By.xpath("(//div[@role='row'])[2]//button[contains(@class,'oxd-table-cell-action-space')][1]");

public static final By FIRST_EMPLOYEE_EDIT_L3 =
        By.xpath("(//div[@role='row'])[2]//i[contains(@class,'bi-pencil-fill')]/..");
```

The framework attempts each locator sequentially until an element can be interacted with.

This provides resilience against changes in the button structure or CSS classes of the employee table.

---

## Maven Configuration

The project uses Maven for dependency management.

The current `pom.xml` includes:

### Selenium

```text
org.seleniumhq.selenium:selenium-java:4.48.0
```

### TestNG

```text
org.testng:testng:7.11.0
```

### Logback

```text
ch.qos.logback:logback-classic:1.5.18
```

The project is configured for Java 22.

---

## How to Run the Project

### Prerequisites

Make sure the following are installed:

- Java 22 or a compatible Java environment.
- Maven.
- Google Chrome.
- A compatible ChromeDriver setup.

Modern Selenium versions can manage the required browser driver through Selenium Manager when the environment allows it.

### Clone or Open the Project

Open the project in an IDE such as IntelliJ IDEA or another Java IDE with Maven support.

### Build the Project

Run:

```bash
mvn clean test
```

### Run Tests from the IDE

The TestNG tests can also be executed directly from the IDE.

The main test class is:

```text
src/test/java/tests/EndToEnd.java
```

---

## Example Self-Healing Log

A successful fallback may produce logs similar to:

```text
WARN  Locator 1 failed for: Username | TimeoutException
WARN  Locator 2 failed for: Username | TimeoutException
INFO  Locator 3 succeeded for: Username
WARN  Self-healing fallback: Locator 3 used for: Username
```

This clearly demonstrates that the test did not silently ignore the locator failure.

Instead, the framework records:

```text
Primary locator failed
        ↓
Alternative locator failed
        ↓
Fallback locator succeeded
        ↓
Test continued
```

---

## Advantages

### Resilience Against UI Changes

Minor changes to attributes, text, or DOM structure do not necessarily cause immediate test failure when an alternative locator remains valid.

### Automatic Recovery

The framework automatically attempts alternative locators without requiring changes to the test flow.

### Diagnostic Logging

Every failed locator and successful fallback is recorded.

### HTML Reporting

Each test execution produces a dedicated report containing both test logs and self-healing information.

### Reusable Architecture

The self-healing mechanism is separated from the test cases through:

```text
EndToEnd
    ↓
Common
    ↓
SelfHealingLocator
    ↓
Selenium WebDriver
```

This keeps the test code cleaner and makes the self-healing functionality reusable.

---

## Limitations

The current implementation uses manually defined fallback locators.

The framework does not currently generate completely new locators using machine learning or similarity analysis.

The self-healing mechanism is based on predefined locator alternatives:

```text
L1 → L2 → L3
```

Therefore, the effectiveness of the recovery depends on whether at least one of the configured fallback locators remains valid after the UI change.

This is intentionally designed as a lightweight and explainable self-healing approach rather than a machine-learning-based locator generation system.

---

## Future Enhancements

Potential future improvements include:

- Automatically learning successful fallback locators.
- Persisting healing history across test runs.
- Automatically suggesting replacement locators.
- Adding more than three locator levels.
- Supporting additional locator types.
- Adding screenshot capture when healing occurs.
- Adding execution timestamps to reports.
- Adding pass/fail summaries to the HTML report.
- Adding test duration information.
- Integrating the report with CI/CD pipelines.
- Supporting parallel test execution.
- Adding configurable timeout values.
- Introducing a more advanced similarity-based locator recommendation system.

---

## Design Philosophy

The framework follows three core principles:

### 1. Recover, But Do Not Hide Failures

A locator failure is still recorded even when a fallback locator successfully recovers the test.

### 2. Keep Tests Readable

The test should describe the business flow rather than contain complicated locator-recovery logic.

For example:

```java
common.selfHealingClick("LOGIN_BUTTON");
common.selfHealingClick("PIM");
common.selfHealingClick("ADD_EMPLOYEE");
```

The test does not need to know how many locator attempts are required internally.

### 3. Make Recovery Observable

Every fallback action should be visible through logs and reports.

This makes it possible to identify when the application UI has changed even if the automated test continues to pass.

---

## Overall Architecture

```text
                    EndToEnd Test
                          |
                          v
                       Common
                          |
             +------------+------------+
             |                         |
             v                         v
     Selenium Operations       SelfHealingLocator
                                       |
                              +--------+--------+
                              |        |        |
                              v        v        v
                             L1       L2       L3
                              |        |        |
                              +--------+--------+
                                       |
                                       v
                                Healing Events
                                       |
                                       v
                               SelfHealingReport
                                       |
                         +-------------+-------------+
                         |                           |
                         v                           v
                  Test Execution Logs       Self-Healing Events
                         |                           |
                         +-------------+-------------+
                                       |
                                       v
                                  HTML Report
```

---

## Summary

SelfHealingInSelenium demonstrates a lightweight, reusable approach for improving Selenium test resilience through predefined locator fallback strategies.

The framework combines:

- Three-level locator fallback.
- Standard and fast self-healing strategies.
- Reusable Selenium utilities.
- Form-loader synchronization.
- Structured logging.
- Self-healing event tracking.
- Automatic HTML report generation.
- TestNG lifecycle management.

The key concept is simple:

```text
Detect locator failure
        ↓
Try an alternative locator
        ↓
Recover automatically when possible
        ↓
Record the recovery
        ↓
Continue the test
```

This approach provides a practical foundation for building more resilient Selenium automation while keeping the recovery mechanism transparent, explainable, and easy to maintain.
````
