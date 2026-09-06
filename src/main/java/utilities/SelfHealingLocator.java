package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelfHealingLocator {

    private static final Logger logger = LoggerFactory.getLogger(SelfHealingLocator.class);
    private final Common common;
    public SelfHealingLocator(Common common) {
        this.common = common;
    }
    public WebElement find(
            By locator1,
            By locator2,
            By locator3,
            String description) {
        // Locator 1 - Most specific
        try {
            WebElement element = common.waitForElement(locator1);
            logger.info("Locator 1 succeeded for: {}", description);
            return element;
        } catch (Exception e1) {
            logger.warn("Locator 1 failed for: {}", description);
        }
        // Locator 2 - Moderately specific
        try {
            WebElement element = common.waitForElement(locator2);

            logger.warn("Self-healing: Locator 2 succeeded after Locator 1 failed for: {}", description);
            return element;
        } catch (Exception e2) {logger.warn("Locator 2 failed for: {}", description);
        }
        // Locator 3 - Generic
        try {
            WebElement element = common.waitForElement(locator3);
            logger.warn("Self-healing: Locator 3 succeeded after Locator 1 and Locator 2 failed for: {}", description);
            return element;
        } catch (Exception e3) {
            logger.error("All three locators failed for: {}", description);
            throw e3;
        }
    }
}