package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activities.ChoiceActivity;
import com.example.myapplication.Activities.ConfigManager;
import com.example.myapplication.Activities.ScanActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_scan_qr, btn_machine_near, btn_my_drinks, btn_price_list, btn_options;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences answer = getSharedPreferences("answer", Context.MODE_PRIVATE); //Выбираем файл реестра
        ConfigManager.permissionCheck(this);
        SharedPreferences.Editor editor = answer.edit();  //Включаем режим редактирования файла
        editor.clear();          //Чистим реестар
        editor.apply();          //Сохроняем настройки
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_scan_qr = findViewById(R.id.btn_scan_qr);
        btn_scan_qr.setOnClickListener(MenuActivity.this);

        btn_machine_near = findViewById(R.id.btn_machine_near);
        btn_machine_near.setOnClickListener(MenuActivity.this);

        btn_my_drinks = findViewById(R.id.btn_my_drinks);
        btn_my_drinks.setOnClickListener(MenuActivity.this);

        btn_price_list = findViewById(R.id.btn_price_list);
        btn_price_list.setOnClickListener(MenuActivity.this);

        btn_options = findViewById(R.id.btn_options);
        btn_options.setOnClickListener(MenuActivity.this);

    }

    //TODO перекинуть на мои напитки и вымышленный прайс лист
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan_qr:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                    } else {
                        startActivity(new Intent(MenuActivity.this, ScanActivity.class));
                    }
                } else {
                    startActivity(new Intent(MenuActivity.this, ScanActivity.class));
                }
                break;
            case R.id.btn_machine_near:
                btn_machine_near.setText("Coming Soon");
                break;
            case R.id.btn_options:
                btn_options.setText("Coming Soon");
                break;
            case R.id.btn_my_drinks:
                startActivity(new Intent(MenuActivity.this, ChoiceActivity.class));
                break;
            case R.id.btn_price_list:

                btn_price_list.setText("Coming Soon");
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Start your camera handling here
            } else {
                Toast.makeText(MenuActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
