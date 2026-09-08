package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utilities.Common;
import utilities.SelfHealingReport;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import utilities.TestLogAppender;

public class ParentClass {

    protected WebDriver driver;
    protected Common common;
    private TestLogAppender testLogAppender;
    private Logger rootLogger;

    @BeforeMethod
    public void setUp() {
        rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        testLogAppender = new TestLogAppender();
        testLogAppender.start();
        rootLogger.addAppender(testLogAppender);

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/");

        common = new Common(driver);
        common.loadCompleteWebPage();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            String testName = result.getMethod().getMethodName();
            String reportPath = SelfHealingReport.createNewReportFile();

            SelfHealingReport.generateReport(
                    reportPath,
                    testName,
                    testLogAppender.getLogs(),
                    common.getHealingEvents()
            );

            System.out.println("Self-healing report generated: " + reportPath);
        } catch (Exception e) {
            System.out.println("Failed to generate self-healing report: " + e.getMessage());
        } finally {
            if (rootLogger != null && testLogAppender != null) {
                rootLogger.detachAppender(testLogAppender);
                testLogAppender.stop();
            }

            if (driver != null) {
                driver.quit();
            }
        }
    }
}