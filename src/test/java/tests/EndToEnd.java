package tests;

import elements.PageElements;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndToEnd extends ParentClass {

    private static final Logger logger = LoggerFactory.getLogger(EndToEnd.class);

    @Test
    public void employeeLifecycleAddAndEdit() {
        logger.info("Starting employeeLifecycleAddAndEdit employee lifecycle test");
        common.selfHealingType("USERNAME", "Admin");
        common.selfHealingType("PASSWORD", "admin123");
        common.selfHealingClickFast("LOGIN_BUTTON");
        common.selfHealingClickFast("PIM");
        logger.info("Starting employee creation");
        common.selfHealingClickFast("ADD_EMPLOYEE");
        common.waitForElementWithTimeout(PageElements.FIRST_NAME, 2);
        common.type(PageElements.FIRST_NAME, "New", "First Name");
        common.type(PageElements.MIDDLE_NAME, "Test", "Middle Name");
        common.type(PageElements.LAST_NAME, "Employee", "Last Name");
        common.selfHealingClickFast("SAVE_BUTTON");
        logger.info("Employee created successfully");
        common.selfHealingClickFast("EMPLOYEE_LIST");
        common.click(PageElements.FIRST_EMPLOYEE_EDIT, "Edit First Employee");
        common.clear(PageElements.FIRST_NAME);
        common.type(PageElements.FIRST_NAME, "Edited_New", "Updated First Name");
        common.clear(PageElements.MIDDLE_NAME);
        common.type(PageElements.MIDDLE_NAME, "EditedMiddle", "Updated Middle Name");
        common.clear(PageElements.LAST_NAME);
        common.type(PageElements.LAST_NAME, "EditedLast", "Updated Last Name");
        common.selfHealingClickFast("SAVE_BUTTON");
    }

    @Test
    public void completeEmployeeLifecycle() {
        // login flow
        logger.info("Starting employee lifecycle test"+ " Performing login");
        common.selfHealingType("USERNAME", "Admin");
        common.selfHealingType("PASSWORD", "admin123");
        common.selfHealingClick("LOGIN_BUTTON");
        logger.info("Login completed successfully");
        // Navigate to PIM menu
        common.selfHealingClick("PIM");
        // Add Employee
        logger.info("Starting employee creation");
        common.selfHealingClick("ADD_EMPLOYEE");
        common.type(PageElements.FIRST_NAME, "SelfHealing", "First Name");
        common.type(PageElements.MIDDLE_NAME, "Test", "Middle Name");
        common.type(PageElements.LAST_NAME, "Employee", "Last Name");
        common.selfHealingClick("SAVE_BUTTON");
        logger.info("Employee created successfully");
        // Navigate to Employee List
        common.selfHealingClick("EMPLOYEE_LIST");
        // Edit first employee record
        common.click(PageElements.FIRST_EMPLOYEE_EDIT, "Edit First Employee");
        // Modify employee details
        logger.info("Starting edit of first employee record");
        common.clear(PageElements.FIRST_NAME);
        common.type(PageElements.FIRST_NAME, "UpdatedFirst", "Updated First Name");
        common.clear(PageElements.MIDDLE_NAME);
        common.type(PageElements.MIDDLE_NAME, "UpdatedMiddle", "Updated Middle Name");
        common.clear(PageElements.LAST_NAME);
        common.type(PageElements.LAST_NAME, "UpdatedLast", "Updated Last Name");
        common.selfHealingClick("SAVE_BUTTON");
        // Navigate to Employee List
        common.selfHealingClick("EMPLOYEE_LIST");
        // Delete Selected Employees
        logger.info("Deleting selected employee records");
        List<WebElement> checkboxes = common.waitForElements(PageElements.EMPLOYEE_CHECKBOXES);
        System.out.println("Checkboxes found: " + checkboxes.size());
        for (int i = 1; i <= 2; i++) {
            checkboxes.get(i).click();
        }
        common.click(PageElements.DELETE_SELECTED, "Delete Selected");
        common.click(PageElements.CONFIRM_DELETE, "Confirm Delete");
        String successMessage = common.getText(PageElements.SUCCESS_MESSAGE);
        Assert.assertTrue(successMessage.contains("Successfully Deleted"), "Expected successful deletion message but received: " + successMessage);
    }
}