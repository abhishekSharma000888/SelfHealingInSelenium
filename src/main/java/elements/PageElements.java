package elements;

import org.openqa.selenium.By;

public class PageElements {

    public static final By USERNAME = By.name("username");
    public static final By PASSWORD = By.name("password");
    public static final By LOGIN_BUTTON = By.xpath("//button[@type='submit']");
    public static final By PIM = By.xpath("//span[text()='PIM']");

}