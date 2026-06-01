package specs;

import pojo.users.RequestBodyRegister;

public class RegisterBodyFactory {

    private RegisterBodyFactory(){}

    public static RequestBodyRegister validBody(){
        RequestBodyRegister requestBody = new RequestBodyRegister();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("pistol"); 

        return requestBody;
    }

    public static RequestBodyRegister missingPassword(){
        RequestBodyRegister requestBody = new RequestBodyRegister();
        requestBody.setEmail("eve.holt@reqres.in");

        return requestBody;
    }

   public static RequestBodyRegister missingEmail(){
        RequestBodyRegister requestBody = new RequestBodyRegister();
        requestBody.setPassword("pistol"); 

        return requestBody;
    }

    public static RequestBodyRegister invalidEmailFormat(){
        RequestBodyRegister requestBody = new RequestBodyRegister();
        requestBody.setEmail("eve@");
        requestBody.setPassword("pistol"); 

        return requestBody;
    }

    public static RequestBodyRegister emptyBody(){
        return new RequestBodyRegister();
    }
}
