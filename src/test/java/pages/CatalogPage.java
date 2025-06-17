package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.Map;

public class CatalogPage {

    WebDriver driver;

    public CatalogPage(WebDriver driver) {
        this.driver = driver;
    }

    By tambahProdukButton = By.id("tambahProdukButton");
    By simpanButton = By.id("simpanButton");
    By validationErrors = By.className("validation-error");

    public void clickTambahProdukButton() {
        driver.findElement(tambahProdukButton).click();
    }

    public void fillFields(Map<String, String> data) {
        data.forEach((field, value) -> {
            By fieldLocator = By.id(field.replace(" ", "").toLowerCase());
            driver.findElement(fieldLocator).sendKeys(value);
        });
    }

    public void fillFieldsExceptHarga(Map<String, String> data) {
        data.forEach((field, value) -> {
            if (!field.equalsIgnoreCase("Harga Per Meter")) {
                By fieldLocator = By.id(field.replace(" ", "").toLowerCase());
                driver.findElement(fieldLocator).sendKeys(value);
            }
        });
    }

    public void clickSimpanButton() {
        driver.findElement(simpanButton).click();
    }

    public void verifyProductInCatalog() {
        // Implement verification logic for product presence in catalog
    }

    public void verifyValidationErrors() {
        boolean errorsDisplayed = driver.findElements(validationErrors).size() > 0;
        if (!errorsDisplayed) {
            throw new AssertionError("Validation errors not displayed");
        }
    }
}
