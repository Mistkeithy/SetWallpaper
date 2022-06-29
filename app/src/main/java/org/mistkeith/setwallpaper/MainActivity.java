package org.mistkeith.setwallpaper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity {
    private int STORAGE_PERMISSION_CODE = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check permission
        requestStoragePermission();
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "No permission", Toast.LENGTH_SHORT).show();
            this.finishAffinity();
        }

        Intent intent = getIntent();
        TextView dataText = (TextView)findViewById(R.id.dataText);

        // Check uri and drop if invalid
        String data_string = intent.getDataString();
        if (data_string == null) {
            Toast.makeText(this, "No source", Toast.LENGTH_SHORT).show();
            this.finishAffinity();
        }

        // Convert to normal path format
        Uri uri_file = Uri.parse(data_string);
        File file = new File(uri_file.getPath());

        // am start -a android.intent.action.SET_WALLPAPER -c android.intent.category.DEFAULT -d file:///sdcard/Documents/wallpaper.png -t 'image/*' -e mimeType 'image/*' -f 0x10000000 org.mistkeith.setwallpaper/.MainActivity

        // Set bitmap
        ImageView imageView = (ImageView)findViewById(R.id.showimage);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        // Set Wallpaper
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

        // Show UI
        //dataText.setText(file.getPath());
        //imageView.setImageBitmap(bitmap);

        try {
            wallpaperManager.setBitmap(bitmap);
            Toast.makeText(this, "Wallpaper installed!", Toast.LENGTH_SHORT).show();
            this.finishAffinity();
        } catch (IOException e) {
            Toast.makeText(this, "Cannot to set the wallpaper!", Toast.LENGTH_SHORT).show();
            this.finishAffinity();
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Has no permission")
                    .setMessage("Application needed for this permission\nREAD_EXTERNAL_STORAGE")
                    .setPositiveButton("Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.this.finishAffinity();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}