package stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import pages.CatalogPage;
import io.cucumber.java.en.*;

public class CatalogSteps {

    WebDriver driver = new ChromeDriver();
    CatalogPage catalogPage;

    public CatalogSteps(WebDriver driver) {
        this.driver = driver;
        catalogPage = PageFactory.initElements(driver, CatalogPage.class);
    }

    @Given("User is on the Catalog Page")
    public void userIsOnCatalogPage() {
        driver.get("https://damaarsi.madanateknologi.web.id/admin/produk");
    }

    @When("User clicks the \"Tambah Produk\" button")
    public void clickTambahProdukButton() {
        catalogPage.clickTambahProdukButton();
    }

    @When("User fills all required fields with valid data:")
    public void fillRequiredFieldsWithValidData(io.cucumber.datatable.DataTable dataTable) {
        catalogPage.fillFields(dataTable.asMap(String.class, String.class));
    }

    @When("User fills all required fields except \"Harga Per Meter\" with valid data:")
    public void fillFieldsExceptHarga(io.cucumber.datatable.DataTable dataTable) {
        catalogPage.fillFieldsExceptHarga(dataTable.asMap(String.class, String.class));
    }

    @And("User clicks the \"Simpan\" button")
    public void clickSimpanButton() {
        catalogPage.clickSimpanButton();
    }

    @Then("The product should appear in the catalog and user interface")
    public void verifyProductInCatalog() {
        catalogPage.verifyProductInCatalog();
    }

    @Then("User should see catalog validation error messages")
    public void verifyCatalogValidationErrors() {
        catalogPage.verifyValidationErrors();
    }
}
