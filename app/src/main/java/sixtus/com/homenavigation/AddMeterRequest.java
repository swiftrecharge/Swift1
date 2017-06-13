package sixtus.com.homenavigation;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dakurah Sixtus on 6/10/2017.
 */

public class AddMeterRequest extends StringRequest {
    private final static String ADD_METER_REQUEST_URI = "http://swiftrecharge.000webhostapp.com/add_meter.php";
    private Map<String, String> parameters;

    public AddMeterRequest(String user_id, String meter_number, Response.Listener<String> listener){
        super(Method.POST, ADD_METER_REQUEST_URI,  listener, null);
        parameters = new HashMap<>();
        parameters.put("txtUserId", user_id);
        parameters.put("txtMeterNumber", meter_number);
    }

    @Override
    protected Map<String, String> getParams() {
        return parameters;
    }
}