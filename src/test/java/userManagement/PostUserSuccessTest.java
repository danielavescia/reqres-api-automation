package userManagement;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import base.BaseTest;
import client.UserClient;
import factory.RegisterBodyFactory;
import factory.RequestSpecFactory;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import model.user.UserRegisterBody;

public class PostUserSuccessTest extends BaseTest {

    private UserClient userClient = new UserClient();

    private RequestSpecification requestSpec;

    private ResponseSpecification responseSpec;

    private UserRegisterBody requestBody;

    @BeforeClass
    public void setupSpecs() {

        requestSpec = RequestSpecFactory.withValidApiKey();

        responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build(); 

        requestBody = RegisterBodyFactory.validBody(); 
    }

    @Test(description = "Deve retornar 200 e Content-Type JSON", groups ={"register-success", "smoke"})
    public void shouldCreateUserSuccesfully(){
        userClient.createUser(requestSpec, requestBody)
            .spec(responseSpec);
    }

    @Test(description = "Deve retornar 200 e validar schema completo", groups ={"register-success", "smoke"})
    public void shouldMatchResponseSchema(){
        userClient.createUser(requestSpec, requestBody)
            .spec(responseSpec)
            .body(matchesJsonSchemaInClasspath("schemas/register-success-schema.json"));
    }
}