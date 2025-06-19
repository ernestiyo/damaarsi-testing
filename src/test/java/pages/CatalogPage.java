package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.time.Duration;
import java.util.List;
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
   private final By successFlashMessage = By.id("successUpdateModal");
    private final By addProductModalSaveButton = By.xpath("//div[@id='addProductModal']//button[contains(text(), 'Tambah')]");

    public CatalogPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void navigateToCatalogPage(String url) {
        driver.get(url);
    }



    public void clickAddProductButton() {
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(addProductButton));
        System.out.println("Clicking 'Tambah Produk' button.");
        addButton.click();
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
        typeDropdown.click();
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
    }

    public boolean isSuccessPopupDisplayed() {
        System.out.println("Attempting to detect success flash message...");
        try {
            WebElement successAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(successFlashMessage));
            String messageText = successAlert.getText();
            System.out.println("Success flash message detected: '" + messageText + "'");

            return successAlert.isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            System.err.println("Timeout: Success flash message was NOT detected within the allotted time.");
            System.err.println("Current URL: " + driver.getCurrentUrl());
            System.err.println("Page title: " + driver.getTitle());
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
                        uploadImage1(entry.getValue());
                }
            }
        }
    }

}