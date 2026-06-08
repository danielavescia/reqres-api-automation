package factory;

import config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
public class RequestSpecFactory {

    private RequestSpecFactory() {}

    public static RequestSpecification withValidApiKey() {
        
        return new RequestSpecBuilder()
                .addHeader("x-api-key", ConfigManager.get("api.key"))
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification withoutApiKey(){
        return new RequestSpecBuilder() 
                .setContentType(ContentType.JSON)
                .build();
             
    }

    public static RequestSpecification withInvalidApiKey(){
        return new RequestSpecBuilder()
            .addHeader("x-api-key", "invalid-key")
            .setContentType(ContentType.JSON)
            .build();
    }
}