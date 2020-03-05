package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class ValuemActivity extends AppCompatActivity implements View.OnClickListener {
Button btn_2, btn_4;
public static String VALUEM_CHOICE;
public static Double VALUEM_CHOICE1;

public static String PRICE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valuem);

        btn_2 = findViewById(R.id.btn_2);
        btn_2.setOnClickListener(ValuemActivity.this);

        btn_4 = findViewById(R.id.btn_4);
        btn_4.setOnClickListener(ValuemActivity.this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_2:
                VALUEM_CHOICE=String.valueOf(0.2);
                VALUEM_CHOICE1=0.2;
                PRICE=String.valueOf(120);
                startActivity(new Intent(ValuemActivity.this, StrengthActivity.class));
                break;
            case R.id.btn_4:
                VALUEM_CHOICE1=0.4;
                VALUEM_CHOICE=String.valueOf(0.4);
                PRICE=String.valueOf(240);
                startActivity(new Intent(ValuemActivity.this, StrengthActivity.class));
                break;

        }
    }
}
