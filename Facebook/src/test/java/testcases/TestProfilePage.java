package testcases;

import base.CommonAPI;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import pageobjects.HomePage;
import pageobjects.ProfilePage;
import pageobjects.WelcomePage;

public class TestProfilePage extends CommonAPI {

    @Test
    public void testPostComment() throws InterruptedException {
        HomePage hp = PageFactory.initElements(driver, HomePage.class);
        hp.logInFB("pntautomationtest@gmail.com", "Selenium123");
        WelcomePage wp = PageFactory.initElements(driver, WelcomePage.class);
        wp.goToProfilePage();
        ProfilePage pp = PageFactory.initElements(driver, ProfilePage.class);
        pp.postComment("Automation Testing 2017!");
    }

}
