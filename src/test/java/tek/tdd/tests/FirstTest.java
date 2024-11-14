package tek.tdd.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import tek.tdd.base.UIBaseClass;

public class FirstTest extends UIBaseClass {

    @Test
    public void validateTitleOfPage() {
        String actualLogoText = getElementText(homePage.logoText);
        Assert.assertEquals(actualLogoText, "TEK Insurance App", "Logo Text should match");
    }

}
