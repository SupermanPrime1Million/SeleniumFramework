package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reporting.ExtentManager;
import reporting.ExtentTestManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class CommonAPI {

    public static ExtentReports extent;

    public static WebDriver driver = null;
    //public static JavascriptExecutor jse = (JavascriptExecutor) driver;

    private String saucelabs_username = "";
    private String browserstack_username = "";
    private String saucelabs_accesskey = "";
    private String browserstack_accesskey = "";

    @BeforeMethod
    @Parameters({"useCloudEnv", "cloudEnvName", "os", "os_version", "browser_name", "browser_version", "url"})
    public void setUp(boolean useCloudEnv, String cloudEnvName, String os, String os_version, String browser_name,
                      String browser_version, String url) throws IOException {
        System.out.println("Beginning Test...");
        if (useCloudEnv == true) {
            if (cloudEnvName.equalsIgnoreCase("browserstack")) {
                getCloudDriver(cloudEnvName, browserstack_username, browserstack_accesskey, os, os_version,
                        browser_name, browser_version);
            } else if (cloudEnvName.equalsIgnoreCase("saucelabs")) {
                getCloudDriver(cloudEnvName, saucelabs_username, saucelabs_accesskey, os, os_version,
                        browser_name, browser_version);
            }
        } else {
            getLocalDriver(os, browser_name);
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(35, TimeUnit.SECONDS);
        driver.navigate().to(url);
        driver.manage().window().maximize();
    }


    public WebDriver getLocalDriver(String os, String browser_name) {
        browser_name = browser_name.toLowerCase();
        os = os.toLowerCase();

        if (browser_name.contains("chrome")) {
            if (os.contains("win")) {
                System.setProperty("webdriver.chrome.driver", "../Generic/driver/chromedriver.exe");
                driver = new ChromeDriver();
            } else if (os.contains("mac") || os.contains("os x")) {
                System.setProperty("webdriver.chrome.driver", "../Generic/driver/chromedriver");
                driver = new ChromeDriver();
            } else {
                System.err.println("ERROR: Invalid driver path");
            }
        } else if (browser_name.contains("firefox")) {
            if (os.contains("win")) {
                System.setProperty("webdriver.gecko.driver", "../Generic/driver/geckodriver.exe");
                driver = new FirefoxDriver();
            } else if (os.contains("mac") || os.contains("os x")) {
                System.setProperty("webdriver.gecko.driver", "../Generic/driver/geckodriver");
                driver = new FirefoxDriver();
            } else {
                System.err.println("ERROR: Invalid driver path");
            }
        }

        return driver;
    }

    public WebDriver getCloudDriver(String envName, String envUsername, String envAccessKey, String os,
                                    String os_version, String browser_name, String browser_version) throws IOException {

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("browser", browser_name);
        cap.setCapability("browser_version", browser_version);
        cap.setCapability("os", os);
        cap.setCapability("os_version", os_version);
        if (envName.equalsIgnoreCase("Saucelabs")) {
            driver = new RemoteWebDriver(new URL("http://" + envUsername + ":" + envAccessKey +
                    "@ondemand.saucelabs.com:80/wd/hub"), cap);
        } else if (envName.equalsIgnoreCase("Browserstack")) {
            cap.setCapability("resolution", "1024x768");
            driver = new RemoteWebDriver(new URL("http://" + envUsername + ":" + envAccessKey +
                    "@hub-cloud.browserstack.com/wd/hub"), cap);
        }
        return driver;
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("Test finished");
        driver.close();
    }

    public void clickByCss(String locator) {
        driver.findElement(By.cssSelector(locator)).click();
    }
    public void clickByXpath(String locator) {
        driver.findElement(By.xpath(locator)).click();
    }

    public void typeByCss(String locator, String value) {
        driver.findElement(By.cssSelector(locator)).sendKeys(value);
    }
    public void typeByCssNEnter(String locator, String value) {
        driver.findElement(By.cssSelector(locator)).sendKeys(value, Keys.ENTER);
    }
    public void typeByXpath(String locator, String value) {
        driver.findElement(By.xpath(locator)).sendKeys(value);
    }
    public void typeByXpathNEnter(String locator, String value) {
        driver.findElement(By.xpath(locator)).sendKeys(value, Keys.ENTER);
    }

    public void clearInputField(String locator) {
        driver.findElement(By.cssSelector(locator)).clear();
    }

    public String getTextByCss(String locator) {
        String st = driver.findElement(By.cssSelector(locator)).getText();
        return st;
    }
    public String getTextByXpath(String locator) {
        String st = driver.findElement(By.xpath(locator)).getText();
        return st;
    }
    public String getTextById(String locator) {
        String st = driver.findElement(By.id(locator)).getText();
        return st;
    }
    public String getTextByName(String locator) {
        String st = driver.findElement(By.name(locator)).getText();
        return st;
    }
    public List<String> getTextFromWebElements(String type, String locator) {
        List<WebElement> element;
        List<String> text = new ArrayList<>();
        element = getElementList(type, locator);
        for (WebElement we : element) {
            text.add(we.getText());
        }
        return text;
    }

    public void selectOptionByVisibleText(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }

    public void navigateBack() {
        driver.navigate().back();
    }
    public void navigateForward() {
        driver.navigate().forward();
    }

    public void sleepFor(int sec) throws InterruptedException {
        Thread.sleep(sec * 1000);
    }

    //handling Alert
    public void okAlert() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
    public void cancelAlert() {
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

    //iframe Handle
    public void iframeHandle(WebElement element) {
        driver.switchTo().frame(element);
    }
    public void goBackToHomeWindow() {
        driver.switchTo().defaultContent();
    }

    //Synchronization
    public void waitUntilClickAble(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public void waitUntilVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public void waitUntilSelectable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        boolean element = wait.until(ExpectedConditions.elementToBeSelected(locator));
    }
    public void clickWhenReady(By locator, int timeout) {
        try {
            WebElement element = null;
            System.out.println("Waiting for a max: " + timeout + " seconds for element to be clickable.");
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            System.out.println("Element clicked on web page");
        } catch (Exception e) {
            System.err.println("Element not found on web page.");
        }
    }
    public WebElement waitForElement(By locator, int timeout) {
        WebElement element = null;
        try {
            System.out.println("Waiting for a max: " + timeout + " seconds for element to be available.");
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            System.out.println("Element found on web page");
        } catch (Exception e) {
            System.err.println("Element not found on web page.");
        }
        return element;
    }

    public WebElement getElement(String type, String locator) {
        type = type.toLowerCase();
        if (type.equals("id")) {
            System.out.println("Element found with id: " + locator);
            return driver.findElement(By.id(locator));
        } else if (type.equals("name")) {
            System.out.println("Element found with name: " + locator);
            return driver.findElement(By.name(locator));
        } else if (type.equals("xpath")) {
            System.out.println("Element found with xpath: " + locator);
            return driver.findElement(By.xpath(locator));
        } else if (type.equals("css")) {
            System.out.println("Element found with css: " + locator);
            return driver.findElement(By.cssSelector(locator));
        } else if (type.equals("classname")) {
            System.out.println("Element found with classname: " + locator);
            return driver.findElement(By.className(locator));
        } else if (type.equals("tagname")) {
            System.out.println("Element found with tagname: " + locator);
            return driver.findElement(By.tagName(locator));
        } else if (type.equals("linktext")) {
            System.out.println("Element found with link text: " + locator);
            return driver.findElement(By.linkText(locator));
        } else if (type.equals("partiallinktext")) {
            System.out.println("Element found with partial link text: " + locator);
            return driver.findElement(By.partialLinkText(locator));
        } else {
            System.out.println("Locator type not supported");
            return null;
        }

    }
    public List<WebElement> getElementList(String type, String locator) {
        type = type.toLowerCase();
        List<WebElement> elementList = new ArrayList<WebElement>();

        if (type.equals("id")) {
            elementList = driver.findElements(By.id(locator));
        } else if (type.equals("name")) {
            elementList = driver.findElements(By.name(locator));
        } else if (type.equals("xpath")) {
            elementList = driver.findElements(By.xpath(locator));
        } else if (type.equals("css")) {
            elementList = driver.findElements(By.cssSelector(locator));
        } else if (type.equals("classname")) {
            elementList = driver.findElements(By.className(locator));
        } else if (type.equals("tagname")) {
            elementList = driver.findElements(By.tagName(locator));
        } else if (type.equals("linktext")) {
            elementList = driver.findElements(By.linkText(locator));
        } else if (type.equals("partiallinktext")) {
            elementList = driver.findElements(By.partialLinkText(locator));
        } else {
            System.out.println("Locator type not supported");
        }
        if (elementList.isEmpty()) {
            System.out.println("Element not found with " + type + ": " + locator);

        } else {
            System.out.println("Element found with " + type + ": " + locator);
        }
        return elementList;
    }

    public boolean isElementPresent(String type, String locator) {
        List<WebElement> elementList = getElementList(type, locator);
        int size = elementList.size();
        if (size > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<WebElement> clickableLinks() {
        List<WebElement> linksToClick = new ArrayList<>();
        List<WebElement> elements = driver.findElements(By.tagName("a"));
        elements.addAll(driver.findElements(By.tagName("img")));

        for (WebElement we : elements) {
            if (we.getAttribute("href") != null) {
                linksToClick.add(we);
            }
        }
        return linksToClick;
    }

    public String getLinkStatus(URL url) {
        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.connect();
            String responseMessage = http.getResponseMessage();
            http.disconnect();
            return responseMessage;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    //Extent Report Setup
    @BeforeSuite
    public void extentSetup(ITestContext context) {
        ExtentManager.setOutputDirectory(context);
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void startExtent(Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName().toLowerCase();
        ExtentTestManager.startTest(method.getName());
        ExtentTestManager.getTest().assignCategory(className);
    }

    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    @AfterMethod
    public void afterEachTestMethod(ITestResult result) {
        ExtentTestManager.getTest().getTest().setStartedTime(getTime(result.getStartMillis()));
        ExtentTestManager.getTest().getTest().setEndedTime(getTime(result.getEndMillis()));

        for (String group : result.getMethod().getGroups()) {
            ExtentTestManager.getTest().assignCategory(group);
        }

        if (result.getStatus() == 1) {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test Passed");
        } else if (result.getStatus() == 2) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, getStackTrace(result.getThrowable()));
        } else if (result.getStatus() == 3) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
        }

        ExtentTestManager.endTest();
        extent.flush();
        if (result.getStatus() == ITestResult.FAILURE) {
            captureScreenshot(driver, result.getName());
        }
    }

    @AfterSuite
    public void generateReport() {
        extent.close();
    }

    public static void captureScreenshot(WebDriver driver, String screenshotName) {

        DateFormat df = new SimpleDateFormat("(MM.dd.yyyy-HH:mma)");
        Date date = new Date();
        df.format(date);

        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(System.getProperty("user.dir") + "/screenshots/" + screenshotName + " " + df.format(date) + ".png"));
            System.out.println("Screenshot captured");
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
            ;
        }

    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}