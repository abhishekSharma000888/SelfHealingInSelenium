package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

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
            healingEvents.add(description + " | Locator 1 | FAILED | " + e1.getClass().getSimpleName());
        }

        // Locator 2 - Slightly specific
        try {
            WebElement element = common.waitForElement(locator2);
            logger.info("Locator 2 succeeded for: {}", description);
            logger.warn("Self-healing fallback: Locator 2 used for: {}", description);
            healingEvents.add(description + " | Locator 2 | FALLBACK SUCCESS");
            return element;
        } catch (Exception e2) {
            logger.warn("Locator 2 failed for: {} | {}", description, e2.getClass().getSimpleName());
            healingEvents.add(description + " | Locator 2 | FAILED | " + e2.getClass().getSimpleName());
        }
        // Locator 3 - Generic
        try {
            WebElement element = common.waitForElement(locator3);
            logger.info("Locator 3 succeeded for: {}", description);
            logger.warn("Self-healing fallback: Locator 3 used for: {}", description);
            healingEvents.add(description + " | Locator 3 | FALLBACK SUCCESS");
            return element;
        } catch (Exception e3) {
            logger.warn("Locator 3 failed for: {} | {}", description, e3.getClass().getSimpleName());
            healingEvents.add(description + " | Locator 3 | FAILED | " + e3.getClass().getSimpleName());
            throw e3;
        }
    }

    // new Test Method
    public WebElement findFast(By locator1, By locator2, By locator3, String description) {

        try {
            WebElement element = common.waitForElementWithTimeout(locator1, 2);
            logger.info("Locator 1 succeeded for: {}", description);
            return element;
        } catch (Exception e1) {
            logger.warn("Locator 1 failed for: {} | {}", description, e1.getClass().getSimpleName());
            healingEvents.add(description + " | Locator 1 | FAILED | " + e1.getClass().getSimpleName());
        }

        try {
            WebElement element = common.waitForElementWithTimeout(locator2, 2);
            logger.info("Locator 2 succeeded for: {}", description);
            logger.warn("Self-healing fallback: Locator 2 used for: {}", description);
            healingEvents.add(description + " | Locator 2 | FALLBACK SUCCESS");
            return element;
        } catch (Exception e2) {
            logger.warn("Locator 2 failed for: {} | {}", description, e2.getClass().getSimpleName());
            healingEvents.add(description + " | Locator 2 | FAILED | " + e2.getClass().getSimpleName());
        }

        try {
            WebElement element = common.waitForElementWithTimeout(locator3, 2);
            logger.info("Locator 3 succeeded for: {}", description);
            logger.warn("Self-healing fallback: Locator 3 used for: {}", description);
            healingEvents.add(description + " | Locator 3 | FALLBACK SUCCESS");
            return element;
        } catch (Exception e3) {
            logger.warn("Locator 3 failed for: {} | {}", description, e3.getClass().getSimpleName());
            healingEvents.add(description + " | Locator 3 | FAILED | " + e3.getClass().getSimpleName());
            throw e3;
        }
    }

    private final List<String> healingEvents = new ArrayList<>();
    public List<String> getHealingEvents() {
        return healingEvents;
    }

    public void clearHealingEvents() {
        healingEvents.clear();
    }

}