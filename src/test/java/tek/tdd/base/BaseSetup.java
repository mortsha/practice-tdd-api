package tek.tdd.base;

import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public abstract class BaseSetup {
    private static final Logger LOGGER = LogManager.getLogger(BaseSetup.class);
    protected static final long WAIT_TIME_IN_SECONDS = 20;
    private static WebDriver driver;
    private Properties properties;

    public BaseSetup() {
        // reading config file and loading properties
        String configFilePath = System.getProperty("user.dir") + "/src/test/resources/configs/dev-config.properties";

        try {
            LOGGER.debug("Reading config file path {}", configFilePath);
            InputStream inputStream = new FileInputStream(configFilePath);
            properties = new Properties();
            properties.load(inputStream);

            String baseURL = properties.getProperty("api.url");
            RestAssured.baseURI = baseURL;

        } catch (IOException ioException) {
            LOGGER.error("Config file error with message {} ", ioException.getMessage());
            throw new RuntimeException("Config file error with message " + ioException.getMessage());
        }
    }


    public void setupBrowser() {
        String url = properties.getProperty("ui.url");
        String browserType = properties.getProperty("ui.browser");
        boolean isHeadless = Boolean.parseBoolean(properties.getProperty("ui.browser.headless"));

        LOGGER.info("Opening the browser {} and headless {}", browserType, isHeadless);

        switch (browserType.toLowerCase()) {
            case "chrome": {
                ChromeOptions options = new ChromeOptions();
                if (isHeadless) options.addArguments("--headless");
                driver = new ChromeDriver(options);
                break;
            }
            case "firefox": {
                FirefoxOptions options = new FirefoxOptions();
                if (isHeadless) options.addArguments("--headless");
                driver = new FirefoxDriver(options);
                break;
            }
            case "edge": {
                EdgeOptions options = new EdgeOptions();
                if (isHeadless) options.addArguments("--headless");
                driver = new EdgeDriver(options);
                break;
            }
            default:
                throw new RuntimeException("Wrong browser type. choose chrome, firefox or edge");
        }
        LOGGER.info("Navigating to url{}", url);
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WAIT_TIME_IN_SECONDS));
    }

    public void quitBrowser() {
        if (driver != null) {
            LOGGER.info("Quitting the browser");
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

}
