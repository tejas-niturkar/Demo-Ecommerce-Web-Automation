package pageEvents;

import org.testng.Assert;
import pageObjects.HomePageElements;
import utils.ElementFetch;

public class HomePageEvents {

    ElementFetch ele = new ElementFetch();

    public void companyLogo(){
       Assert.assertTrue(ele.getWebElement("XPATH", HomePageElements.companyLogo).isDisplayed(),"Logo is not displayed");
    }


}
