package com.example.ronny.swiftrecharge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by fred on 2/16/2017.
 */
public class Verify extends Activity {
    Button vf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify);

        vf= (Button) findViewById(R.id.button8);
        vf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Verify.this,Main.class));
            }
        });
    }
}
