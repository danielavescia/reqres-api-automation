package factory;

import config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {

    private RequestSpecFactory() {
    }

    private static RequestSpecBuilder baseSpec() {
        return new RequestSpecBuilder()
                .addHeader("User-Agent", "Mozilla/5.0")
                .setContentType(ContentType.JSON);
    }

    public static RequestSpecification withValidApiKey() {
        return baseSpec()
                .addHeader("x-api-key", ConfigManager.get("api.key"))
                .build();
    }

    public static RequestSpecification withoutApiKey() {
        return baseSpec().build();
    }

    public static RequestSpecification withInvalidApiKey() {
        return baseSpec()
                .addHeader("x-api-key", "invalid-key")
                .build();
    }
}