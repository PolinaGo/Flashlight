package com.polinago.flashlight;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
ImageButton imageButton;
    Camera camera;
    Camera.Parameters Parameters;
    boolean flash = false;
    boolean on = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageButton = (ImageButton)findViewById(R.id.imageButton);
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
        {
            camera = Camera.open();
            Parameters = camera.getParameters();
            flash = true;
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
            if (flash)
            {
                if (!on)
                {
                    imageButton.setImageResource(R.drawable.on);
                    Parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(Parameters);
                    camera.startPreview();
                    on = true;
                }
                else
                {
                    imageButton.setImageResource(R.drawable.off);
                    Parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(Parameters);
                    camera.stopPreview();
                    on = false;
                }
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Oooops, something is wrong...");
                builder.setMessage("Flashlight is not available on this device");
                builder.setPositiveButton("Okay:(", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
            }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (camera!=null)
        {
            camera.release();
            camera = null;
        }
    }
}
