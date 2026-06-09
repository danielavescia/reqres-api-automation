package base;

import java.util.Collections;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import config.ConfigManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

@Listeners({AllureTestNg.class})
public class BaseTest {

    @BeforeSuite
    public void setupSuite() {
        RestAssured.baseURI = ConfigManager.get("base.url");
    }

    @BeforeMethod
    public void setup() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured());
    }

    @AfterMethod
    public void reset() {
           RestAssured.replaceFiltersWith(Collections.emptyList());
    }
}