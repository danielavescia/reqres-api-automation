package userManagement;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import base.BaseTest;
import client.UserClient;
import factory.RequestSpecFactory;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
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

        responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build(); 
    }

    @Test(description = "Deve listar usuários da página 1 com sucesso", groups = {"users-success, smoke"})
    public void shouldReturnUsersListNotEmpty() {

        userClient.getUsers(requestSpec,1)
            .spec(responseSpec)
            .body("page", equalTo(1))
            .body("per_page", equalTo(6))
            .body("data.size()", greaterThan(0))
            .body("data.size()", lessThanOrEqualTo(6));
    }

    @Test(description = "Deve validar o schema da response de GET Usuário", groups = {"users-success, smoke"})
    public void shouldValidateUsersSchema(){

        userClient.getUsers(requestSpec,2)
            .spec(responseSpec)
            .body(matchesJsonSchemaInClasspath("schemas/users-schema.json"));
    }

    @Test(description = "Validar tempo de resposta da API dentro do limite estabelecido", groups = {"users-success, smoke"})
    public void shouldValidateResponseTime(){
        
        userClient.getUsers(requestSpec,1)
            .spec(responseSpec)
            .time(lessThan(2000L));
    }
}