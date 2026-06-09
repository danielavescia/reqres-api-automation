package userManagement;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.specification.RequestSpecification;
import model.user.UserRegisterBody;
import provider.TestDataProvider;
import org.testng.annotations.Test;
import base.BaseTest;
import client.UserClient;
import factory.RegisterBodyFactory;
import factory.RequestSpecFactory;

public class PostUserErrorTest extends BaseTest {

    private UserClient userClient = new UserClient();

    @Test(description = "Deve retornar 403 quando API Key é inválida e 401 quando API key é ausente", dataProvider = "unathorizedRequests", dataProviderClass = TestDataProvider.class, groups = {
            "error-register", "regression" })
    public void shouldReturnErrorWhenApiKeyIsInvalidOrMissing(RequestSpecification request, int status,
            String errorMessage) {
        userClient.createUser(request, RegisterBodyFactory.validBody())
                .statusCode(status)
                .body("message", equalTo(errorMessage));
    }

    @Test(description = "Deve retorna 400 para requests com payloads inválidos", dataProvider = "InvalidRegisterPayload", dataProviderClass = TestDataProvider.class, groups = {
            "error-register", "regression" })
    public void shouldReturn400ForInvalidRegisterPayload(UserRegisterBody body, int statusCode, String errorMessage) {
        userClient.createUser(RequestSpecFactory.withValidApiKey(), body)
                .statusCode(statusCode)
                .body("error", equalTo(errorMessage));
    }

    @Test(description = "Deve validar o schema da error response", groups = { "error-register", "regression" })
    public void shouldValidateErrorSchema() {
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