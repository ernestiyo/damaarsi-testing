package stepdefinitions;

import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.junit.Assert;
import pages.LoginPage;
import utils.ExtentReportManager;
import utils.TestContext;

public class AuthorizationSteps {
    private WebDriver driver;
    private ExtentTest test;
    private final TestContext testContext;
    private LoginPage loginPage;
    private String baseUrl = "https://damaarsi.madanateknologi.web.id/admin"; // Adjust this to your application's login URL

    public AuthorizationSteps(TestContext context) {
        this.testContext = context;
        driver = testContext.getDriver();
        loginPage = new LoginPage(testContext.getDriver());
        test = ExtentReportManager.getInstance().createTest("Checkout Test");
    }

    @Given("User is on the Login Page")
    public void user_is_on_the_login_page() {
        loginPage.navigateToLoginPage(baseUrl);
    }

    @Given("User Should Login with valid credential")
    public void userShouldLoginWithValidCredential() {
        loginPage.navigateToLoginPage(baseUrl);
        loginPage.enterUsername("damaarsi01"); // Use your valid username
        loginPage.enterPassword("DamaarsiDiHati"); // Use your valid password
        loginPage.clickLoginButton();
    }

    @When("User inputs valid username {string} and password {string}")
    public void user_inputs_valid_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("User inputs invalid username {string} and password {string}")
    public void user_inputs_invalid_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @And("User clicks the login button")
    public void user_clicks_the_login_button() {
        loginPage.clickLoginButton();
    }

    @Then("User should be redirected to the Superadmin Dashboard")
    public void user_should_be_redirected_to_the_superadmin_dashboard() {
        // Assuming the dashboard URL contains "/admin/dashboard" or similar
        Assert.assertTrue("User was not redirected to the Superadmin Dashboard",
                loginPage.isRedirectedToDashboard("/admin/dashboard"));
    }

    @Then("User should see validation error messages")
    public void user_should_see_validation_error_messages() {
        // This step needs to be more specific based on the error messages.
        // For example, if it's "Username is required" or "Password is required"
        // Based on the JavaScript, it could be an alert or inline validation.
        Assert.assertTrue("Validation error messages were not displayed",
                loginPage.hasValidationError("")); // Checking for any validation message
        // You might want to make this more specific, e.g.,
        // Assert.assertTrue("Username error not displayed", loginPage.hasValidationError("The username field is required."));
        // Assert.assertTrue("Password error not displayed", loginPage.hasValidationError("The password field is required."));
    }
}