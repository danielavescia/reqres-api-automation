package userManagement;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetUsersSucessTest {

    protected static RequestSpecification requestSpec;

    protected static ResponseSpecification responseSpec;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigManager.get("baseURI");

        requestSpec = given()
                .header("x-api-key", ConfigManager.get("api.key"))
                .header("Content-Type", "application/json");

        responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build(); 
    }

    @Test(description = "Deve retornar usuários da página 2 com sucesso", groups = "users-sucess")
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

    @Test(description = "Deve validar que todos os usuários possuem campos obrigatórios", groups = "users-sucess")
    public void shouldValidateAllUsersFields() {

        given()
            .spec(requestSpec)
            .queryParam("page", 1)
        .when()
            .get("/users")
        .then()
            .spec(responseSpec)
            .body("data[0].id", notNullValue())
            .body("data[0].email", notNullValue())
            .body("data[0].first_name", notNullValue())
            .body("data[0].last_name", notNullValue())
            .body("data[0].avatar", notNullValue());
    }

    @Test(description = "Deve validar o schema da response de GET Usuário", groups = "users-sucess")
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


    @Test(description = "Deve validar os campos obrigatórios de Usuário", groups = "users-sucess")
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
}