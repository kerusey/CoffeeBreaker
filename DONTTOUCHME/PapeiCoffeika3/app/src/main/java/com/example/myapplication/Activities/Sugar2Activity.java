package com.example.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class Sugar2Activity extends AppCompatActivity implements View.OnClickListener {

    Button btn_01, btn_11, btn_21, btn_31, btn_41;
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
        setContentView(R.layout.activity_sugar2);

        btn_01 = findViewById(R.id.btn_01);
        btn_01.setOnClickListener(this);

        btn_11 = findViewById(R.id.btn_11);
        btn_11.setOnClickListener(this);

        btn_21 = findViewById(R.id.btn_21);
        btn_21.setOnClickListener(this);

        btn_31 = findViewById(R.id.btn_31);
        btn_31.setOnClickListener(this);

        btn_41 = findViewById(R.id.btn_41);
        btn_41.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_01:
                editor = answer.edit();               //Включаем режим редактирования файла
                editor.putInt("shugar", 0);  //Устанавливаем переменной btnValue значения true
                editor.apply();
                startActivity(new Intent(Sugar2Activity.this, ResultActivity.class));
                break;
            case R.id.btn_11:
                editor = answer.edit();               //Включаем режим редактирования файла
                editor.putInt("shugar", 1);  //Устанавливаем переменной btnValue значения true
                editor.apply();
                startActivity(new Intent(Sugar2Activity.this, ResultActivity.class));
                break;
            case R.id.btn_21:
                editor = answer.edit();               //Включаем режим редактирования файла
                editor.putInt("shugar", 2);  //Устанавливаем переменной btnValue значения true
                editor.apply();
                startActivity(new Intent(Sugar2Activity.this, ResultActivity.class));
                break;
            case R.id.btn_31:
                editor = answer.edit();               //Включаем режим редактирования файла
                editor.putInt("shugar", 3);  //Устанавливаем переменной btnValue значения true
                editor.apply();
                startActivity(new Intent(Sugar2Activity.this, ResultActivity.class));
                break;
            case R.id.btn_41:
                editor = answer.edit();               //Включаем режим редактирования файла
                editor.putInt("shugar", 4);  //Устанавливаем переменной btnValue значения true
                editor.apply();
                startActivity(new Intent(Sugar2Activity.this, ResultActivity.class));
                break;
        }


    }
}
