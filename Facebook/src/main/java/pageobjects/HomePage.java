package pageobjects;

import base.CommonAPI;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePage extends CommonAPI {

    @FindBy(how = How.ID, using = "email")
    public static WebElement emailBox;

    @FindBy(how = How.ID, using = "pass")
    public static WebElement passBox;

    @FindBy(how = How.XPATH, using = ".//a[text()='Sign Up']")
    public static WebElement signUpPage;

    @FindBy(how = How.XPATH, using = ".//a[text()='Log In']")
    public static WebElement loginPage;

    @FindBy(how = How.XPATH, using = ".//input[@type='submit']")
    public static WebElement loginBtn;

    public void logInFB(String email, String password) {
        emailBox.sendKeys(email);
        passBox.sendKeys(password);
        loginBtn.click();
    }

    public void goToSignUpPage() {
        signUpPage.click();
    }

    public void goToLoginPage() {
        loginPage.click();
    }

}
