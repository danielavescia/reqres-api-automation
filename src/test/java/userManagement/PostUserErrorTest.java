package userManagement;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import model.user.UserRegisterBody;
import provider.TestDataProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import assertion.Assertions;
import base.BaseTest;
import client.UserClient;
import factory.RegisterBodyFactory;
import factory.RequestSpecFactory;

public class PostUserErrorTest extends BaseTest {

        private UserClient userClient = new UserClient();

        private RequestSpecification requestWithValidKey;

        @BeforeMethod
        public void setupSpecs(){
                requestWithValidKey = RequestSpecFactory.withValidApiKey();
        }

        @Test(description = "Deve retornar 403 quando API Key é inválida e 401 quando API key é ausente", dataProvider = "unathorizedRequests", dataProviderClass = TestDataProvider.class, groups = "error-register")
        public void shouldReturnErrorWhenApiKeyIsInvalidOrMissing(RequestSpecification request, int statusCode,
                        String errorMessage) {

                ValidatableResponse response = userClient.createUser(request, RegisterBodyFactory.validBody());
                Assertions.assertErrorResponse(response, statusCode, errorMessage);
        }

        @Test(description = "Deve retorna 400 para requests com payloads inválidos", dataProvider = "InvalidRegisterPayload", dataProviderClass = TestDataProvider.class, groups = "regression")
        public void shouldReturn400ForInvalidRegisterPayload(UserRegisterBody body, int statusCode,
                        String errorMessage) {

                ValidatableResponse response = userClient.createUser(requestWithValidKey, body);
                Assertions.assertErrorResponse(response, statusCode, errorMessage);
        }

        @Test(description = "Deve validar o schema da error response", dataProvider = "InvalidRegisterPayload", dataProviderClass = TestDataProvider.class, groups = "error-register")
        public void shouldValidateErrorSchema(UserRegisterBody body, int statusCode, String errorMessage) {
                
                ValidatableResponse response = userClient.createUser(requestWithValidKey, body);
                Assertions.assertErrorResponse(response, statusCode, errorMessage);
                response.body(matchesJsonSchemaInClasspath("schemas/register-error-schema.json"));
        }
}