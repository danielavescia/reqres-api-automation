package factory;

import static org.hamcrest.Matchers.lessThan;
import java.util.concurrent.TimeUnit;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecFactory {

    private ResponseSpecFactory() {}

    public static ResponseSpecification okContentTypeJson(){
        return new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(200)
            .expectResponseTime(lessThan(3000L), TimeUnit.SECONDS)
            .build();
    }
}
