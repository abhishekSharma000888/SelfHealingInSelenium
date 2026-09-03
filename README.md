# SelfHealingInSelenium
A Selenium-based test automation framework that uses a three-level locator strategy to make automated tests more resilient to UI changes.
## Work Done till Thursday
## Current Progress

Java 22 + Maven + Selenium + TestNG framework is set up.

Working E2E flow:
Login → PIM → Add Employee → Save Employee

Created:
- `ParentClass.java` – WebDriver setup/teardown
- `Common.java` – reusable Selenium helper methods
- `PageElements.java` – application locators
- `EndToEnd.java` – single E2E test

Next: Employee List → Search → Edit → Save → Delete → Confirm Delete.

Future: 3-level self-healing locators → warning logs → configurable reports.