package userManagement;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import factory.RequestSpecFactory;
import io.restassured.specification.RequestSpecification;
import provider.TestDataProvider;
import org.testng.annotations.Test;
import base.BaseTest;
import client.UserClient;

public class GetUsersErrorTest extends BaseTest {

    UserClient userClient = new UserClient();

    @Test(description = "Deve retornar lista vazia para página inexistente", groups = { "regression", "error-users" })
    public void shouldReturnEmptyListForNonExistingPage() {

        userClient.getUsers(RequestSpecFactory.withValidApiKey(), 9999)
                .statusCode(200)
                .body("data", empty());
    }

    @Test(description = "Deve retornar 401 quando API Key estiver ausente e 403 quando inválida", dataProvider = "unathorizedRequests", dataProviderClass = TestDataProvider.class, groups = {
            "regression", "error-users" })
    public void shouldReturnErrorWhenApiKeyIsInvalidOrMissing(RequestSpecification request, int statusCode,
            String errorMessage) {

        userClient.getUsers(request, 2)
                .statusCode(statusCode)
                .body("message", equalTo(errorMessage));
    }
}