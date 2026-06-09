package base;

import org.testng.annotations.BeforeClass;
import config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class BaseTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigManager.get("base.url");
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}