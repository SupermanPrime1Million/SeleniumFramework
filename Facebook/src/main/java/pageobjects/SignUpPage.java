package pageobjects;

import base.CommonAPI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import static java.lang.Thread.sleep;

public class SignUpPage extends CommonAPI {

    @FindBy(how = How.NAME, using = "firstname")
    public static WebElement firstNameBox;

    @FindBy(how = How.NAME, using = "lastname")
    public static WebElement lastNameBox;

    @FindBy(how = How.NAME, using = "reg_email__")
    public static WebElement emailBox;

    @FindBy(how = How.NAME, using = "reg_email_confirmation__")
    public static WebElement emailConfirmBox;

    @FindBy(how = How.NAME, using = "reg_passwd__")
    public static WebElement passwordBox;

    @FindBy(how = How.ID, using = "month")
    public static WebElement monthDropDown;

    @FindBy(how = How.ID, using = "day")
    public static WebElement dayDropDown;

    @FindBy(how = How.ID, using = "year")
    public static WebElement yearDropDown;

    @FindBy(how = How.XPATH, using = ".//input[@name='sex' and @value='1']")
    public static WebElement femaleBtn;

    @FindBy(how = How.XPATH, using = ".//input[@name='sex' and @value='2']")
    public static WebElement maleBtn;

    @FindBy(how = How.NAME, using = "websubmit")
    public static WebElement createAcc;

    public WebElement getMonthWebElement() {
        return monthDropDown;
    }

    public WebElement getDayWebElement() {
        return dayDropDown;
    }

    public WebElement getYearWebElement() {
        return yearDropDown;
    }

    public void signUpFB(String firstName, String lastName, String email, String pass, String month,
                         String day, String year, String gender) throws InterruptedException { //
        firstNameBox.sendKeys(firstName);
        sleep(1000);
        lastNameBox.sendKeys(lastName);
        sleep(1000);
        emailBox.sendKeys(email);
        sleep(1000);
        emailConfirmBox.sendKeys(email);
        sleep(1000);
        passwordBox.sendKeys(pass);
        sleep(1000);
        inputBirthday("month", "day", "year", month, day, year);
        sleep(1000);

        if (gender.equalsIgnoreCase("male")) {
            maleBtn.click();
        } else if (gender.equalsIgnoreCase("female")) {
            femaleBtn.click();
        }
        sleep(1000);
        createAcc.click();
    }

    private void inputBirthday(String monthElem, String dayElem, String yearElem, String month, String day, String year) {
        month = month.toLowerCase().substring(0, 3); //August -> august -> aug
        month = month.substring(0, 1).toUpperCase() + month.substring(1, 3); //Aug

        Select selMonth = new Select(driver.findElement(By.id(monthElem)));
        selMonth.selectByVisibleText(month);
        Select selDay = new Select(driver.findElement(By.id(dayElem)));
        selDay.selectByVisibleText(day);
        Select selYear = new Select(driver.findElement(By.id(yearElem)));
        selYear.selectByVisibleText(year);
    }


}
