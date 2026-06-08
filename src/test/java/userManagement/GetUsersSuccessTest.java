package userManagement;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import config.ConfigManager;
import factory.RequestSpecFactory;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
public class GetUsersSuccessTest {

    protected static RequestSpecification requestSpec;

    protected static ResponseSpecification responseSpec;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigManager.get("base.url");

        requestSpec = RequestSpecFactory.withValidApiKey();

        responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build(); 
    }

    @Test(description = "Deve retornar usuários da página 1 com sucesso", groups = {"users-success, smoke"})
    public void shouldReturnUsersListNotEmpty() {

        given()
            .spec(requestSpec)
            .queryParam("page", 1)
        .when()
            .get("/users")
        .then()
            .spec(responseSpec)
            .body("page", equalTo(1))
            .body("data.size()", greaterThan(0));
    }

    @Test(description = "Deve validar os campos obrigatórios de Usuário", groups = {"users-success, smoke"})
    public void shouldValidateUsersData(){
        
        given()
            .spec(requestSpec)
            .queryParam("page", 1)
        .when() 
            .get("/users")
        .then()
            .spec(responseSpec)
            .body("data.id", everyItem(notNullValue()))
            .body("data.email", everyItem(notNullValue()))
            .body("data.first_name", everyItem(notNullValue()))
            .body("data.last_name", everyItem(notNullValue()))
            .body("data.avatar", everyItem(notNullValue()));
    }

    @Test(description = "Deve validar o schema da response de GET Usuário", groups = {"users-success, smoke"})
    public void shouldValidateUsersSchema(){

        given()
            .spec(requestSpec)
            .queryParam("page", 2)
        .when() 
            .get("/users")
        .then()
            .spec(responseSpec)
            .body(matchesJsonSchemaInClasspath("schemas/users-schema.json"));
    }

    @Test(description = "Validar consistência de paginação", groups = {"users-success, smoke"})
    public void shouldValidatePagination(){
        
        given()
            .spec(requestSpec)
            .queryParam("page", 1)
        .when()
            .get("/users")

        .then() 
            .spec(responseSpec)
            .body("page", equalTo(1))
            .body("per_page", equalTo(6))
            .body("data.size()", greaterThan(0))
            .body("data.size()", lessThanOrEqualTo(6));
    }

    @Test(description = "Validar tempo de resposta da API dentro do limite estabelecido", groups = {"users-success, smoke"})
    public void shouldValidateResponseTime(){
        given()
            .spec(requestSpec)
        .when()
            .get("/users")
        .then()
            .spec(responseSpec)
            .time(lessThan(2000L));
    }
}