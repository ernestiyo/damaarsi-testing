package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage {

    WebDriver driver;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    By dashboardHeader = By.id("dashboardHeader");

    public void verifyDashboardLoaded() {
        boolean isLoaded = driver.findElement(dashboardHeader).isDisplayed();
        if (!isLoaded) {
            throw new AssertionError("Dashboard not loaded");
        }
    }
}
