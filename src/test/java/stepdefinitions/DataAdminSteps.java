package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.junit.Assert;
import pages.DataAdminPage;
import utils.TestContext;

public class DataAdminSteps {

    private WebDriver driver;
    private DataAdminPage dataAdminPage;
    private final TestContext testContext;
    private static final String DATA_ADMIN_PAGE_URL = "https://damaarsi.madanateknologi.web.id/admin/dataadmin";

    private String selectedAdminUsername;

    public DataAdminSteps(TestContext context) {
        this.testContext = context;
        this.driver = testContext.getDriver();
        this.dataAdminPage = new DataAdminPage(this.driver);
    }

    @Given("User is on the Data Admin Page")
    public void userIsOnDataAdminPage() {
        driver.get(DATA_ADMIN_PAGE_URL);
        dataAdminPage.waitForDataAdminPageLoad();
    }

    @When("User selects an admin with {string} status")
    public void userSelectsAnAdminWithStatus(String status) {
        selectedAdminUsername = dataAdminPage.findAdminUsernameByStatus(status);
        Assert.assertNotNull(
                String.format("No admin with '%s' status found to select. Please ensure your test data contains such an admin.", status),
                selectedAdminUsername
        );
        System.out.println("Selected admin with " + status + " status: " + selectedAdminUsername);
    }

    @And("User clicks the {string} button for the selected admin")
    public void userClicksTheButtonForSelectedAdmin(String buttonText) {
        dataAdminPage.clickToggleButtonForAdmin(selectedAdminUsername);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        driver.navigate().refresh();
        System.out.println("Page refreshed after clicking toggle button.");
        dataAdminPage.waitForDataAdminPageLoad();
    }

    @Then("The selected admin's status should change to {string}")
    public void theSelectedAdminStatusShouldChangeTo(String expectedStatus) {
        String currentStatus = dataAdminPage.getAdminStatus(selectedAdminUsername);
        Assert.assertEquals(
                String.format("Admin status did not change to '%s'. Current status: '%s'", expectedStatus, currentStatus),
                expectedStatus,
                currentStatus
        );
        System.out.println("Verified admin status changed to: " + currentStatus);
    }

    @And("The button for the selected admin should change to {string}")
    public void theButtonForTheSelectedAdminShouldChangeTo(String expectedButtonText) {
        String currentButtonText = dataAdminPage.getToggleButtonText(selectedAdminUsername);
        Assert.assertEquals(
                String.format("Button text did not change to '%s'. Current text: '%s'", expectedButtonText, currentButtonText),
                expectedButtonText,
                currentButtonText
        );
        System.out.println("Verified button text changed to: " + currentButtonText);
    }
}