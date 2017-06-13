package sixtus.com.homenavigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import io.paperdb.Paper;

public class Pattern extends AppCompatActivity {
    String save_pattern_key = "pattern_code";
    PatternLockView mPatternLockView;
    String final_pattern = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);
        final String save_pattern = Paper.book().read(save_pattern_key);
        //Toast.makeText(MainActivity.this, save_pattern, Toast.LENGTH_LONG).show();
        if (save_pattern != null && !save_pattern.isEmpty()){
            //then they is a saved pattern
            setContentView(R.layout.pattern_screen);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    Snackbar.make(view, "Forgot Pattern?", Snackbar.LENGTH_LONG)
                            .setAction("Reset", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Pattern.this);
                                    String message = "You are about to reset your pattern!";
                                    builder.setMessage(message)
                                            .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //reset all shared preferences
                                                    SharedPreferences sessionMail = getSharedPreferences("userSession", 0);
                                                    SharedPreferences.Editor editor = sessionMail.edit();
                                                    editor.clear();
                                                    editor.commit();

                                                    //reset pattern
                                                    Paper.book().write(save_pattern_key, "");

                                                    //return home
                                                    Intent launchHomePage = new Intent(Pattern.this, HomePage.class);
                                                    startActivity(launchHomePage);
                                                    finish();
                                                }
                                            })
                                            .setNegativeButton("Cancel", null)
                                            .show();
                                }
                            }).show();
                }
            });
            mPatternLockView = (PatternLockView)findViewById(R.id.pattern_lock_view);
            mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils.patternToString(mPatternLockView, pattern);
                    if (final_pattern.equals(save_pattern)){
                        Toast.makeText(Pattern.this, "Pattern Accepted", Toast.LENGTH_SHORT).show();
                        //redirect to home
                        Intent launchHome = new Intent(Pattern.this, HomeNav.class);
                        startActivity(launchHome);
                        finish();
                    }else{
                        Toast.makeText(Pattern.this, "Wrong Pattern, Try Again!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCleared() {

                }
            });

        }else{
            //they is no pattern, show set pattern screen to set paettern
            setContentView(R.layout.pattern_lock);
            mPatternLockView = (PatternLockView)findViewById(R.id.pattern_lock_view);
            mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils.patternToString(mPatternLockView, pattern);
                }

                @Override
                public void onCleared() {

                }
            });

            Button btnSetPattern = (Button)findViewById(R.id.set_pattern);
            btnSetPattern.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (final_pattern.isEmpty() || final_pattern == null){
                        Toast.makeText(Pattern.this, "Please set pattern", Toast.LENGTH_SHORT).show();
                    }else {
                        Paper.book().write(save_pattern_key, final_pattern);
                        Toast.makeText(Pattern.this, "Pattern Set Successful", Toast.LENGTH_SHORT).show();
                        Intent launchPattern = new Intent(Pattern.this, Pattern.class);
                        startActivity(launchPattern);
                        finish();
                    }

                }
            });
        }
    }
}