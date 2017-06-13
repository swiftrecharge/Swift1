package sixtus.com.homenavigation;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static android.support.v7.appcompat.R.styleable.ActionBar;

public class UpdateDetails extends AppCompatActivity {
    //declare a class level list view and array adapter object object

    ListView listView;
    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //call the navigation class
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.app_back));
        setContentView(R.layout.activity_update_personal_details);
        listView = (ListView) findViewById(R.id.details_list);
        adapter = ArrayAdapter.createFromResource(this, R.array.details_list, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        detailSelected();
    }

    protected void detailSelected(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get detail id
                long listPosition = listView.getItemIdAtPosition(position);
                int positionId = (int)listPosition;
                //switch between the ids to launch the right activity
                switch (positionId){
                    case 0:
                        //password
                        Intent launchPasswordUpdate = new Intent(UpdateDetails.this, UserPassword.class);
                        startActivity(launchPasswordUpdate);
                        break;
                    case 1:
                        //email
                        Intent launchEmailUpadte = new Intent(UpdateDetails.this, UserEmail.class);
                        startActivity(launchEmailUpadte);
                        break;
                    case 2:
                        Intent launchOccupationUpadte = new Intent(UpdateDetails.this, UserOccupation.class);
                        startActivity(launchOccupationUpadte);
                        break;
                    case 3:
                        Intent launchContactUpadte = new Intent(UpdateDetails.this, UserMobileContact.class);
                        startActivity(launchContactUpadte);
                        break;
                    case 4:
                        Intent launchPaymentDetailsUpadte = new Intent(UpdateDetails.this, PaymentDetails.class);
                        startActivity(launchPaymentDetailsUpadte);
                        break;
                    default:
                        //do nothing
                }
            }
        });
    }
}