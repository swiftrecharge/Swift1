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

import static sixtus.com.homenavigation.R.id.mobile_number;

public class Login extends AppCompatActivity {
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.app_back));
        setContentView(R.layout.login);
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Logging in...");
        mProgress.setMessage("Please wait...");
        mProgress.setProgressDrawable(getResources().getDrawable(R.drawable.ecg2));
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);


        final EditText txtEmail = (EditText)findViewById(R.id.loginemail);
        final EditText txtPassword = (EditText)findViewById(R.id.loginpassword);

        Button loginButton = (Button)findViewById(R.id.loginuser);
        loginButton.setOnClickListener(new View.OnClickListener(){
            //@Override;
            public void onClick(View v){
                final String email = txtEmail.getText().toString();
                final String password = txtPassword.getText().toString();
                //code goes here to check sign up details before login
                if (email.trim().equalsIgnoreCase("")){
                    txtEmail.setError("Email is required", getResources().getDrawable(R.drawable.ic_action_edit));
                }else if (password.trim().equalsIgnoreCase("")){
                    txtPassword.setError("Password is required", getResources().getDrawable(R.drawable.ic_action_edit));
                }else{
                    mProgress.show();
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success  = jsonResponse.getBoolean("error");

                                if(!success){
                                    mProgress.dismiss();
                                    Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Intent signinuser = new Intent(Login.this, HomeNav.class);
                                    startActivity(signinuser);
                                    //then persist user mail and status
                                    SharedPreferences loggedIn = getSharedPreferences("userSession", 0);
                                    SharedPreferences.Editor editor1 = loggedIn.edit();
                                    editor1.putString("loggedIn", "yes");
                                    editor1.commit();
                                    //user id persist
                                    String id = jsonResponse.getString("user_id");
                                    //Toast.makeText(Login.this, id, Toast.LENGTH_LONG).show();
                                    SharedPreferences userIid = getSharedPreferences("userSession", 0);
                                    SharedPreferences.Editor editor2 = userIid.edit();
                                    editor2.putString("user_id", id);
                                    editor2.commit();
                                    //email
                                    SharedPreferences sessionMail = getSharedPreferences("userSession", 0);
                                    SharedPreferences.Editor editor = sessionMail.edit();
                                    editor.putString("user_mail", email);
                                    editor.commit();
                                    //!important, make sure to update this on email update.
                                    finish();
                                }else{
                                    mProgress.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                    //get the specific error message
                                    String message = jsonResponse.getString("error_msg");
                                    builder.setMessage(message)
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Login.this, "Exception Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Login.this);
                    queue.add(loginRequest);
                }
            }
        });
        //add text change listeners here
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtEmail.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //password 1
        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}