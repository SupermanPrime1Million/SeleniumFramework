package testcases;

import base.CommonAPI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.HomePage;

public class TestHomePage extends CommonAPI {

    @Test
    public void testValidLoginFB(){
        HomePage hp = PageFactory.initElements(driver, HomePage.class);
        hp.logInFB("pntautomationtest@gmail.com", "Selenium123");
        WebElement homeTab = driver.findElement(By.xpath(".//a[@accesskey='1']"));
        Assert.assertTrue(homeTab != null);
        System.out.println("Test Passed");
    }

}
