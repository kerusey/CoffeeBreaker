package com.example.myapplication.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager  {
    public ConfigManager(Context context, String content, String filename) {
        File root = new File(Environment.getExternalStorageDirectory(),
                context.getString(R.string.app_name));
        File db = new File(root + "/" + filename + ".json");

        /*  Create file and folder  */
        try {
            if (!root.exists()) {
                root.mkdir();
                if (!db.exists()) {
                    db.createNewFile();
                    Toast.makeText(context, "Local Cash is created", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            Toast.makeText(context, "File create error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        /*  Write to file  */
        try {
            FileWriter writer = new FileWriter(db);
            writer.append(content);    //Content here
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*  Read from file  */
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(db));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    public static void permissionCheck(Context context) {
        int writeExternalStoragePermission = ContextCompat
                .checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions((Activity) context,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            Variables.WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE());
        }
    }

    public static void onRequestPermissionsResult(Context context, int requestCode,
                                                  @NonNull int[] grantResults) {
        if (requestCode == Variables.WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE()) {
            int grantResultsLength = grantResults.length;
            if (grantResultsLength > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context.getApplicationContext(),
                        "You grant write external storage permission. Please click original button again to continue.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context.getApplicationContext(),
                        "You denied write external storage permission.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
