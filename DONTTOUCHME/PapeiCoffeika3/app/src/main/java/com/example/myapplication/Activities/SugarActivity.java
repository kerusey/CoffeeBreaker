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
    SharedPreferences.Editor editor;  //Выделение памяти под редактор настроек
    SharedPreferences answer;
    //TODO он скажет
    @Override
    protected void onStart() {
        super.onStart();
        answer  = getSharedPreferences("answer", Context.MODE_PRIVATE); //Выбираем файл реестра


    }
//public static String SUGAR_QTY;
//public static int SUGAR_QTY1;
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
                editor = answer.edit();               //Включаем режим редактирования файла
                editor.putInt("shugar", 0);  //Устанавливаем переменной btnValue значения true
                editor.apply();
                startActivity(new Intent(SugarActivity.this, MilkActivity.class));
                break;
            case R.id.btn_1:
                editor = answer.edit();               //Включаем режим редактирования файла
                editor.putInt("shugar", 1);  //Устанавливаем переменной btnValue значения true
                editor.apply();
                startActivity(new Intent(SugarActivity.this, MilkActivity.class));
                break;
            case R.id.btn_2:
                editor = answer.edit();               //Включаем режим редактирования файла
                editor.putInt("shugar", 2);  //Устанавливаем переменной btnValue значения true
                editor.apply();
                startActivity(new Intent(SugarActivity.this, MilkActivity.class));
                break;
            case R.id.btn_3:
                editor = answer.edit();               //Включаем режим редактирования файла
                editor.putInt("shugar", 3);  //Устанавливаем переменной btnValue значения true
                editor.apply();
                startActivity(new Intent(SugarActivity.this, MilkActivity.class));
                break;
            case R.id.btn_4:
                editor = answer.edit();               //Включаем режим редактирования файла
                editor.putInt("shugar", 4);  //Устанавливаем переменной btnValue значения true
                editor.apply();
                startActivity(new Intent(SugarActivity.this, MilkActivity.class));
                break;
        }


    }


}