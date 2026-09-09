package elements;

import org.openqa.selenium.By;

public class PageElements {

    public static final By USERNAME_L1 = By.name("usernam");
    public static final By USERNAME_L2 = By.xpath("//input[@name='usernam']");
    public static final By USERNAME_L3 = By.name("username");
    public static final By PASSWORD_L1 = By.name("passwor");
    public static final By PASSWORD_L2 = By.xpath("//input[@name='password']");
    public static final By PASSWORD_L3 = By.name("password");
    public static final By LOGIN_BUTTON_L1 = By.xpath("//button[@type='submi']");
    public static final By LOGIN_BUTTON_L2 = By.cssSelector("button[type='submit']");
    public static final By LOGIN_BUTTON_L3 = By.xpath("//button[@type='submit']");
    public static final By PIM_L1 = By.xpath("//span[text()='PIMX']");
    public static final By PIM_L2 = By.xpath("//span[contains(text(),'PIMX')]");
    public static final By PIM_L3 = By.xpath("//span[text()='PIM']");
    public static final By ADD_EMPLOYEE_L1 = By.xpath("//a[text()='Add Employe']");
    public static final By ADD_EMPLOYEE_L2 = By.xpath("//a[contains(text(),'Add Employe')]");
    public static final By ADD_EMPLOYEE_L3 = By.xpath("//a[text()='Add Employee']");
    public static final By FIRST_NAME = By.name("firstName");
    public static final By MIDDLE_NAME = By.name("middleName");
    public static final By LAST_NAME = By.name("lastName");
    public static final By SAVE_BUTTON_L1 = By.xpath("//button[@type='submi']");
    public static final By SAVE_BUTTON_L2 = By.cssSelector("button[type='submi']");
    public static final By SAVE_BUTTON_L3 = By.xpath("//button[@type='submit']");
    public static final By EMPLOYEE_LIST_L1 = By.xpath("//a[text()='Employee Lis']");
    public static final By EMPLOYEE_LIST_L2 = By.xpath("//a[contains(text(),'Employee Lis')]");
    public static final By EMPLOYEE_LIST_L3 = By.xpath("//a[text()='Employee List']");
    public static final By FIRST_EMPLOYEE_EDIT_L1 = By.xpath("(//div[@role='row'])[2]//button[1]");
    public static final By FIRST_EMPLOYEE_EDIT_L2 = By.xpath("(//div[@role='row'])[2]//button[contains(@class,'oxd-table-cell-action-space')][1]");
    public static final By FIRST_EMPLOYEE_EDIT_L3 = By.xpath("(//div[@role='row'])[2]//i[contains(@class,'bi-pencil-fill')]/..");
    public static final By FORM_LOADER = By.cssSelector(".oxd-form-loader");
    public static final By SUCCESS_MESSAGE = By.xpath("//div[contains(@class,'oxd-toast--success')]");
    public static final By EMPLOYEE_CHECKBOXES = By.cssSelector(".oxd-table-card .oxd-checkbox-input-icon");
    public static final By DELETE_SELECTED = By.xpath("//button[contains(.,'Delete Selected')]");
    public static final By CONFIRM_DELETE = By.xpath("//button[contains(.,'Yes, Delete')]");
}