package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import elements.PageElements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class Common {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final SelfHealingLocator selfHealingLocator;

    public Common(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.selfHealingLocator = new SelfHealingLocator(this);
    }

    // Wait for complete page load
    public void loadCompleteWebPage() {
        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }

    // Wait until element is visible
    public WebElement waitForElement(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    // Wait until element is clickable
    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );
    }

    // Click an element
    public void click(By locator, String description) {
        waitForFormLoaderToDisappear();
        WebElement element = waitForElementToBeClickable(locator);
        element.click();
    }

    // Enter text into an element
    public void type(By locator, String text, String description) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    // Get text from an element
    public String getText(By locator) {
        return waitForElement(locator).getText();
    }

    // Check whether an element is displayed
    public boolean isDisplayed(By locator) {
        try {
            return waitForElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Scroll element into view
    public void scrollToElement(By locator) {
        WebElement element = waitForElement(locator);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                element
        );
    }

    // Clear an input field
    public void clear(By locator) {
        waitForElement(locator).clear();
    }

    public String getValue(By locator) {
        return waitForElement(locator).getAttribute("value");
    }

    // Wait for OrangeHRM form loader to disappear
    public void waitForFormLoaderToDisappear() {
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        PageElements.FORM_LOADER
                )
        );
    }

    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    public List<WebElement> waitForElements(By locator) {
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(locator)
        );
    }

    private static final Logger logger =
            LoggerFactory.getLogger(Common.class);

    // Self-healing locator
    public WebElement selfHealingFind(By locator1, By locator2, By locator3, String description) {
        return selfHealingLocator.find(locator1, locator2, locator3, description);
    }
    
    public void selfHealingClick(By locator1, By locator2, By locator3, String description) {
        waitForFormLoaderToDisappear();
        WebElement element = selfHealingLocator.find(locator1, locator2, locator3, description);
        element.click();
    }
}