package userManagement;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import config.ConfigManager;
import factory.RegisterBodyFactory;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import specs.RequestSpecFactory;

public class PostUserSuccessTest {

    protected  RequestSpecification requestSpec;

    protected  ResponseSpecification responseSpec;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigManager.get("base.url");

        requestSpec = RequestSpecFactory.withValidApiKey();

        responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build(); 
    }

    @Test(description = "Deve retornar 200 e Content-Type JSON", groups ={"register-success", "smoke"})
    public void shouldCreateUserSuccesfully(){

        given()
            .spec(requestSpec)
            .body(RegisterBodyFactory.validBody())
        .when()
            .post("/register")
        .then()
            .spec(responseSpec);
    }

    @Test(description = "Deve retornar 200 e schema completo", groups ={"register-success", "smoke"})
    public void shouldMatchResponseSchema(){
        given()
            .spec(requestSpec)
            .body(RegisterBodyFactory.validBody())
        .when()
            .post("/register")
        .then()
            .spec(responseSpec)
            .body(matchesJsonSchemaInClasspath("schemas/register-success-schema.json"));
    }

    @Test(description = "Deve retornar 200 e token não vazio", groups ={"register-success", "smoke"})
    public void shouldReturnNonEmptyToken(){
        given()
            .spec(requestSpec)
            .body(RegisterBodyFactory.validBody())
        .when()
            .post("/register")
        .then()
            .spec(responseSpec)
            .body("token", not(emptyString()));
    }
}