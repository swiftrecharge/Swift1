package sixtus.com.homenavigation;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by markzeno on 5/22/17.
 */

public class SelectMeter extends AppCompatActivity{
    private Connection mycon;
    private Statement statement;
    private ResultSet resultSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.app_back));
        setContentView(R.layout.selectmeter);
    }
    public SelectMeter(){
        try{

            Class.forName("com.mysql.jdbc.Driver");
            mycon= DriverManager.getConnection("jdbc:mysql://localhost:3306/swiftech","DrCod","r0031010076125t");
            statement=mycon.createStatement();
        }catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    }


