package sixtus.com.homenavigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import sixtus.com.homenavigation.R;
import sixtus.com.homenavigation.RegisterRequest;

public class CreateAccount extends AppCompatActivity {
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.app_back));
        setContentView(R.layout.create_account);
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Registering...");
        mProgress.setMessage("Please wait...");
        mProgress.setProgressDrawable(getResources().getDrawable(R.drawable.ecg2));
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        final EditText txtEmail = (EditText) findViewById(R.id.email_address);
        final EditText txtMobile = (EditText) findViewById(R.id.mobile_number);
        final EditText txtPassword1 = (EditText) findViewById(R.id.password1);
        final EditText txtPassword2 = (EditText) findViewById(R.id.password2);

        Button createAccount = (Button) findViewById(R.id.create_account);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(CreateAccount.this, "you clicked me", Toast.LENGTH_SHORT).show();
                final String email = txtEmail.getText().toString();
                final String password1 = txtPassword1.getText().toString();
                final String password2 = txtPassword2.getText().toString();
                String mobile_temp = txtMobile.getText().toString();
                //do internal validation
                if (email.trim().equalsIgnoreCase("")) {
                    txtEmail.setError("Email is required", getResources().getDrawable(R.drawable.ic_action_edit));
                } else if (mobile_temp.trim().equalsIgnoreCase("")) {
                    txtMobile.setError("Mobile contact is required", getResources().getDrawable(R.drawable.ic_action_edit));
                } else if (password1.trim().equalsIgnoreCase("")) {
                    txtPassword1.setError("Password is required", getResources().getDrawable(R.drawable.ic_action_edit));
                } else if (password2.trim().equalsIgnoreCase("")) {
                    txtPassword2.setError("Please confirm password", getResources().getDrawable(R.drawable.ic_action_edit));
                } else if (!(password1.trim().equals(password2.trim()))) {
                    txtPassword1.setError("Passwords do not match", getResources().getDrawable(R.drawable.ic_action_edit));
                    txtPassword2.setError("Passwords do not match", getResources().getDrawable(R.drawable.ic_action_edit));
                } else {
                    mProgress.show();
                    //now parse mobile number to int
                    final int mobile = Integer.parseInt(txtMobile.getText().toString());
                    //good proceed to connect to server
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("error");

                                if (success) {
                                    mProgress.dismiss();
                                    Toast.makeText(CreateAccount.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                    Intent launchLogin = new Intent(CreateAccount.this, Login.class);
                                    CreateAccount.this.startActivity(launchLogin);
                                    finish();
                                } else {
                                    mProgress.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this);
                                    //get the specific error message
                                    String message = jsonResponse.getString("error_msg");
                                    builder.setMessage(message)
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(CreateAccount.this, "Exception Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };

                    RegisterRequest registerRequest = new RegisterRequest(email, mobile, password1, password2, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(CreateAccount.this);
                    queue.add(registerRequest);
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
        txtPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtPassword1.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //password 2
        txtPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtPassword2.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //mobile
        txtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtMobile.setError(null);
                try {
                    int num = Integer.parseInt(txtMobile.getText().toString());
                } catch (NumberFormatException e) {
                    //not a number
                    Toast.makeText(CreateAccount.this, "Mobile number not an integer", Toast.LENGTH_SHORT).show();
                    txtEmail.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}