package com.example.ronny.swiftrecharge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by fred on 2/16/2017.
 */

public class SnapMeter extends Activity {
    Button btnsnap;
    Button next;
    ImageView imagecapture;
    private static final int CAM_REQUEST=1313;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snapmeter);

        btnsnap= (Button) findViewById(R.id.button4);
        imagecapture= (ImageView)findViewById(R.id.imageView);
        next=(Button)findViewById(R.id.button5);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SnapMeter.this,Paymentdetails.class));
            }
        });
      btnsnap.setOnClickListener(new btnsnapClicker());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CAM_REQUEST){
            Bitmap bm= (Bitmap) data.getExtras().get("data");
            imagecapture.setImageBitmap(bm);
        }
    }

     class btnsnapClicker implements Button.OnClickListener {
         @Override
         public void onClick(View v) {
             Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
             startActivityForResult(cameraIntent,CAM_REQUEST);
         }
     }
}
