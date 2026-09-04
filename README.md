# SelfHealingInSelenium

A Selenium-based test automation framework that uses a three-level locator strategy to make automated tests more resilient to UI changes.

## Technology Stack

- Java 22
- Maven
- Selenium WebDriver 4.48.0
- TestNG
- Chrome / ChromeDriver

## Project Structure

- `ParentClass.java` – WebDriver setup and teardown
- `Common.java` – reusable Selenium helper methods
- `PageElements.java` – application locators
- `EndToEnd.java` – main E2E employee lifecycle test

## Current Progress

### Completed and Passing

The following E2E flow is working successfully:

```text
Login
  ↓
PIM
  ↓
Add Employee
  ↓
Enter First / Middle / Last Name
  ↓
Save Employee
  ↓
Employee List
  ↓
Select First Employee Record
  ↓
Edit Employee
  ↓
Modify First / Middle / Last Name
  ↓
Save Changes