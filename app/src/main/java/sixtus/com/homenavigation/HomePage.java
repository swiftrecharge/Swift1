package sixtus.com.homenavigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //call the finish method here
        super.onCreate(savedInstanceState);
        //shared preference to either redirect to pattern view or show login or signup page
        SharedPreferences persistLogin = getSharedPreferences("userSession", 0);
        //persistLogin.getString("loggedIn", "");
        String loginStatus = persistLogin.getString("loggedIn", "no");
        if (loginStatus.equals("yes")){
            //user have previously logged in
            Intent launchPattern = new Intent(HomePage.this, Pattern.class);
            startActivity(launchPattern);
            //kill this page
            finish();
        }else{
            setContentView(R.layout.home_page);
            final NetworkStatus networkStatus = new NetworkStatus(this);
            //Login
            Button login = (Button) findViewById(R.id.login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!networkStatus.isNetworkAvailable()) {
                        Toast.makeText(HomePage.this,"No network access. Please turn on mobile data or connect to wifi.", Toast.LENGTH_LONG).show();
                    } else {

                        //launch the login activity
                        Intent launchLogin = new Intent(HomePage.this, Login.class);
                        startActivity(launchLogin);
                        finish();
                        //Toast.makeText(HomePage.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            //Signup
            Button signup =(Button) findViewById(R.id.signup);
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!networkStatus.isNetworkAvailable()) {
                        Toast.makeText(HomePage.this,"No network access. Please turn on mobile data or connect to wifi.", Toast.LENGTH_LONG).show();
                    } else {
                        //launch the signup activity
                        Intent launchSignup = new Intent(HomePage.this, CreateAccount.class);
                        startActivity(launchSignup);
                        finish();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
        //Toast.makeText(HomePage.this, "Press Again to Exit", Toast.LENGTH_LONG).show();
    }
}