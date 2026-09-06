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
    public WebElement find(By locator1, By locator2, By locator3, String description) {
        // Locator 1 - Very specific
        try {
            WebElement element = common.waitForElement(locator1);
            logger.info("Locator 1 succeeded for: {}", description);
            return element;
        } catch (Exception e1) {
            logger.warn("Locator 1 failed for: {} | {}", description, e1.getClass().getSimpleName());
        }

        // Locator 2 - Slightly specific
        try {
            WebElement element = common.waitForElement(locator2);
            logger.info("Locator 2 succeeded for: {}", description);
            logger.warn("Self-healing fallback: Locator 2 used for: {}", description);
            return element;
        } catch (Exception e2) {
            logger.warn("Locator 2 failed for: {} | {}", description, e2.getClass().getSimpleName());
        }
        // Locator 3 - Generic
        try {
            WebElement element = common.waitForElement(locator3);
            logger.info("Locator 3 succeeded for: {}", description);
            logger.warn("Self-healing fallback: Locator 3 used for: {}", description);
            return element;
        } catch (Exception e3) {
            logger.warn("Locator 3 failed for: {} | {}", description, e3.getClass().getSimpleName());
            logger.error("All three locators failed for: {}", description);
            throw e3;
        }
    }
}