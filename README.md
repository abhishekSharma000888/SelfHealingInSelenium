# SelfHealingInSelenium

A Selenium-based test automation framework that uses a simple three-level locator fallback strategy to make automated tests more resilient to UI locator changes.

The project demonstrates how a Selenium test can automatically recover when a primary locator becomes invalid, while still generating diagnostic warnings that clearly identify the failed locator and the fallback mechanism used.

---

## Why This Project?

Traditional Selenium automation depends heavily on fixed locators.

When the UI changes, even a small change to an attribute, text, or DOM structure can cause a test to fail.

For example:

Locator 1 → fails  
→ Test normally stops

This project introduces a simple fallback approach:

Locator 1 → Locator 2 → Locator 3

If the first locator fails, the framework automatically attempts the next available locator.

The objective is not to hide Selenium failures.

The objective is to:

- Detect locator failures.
- Automatically recover when an alternative locator works.
- Generate diagnostic WARN logs.
- Identify exactly which locator failed.
- Identify which fallback locator successfully recovered the action.
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