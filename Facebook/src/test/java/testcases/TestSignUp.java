package testcases;

import base.CommonAPI;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import pageobjects.HomePage;
import pageobjects.SignUpPage;

import static java.lang.Thread.sleep;

public class TestSignUp extends CommonAPI {

    @Test
    public void testSignUp() throws InterruptedException {
        HomePage hp = PageFactory.initElements(driver, HomePage.class);
        hp.goToSignUpPage();
        sleepFor(3);
        SignUpPage fbSignUp = PageFactory.initElements(driver, SignUpPage.class);
        fbSignUp.signUpFB("Jon", "Snow", "bastardofthenorth@gmail.com", "iknownothing",
                "January", "21", "1993", "male");
    }

}
