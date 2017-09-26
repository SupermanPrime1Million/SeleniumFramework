package pageobjects;

import base.CommonAPI;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class ProfilePage extends CommonAPI {

    @FindBy(how = How.XPATH, using = ".//textarea[contains(@placeholder,'on your mind')]")
    public static WebElement commentBox;

    @FindBy(how = How.XPATH, using = ".//button[@type='submit']//span")
    public static WebElement postBtn;

    @FindBy(how = How.XPATH, using = ".//div[@data-ft='{\"tn\":\"K\"}']/div/p")
    public static WebElement latestComment;

    public void postComment(String text) throws InterruptedException {
        commentBox.sendKeys(text);
        sleepFor(5);
        postBtn.click();
        sleepFor(5);
        String commentedText = latestComment.getText();
        Assert.assertEquals(text, commentedText);
        System.out.println("Test passed");
    }

}
