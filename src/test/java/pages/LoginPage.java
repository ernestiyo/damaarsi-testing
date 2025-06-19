package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By usernameError = By.id("usernameError");
    private final By passwordError = By.id("passwordError");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToLoginPage(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
    }

    public void enterUsername(String username) {
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton() {
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginBtn.click();
    }

    public boolean isRedirectedToDashboard(String dashboardUrlPart) {
        return wait.until(ExpectedConditions.urlContains(dashboardUrlPart));
    }

    public boolean hasValidationError(String errorMessagePart) {
//        boolean usernameErrorVisible = false;
//        try {
//            WebElement userErr = driver.findElement(usernameError);
//            usernameErrorVisible = userErr.isDisplayed() && userErr.getText().contains(errorMessagePart);
//        } catch (org.openqa.selenium.NoSuchElementException e) {
//        }
//
//        boolean passwordErrorVisible = false;
//        try {
//            WebElement passErr = driver.findElement(passwordError);
//            passwordErrorVisible = passErr.isDisplayed() && passErr.getText().contains(errorMessagePart);
//        } catch (org.openqa.selenium.NoSuchElementException e) {
//        }

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            String alertText = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
            return alertText.contains(errorMessagePart);
        } catch (org.openqa.selenium.TimeoutException e) {
        }

//        return usernameErrorVisible || passwordErrorVisible;
    }
}