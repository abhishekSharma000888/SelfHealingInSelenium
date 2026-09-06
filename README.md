# SelfHealingInSelenium

A Selenium-based test automation framework that uses a three-level locator strategy to make automated tests more resilient to UI changes.

## Current Framework

- Java 22
- Maven
- Selenium 4.48.0
- TestNG 7.11.0
- SLF4J + Logback logging
- OrangeHRM demo application

## Completed E2E Flow

The conventional Selenium automation flow is working successfully:

Login
→ Navigate to PIM
→ Add Employee
→ Employee List
→ Edit First Employee
→ Modify First/Middle/Last Name
→ Save Employee
→ Employee List
→ Select 2 employee records
→ Delete Selected
→ Confirm Delete
→ Validate "Successfully Deleted" message

## Current Classes

- `ParentClass.java` – WebDriver setup and teardown
- `Common.java` – reusable Selenium methods, waits, clicks, typing, element handling and self-healing integration
- `PageElements.java` – application locators
- `EndToEnd.java` – complete E2E test
- `SelfHealingLocator.java` – three-level locator fallback engine

## Self-Healing Implementation – Current Progress

The self-healing architecture has now been created.

`SelfHealingLocator.java` implements:

Locator 1 → Locator 2 → Locator 3

Locator levels:

- Locator 1 = Very Specific
- Locator 2 = Slightly Specific
- Locator 3 = Generic

Behavior:

1. Try Locator 1.
2. If Locator 1 fails, generate a `WARN`.
3. Try Locator 2.
4. If Locator 2 fails, generate another `WARN`.
5. Try Locator 3.
6. If Locator 3 succeeds, generate a self-healing `WARN` and continue execution.
7. If Locator 3 also fails, generate a `WARN` followed by an `ERROR` and fail the test.

Important: Every failed locator must generate its own WARN. We want to retain diagnostic information showing exactly which locator failed.

Example:

L1 ❌ → WARN
L2 ❌ → WARN
L3 ✅ → WARN: Self-healing fallback used
→ Test continues

If all fail:

L1 ❌ → WARN
L2 ❌ → WARN
L3 ❌ → WARN
→ ERROR
→ Test fails

## Common.java Integration

`Common.java` now contains:

`selfHealingFind(By locator1, By locator2, By locator3, String description)`

This delegates the locator search to `SelfHealingLocator`.

The `SelfHealingLocator` instance is maintained inside `Common`.

## Important Design Decision

Do NOT put try/catch fallback logic directly inside `EndToEnd`.

The architecture should remain:

EndToEnd
→ Common
→ SelfHealingLocator
→ Locator 1 / Locator 2 / Locator 3

The existing E2E flow should remain stable while we introduce self-healing incrementally.

## Next Step

The next task is to integrate self-healing into ONE existing action first.

Recommended first target:

`FIRST_EMPLOYEE_EDIT`

Create three locators for the same element:

- `FIRST_EMPLOYEE_EDIT_L1`
- `FIRST_EMPLOYEE_EDIT_L2`
- `FIRST_EMPLOYEE_EDIT_L3`

Then replace the normal Edit click with the self-healing mechanism.

First test should deliberately break Locator 1 while keeping Locator 2 or Locator 3 valid so that we can verify:

- Locator failure is detected
- WARN is generated
- Fallback locator executes
- Test continues successfully

After this works, expand the mechanism to other important locators.

## Future Plan

1. Integrate three-level locators.
2. Intentionally break locators to validate self-healing.
3. Generate detailed WARN logs for each failed locator.
4. Capture locator failure reasons.
5. Build configurable execution/reporting.
6. Track results such as:
    - L1 failed / L2 passed
    - L1 failed / L2 failed / L3 passed
    - All locators failed
7. Later investigate AI/Similarity-based locator recommendation.

## Core Project Strategy

The objective is not simply to hide Selenium failures.

The framework should make locator failures **diagnosable** while allowing the test to continue when a valid fallback locator can successfully identify the element.

AI/Similarity analysis is a future enhancement; the current implementation focuses on establishing a reliable three-level fallback architecture first.