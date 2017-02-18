package com.example.ronny.swiftrecharge;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;



public class CreateAccount extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);


        Button b1 = (Button) findViewById(R.id.button3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccount.this, SnapMeter.class));
            }
        });
    }
}
