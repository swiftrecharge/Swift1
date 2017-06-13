package sixtus.com.homenavigation;

import android.app.ProgressDialog;
import android.content.Context;
        import android.content.SharedPreferences;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Comparator;
        import java.util.List;

public class SortedByMeter extends AppCompatActivity {

    ListView unsortedlist;
    MeterAdapter meterAdapter;String mn;
    JSONArray jsonArray;

    ProgressDialog progressDialog;
    String LOGIN_REQUEST_URI = "http://swiftrecharge.000webhostapp.com/getAllMeters.php";
    SharedPreferences sharedPreferences;

    ArrayList<Meter> meterList= new ArrayList<Meter>();
    String jsonString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sorted_by_meter);
        unsortedlist = (ListView) findViewById(R.id.sortedlist);
        progressDialog = new ProgressDialog(this);
        sharedPreferences = getSharedPreferences("SHARED_PREF_NAME", Context.MODE_PRIVATE);
        getAllMeters();


//data being retreived through SharedPreferences .
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF_NAME", Context.MODE_PRIVATE);
        jsonString = sharedPreferences.getString("jsonString",null);

//conversion of jsonString to jsonArray
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//sortedJSONArray would contain the sorted JSONObjects.
        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> jsonValues = new ArrayList<JSONObject>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonValues.add(jsonArray.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//The following section feeds the sorted data into the sortedJsonArray
        Collections.sort( jsonValues, new Comparator<JSONObject>() {
            //You can change "brand" with "price" if you want to sort by price
            private static final String KEY_NAME = "meter_id";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();

                try {
                    valA = (String) a.get(KEY_NAME);
                    valB = (String) b.get(KEY_NAME);
                }
                catch (JSONException e) {
                    //do something
                }

                return valA.compareTo(valB);
                //if you want to change the sort order, simply use the following:
                //return -valA.compareTo(valB);
            }
        });

        for (int i = 0; i < jsonArray.length(); i++) {
            sortedJsonArray.put(jsonValues.get(i));
        }


        for(int i=0;i<sortedJsonArray.length();i++){

            try {
                JSONObject jsonObject = sortedJsonArray.getJSONObject(i);
                int meterId = Integer.parseInt("Meter :" + jsonObject.getString("meter_id"));
                int meter_number = Integer.parseInt("Meter Number:" + jsonObject.getString("meter_number"));
                double current_units = Double.parseDouble("Current Units:" +jsonObject.getString("remaining_units"));

//The data from each JSONObject is copied to an Object of Class Laptop
                Meter meter = new Meter(meterId,meter_number,current_units);
//The objects of class Laptop are thereby added into the ArrayList i.e laptopList. This will be used
//as we'll set the adapter to the listView.
                meterList.add(meter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


//the list_layout and laptopList are passed to the Adapter here.
            meterAdapter=new MeterAdapter(SortedByMeter.this,R.layout.meter_list, meterList);

            unsortedlist.setAdapter(meterAdapter);

            meterAdapter.notifyDataSetChanged();
        }

    }
    public void getAllMeters() {
        progressDialog.setMessage("Fetching Meters from the Server...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_REQUEST_URI,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        Toast.makeText(SortedByMeter.this, "Meters Successfully Fetched", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject js = new JSONObject(response);

                            JSONArray jsonArray = js.getJSONArray("MeterInfo");

                            jsonString = jsonArray.toString();


                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("jsonString", jsonString);
                            editor.apply();


                            JSONArray sortedJsonArray = new JSONArray();
                            List<JSONObject> jsonValues = new ArrayList<JSONObject>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonValues.add(jsonArray.getJSONObject(i));
                            }
                            Collections.sort(jsonValues, new Comparator<JSONObject>() {
                                //You can change "Name" with "ID" if you want to sort by ID
                                private static final String KEY_NAME = "meter_id";

                                @Override
                                public int compare(JSONObject a, JSONObject b) {
                                    String valA = new String();
                                    String valB = new String();

                                    try {
                                        valA = (String) a.get(KEY_NAME);
                                        valB = (String) b.get(KEY_NAME);
                                    } catch (JSONException e) {
                                        //do something
                                    }

                                    return valA.compareTo(valB);
                                    //if you want to change the sort order, simply use the following:
                                    //return -valA.compareTo(valB);
                                }
                            });

                            for (int i = 0; i < jsonArray.length(); i++) {
                                sortedJsonArray.put(jsonValues.get(i));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue request = Volley.newRequestQueue(this);
        request.add(stringRequest);
    }
}



