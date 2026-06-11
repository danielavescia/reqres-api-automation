package assertion;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import java.util.concurrent.TimeUnit;
import constant.TestConstant;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

public class Assertions {

    private Assertions () {}

    public static void assertErrorResponse(ValidatableResponse response, int expectedStatus, String expectedMessage){
        response
            .statusCode(expectedStatus)
            .body("error", equalTo(expectedMessage))
            .contentType(ContentType.JSON)
            .time(lessThan(TestConstant.MAX_TIME_RESPONSE), TimeUnit.SECONDS);    
    }
}