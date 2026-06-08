package userManagement;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import config.ConfigManager;
import factory.RegisterBodyFactory;
import factory.RequestSpecFactory;

public class PostUserErrorTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigManager.get("base.url");
    }

    @Test(description = "Deve retorna 401 quando API Key estiver ausente", groups = {"error-register", "regression"})
    public void shouldReturnUnauthorizedWhenApiKeyIsMissing(){
        given()
            .spec(RequestSpecFactory.withoutApiKey())
            .body(RegisterBodyFactory.validBody())
        .when()
            .post("/register")
        .then()
            .statusCode(401)
            .body("error", equalTo("missing_api_key"))
            .body("message", equalTo("The x-api-key header is required for this endpoint."));
    }

    @Test(description = "Deve retorna 403 quando API Key é inválida", groups = {"error-register", "regression"})
    public void shouldReturnForbiddenWhenApiKeyIsInvalid(){
        given()
            .spec(RequestSpecFactory.withInvalidApiKey())
            .body(RegisterBodyFactory.validBody())
        .when()
            .post("/register")
        .then()
            .statusCode(403)
            .body("error", equalTo("invalid_api_key"))
            .body("message", equalTo("This API key is not recognized or has been revoked."));
    }

    @Test(description = "Deve retorna 400 sem campo password", groups = {"error-register", "regression"})
    public void shouldReturn400WhenPasswordIsMissing(){
        given()
            .spec(RequestSpecFactory.withValidApiKey())
            .body(RegisterBodyFactory.missingPassword())
        .when()
            .post("/register")
        .then()
            .statusCode(400)
            .body("error", equalTo("Missing password"));      
    }

    @Test(description = "Deve retorna 400 sem campo email", groups = {"error-register", "regression"})
    public void shouldReturn400WhenEmailIsMissing(){
        given()
            .spec(RequestSpecFactory.withValidApiKey())
            .body(RegisterBodyFactory.missingEmail())
        .when()
            .post("/register")
        .then()
            .statusCode(400)
            .body("error", equalTo("Missing email or username"));      
    }

    @Test(description = "Deve retorna 400 sem body", groups = {"error-register", "regression"})
    public void shouldReturn400WhenBodyIsEmpty(){
        given()
            .spec(RequestSpecFactory.withValidApiKey())
            .body(RegisterBodyFactory.emptyBody())
        .when()
            .post("/register")
        .then()
            .statusCode(400)
            .body("error", equalTo("Missing email or username"));      
    }

    @Test(description = "Deve retorna 400 devido e-mail com formato inválido",groups = {"error-register", "regression"})
    public void shouldReturn400WhenInvalidEmailFormat(){
        given()
            .spec(RequestSpecFactory.withValidApiKey())
            .body(RegisterBodyFactory.invalidEmailFormat())
        .when()
            .post("/register")
        .then()
            .statusCode(400)
            .body("error", equalTo("Note: Only defined users succeed registration"));      
    }

    @Test(description = "Deve validar o schema da error response", groups = {"error-register", "regression"})
    public void shouldValidateErrorSchema(){
        given()
            .spec(RequestSpecFactory.withValidApiKey())
            .body(RegisterBodyFactory.missingPassword())
        .when()
            .post("/register")
        .then()
            .statusCode(400)
            .body(matchesJsonSchemaInClasspath("schemas/register-error-schema.json"));  
    }
}