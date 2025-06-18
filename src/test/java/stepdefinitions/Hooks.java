package stepdefinitions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.ExtentReportManager;
import utils.ScreenshotUtil;
import utils.TestContext;

public class Hooks {

    private final TestContext context;
    private static final ExtentReports extent = ExtentReportManager.getInstance();
    private ExtentTest test;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    public void setup(Scenario scenario) {
        test = extent.createTest(scenario.getName());
        context.setTest(test);
        context.setDriver(new ChromeDriver());
        context.getDriver().manage().window().maximize();
        test.log(Status.INFO, "Scenario Started: " + scenario.getName());
    }

    @After
    public void teardown(Scenario scenario) {
        WebDriver driver = context.getDriver();
        if (driver != null) {
            // Ambil screenshot sebagai Base64
            String base64Screenshot = ScreenshotUtil.takeScreenshotAsBase64(driver);

            // Tentukan status dan log ke report
            if (scenario.isFailed()) {
                test.log(Status.FAIL, "Scenario Failed: " + scenario.getName());
                // Lampirkan screenshot Base64 ke log FAIL
                attachBase64ScreenshotToReport(test, "Screenshot on Failure:", base64Screenshot);
            } else {
                test.log(Status.PASS, "Scenario Passed.");
                // Lampirkan screenshot Base64 ke log PASS
                attachBase64ScreenshotToReport(test, "Final Screenshot:", base64Screenshot);
            }

            // Tutup browser
            driver.quit();
            test.log(Status.INFO, "Browser closed.");
        }
    }

    /**
     * Metode bantuan untuk melampirkan screenshot Base64 ke report.
     */
    private void attachBase64ScreenshotToReport(ExtentTest test, String title, String base64Image) {
        if (base64Image != null && !base64Image.isEmpty()) {
            try {
                // Menggunakan fail() atau pass() akan memberikan ikon visual di report
                if (test.getStatus() == Status.FAIL) {
                    test.fail(title, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
                } else {
                    test.pass(title, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
                }
            } catch (Exception e) {
                test.log(Status.WARNING, "Could not attach Base64 screenshot: " + e.getMessage());
            }
        }
    }

    @AfterAll
    public static void afterAll() {
        ExtentReportManager.endReport();
    }
}