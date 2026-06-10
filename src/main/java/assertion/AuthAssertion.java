package assertion;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import java.util.concurrent.TimeUnit;
import constant.TestConstant;
import io.restassured.response.ValidatableResponse;

public class AuthAssertion {

    private AuthAssertion () {}

    public static void assertAuthErrorResponse(ValidatableResponse response, int expectedStatus, String expectedMessage){
        response
            .statusCode(expectedStatus)
            .body("message", equalTo(expectedMessage))
            .time(lessThan(TestConstant.MAX_TIME_RESPONSE), TimeUnit.SECONDS);    
    }
}
