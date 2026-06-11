package userManagement;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import base.BaseTest;
import client.UserClient;
import constant.TestConstant;
import factory.RequestSpecFactory;
import factory.ResponseSpecFactory;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
public class GetUsersSuccessTest extends BaseTest {

    private RequestSpecification requestSpec;

    private ResponseSpecification responseSpec;

    private UserClient userClient = new UserClient();

    @BeforeClass
    public void setupSpecs() {

        requestSpec = RequestSpecFactory.withValidApiKey();

        responseSpec = ResponseSpecFactory.okContentTypeJson();
    }

    @Test(description = "Deve listar usuários da página 1 com sucesso", groups = "smoke")
    public void shouldReturnUsersListNotEmpty() {

        userClient.getUsers(requestSpec,1)
            .spec(responseSpec)
            .body("page", equalTo(TestConstant.FIRST_PAGE))
            .body("per_page", equalTo(TestConstant.PER_PAGE))
            .body("data.size()", greaterThan(0))
            .body("data.size()", lessThanOrEqualTo(TestConstant.PER_PAGE));
    }

    @Test(description = "Deve validar o schema da response de GET Usuário", groups = "smoke")
    public void shouldValidateUsersSchema(){

        userClient.getUsers(requestSpec,TestConstant.FIRST_PAGE)
            .spec(responseSpec)
            .body(matchesJsonSchemaInClasspath("schemas/users-schema.json"));
    }
}