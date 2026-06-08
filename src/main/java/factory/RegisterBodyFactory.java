package factory;

import model.user.UserRegisterBody;
import utils.JsonReader;

public class RegisterBodyFactory {

    private RegisterBodyFactory(){}

    public static UserRegisterBody validBody(){
        
         return JsonReader.read(
            "registerRequestBody.json",
            UserRegisterBody.class
        );
    }

    public static UserRegisterBody missingPassword(){
        UserRegisterBody requestBody = validBody();
        requestBody.setPassword(null);

        return requestBody;
    }

   public static UserRegisterBody missingEmail(){
        UserRegisterBody requestBody = validBody();
        requestBody.setEmail(null); 

        return requestBody;
    }

    public static UserRegisterBody invalidEmailFormat(){
        UserRegisterBody requestBody = validBody();
        requestBody.setEmail("eve@");

        return requestBody;
    }

    public static UserRegisterBody emptyBody(){
        return new UserRegisterBody();
    }
}
