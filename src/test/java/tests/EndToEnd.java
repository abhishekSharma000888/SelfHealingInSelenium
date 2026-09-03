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

    }
}