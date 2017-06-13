package sixtus.com.homenavigation;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManageMeters extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.app_back));
        setContentView(R.layout.manage_meters);
        Button btnAddMeter = (Button)findViewById(R.id.add_meter);
        btnAddMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageMeters.this, AddMeter.class);
                startActivity(intent);
            }
        });


        Button btnViewMeter = (Button) findViewById(R.id.check_meter_credit);
        btnViewMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ManageMeters.this,SortedByMeter.class);
                startActivity(intent);
            }
        });
     }
}