package userManagement;

import static org.hamcrest.Matchers.empty;
import factory.RequestSpecFactory;
import factory.ResponseSpecFactory;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import provider.TestDataProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import assertion.Assertions;
import base.BaseTest;
import client.UserClient;
import constant.TestConstant;

public class GetUsersErrorTest extends BaseTest {

    UserClient userClient = new UserClient();
    ResponseSpecification responseSpec;

    @BeforeMethod
    public void setupSpecs() {
        responseSpec = ResponseSpecFactory.okContentTypeJson();
    }

    @Test(description = "Deve retornar lista vazia para página inexistente", groups = "regression")
    public void shouldReturnEmptyListForNonExistingPage() {

        userClient.getUsers(RequestSpecFactory.withValidApiKey(), TestConstant.NON_EXISTENT_PAGE)
                .spec(responseSpec)
                .body("data", empty());
    }

    @Test(description = "Deve retornar 401 quando API Key estiver ausente e 403 quando inválida", dataProvider = "unathorizedRequests", dataProviderClass = TestDataProvider.class, groups = "regression")
    public void shouldReturnErrorWhenApiKeyIsInvalidOrMissing(RequestSpecification request, int statusCode,
            String errorMessage) {

        ValidatableResponse response = userClient.getUsers(request, TestConstant.SECOND_PAGE);
        Assertions.assertErrorResponse(response, statusCode, errorMessage);
    }
}