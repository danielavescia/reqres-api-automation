package userManagement;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class GetUsersSucessTest {

    protected static RequestSpecification requestSpec;

    @BeforeSuite
    public void setup() {
        RestAssured.baseURI = ConfigManager.get("baseURI");

        requestSpec = given()
                .header("x-api-key", ConfigManager.get("api.key"))
                .header("Content-Type", "application/json");
    }

    @Test(description = "Deve retornar usuários da página 2 com sucesso", groups = "users-sucess")
    public void shouldReturnUsersListSuccessfully() {

            given()
                .spec(requestSpec)
                .queryParam("page", 2)
            .when()
                .get("/users")
            .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .body("page", equalTo(2))
                .body("data.size()", greaterThan(0));
    }
}