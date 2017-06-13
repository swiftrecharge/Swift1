package sixtus.com.homenavigation;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dakurah Sixtus on 5/19/2017.
 */

public class LoginRequest extends StringRequest {
    private final static String LOGIN_REQUEST_URI = "http://swiftrecharge.000webhostapp.com/login.php";
    private Map<String, String> parameters;

    public LoginRequest(String email_address, String password, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URI,  listener, null);
        parameters = new HashMap<>();
        parameters.put("txtEmail", email_address);
        parameters.put("txtPassword", password);
    }

    @Override
    protected Map<String, String> getParams() {
        return parameters;
    }
}
