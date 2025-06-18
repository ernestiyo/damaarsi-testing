package utils;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;

public class TestContext {
    private WebDriver driver;
    private ExtentTest test;

    // Getters and Setters
    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public ExtentTest getTest() {
        return test;
    }

    public void setTest(ExtentTest test) {
        this.test = test;
    }
}