package com.example.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class SugarActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_0, btn_1, btn_2, btn_3, btn_4;
    SharedPreferences answer;
    Bundle bundle;

    @Override
    protected void onStart() {
        super.onStart();
        answer = getSharedPreferences("answer", Context.MODE_PRIVATE); //Выбираем файл реестра
        bundle = getIntent().getExtras();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar);

        btn_0 = findViewById(R.id.btn_0);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);

        btn_0.setOnClickListener(SugarActivity.this);
        btn_1.setOnClickListener(SugarActivity.this);
        btn_2.setOnClickListener(SugarActivity.this);
        btn_3.setOnClickListener(SugarActivity.this);
        btn_4.setOnClickListener(SugarActivity.this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = answer.edit();  //Включаем режим редактирования файла
        switch (v.getId()) {
            case R.id.btn_0:
                editor.putInt("sugar", 0);  //Устанавливаем переменной btnValue значения true
                break;
            case R.id.btn_1:
                editor.putInt("sugar", 1);  //Устанавливаем переменной btnValue значения true
                break;
            case R.id.btn_2:
                editor.putInt("sugar", 2);  //Устанавливаем переменной btnValue значения true
                break;
            case R.id.btn_3:
                editor.putInt("sugar", 3);  //Устанавливаем переменной btnValue значения true
                break;
            case R.id.btn_4:
                editor.putInt("sugar", 4);  //Устанавливаем переменной btnValue значения true
                break;
        }
        editor.apply();
        if (!bundle.getBoolean("editMode"))
            startActivity(new Intent(SugarActivity.this, MilkActivity.class).putExtra("editMode", false));
        else {
            startActivity(new Intent(SugarActivity.this, ResultActivity.class));
            finish();
        }
    }
}