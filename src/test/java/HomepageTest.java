import base.BaseTest;
import org.testng.annotations.Test;
import pageEvents.HomePageEvents;
import pageEvents.LoginPageEvents;
import utils.ElementFetch;

public class HomepageTest extends BaseTest {

    ElementFetch ele = new ElementFetch();
    HomePageEvents homePage = new HomePageEvents();
    LoginPageEvents loginPage = new LoginPageEvents();


    @Test
    public void verifyHomepageLogo(){
        homePage.companyLogo();
    }
}
