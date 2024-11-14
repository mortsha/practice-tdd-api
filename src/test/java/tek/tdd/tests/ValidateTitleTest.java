package tek.tdd.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import tek.tdd.base.UIBaseClass;

public class ValidateTitleTest extends UIBaseClass {

    @Test
    public void validateTitleOfPage() {
        String actualLogoText = getElementText(homePage.logoText);
        Assert.assertEquals(actualLogoText, "TEK Insurance App1", "Logo Text should match");

    }

}
