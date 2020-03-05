package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import static com.example.myapplication.Activities.MilkActivity.MILK_CHOICE1;

import com.example.myapplication.R;

public class Milk2Activity extends AppCompatActivity implements View.OnClickListener {

    Button btn_yes1, btn_no1;
    public static String MILK_CHOICE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_);

        btn_yes1 = findViewById(R.id.btn_yes1);
        btn_yes1.setOnClickListener(Milk2Activity.this);

        btn_no1 = findViewById(R.id.btn_no1);
        btn_no1.setOnClickListener(Milk2Activity.this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes1:
                MILK_CHOICE ="Да";
                MILK_CHOICE1 =true;

                startActivity(new Intent(Milk2Activity.this, ResultActivity.class));
                break;
            case R.id.btn_no1:
                MILK_CHOICE="Нет";
                MILK_CHOICE1=false;
                startActivity(new Intent(Milk2Activity.this,ResultActivity.class));
                break;

        }

    }
}
