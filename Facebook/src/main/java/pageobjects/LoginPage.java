package pageobjects;

import base.CommonAPI;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage extends CommonAPI {

    @FindBy(how = How.ID, using = "email")
    public static WebElement emailBox;

    @FindBy(how = How.ID, using = "pass")
    public static WebElement passBox;

    @FindBy(how = How.ID, using = "loginbutton")
    public static WebElement loginBtn;

    @FindBy(how = How.XPATH, using = ".//a[text()='Create New Account']")
    public static WebElement createNewAcc;

    @FindBy(how = How.ID, using = "forgot-password-link")
    public static WebElement forgotAcc;

    public void loginFB(String email, String password) {
        emailBox.sendKeys(email);
        passBox.sendKeys(password);
        loginBtn.click();
    }

    public void createNewAcc() {
        createNewAcc.click();
    }

    public void forgotAcc() {
        forgotAcc.click();
    }

}
