package sixtus.com.homenavigation;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
public class AddMeter extends AppCompatActivity {
    // Progress Dialog
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.app_back));
        setContentView(R.layout.add_meter);
        pDialog = new ProgressDialog(AddMeter.this);
        pDialog.setMessage("Adding meter. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        //get meter number
        final EditText txtMeterNumber = (EditText)findViewById(R.id.meter_number);
        //get user id from shared preferences
        SharedPreferences userId = getSharedPreferences("userSession", 0);
        final String user_id = userId.getString("user_id", "");
        Button btnAddMeter = (Button)findViewById(R.id.btn_add_meter);
        btnAddMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String number = txtMeterNumber.getText().toString();
                if (number.trim().equalsIgnoreCase("")){
                    txtMeterNumber.setError("Please input meter number");
                }else{
                    pDialog.show();
                    //make request to server and get response
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success  = jsonResponse.getBoolean("error");
                                if(success){
                                    pDialog.dismiss();
                                    //redirect to buy credit
                                    Toast.makeText(AddMeter.this, "Meter Added Successfully", Toast.LENGTH_SHORT).show();
                                    Intent buyCredit = new Intent(AddMeter.this, BuyCredit.class);
                                    startActivity(buyCredit);
                                    finish();
                                }else{
                                    pDialog.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddMeter.this);
                                    //get the specific error message
                                    String message = jsonResponse.getString("error_msg");
                                    builder.setMessage(message)
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                pDialog.dismiss();
                                e.printStackTrace();
                                Toast.makeText(AddMeter.this, "Exception Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    AddMeterRequest addMeterRequestRequest = new AddMeterRequest(user_id, number, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(AddMeter.this);
                    queue.add(addMeterRequestRequest);
                }
            }
        });
        //add text change listeners here
        txtMeterNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtMeterNumber.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}