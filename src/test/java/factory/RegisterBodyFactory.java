package factory;

import pojo.users.RequestBodyRegister;
import utils.JsonReader;

public class RegisterBodyFactory {

    private RegisterBodyFactory(){}

    public static RequestBodyRegister validBody(){
        
         return JsonReader.read(
            "registerRequestBody.json",
            RequestBodyRegister.class
        );
    }

    public static RequestBodyRegister missingPassword(){
        RequestBodyRegister requestBody = validBody();
        requestBody.setPassword(null);

        return requestBody;
    }

   public static RequestBodyRegister missingEmail(){
        RequestBodyRegister requestBody = validBody();
        requestBody.setEmail(null); 

        return requestBody;
    }

    public static RequestBodyRegister invalidEmailFormat(){
        RequestBodyRegister requestBody = validBody();
        requestBody.setEmail("eve@");

        return requestBody;
    }

    public static RequestBodyRegister emptyBody(){
        return new RequestBodyRegister();
    }
}
