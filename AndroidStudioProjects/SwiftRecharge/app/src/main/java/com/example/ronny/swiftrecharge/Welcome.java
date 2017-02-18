package com.example.ronny.swiftrecharge;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by fred on 2/11/2017.
 */

public class Welcome extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        Toast.makeText(this,"WELCOME !",Toast.LENGTH_LONG).show();
    }
}


