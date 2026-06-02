package userManagement;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.BeforeClass;
import config.ConfigManager;
import io.restassured.RestAssured;
import specs.RequestSpecFactory;
import org.testng.annotations.Test;

public class GetUsersErrorTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigManager.get("base.url");
    }

    @Test(description = "Deve retornar lista vazia para página inexistente", groups = {"regression", "error-users"})
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

    @Test(description = "Deve retornar 401 quando API Key estiver ausente", groups = {"regression", "error-users"})
    public void shouldReturnUnauthorizedWhenApiKeyIsMissing(){
        given()
            .spec(RequestSpecFactory.withoutApiKey())
            .queryParam("page", 1)
            .queryParam("_t", System.currentTimeMillis())
        .when()
            .get("/users")
        .then()
            .statusCode(401)
            .body("error", equalTo("missing_api_key"))
            .body("message", equalTo("The x-api-key header is required for this endpoint."));
    }

    @Test(description = "Deve retornar 403 quando API Key for inválida", groups = {"regression", "error-users"})
    public void shouldReturnForbiddenWhenApiKeyIsInvalid(){
        given()
            .spec(RequestSpecFactory.withInvalidApiKey())
            .queryParam("page", 1)
            .queryParam("_t", System.currentTimeMillis())
        .when()
            .get("/users")
        .then()
            .statusCode(403)
            .body("error", equalTo("invalid_api_key"))
            .body("message", equalTo("This API key is not recognized or has been revoked."));
    }
}