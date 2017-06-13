package sixtus.com.homenavigation;

/**
 * Created by markzeno on 6/12/17.
 */



import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

    /**
     * Created by Dakurah Sixtus on 6/11/2017.
     */

    public class BuyCreditRequest extends StringRequest {
        private final static String BUT_CREDIT_REQUEST_URI = "http://swiftrecharge.000webhostapp.com/buy_credit.php";
        private Map<String, String> parameters;

        public BuyCreditRequest(String user_id, String meter_number, String meter_id, String purchased_value, Response.Listener<String> listener){
            super(Method.POST, BUT_CREDIT_REQUEST_URI,  listener, null);
            parameters = new HashMap<>();
            parameters.put("txtUserId", user_id);
            parameters.put("txtMeterId", meter_id);
            parameters.put("txtMeterNumber", meter_number);
            parameters.put("txtPurchasedValue", purchased_value);
        }

        @Override
        protected Map<String, String> getParams() {
            return parameters;
        }
    }


