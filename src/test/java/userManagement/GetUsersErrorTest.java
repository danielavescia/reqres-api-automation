package userManagement;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import org.testng.annotations.BeforeClass;
import config.ConfigManager;
import io.restassured.RestAssured;
import specs.RequestSpecFactory;
import org.testng.annotations.Test;

public class GetUsersErrorTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigManager.get("baseURI");
    }

    @Test(description = "Deve retornar lista vazia para página inexistente", groups = "error-users")
    public void shouldReturnEmptyListForNonExistingPage(){
        given()
            .spec(RequestSpecFactory.withValidApiKey())
            .queryParam("page", 9999)
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .body("data", empty());
    } 
}

