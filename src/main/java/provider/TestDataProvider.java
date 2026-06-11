package provider;

import org.testng.annotations.DataProvider;
import factory.RegisterBodyFactory;
import factory.RequestSpecFactory;

public class TestDataProvider {

    @DataProvider(name = "unathorizedRequests")
    public Object[][] unnathorizedRequests(){
        return new Object[][]{
            {RequestSpecFactory.withoutApiKey(), 401,"missing_api_key"},
            {RequestSpecFactory.withInvalidApiKey(), 403, "invalid_api_key"}
        };
    }

    @DataProvider(name = "InvalidRegisterPayload")
    public Object[][] InvalidRegisterPayload(){
        return new Object[][]{
            {RegisterBodyFactory.missingPassword(), 400, "Missing password"},
            {RegisterBodyFactory.missingEmail(), 400, "Missing email or username"},
            {RegisterBodyFactory.invalidEmailFormat(), 400, "Note: Only defined users succeed registration"},
            {RegisterBodyFactory.emptyBody(), 400, "Missing email or username"}
        };
    }
}
