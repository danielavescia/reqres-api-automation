package userManagement;

import org.testng.annotations.BeforeClass;
import config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class GetUsersErrorTest {
    protected static RequestSpecification requestSpec;

    protected static ResponseSpecification responseSpec;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigManager.get("baseURI");

    }
}
