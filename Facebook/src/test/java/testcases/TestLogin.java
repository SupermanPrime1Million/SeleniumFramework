package testcases;

import base.CommonAPI;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import pageobjects.HomePage;
import pageobjects.LoginPage;

import static java.lang.Thread.sleep;

public class TestLogin extends CommonAPI {

    @Test
    public void testLoginFB() throws InterruptedException {
        HomePage hp = PageFactory.initElements(driver, HomePage.class);
        hp.goToLoginPage();
        LoginPage lp = PageFactory.initElements(driver, LoginPage.class);
        lp.loginFB("pntautomationtest@gmail.com", "Selenium123");
    }

}
