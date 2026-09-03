package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Common {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public Common(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
}