package pageobjects;

import base.CommonAPI;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class WelcomePage extends CommonAPI {

    @FindBy(how = How.XPATH, using = ".//div[text()='News Feed']")
    public static WebElement newsFeedPage;

    @FindBy(how = How.XPATH, using = ".//div[text()='Messenger']")
    public static WebElement messengerPage;

    @FindBy(how = How.XPATH, using = ".//a[@title='Profile']")
    public static WebElement profilePage;

    public void goToProfilePage(){
        profilePage.click();
    }

    public void goToNewsFeedPage(){
        newsFeedPage.click();
    }

    public void goToMessengerPage(){
        messengerPage.click();
    }

}
