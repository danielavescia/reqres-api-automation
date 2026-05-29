package userManagement;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.users.RequestBodyRegister;
import specs.RequestSpecFactory;

public class PostUserSucess {

    protected static RequestSpecification requestSpec;

    protected static ResponseSpecification responseSpec;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigManager.get("baseURI");

        requestSpec = RequestSpecFactory.withValidApiKey();

        responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build(); 
    }

    @Test(description = "Criar Usuário com Sucesso", groups = "users-sucess")
    public void ShouldCreateUserSuccesfully(){

        RequestBodyRegister postBody = new RequestBodyRegister();
        postBody.setEmail("eve.holt@reqres.in");
        postBody.setPassword("pistol");

        given()
            .spec(RequestSpecFactory.withValidApiKey())
            .body(postBody)
        .when()
            .post("/register")
        .then()
            .spec(responseSpec)
            .body("token", notNullValue());
    }
}