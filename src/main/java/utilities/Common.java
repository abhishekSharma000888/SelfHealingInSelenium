package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import elements.PageElements;

import java.time.Duration;
import java.util.List;

public class Common {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final SelfHealingLocator selfHealingLocator;

    public Common(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(04));
        this.selfHealingLocator = new SelfHealingLocator(this);
    }

    /* Waits until the browser reports that the complete HTML document has loaded.
       This helps ensure that the page is ready before further Selenium actions are performed. */
    public void loadCompleteWebPage() {
        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }

    /* Waits until the element identified by the supplied locator is visible on the page.
       Returns the visible WebElement so that the caller can perform further actions on it. */
    public WebElement waitForElement(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    /* Waits until the element is both visible and enabled for user interaction.
       Returns the WebElement when Selenium determines that it can be clicked. */
    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );
    }

    /* Waits for any OrangeHRM form loader to disappear before attempting the click.
       This prevents interactions from occurring while the application is still processing a request. */
    public void click(By locator, String description) {
        waitForFormLoaderToDisappear();
        WebElement element = waitForElementToBeClickable(locator);
        element.click();
    }

    /* Waits for the target input element to become visible before entering text.
       The existing value is cleared first so that the supplied text replaces previous content. */
    public void type(By locator, String text, String description) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    /* Waits for the requested element and retrieves the visible text contained within it.
       This is useful for validations and for reading messages or displayed application data. */
    public String getText(By locator) {
        return waitForElement(locator).getText();
    }

    /* Checks whether an element becomes visible within the configured wait period.
       Returns false instead of failing the test when the element cannot be found or displayed. */
    public boolean isDisplayed(By locator) {
        try {
            return waitForElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /* Waits for the requested element and scrolls it into the visible browser area.
       The element is positioned near the center of the viewport to make interaction more reliable. */
    public void scrollToElement(By locator) {
        WebElement element = waitForElement(locator);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                element
        );
    }

    /* Waits for the requested input element and removes its existing value.
       This provides a reusable way to reset an input before entering new data. */
    public void clear(By locator) {
        waitForElement(locator).clear();
    }

    // Wait for OrangeHRM form loader to disappear
    public void waitForFormLoaderToDisappear() {
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        PageElements.FORM_LOADER
                )
        );
    }

    // Waits until at least one element matching the locator is present in the DOM.
    public List<WebElement> waitForElements(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    /* Passes three locator levels to the self-healing engine and returns the first successful element.
       The method keeps the fallback logic outside the test case so EndToEnd remains simple and readable. */
    public WebElement selfHealingFind(By locator1, By locator2, By locator3, String description) {
        return selfHealingLocator.find(locator1, locator2, locator3, description);
    }

    /* Uses the three-level locator strategy to find the requested element before clicking it.
       If the primary locator fails, SelfHealingLocator automatically attempts the configured fallback locators. */
    public void selfHealingClick(By locator1, By locator2, By locator3, String description) {
        waitForFormLoaderToDisappear();
        WebElement element = selfHealingLocator.find(locator1, locator2, locator3, description);
        element.click();
    }

    // Provides a simple element name so the test does not need to pass three locators manually.
    public void selfHealingClick(String elementName) {

        switch (elementName) {

            case "LOGIN_BUTTON":
                selfHealingClick(PageElements.LOGIN_BUTTON_L1, PageElements.LOGIN_BUTTON_L2, PageElements.LOGIN_BUTTON_L3, "Login button");
                break;
            case "PIM":
                selfHealingClick(PageElements.PIM_L1, PageElements.PIM_L2, PageElements.PIM_L3, "PIM");
                break;
            case "ADD_EMPLOYEE":
                selfHealingClick(PageElements.ADD_EMPLOYEE_L1, PageElements.ADD_EMPLOYEE_L2, PageElements.ADD_EMPLOYEE_L3, "Add Employee");
                break;
            case "SAVE_BUTTON":
                selfHealingClick(PageElements.SAVE_BUTTON_L1, PageElements.SAVE_BUTTON_L2, PageElements.SAVE_BUTTON_L3, "Save button");
                break;
            case "EMPLOYEE_LIST":
                selfHealingClick(PageElements.EMPLOYEE_LIST_L1, PageElements.EMPLOYEE_LIST_L2, PageElements.EMPLOYEE_LIST_L3, "Employee List");
                break;
            default:
                throw new IllegalArgumentException("Unknown self-healing element: " + elementName);
        }
    }
    /* Provides a simple element name so the test can enter text using the self-healing strategy.
       The method resolves the element name to its L1, L2 and L3 locators before entering the supplied text. */
    public void selfHealingType(String elementName, String text) {

        WebElement element;
        switch (elementName) {
            case "USERNAME":
                element = selfHealingFind(PageElements.USERNAME_L1, PageElements.USERNAME_L2, PageElements.USERNAME_L3, "Username");
                break;
            case "PASSWORD":
                element = selfHealingFind(PageElements.PASSWORD_L1, PageElements.PASSWORD_L2, PageElements.PASSWORD_L3, "Password");
                break;

            default:
                throw new IllegalArgumentException("Unknown self-healing element: " + elementName);
        }
        element.clear();
        element.sendKeys(text);
    }

    public WebElement waitForElementWithTimeout(By locator, int seconds) {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement selfHealingFindFast(By locator1, By locator2, By locator3, String description) {
        return selfHealingLocator.findFast(locator1, locator2, locator3, description);
    }
    public void selfHealingClickFast(String elementName) {

        WebElement element;

        switch (elementName) {

            case "LOGIN_BUTTON":
                element = selfHealingFindFast(PageElements.LOGIN_BUTTON_L1, PageElements.LOGIN_BUTTON_L2, PageElements.LOGIN_BUTTON_L3, "Login button");
                break;
            case "PIM":
                element = selfHealingFindFast(PageElements.PIM_L1, PageElements.PIM_L2, PageElements.PIM_L3, "PIM");
                break;
            case "ADD_EMPLOYEE":
                element = selfHealingFindFast(PageElements.ADD_EMPLOYEE_L1, PageElements.ADD_EMPLOYEE_L2, PageElements.ADD_EMPLOYEE_L3, "Add Employee");
                break;
            case "SAVE_BUTTON":
                element = selfHealingFindFast(PageElements.SAVE_BUTTON_L1, PageElements.SAVE_BUTTON_L2, PageElements.SAVE_BUTTON_L3, "Save button");
                break;
            case "EMPLOYEE_LIST":
                element = selfHealingFindFast(PageElements.EMPLOYEE_LIST_L1, PageElements.EMPLOYEE_LIST_L2, PageElements.EMPLOYEE_LIST_L3, "Employee List");
                break;
            default:
                throw new IllegalArgumentException("Unknown self-healing element: " + elementName);
        }

        element.click();
    }

}