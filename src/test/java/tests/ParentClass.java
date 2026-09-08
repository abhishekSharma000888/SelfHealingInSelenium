package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utilities.Common;
import utilities.SelfHealingReport;

public class ParentClass {

    protected WebDriver driver;
    protected Common common;

    @BeforeMethod
    public void setUp() {
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
                    common.getHealingEvents()
            );

            System.out.println("Self-healing report generated: " + reportPath);

        } catch (Exception e) {
            System.out.println("Failed to generate self-healing report: " + e.getMessage());
        }

        if (driver != null) {
            driver.quit();
        }
    }
}