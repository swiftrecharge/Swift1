package sixtus.com.homenavigation;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeNav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.home_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the reference to the user mail and pic and display it appropriately
        //preference settings can be found in login class
        //TextView userMailDisplay = (TextView)findViewById(R.id.user_mail_display);
        //SharedPreferences sessionMail = getSharedPreferences("userSession", 0);
        //userMailDisplay.setText(sessionMail.getString("user_mail", ""));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Help?", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
            case R.id.action_settings:
                //logout user
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeNav.this);
                String message = "Logout of SwiftRecharge?";
                builder.setMessage(message)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sessionMail = getSharedPreferences("userSession", 0);
                                SharedPreferences.Editor editor = sessionMail.edit();
                                editor.clear();
                                editor.commit();

                                Intent launchHomePage = new Intent(HomeNav.this, HomePage.class);
                                startActivity(launchHomePage);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                //return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item); */
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Toast.makeText(this, "Swift Recharge Home",Toast.LENGTH_LONG).show();
        }else if (id == R.id.nav_manage_meters){
            //send to manage meters page
            Intent launchManageMeters = new Intent(HomeNav.this, ManageMeters.class);
            startActivity(launchManageMeters);

        } else if (id == R.id.nav_buy_credit){
            //lauch buy credit page
            Intent launchBuyCredit = new Intent(HomeNav.this, BuyCredit.class);
            startActivity(launchBuyCredit);
        } else if (id == R.id.nav_personal_info) {
            Intent launchAccount = new Intent(HomeNav.this, UpdateDetails.class);
            startActivity(launchAccount);

        } else if (id == R.id.nav_payment_wallet) {
            Toast.makeText(this, "Swift Recharge Payment Wallet",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_share) {
            String shareBody = "Download Swift Recharge App. From https://github.com/swiftrecharge/swift";
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(intent.EXTRA_TEXT, shareBody);
            try{
                startActivity(intent.createChooser(intent, "Complete Action Using"));
            }catch (android.content.ActivityNotFoundException exception){
                //handle error
            }

        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "Swift Recharge Will be glad to hear from you",Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}