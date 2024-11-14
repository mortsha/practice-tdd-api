package tek.tdd.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import tek.tdd.page.HomePage;
import tek.tdd.utility.SeleniumUtility;

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
    public void teatCleanUp() {
        LOGGER.info("running after each test and quit browser");
        quitBrowser();
    }
}
