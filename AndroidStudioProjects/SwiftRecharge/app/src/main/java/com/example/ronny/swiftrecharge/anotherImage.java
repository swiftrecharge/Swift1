package com.example.ronny.swiftrecharge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by fred on 2/16/2017.
 */

public class anotherImage extends Activity {
    Button btnother;
    ImageView imagecaptur2;
    Button other;
    private static final int CAME_REQUEST = 1212;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takeanotherimage);

        btnother = (Button) findViewById(R.id.button9);
        imagecaptur2 = (ImageView) findViewById(R.id.imageView2);
        other = (Button) findViewById(R.id.but);

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(anotherImage.this);
                builder.setMessage("Would you like to add another meter?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(anotherImage.this,anotherImage.class));

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(anotherImage.this,Verify.class));
                    }
                });
                AlertDialog alert =builder.create();
                builder.show();
            }

        }
                );

        btnother.setOnClickListener(new btnotherClicker());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAME_REQUEST) {
            Bitmap bd = (Bitmap) data.getExtras().get("data");
            imagecaptur2.setImageBitmap(bd);
        }
    }

    class btnotherClicker implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camIntent, CAME_REQUEST);
        }
    }
}