package client;

import static io.restassured.RestAssured.given;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import model.user.UserRegisterBody;

public class UserClient {

    public ValidatableResponse getUsers(RequestSpecification requestSpec, Integer pageParam){
         
       return given()
            .spec(requestSpec)
            .queryParam("page", pageParam)
        .when()
            .get("/users")
        .then();
    }

    public ValidatableResponse createUser(RequestSpecification requestSpec, UserRegisterBody body){
         
        return given()
            .spec(requestSpec)
            .body(body)
        .when()
            .post("/register")
        .then();
    }
}
