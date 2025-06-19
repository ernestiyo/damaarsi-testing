package stepdefinitions;

import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.junit.Assert;
import pages.CatalogPage;
import utils.ExtentReportManager;
import utils.TestContext;

import java.util.Map;

public class CatalogSteps {

    private WebDriver driver;
    private CatalogPage catalogPage;
    private String baseUrl = "https://damaarsi.madanateknologi.web.id/admin/produk";
    private ExtentTest test;
    private final TestContext testContext;

    public CatalogSteps(TestContext context) {
        this.testContext = context;
        driver = testContext.getDriver();
        catalogPage = new CatalogPage(testContext.getDriver());
        test = ExtentReportManager.getInstance().createTest("Catalog Test");
    }

    @Given("User is on the Catalog Page")
    public void userIsOnCatalogPage() {
        catalogPage.navigateToCatalogPage(baseUrl);
    }

    @When("User clicks the {string} button")
    public void clickTambahProdukButton(String buttonText) {
        if ("Tambah Produk".equals(buttonText)) {
            catalogPage.clickAddProductButton();
        } else if ("Tambah".equals(buttonText)) {
            catalogPage.clickSaveButton();
        }
    }

    @And("User fills all required fields with valid data:")
    public void fillRequiredFieldsWithValidData(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        catalogPage.fillProductForm(data);
    }

    @And("User fills all required fields except {string} with valid data:")
    public void fillFieldsExceptHarga(String fieldToSkip, io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        catalogPage.fillProductFormExcept(fieldToSkip, data);
    }

    @Then("User should see a success message popup")
    public void userShouldSeeASuccessMessagePopup() {
        Assert.assertTrue("Success message popup (flash message) was not displayed", catalogPage.isSuccessPopupDisplayed());
    }
}