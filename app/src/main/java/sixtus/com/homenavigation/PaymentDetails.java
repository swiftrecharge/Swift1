package sixtus.com.homenavigation;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class PaymentDetails extends AppCompatActivity {
    Spinner wallet;
    final Context context = this;
    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.app_back));
        setContentView(R.layout.payment_details);

        wallet = (Spinner) findViewById(R.id.spinner_mobile_wallet);
        adapter = ArrayAdapter.createFromResource(this, R.array.wallet, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wallet.setAdapter(adapter);

        walletSelected();
    }

    protected void walletSelected(){
        wallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                long walletPosition = wallet.getItemIdAtPosition(position);
                int positionId = (int)walletPosition;
                switch (positionId) {
                    case 0:
                        //showPrompt();
                        //default not a vlaod wallet
                        break;
                    case 1:
                        showPrompt();
                        break;
                    case 2:
                        showPrompt();
                        break;
                    case 3:
                        showPrompt();
                        break;
                    case 4:
                        showPrompt();
                        break;
                    case 5:
                        showPrompt();
                        break;
                    default:
                        //do nothing
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    protected void showPrompt(){
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.wallet_prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                // get user input and set it to result
                                // edit text
                                //result.setText(userInput.getText());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

}
