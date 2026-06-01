package userManagement;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;
import specs.RegisterBodyFactory;
import specs.RequestSpecFactory;

public class PostUserErrorTest {

    @Test(description = "Deve retorna 401 quando API Key estiver ausente", groups ="error-register")
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

    @Test(description = "Deve retorna 403 quando API Key é inválida", groups ="error-register")
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
}
