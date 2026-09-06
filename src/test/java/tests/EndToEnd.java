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
    public void employeeLifecycle() {
        // login flow
        logger.info("Starting employee lifecycle test"+ " Performing login");
        common.type(PageElements.USERNAME, "Admin", "Username");
        common.type(PageElements.PASSWORD, "admin123", "Password");
        common.click(PageElements.LOGIN_BUTTON, "Login button");
        logger.info("Login completed successfully");
        // Navigate to PIM menu
        common.click(PageElements.PIM, "PIM menu");
        // Add Employee
        logger.info("Starting employee creation");
        common.click(PageElements.ADD_EMPLOYEE, "Add Employee");
        common.type(PageElements.FIRST_NAME, "SelfHealing", "First Name");
        common.type(PageElements.MIDDLE_NAME, "Test", "Middle Name");
        common.type(PageElements.LAST_NAME, "Employee", "Last Name");
        common.click(PageElements.SAVE_BUTTON, "Save Employee");
        logger.info("Employee created successfully");
        // Navigate to Employee List
        common.click(PageElements.EMPLOYEE_LIST, "Employee List");
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
        common.click(PageElements.SAVE_BUTTON, "Save Employee");
        // Navigate to Employee List
        common.click(PageElements.EMPLOYEE_LIST, "Employee List");
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