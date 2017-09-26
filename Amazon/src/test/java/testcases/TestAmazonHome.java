package testcases;

import base.CommonAPI;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import pageobjects.HomePage;

import java.io.IOException;

public class TestAmazonHome extends CommonAPI {

   // @Test
    public void testSearchDropdownBox() throws InterruptedException {
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.clickOnSearchDropdownBox();
    }

    //@Test
    public void testprintSearchDropdownOptions() {
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.printSearchDropdownOptions();
    }

    @Test
    public void testSearchDropdownBoxWithSelect() throws IOException {
        HomePage hp = PageFactory.initElements(driver, HomePage.class);
        hp.selectOnSearchDropdownBox();
    }

    @Test
    public void testSearchFor() throws Exception {
        HomePage hp = PageFactory.initElements(driver, HomePage.class);
        hp.getDataFromExcelFileAndSearch();
    }


//    @Test
//    public void testFindLinks(){
//        List<WebElement> linksList = clickableLinks();
//        for(WebElement link : linksList){
//            String href = link.getAttribute("href");
//            try{
//                System.out.println("URL: " + href + " returned " + getLinkStatus(new URL(href)));
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//        }
//    }

//    @DataProvider(name = "amazonList")
//    public Object[][] dataProvider(){
//        Object[][] testData = ExcelReader.getTestData("AmazonList");
//        return testData;
//    }
//
//    @Test(dataProvider = "amazonList")
//    public void testsearchUsingExcel(String item) throws InterruptedException {
//        HomePage hp = PageFactory.initElements(driver, HomePage.class);
//        sleepFor(2);
//        hp.searchInput.sendKeys(item);
//        hp.searchBtn.click();
//        sleepFor(2);
//    }

}
