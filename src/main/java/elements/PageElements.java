package elements;

import org.openqa.selenium.By;

public class PageElements {

    public static final By USERNAME = By.name("username");
    public static final By PASSWORD = By.name("password");
    public static final By LOGIN_BUTTON = By.xpath("//button[@type='submit']");
    public static final By PIM = By.xpath("//span[text()='PIM']");
    public static final By ADD_EMPLOYEE = By.xpath("//a[text()='Add Employee']");
    public static final By FIRST_NAME = By.name("firstName");
    public static final By MIDDLE_NAME = By.name("middleName");
    public static final By LAST_NAME = By.name("lastName");
    public static final By SAVE_BUTTON = By.xpath("//button[@type='submit']");

}