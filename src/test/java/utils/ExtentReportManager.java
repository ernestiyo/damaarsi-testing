package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    private static void createInstance() {
        String reportPath = System.getProperty("user.dir") + "/test-output/AutomationTestReport.html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Test Results");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setEncoding("utf-8");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("Kelompok", "PAD 17");
        extent.setSystemInfo("Environment", "Staging");
    }

    public static void endReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}