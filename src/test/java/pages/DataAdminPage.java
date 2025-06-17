package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DataAdminPage {

    WebDriver driver;

    public DataAdminPage(WebDriver driver) {
        this.driver = driver;
    }

    By nonActiveAdmin = By.className("non-active-admin");
    By aktivasiButton = By.id("aktivasiButton");
    By adminStatus = By.className("admin-status");

    public void selectNonActiveAdmin() {
        driver.findElement(nonActiveAdmin).click();
    }

    public void clickAktivasiButton() {
        driver.findElement(aktivasiButton).click();
    }

    public void verifyAdminStatusActive() {
        String status = driver.findElement(adminStatus).getText();
        if (!status.equalsIgnoreCase("active")) {
            throw new AssertionError("Admin status did not change to active");
        }
    }

    public void verifyButtonChangeToNonAktivasi() {
        String buttonText = driver.findElement(aktivasiButton).getText();
        if (!buttonText.equalsIgnoreCase("Non-Aktivasi")) {
            throw new AssertionError("Button did not change to Non-Aktivasi");
        }
    }
}
