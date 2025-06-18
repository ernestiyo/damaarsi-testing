package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.time.Duration;
import java.util.List; // For finding multiple elements
import java.util.Map;

public class CatalogPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private final By addProductButton = By.xpath("//a[contains(text(), 'Tambah Produk')]");
    private final By productNameField = By.id("productName");
    private final By pricePerMeterField = By.id("productPrice");
    private final By descriptionField = By.id("productDescription");
    private final By productTypeDropdown = By.id("productType");
    private final By image1Field = By.name("gambar1");
    private final By dataTable = By.id("dataTable");
    private final By catalogPageHeading = By.xpath("//h1[contains(text(), 'Daftar Katalog')]");
    private final By validationError = By.className("text-danger"); // Generic locator for validation errors
    private final By successFlashMessage = By.cssSelector(".alert.alert-success");
    private final By addProductModalSaveButton = By.xpath("//div[@id='addProductModal']//button[contains(text(), 'Tambah')]");

    public CatalogPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increased wait time to 15 seconds
    }

    public void navigateToCatalogPage(String url) {
        driver.get(url);
        waitForCatalogPageLoad(); // Call this immediately after navigation
    }

    public void waitForCatalogPageLoad() {
        try {
            // Wait for URL to be the expected base URL after potential redirects/reloads
            wait.until(ExpectedConditions.urlContains("/admin/produk"));

            // Wait for key elements to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(catalogPageHeading));
            wait.until(ExpectedConditions.visibilityOfElementLocated(addProductButton));
            wait.until(ExpectedConditions.visibilityOfElementLocated(dataTable));
            System.out.println("Catalog Page loaded successfully and stable.");
        } catch (org.openqa.selenium.TimeoutException e) {
            System.err.println("Timeout waiting for Catalog Page elements to load. Current URL: " + driver.getCurrentUrl());
            throw e;
        }
    }

    public void clickAddProductButton() {
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(addProductButton));
        System.out.println("Clicking 'Tambah Produk' button.");
        addButton.click();
        // Wait for the add product modal to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(productNameField)); // Check for a field in the modal
        System.out.println("'Tambah Produk' modal is displayed.");
    }

    public void enterProductName(String productName) {
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(productNameField));
        nameField.clear();
        nameField.sendKeys(productName);
        System.out.println("Entered product name: " + productName);
    }

    public void enterPricePerMeter(String price) {
        WebElement priceField = wait.until(ExpectedConditions.visibilityOfElementLocated(pricePerMeterField));
        priceField.clear();
        priceField.sendKeys(price);
        System.out.println("Entered price per meter: " + price);
    }

    public void enterDescription(String description) {
        WebElement descField = wait.until(ExpectedConditions.visibilityOfElementLocated(descriptionField));
        descField.clear();
        descField.sendKeys(description);
        System.out.println("Entered description: " + description);
    }

    public void selectProductType(String type) {
        WebElement typeDropdown = wait.until(ExpectedConditions.elementToBeClickable(productTypeDropdown));
        typeDropdown.click(); // Open the dropdown
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='productType']/option[text()='" + type + "']")));
        option.click();
        System.out.println("Selected product type: " + type);
    }

    public void uploadImage1(String fileName) {
        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" +
                File.separator + "resources" + File.separator + "assets" + File.separator + fileName;

        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("File not found at: " + filePath + ". Current working directory: " + System.getProperty("user.dir"));
        }

        WebElement imageField = wait.until(ExpectedConditions.presenceOfElementLocated(image1Field));
        imageField.sendKeys(file.getAbsolutePath());
        System.out.println("Uploaded image: " + file.getAbsolutePath());
    }

    public void clickSaveButton() {
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(addProductModalSaveButton));
        System.out.println("Clicking 'Tambah' button in the modal...");
        saveBtn.click();

        // **Critical Improvement:** Wait for the page to become "stale" (old DOM) and then for new elements to load (new DOM).
        // This is a robust way to handle location.reload() or full page navigations.
        System.out.println("Waiting for page reload and stability...");
        try {
            // Wait for a key element from the *old* page to become stale
            wait.until(ExpectedConditions.stalenessOf(driver.findElement(catalogPageHeading)));
            System.out.println("Old page elements are stale. Page reload in progress.");
        } catch (Exception e) {
            System.out.println("Could not detect staleness, assuming immediate reload or element not found: " + e.getMessage());
        }

        // Then, wait for the new page to load (by checking for its key elements)
        waitForCatalogPageLoad(); // Re-use existing method for page load
        System.out.println("Page reloaded and new DOM is ready.");
    }

    public boolean isSuccessPopupDisplayed() {
        System.out.println("Attempting to detect success flash message...");
        try {
            // Wait for the flash message to be visible after the page has reloaded
            WebElement successAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(successFlashMessage));
            String messageText = successAlert.getText();
            System.out.println("Success flash message detected: '" + messageText + "'");

            // Optional: You might want to wait for it to disappear if it's transient,
            // but for a simple display check, visibility is enough.
            // wait.until(ExpectedConditions.invisibilityOfElementLocated(successFlashMessage));
            // System.out.println("Success flash message disappeared (if transient).");

            return successAlert.isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            System.err.println("Timeout: Success flash message was NOT detected within the allotted time.");
            // Print page source to help debug if element is present but not visible
            System.err.println("Current URL: " + driver.getCurrentUrl());
            System.err.println("Page title: " + driver.getTitle());
            // System.err.println("Page Source (partial, for debugging): " + driver.getPageSource().substring(0, Math.min(driver.getPageSource().length(), 2000))); // Print first 2000 chars

            // Further check: Try to find elements, even if not visible
            List<WebElement> alerts = driver.findElements(successFlashMessage);
            if (!alerts.isEmpty()) {
                System.err.println("Flash message element found in DOM but not visible. Text: " + alerts.get(0).getText() + ", Displayed: " + alerts.get(0).isDisplayed());
            } else {
                System.err.println("Flash message element not found in DOM at all.");
            }
            return false;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while checking for success message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasValidationError() {
        try {
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(validationError));
            System.out.println("Validation error detected: " + errorElement.getText());
            return errorElement.isDisplayed() && !errorElement.getText().isEmpty();
        } catch (org.openqa.selenium.TimeoutException | org.openqa.selenium.NoSuchElementException e) {
            try {
                wait.until(ExpectedConditions.alertIsPresent());
                driver.switchTo().alert().accept();
                System.out.println("Alert validation error detected and dismissed.");
                return true;
            } catch (org.openqa.selenium.TimeoutException alertEx) {
                System.out.println("No validation error or alert found.");
                return false;
            }
        }
    }

    public void fillProductForm(Map<String, String> data) {
        if (data.containsKey("Nama Katalog")) enterProductName(data.get("Nama Katalog"));
        if (data.containsKey("Harga Per Meter")) enterPricePerMeter(data.get("Harga Per Meter"));
        if (data.containsKey("Deskripsi")) enterDescription(data.get("Deskripsi"));
        if (data.containsKey("Tipe Produk")) selectProductType(data.get("Tipe Produk"));
        if (data.containsKey("Gambar 1")) uploadImage1(data.get("Gambar 1"));
    }

    public void fillProductFormExcept(String fieldToSkip, Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (!entry.getKey().equals(fieldToSkip)) {
                switch (entry.getKey()) {
                    case "Nama Katalog":
                        enterProductName(entry.getValue());
                        break;
                    case "Harga Per Meter":
                        enterPricePerMeter(entry.getValue());
                        break;
                    case "Deskripsi":
                        enterDescription(entry.getValue());
                        break;
                    case "Tipe Produk":
                        selectProductType(entry.getValue());
                        break;
                    case "Gambar 1":
                        if (!entry.getValue().equalsIgnoreCase("(kosong)")) {
                            uploadImage1(entry.getValue());
                        }
                        break;
                }
            }
        }
    }

    public boolean isProductListed(String productName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='dataTable']//td[contains(., '" + productName + "')]")));
            System.out.println("Product '" + productName + "' found in the table.");
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            System.err.println("Product '" + productName + "' was NOT found in the table within the allotted time.");
            return false;
        }
    }
}