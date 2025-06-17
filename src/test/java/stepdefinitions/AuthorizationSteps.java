package stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import pages.LoginPage;
import pages.DashboardPage;
import io.cucumber.java.en.*;

public class AuthorizationSteps {

    WebDriver driver = new ChromeDriver();
    LoginPage loginPage;
    DashboardPage dashboardPage;

    public AuthorizationSteps() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
    }

    @Given("User is on the Login Page")
    public void userIsOnLoginPage() {
        driver.get("https://damaarsi.madanateknologi.web.id/admin");
    }

    @When("User inputs valid username {string} and password {string}")
    public void inputValidCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("User inputs invalid username {string} and password {string}")
    public void inputInvalidCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @And("User clicks the login button")
    public void clickLoginButton() {
        loginPage.clickLoginButton();
    }

    @Then("User should be redirected to the Superadmin Dashboard")
    public void verifyDashboardRedirection() {
        dashboardPage.verifyDashboardLoaded();
    }

    @Then("User should see login validation error messages")
    public void verifyLoginValidationErrors() {
        loginPage.verifyValidationErrors();
    }
}
