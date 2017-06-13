package sixtus.com.homenavigation;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dakurah Sixtus on 6/9/2017.
 */

public class MeterRequest extends StringRequest {

    private final static String METER_REQUEST_URI = "http://10.0.3.2/SwiftRecharge/meters.php";
    private Map<String, String> parameters;

    public MeterRequest(String user_id, Response.Listener<String> listener){
        super(Method.POST, METER_REQUEST_URI,  listener, null);
        parameters = new HashMap<>();
        parameters.put("txtUserId", user_id);
    }

    @Override
    protected Map<String, String> getParams() {
        return parameters;
    }
}