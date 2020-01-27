package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class SugarActivity extends AppCompatActivity implements View.OnClickListener {
Button btn_0, btn_1, btn_2, btn_3, btn_4;
public static String SUGAR_QTY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar);

        btn_0 = findViewById(R.id.btn_0);
        btn_0.setOnClickListener(SugarActivity.this);

        btn_1 = findViewById(R.id.btn_1);
        btn_1.setOnClickListener(SugarActivity.this);

        btn_2 = findViewById(R.id.btn_2);
        btn_2.setOnClickListener(SugarActivity.this);

        btn_3 = findViewById(R.id.btn_3);
        btn_3.setOnClickListener(SugarActivity.this);

        btn_4 = findViewById(R.id.btn_4);
        btn_4.setOnClickListener(SugarActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_0:
                SUGAR_QTY = String.valueOf(0);
                startActivity(new Intent(SugarActivity.this, MilkActivity.class));
                break;
            case R.id.btn_1:
                SUGAR_QTY = String.valueOf(1);
                startActivity(new Intent(SugarActivity.this, MilkActivity.class));
                break;
            case R.id.btn_2:
                SUGAR_QTY = String.valueOf(2);
                startActivity(new Intent(SugarActivity.this, MilkActivity.class));
                break;
            case R.id.btn_3:
                SUGAR_QTY = String.valueOf(3);
                startActivity(new Intent(SugarActivity.this, MilkActivity.class));
                break;
            case R.id.btn_4:
                SUGAR_QTY = String.valueOf(4);
                startActivity(new Intent(SugarActivity.this, MilkActivity.class));
                break;
        }


    } }