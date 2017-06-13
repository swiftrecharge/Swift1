package sixtus.com.homenavigation;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dakurah Sixtus on 5/19/2017.
 */

public class RegisterRequest extends StringRequest {
    private final static String REGISTER_REQUEST_URI = "http://swiftrecharge.000webhostapp.com/register.php";
    private Map<String, String> params;

    public RegisterRequest(String email_address, int mobile_number, String password1, String password2, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URI,  listener, null);
        params = new HashMap<>();
        params.put("txtEmail", email_address);
        params.put("txtPassword", password1);
        params.put("txtPasswordC", password2);
        params.put("txtPhone", mobile_number + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}