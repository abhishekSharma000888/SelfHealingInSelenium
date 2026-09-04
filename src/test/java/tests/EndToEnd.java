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
        // Navigate to Employee List
        common.click(PageElements.EMPLOYEE_LIST, "Employee List");
        // Edit first employee record
        common.click(PageElements.FIRST_EMPLOYEE_EDIT, "Edit First Employee");
        // Modify employee details
        common.clear(PageElements.FIRST_NAME);
        common.type(PageElements.FIRST_NAME, "UpdatedFirst", "Updated First Name");
        common.clear(PageElements.MIDDLE_NAME);
        common.type(PageElements.MIDDLE_NAME, "UpdatedMiddle", "Updated Middle Name");
        common.clear(PageElements.LAST_NAME);
        common.type(PageElements.LAST_NAME, "UpdatedLast", "Updated Last Name");
        common.click(PageElements.SAVE_BUTTON, "Save Employee");
    }
}