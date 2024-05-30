package base;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import utils.Constants;

import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    public static WebDriver driver ;
    public ExtentSparkReporter sparkReporter ;
    public ExtentReports extent ;
    public ExtentTest logger ;


    @BeforeTest
    public void beforeTestMethod(){
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/STMExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Host Name", "TestHostName");
        extent.setSystemInfo("Environment", "TestProduction");
        extent.setSystemInfo("User Name", "TestUserName");
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Automation test execution result ");
        sparkReporter.config().setTheme(Theme.DARK);
    }

    @BeforeMethod
    @Parameters("browser")
    public void beforeMethod(String browser, Method testMethod){
        logger = extent.createTest(testMethod.getName());
        setupDriver(browser);
        driver.manage().window().maximize();
        driver.get(Constants.url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
    }

    @AfterMethod
    public void afterMethod(ITestResult result){
        if(result.getStatus() == ITestResult.FAILURE){
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test case Failed", ExtentColor.RED));
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test case Skipped", ExtentColor.ORANGE));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test case Passed", ExtentColor.GREEN));
            driver.quit();
        }
    }
    @AfterTest
    public void afterTest(){
        extent.flush();
    }

    public void setupDriver(String browser){
        if(browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }
    }

}
