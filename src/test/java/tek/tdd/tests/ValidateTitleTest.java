package tek.tdd.tests;

import com.aventstack.extentreports.service.ExtentTestManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import tek.tdd.base.UIBaseClass;

public class ValidateTitleTest extends UIBaseClass {

    @Test
    public void validateTitleOfPage() {
        String actualLogoText = getElementText(homePage.logoText);
        Assert.assertEquals(actualLogoText, "TEK Insurance App", "Logo Text should match");
        ExtentTestManager.getTest().info("validate the logo text");

    }

}
