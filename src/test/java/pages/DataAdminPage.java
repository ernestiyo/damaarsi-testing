package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class DataAdminPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private final By dataAdminPageHeading = By.xpath("//h1[contains(text(), 'Daftar Admin')]");
    private final By dataTable = By.id("dataTable");
    private final By tableRows = By.xpath("//table[@id='dataTable']/tbody/tr");

    public DataAdminPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver instance cannot be null in DataAdminPage constructor.");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void waitForDataAdminPageLoad() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(dataAdminPageHeading));
            wait.until(ExpectedConditions.visibilityOfElementLocated(dataTable));
            System.out.println("Data Admin Page loaded successfully.");
        } catch (org.openqa.selenium.TimeoutException e) {
            System.err.println("Timeout waiting for Data Admin Page elements. Current URL: " + driver.getCurrentUrl());
            throw new RuntimeException("Failed to load Data Admin Page within timeout.", e);
        }
    }


    public String findAdminUsernameByStatus(String status) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));
        List<WebElement> rows = driver.findElements(tableRows);

        for (WebElement row : rows) {
            try {
                 WebElement statusCell = row.findElement(By.xpath("./td[6]"));
                String currentStatus = statusCell.getText().trim();

                if (currentStatus.equalsIgnoreCase(status)) {
                    WebElement usernameCell = row.findElement(By.xpath("./td[3]"));
                    return usernameCell.getText().trim();
                }
            } catch (org.openqa.selenium.NoSuchElementException e) {
                continue;
            }
        }
        return null;
    }

    public WebElement getAdminRowByUsername(String username) {
        String xpath = String.format("//table[@id='dataTable']/tbody/tr[./td[3][normalize-space(text())='%s']]", username);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public WebElement getToggleButtonForAdminRow(WebElement adminRow) {
        return wait.until(ExpectedConditions.elementToBeClickable(adminRow.findElement(
                By.cssSelector("td:last-child .toggle-role-btn")
        )));
    }

    public void clickToggleButtonForAdmin(String adminUsername) {
        WebElement adminRow = getAdminRowByUsername(adminUsername);
        WebElement toggleButton = getToggleButtonForAdminRow(adminRow);
        toggleButton.click();
    }

    public String getAdminStatus(String adminUsername) {
        WebElement adminRow = getAdminRowByUsername(adminUsername);
        WebElement statusCell = adminRow.findElement(By.cssSelector("td.status"));
        return statusCell.getText().trim();
    }

    public String getToggleButtonText(String adminUsername) {
        WebElement adminRow = getAdminRowByUsername(adminUsername);
        WebElement toggleButton = getToggleButtonForAdminRow(adminRow);
        return toggleButton.getText().trim();
    }
}