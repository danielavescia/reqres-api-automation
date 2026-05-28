package userManagement;

import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;
import config.ConfigManager;
import io.restassured.RestAssured;

public class GetUsersSucessTest {

    @Test(description = "Deve retornar usuários da página 2 com sucesso", groups ="users-sucess")
    public void shouldGetUsersPage2() {

        RestAssured.baseURI = ConfigManager.get("baseURI");
        String apiKey = ConfigManager.get("api.key");

        given()
            .header("x-api-key", apiKey)
        .when()
            .get("/users?page=2")
        .then()
            .assertThat()
            .statusCode(200);
    }
}
