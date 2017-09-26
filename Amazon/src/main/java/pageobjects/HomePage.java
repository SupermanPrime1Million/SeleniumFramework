package pageobjects;

import base.CommonAPI;
import datareader.DataReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.util.List;

public class HomePage extends CommonAPI {

    DataReader dr = new DataReader();

    @FindBy(how = How.CSS, using = "#searchDropdownBox")
    public static WebElement searchDropdownBox;

    @FindBy(how = How.CSS, using = "#twotabsearchtextbox")
    public static WebElement searchInput;

    @FindBy(how = How.CSS, using = ".nav-input")
    public static WebElement searchBtn;

    public void clickOnSearchDropdownBox() {
        List<WebElement> list = getElementList("css","#searchDropdownBox>option" );
        for(int i=0; i < list.size(); i++){
            list.get(i).click();
        }
    }

    public void selectOnSearchDropdownBox(){
        Select sel = new Select(searchDropdownBox);
        List<WebElement> list = sel.getOptions();
        for(int i=0; i < list.size(); i++){
            list.get(i).click();
        }
    }

    public void printSearchDropdownOptions(){
        Select sel = new Select(searchDropdownBox);
        List<WebElement> list = sel.getOptions();
        for(int i=0; i < list.size(); i++){
            String optionName = list.get(i).getText();
            System.out.println(optionName);
        }
    }

    public String[] getDataFromExcel() throws IOException{
        String path = System.getProperty("user.dir")+"/src/test/resources/testData.xlsx";
        String [] st = dr.fileReader(path);
        return st;
    }

    public void getDataFromExcelFileAndSearch()throws IOException, InterruptedException{
        String[] value = getDataFromExcel();

        for (int i = 1; i < value.length; i++) {
            searchFor(value[i]);
            sleepFor(2);
            searchInput.clear();
        }
    }

    public void searchFor(String item){
        searchInput.sendKeys(item);
        searchBtn.click();
    }

}
