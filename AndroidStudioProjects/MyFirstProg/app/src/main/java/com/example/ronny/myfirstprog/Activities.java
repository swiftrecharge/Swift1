package com.example.ronny.myfirstprog;

import android.app.Activity;
import android.os.Bundle;


public abstract class Activities extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.Activities);
    }
}
