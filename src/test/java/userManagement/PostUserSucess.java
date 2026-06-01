package userManagement;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;
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

    protected  RequestSpecification requestSpec;

    protected  RequestBodyRegister requestBody;

    protected  ResponseSpecification responseSpec;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigManager.get("baseURI");

        requestSpec = RequestSpecFactory.withValidApiKey();

        responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build(); 

        requestBody = new RequestBodyRegister();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("pistol");
    }

    @Test(description = "Deve retornar 200 e Content-Type JSON", groups = "register-sucess")
    public void shouldCreateUserSuccesfully(){

        given()
            .spec(requestSpec)
            .body(requestBody)
        .when()
            .post("/register")
        .then()
            .spec(responseSpec);
    }

    @Test(description = "Deve retornar 200 e schema completo", groups ="register-sucess")
    public void shouldMatchResponseSchema(){
        given()
            .spec(requestSpec)
            .body(requestBody)
        .when()
            .post("/register")
        .then()
            .spec(responseSpec)
            .body(matchesJsonSchemaInClasspath("schemas/register-success-schema.json"));
    }

    @Test(description = "Deve retornar 200 e token não vazio", groups ="register-sucess")
    public void shouldReturnNonEmptyToken(){
        given()
            .spec(requestSpec)
            .body(requestBody)
        .when()
            .post("/register")
        .then()
            .spec(responseSpec)
            .body("token", not(emptyString()));
    }
}