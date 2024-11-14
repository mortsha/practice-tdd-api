package tek.tdd.base;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.service.ExtentTestManager;
import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import tek.tdd.page.HomePage;
import tek.tdd.utility.SeleniumUtility;

//@Listeners({ExtentITestListenerClassAdapter.class})
@Listeners({ExtentITestListenerClassAdapter.class})
public class UIBaseClass extends SeleniumUtility {

    private static final Logger LOGGER = LogManager.getLogger(UIBaseClass.class);
    public HomePage homePage;

    @BeforeMethod
    public void setupTest() {
        LOGGER.info("Setup test and opening browser");
        setupBrowser();
        homePage = new HomePage();
    }

    @AfterMethod
    public void teatCleanUp(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
            String shot = takesScreenshot.getScreenshotAs(OutputType.BASE64);

            ExtentTestManager.getTest().fail("Test failed taking screenshot", MediaEntityBuilder.createScreenCaptureFromBase64String(shot).build());
        }
        LOGGER.info("running after each test and quit browser");

        quitBrowser();
    }
}
