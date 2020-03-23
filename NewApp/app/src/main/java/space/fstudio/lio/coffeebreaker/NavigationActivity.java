package space.fstudio.lio.coffeebreaker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import space.fstudio.lio.coffeebreaker.Activities.ScanActivity;

import static space.fstudio.lio.coffeebreaker.Utils.Variables.CAMERA_PERMISSION_REQUEST_CODE;

public class NavigationActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnScan, btnSetting, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        btnScan = findViewById(R.id.btnScan);
        btnSetting = findViewById(R.id.btnSettings);
        btnExit = findViewById(R.id.btnExit);

        btnScan.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnScan:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                    } else {
                        startActivity(new Intent(NavigationActivity.this, ScanActivity.class));
                    }
                } else {
                    startActivity(new Intent(NavigationActivity.this, ScanActivity.class));
                }
                break;
            case R.id.btnSettings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnExit:
                finish();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(NavigationActivity.this, ScanActivity.class));
            }
        }
    }
}
