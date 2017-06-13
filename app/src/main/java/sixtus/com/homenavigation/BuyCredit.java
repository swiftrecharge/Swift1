package sixtus.com.homenavigation;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import static sixtus.com.homenavigation.R.drawable.button_layout;

public class BuyCredit extends AppCompatActivity {
    // meters JSONArray
    JSONArray meters = null;
    // creating new HashMap
    HashMap<String, String> map = new HashMap<String, String>();
    // Progress Dialog
    ProgressDialog pDialog;
    ProgressDialog pDialog2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.app_back));
        setContentView(R.layout.buy_credit);

        ArrayList<HashMap<String, String>> metersList;

        // JSON Node names
        final String TAG_SUCCESS = "error";
        final String TAG_METERS = "meters";
        final String TAG_MID = "meter_id";
        final String TAG_NUMBER = "meter_number";
        final String TAG_UNITS = "remaining_units";
        //get user id
        SharedPreferences userId = getSharedPreferences("userSession", 0);
        String stored_id = userId.getString("user_id", "");
        //Toast.makeText(BuyCredit.this, stored_id, Toast.LENGTH_LONG).show();

        pDialog = new ProgressDialog(BuyCredit.this);
        pDialog.setMessage("Loading meters. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        pDialog2 = new ProgressDialog(BuyCredit.this);
        pDialog2.setMessage("Processing purchase. Please wait...");
        pDialog2.setIndeterminate(false);
        pDialog2.setCancelable(false);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success  = jsonResponse.getBoolean(TAG_SUCCESS);

                    if(success){
                        //retrieval successful
                        meters = jsonResponse.getJSONArray(TAG_METERS);
                        // looping through All meters
                        //if (meters.length()>0){
                        //person have added meters
                        for (int i = 0; i < meters.length(); i++) {
                            JSONObject c = meters.getJSONObject(i);

                            // Storing each json item in variable
                            String id = c.getString(TAG_MID);
                            String number = c.getString(TAG_NUMBER);
                            String remaining = c.getString(TAG_UNITS);
                            String btnText = "Meter Number: "+number + '\n'+'\n' + "Remaining Units: " + remaining;

                            // adding each child node to HashMap key => value
                            map.put(id, btnText);
                            //map.put(TAG_NUMBER, number);
                        }
                        /**
                         * Updating parsed JSON data into buttons
                         * */
                        Set<String> keys = map.keySet();
                        Iterator<String> iterator = keys.iterator();
                        while (iterator.hasNext()){
                            String meter_id = iterator.next();
                            String meter_number = map.get(meter_id);
                            //now construct buttons
                            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.manage_meters2);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(0,0,0,30);
                            Button btn = new Button(BuyCredit.this);
                            btn.setId(Integer.parseInt(meter_id));
                            final int btn_id = btn.getId();
                            btn.setText(meter_number);
                            btn.setBackground(getResources().getDrawable(R.drawable.button_layout));
                            linearLayout.addView(btn, params);
                            final Button btn1 = ((Button) findViewById(btn_id));
                            //show dialog when a button is clicked
                            btn1.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View view) {
                                    //Toast.makeText(view.getContext(),"Button clicked index = " + btn_id, Toast.LENGTH_SHORT).show();
                                    //show alert dialog with spinner to do purchase
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BuyCredit.this);
                                    String title = "Complete Credit Purchase";
                                    ArrayList<String> spinnerArray = new ArrayList<String>();
                                    spinnerArray.add("Choose Amount(GHS)");
                                    for (int i=5; i<200; i+=5){
                                        spinnerArray.add(i+"");
                                    }
                                    final Spinner spinner = new Spinner(BuyCredit.this);
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(BuyCredit.this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                                    spinner.setAdapter(spinnerArrayAdapter);
                                    LinearLayout layout = new LinearLayout(BuyCredit.this);
                                    layout.setGravity(Gravity.CENTER_HORIZONTAL);
                                    layout.addView(spinner);
                                    alertDialogBuilder.setTitle(title)
                                            .setView(layout)
                                            .setNegativeButton("Cancel Transaction", null)
                                            .show();
                                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            final String amount = spinner.getSelectedItem().toString();
                                            if (amount.equals("Choose Amount(GHS)")){
                                                //Toast.makeText(BuyCredit.this, "Please Select a valid amount.", Toast.LENGTH_LONG).show();
                                            }else{
                                                AlertDialog.Builder alB = new AlertDialog.Builder(BuyCredit.this);
                                                alB.setMessage("Purchase Credit Worth GHS" + amount + "?")
                                                        .setNegativeButton("Cancel", null)
                                                        .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                //user authorised purchase...proceed
                                                                final int m_id = btn1.getId();
                                                                String txt = btn1.getText().toString();
                                                                final String txt_number = txt.substring(txt.indexOf("Number:")+7,txt.indexOf("Remaining")-2).trim();
                                                                SharedPreferences user_id = getSharedPreferences("userSession", 0);
                                                                final String id_user = user_id.getString("user_id","");
                                                                final String purchased_value = amount;

                                                                //Toast.makeText(BuyCredit.this, txt, Toast.LENGTH_LONG).show();
                                                                pDialog2.show();
                                                                //make request to server and get response
                                                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {
                                                                        try{
                                                                            JSONObject jsonResponse = new JSONObject(response);
                                                                            boolean success  = jsonResponse.getBoolean("error");

                                                                            if(success){
                                                                                pDialog2.dismiss();
                                                                                //redirect to buy credit
                                                                                Toast.makeText(BuyCredit.this, "Purchase successful!", Toast.LENGTH_SHORT).show();
                                                                                Intent buyCredit = new Intent(BuyCredit.this, BuyCredit.class);
                                                                                startActivity(buyCredit);
                                                                                finish();
                                                                            }else{
                                                                                pDialog2.dismiss();
                                                                                AlertDialog.Builder builder = new AlertDialog.Builder(BuyCredit.this);
                                                                                //get the specific error message
                                                                                String message = jsonResponse.getString("error_msg");
                                                                                builder.setMessage(message)
                                                                                        .setNegativeButton("Retry", null)
                                                                                        .create()
                                                                                        .show();
                                                                            }
                                                                        } catch (JSONException e) {
                                                                            pDialog2.dismiss();
                                                                            e.printStackTrace();
                                                                            Toast.makeText(BuyCredit.this, "Exception Error", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                };
                                                                BuyCreditRequest buyCreditRequest = new BuyCreditRequest(id_user, txt_number, m_id+"", purchased_value, responseListener);
                                                                RequestQueue queue = Volley.newRequestQueue(BuyCredit.this);
                                                                queue.add(buyCreditRequest);
                                                            }
                                                        })
                                                        .show();
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                }
                            });
                        }
                        //after constructing buttons dismiss progress bar
                        pDialog.dismiss();
                        //}else{
                        //}
                    }else {
                        pDialog.dismiss();
                        //person have not added any meter
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.manage_meters2);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        Button btn = new Button(BuyCredit.this);
                        btn.setText("Click to add meter");
                        btn.setBackground(getResources().getDrawable(R.drawable.button_layout));
                        linearLayout.addView(btn, params);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(BuyCredit.this, AddMeter.class);
                                startActivity(intent);
                            }
                        });
                        String message = jsonResponse.getString("error_msg");
                        Toast.makeText(BuyCredit.this, message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    pDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(BuyCredit.this, "Exception Error", Toast.LENGTH_SHORT).show();
                }
            }
        };
        MeterRequest meterRequest = new MeterRequest(stored_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(BuyCredit.this);
        queue.add(meterRequest);
    }
}