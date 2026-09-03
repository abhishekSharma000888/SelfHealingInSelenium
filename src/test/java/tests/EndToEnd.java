package tests;

import elements.PageElements;
import org.testng.annotations.Test;

public class EndToEnd extends ParentClass {

    @Test
    public void employeeLifecycle() {
        // login flow
        common.type(PageElements.USERNAME, "Admin", "Username");
        common.type(PageElements.PASSWORD, "admin123", "Password");
        common.click(PageElements.LOGIN_BUTTON, "Login button");
        // Navigate to PIM menu
        common.click(PageElements.PIM, "PIM menu");
        // Add Employee
        common.click(PageElements.ADD_EMPLOYEE, "Add Employee");
        common.type(PageElements.FIRST_NAME, "SelfHealing", "First Name");
        common.type(PageElements.MIDDLE_NAME, "Test", "Middle Name");
        common.type(PageElements.LAST_NAME, "Employee", "Last Name");
        common.click(PageElements.SAVE_BUTTON, "Save Employee");

    }
}