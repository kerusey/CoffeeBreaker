package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class MilkActivity extends AppCompatActivity implements View.OnClickListener {
Button btn_yes, btn_no;

    public static boolean MILK_CHOICE1;

    public static String MILK_CHOICE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_);

        btn_yes = findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(MilkActivity.this);

        btn_no = findViewById(R.id.btn_no);
        btn_no.setOnClickListener(MilkActivity.this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
               MILK_CHOICE ="Да";
                MILK_CHOICE1 =true;


                startActivity(new Intent(MilkActivity.this, ValuemActivity.class));
                break;
            case R.id.btn_no:
                MILK_CHOICE="Нет";

                MILK_CHOICE1=false;
                startActivity(new Intent(MilkActivity.this,ValuemActivity.class));
                break;

        }

    }
}
