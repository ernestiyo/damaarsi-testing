package stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import pages.DataAdminPage;
import io.cucumber.java.en.*;

public class DataAdminSteps {

    WebDriver driver = new ChromeDriver();
    DataAdminPage dataAdminPage;

    public DataAdminSteps(WebDriver driver) {
        this.driver = driver;
        dataAdminPage = PageFactory.initElements(driver, DataAdminPage.class);
    }

    @Given("User is on the Data Admin Page")
    public void userIsOnDataAdminPage() {
        driver.get("https://damaarsi.madanateknologi.web.id/admin/dataadmin");
    }

    @When("User selects a non-active admin")
    public void selectNonActiveAdmin() {
        dataAdminPage.selectNonActiveAdmin();
    }

    @And("User clicks the \"Aktivasi\" button")
    public void clickAktivasiButton() {
        dataAdminPage.clickAktivasiButton();
    }

    @Then("The admin account status should change to active")
    public void verifyAdminStatusActive() {
        dataAdminPage.verifyAdminStatusActive();
    }

    @Then("The \"Aktivasi\" button should change to \"Non-Aktivasi\"")
    public void verifyButtonChangeToNonAktivasi() {
        dataAdminPage.verifyButtonChangeToNonAktivasi();
    }
}
