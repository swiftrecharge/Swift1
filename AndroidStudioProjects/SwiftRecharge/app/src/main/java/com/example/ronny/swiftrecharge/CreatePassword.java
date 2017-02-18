package com.example.ronny.swiftrecharge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by fred on 2/11/2017.
 */

public class CreatePassword extends Activity {
    Button b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createpassword);

         b3= (Button) findViewById(R.id.complete);

         b3.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 AlertDialog.Builder builder=new AlertDialog.Builder(CreatePassword.this);
                 builder.setMessage("Would you like to add another meter?");
                 builder.setCancelable(false);
                 builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         startActivity(new Intent(CreatePassword.this,anotherImage.class));

                     }
                 });

                 builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         startActivity(new Intent(CreatePassword.this,CreateAccount.class));
                     }
                 });
                    AlertDialog alert =builder.create();
                    builder.show();
   }

         }
         );
    }
}