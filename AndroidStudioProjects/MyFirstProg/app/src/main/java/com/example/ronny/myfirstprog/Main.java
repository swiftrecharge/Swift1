package com.example.ronny.myfirstprog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.Id.layout.activity_main);

        Button b = (Button) findViewById(R.Id.id.button);
        b.setOnClickListener(new OnClickListener() {
            public void OnClick(View v) {
                startActivity(new Intent(Main.this, Activities.class));
            }


        });
    }}