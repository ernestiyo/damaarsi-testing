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

    // --- Page Level Locators ---
    private final By dataAdminPageHeading = By.xpath("//h1[contains(text(), 'Daftar Admin')]");
    private final By dataTable = By.id("dataTable");
    private final By tableRows = By.xpath("//table[@id='dataTable']/tbody/tr");
    private final By addAdminButton = By.cssSelector("a[data-target='#addAdminModal']");

    // --- Add Admin Modal Locators (Optional - for future scenarios) ---
    private final By addAdminModal = By.id("addAdminModal");
    private final By adminNameField = By.id("adminName");
    private final By adminUsernameField = By.id("adminUsername");
    private final By adminTelpField = By.id("adminTelp");
    private final By adminEmailField = By.id("adminEmail");
    private final By adminPasswordField = By.id("adminPassword");
    private final By adminPasswordConfirmationField = By.id("adminPasswordConfirmation");
    private final By addAdminFormSubmitButton = By.xpath("//div[@id='addAdminModal']//button[contains(text(), 'Tambah')]");
    private final By successAddAdminModal = By.id("successAddAdmin");
    private final By successAddAdminModalCloseButton = By.xpath("//div[@id='successAddAdmin']//button[@class='close']");


    public DataAdminPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver instance cannot be null in DataAdminPage constructor.");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // --- Page Load and Navigation ---
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

    public void navigateToDataAdminPage(String url) {
        driver.get(url);
        waitForDataAdminPageLoad();
    }

    // --- Admin Table Interaction ---

    /**
     * Finds the username of the first admin with a specific status ('admin' or 'nonaktif').
     * @param status The desired status of the admin.
     * @return The username of the found admin, or null if not found.
     */
    public String findAdminUsernameByStatus(String status) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));
        List<WebElement> rows = driver.findElements(tableRows);

        for (WebElement row : rows) {
            try {
                // Assuming status is in the 6th column (td[6]) and username is in the 3rd column (td[3])
                // HTML: <th>No</th> (td[1]), <th>Nama</th> (td[2]), <th>Username</th> (td[3]), ..., <th>Status</th> (td[6])
                WebElement statusCell = row.findElement(By.xpath("./td[6]"));
                String currentStatus = statusCell.getText().trim();

                if (currentStatus.equalsIgnoreCase(status)) {
                    WebElement usernameCell = row.findElement(By.xpath("./td[3]"));
                    return usernameCell.getText().trim();
                }
            } catch (org.openqa.selenium.NoSuchElementException e) {
                // Skip rows that might not be valid data rows (e.g., "No matching records found")
                continue;
            }
        }
        return null; // No admin with the specified status found
    }

    /**
     * Finds the WebElement representing the table row for a given admin username.
     * @param username The username of the admin.
     * @return The WebElement of the admin's table row.
     */
    public WebElement getAdminRowByUsername(String username) {
        // Using normalize-space() to handle potential extra spaces in text comparison
        String xpath = String.format("//table[@id='dataTable']/tbody/tr[./td[3][normalize-space(text())='%s']]", username);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    /**
     * Gets the toggle button (Aktifkan/Nonaktifkan) for a specific admin row.
     * @param adminRow The WebElement of the admin's table row.
     * @return The WebElement of the toggle button.
     */
    public WebElement getToggleButtonForAdminRow(WebElement adminRow) {
        // The toggle button is inside the last 'td' (Aksi column)
        return wait.until(ExpectedConditions.elementToBeClickable(adminRow.findElement(
                By.cssSelector("td:last-child .toggle-role-btn")
        )));
    }

    /**
     * Clicks the toggle button for a selected admin.
     * @param adminUsername The username of the admin whose button needs to be clicked.
     */
    public void clickToggleButtonForAdmin(String adminUsername) {
        WebElement adminRow = getAdminRowByUsername(adminUsername);
        WebElement toggleButton = getToggleButtonForAdminRow(adminRow);
        toggleButton.click();
        // The UI updates directly, so no modal dismissal here.
        // Wait for potential network call/UI update in step definitions if needed.
    }

    /**
     * Gets the current status (role) of an admin from the table.
     * @param adminUsername The username of the admin.
     * @return The status string ('admin' or 'nonaktif').
     */
    public String getAdminStatus(String adminUsername) {
        WebElement adminRow = getAdminRowByUsername(adminUsername);
        // Status is in the 6th column (td[6]) and has class 'status'
        WebElement statusCell = adminRow.findElement(By.cssSelector("td.status"));
        return statusCell.getText().trim();
    }

    /**
     * Gets the current text displayed on the toggle button for an admin.
     * @param adminUsername The username of the admin.
     * @return The button text ('Aktifkan' or 'Nonaktifkan').
     */
    public String getToggleButtonText(String adminUsername) {
        WebElement adminRow = getAdminRowByUsername(adminUsername);
        WebElement toggleButton = getToggleButtonForAdminRow(adminRow);
        return toggleButton.getText().trim();
    }

    // --- Add Admin Modal Methods (Optional - for future scenarios) ---
    public void clickAddAdminButton() {
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(addAdminButton));
        addButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addAdminModal));
    }

    public void fillAddAdminForm(Map<String, String> formData) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminNameField)).sendKeys(formData.get("Nama"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminUsernameField)).sendKeys(formData.get("Username"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminTelpField)).sendKeys(formData.get("No Telp"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminEmailField)).sendKeys(formData.get("Email"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPasswordField)).sendKeys(formData.get("Password"));
    }

    public void confirmAdminPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPasswordConfirmationField)).sendKeys(password);
    }

    public void submitAddAdminForm() {
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(addAdminFormSubmitButton));
        submitButton.click();
        // After submission, JavaScript performs location.reload().
        // For success, you'd usually wait for the success flash message or confirm page reloaded.
    }

    public boolean isSuccessAddAdminModalDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successAddAdminModal)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public void dismissSuccessAddAdminModal() {
        if (isSuccessAddAdminModalDisplayed()) {
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(successAddAdminModalCloseButton));
            closeButton.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(successAddAdminModal));
        }
    }
}