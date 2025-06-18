package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    public static String takeScreenshotAsBase64(WebDriver driver) {
        if (driver == null) {
            System.err.println("WebDriver is null, cannot take screenshot.");
            return null;
        }
        try {
            // Langsung ambil screenshot sebagai Base64
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            System.err.println("Failed to take screenshot as Base64: " + e.getMessage());
            return null;
        }
    }
}