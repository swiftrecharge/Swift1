package sixtus.com.homenavigation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by fred on 2/11/2017.
 */

public class CreatePassword extends AppCompatActivity {
    Button b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.app_back));
        setContentView(R.layout.create_password);

         b3= (Button) findViewById(R.id.complete);

         b3.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent launchHome = new Intent(CreatePassword.this, HomeNav.class);
                 Toast.makeText(CreatePassword.this, "Account Creation Successful", Toast.LENGTH_SHORT).show();
                 startActivity(launchHome);
                 finish();
             }

         }
         );
    }
}