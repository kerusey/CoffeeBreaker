package com.example.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class Valuem2Activity extends AppCompatActivity implements View.OnClickListener {
Button btn_22, btn_42;
    SharedPreferences.Editor editor;  //Выделение памяти под редактор настроек
    SharedPreferences answer;
    //TODO он скажет
    @Override
    protected void onStart() {
        super.onStart();
        answer  = getSharedPreferences("answer", Context.MODE_PRIVATE);}
//public static Double VALUEM_CHOICE1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valuem2);

        btn_22 = findViewById(R.id.btn_22);
        btn_22.setOnClickListener(Valuem2Activity.this);

        btn_42 = findViewById(R.id.btn_42);
        btn_42.setOnClickListener(Valuem2Activity.this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_22:
                editor = answer.edit();               //Включаем режим редактирования файла
                editor.putInt("price", 120);
                editor.putInt("volume", 2);
                editor.putString("valuem","0.2л");//Устанавливаем переменной btnValue значения true
                editor.apply();
                startActivity(new Intent(Valuem2Activity.this, ResultActivity.class));
                break;
            case R.id.btn_42:
                editor = answer.edit();               //Включаем режим редактирования файла
                editor.putInt("price", 240);
                editor.putInt("volume", 3);
                editor.putString("valuem","0.4л");//Устанавливаем переменной btnValue значения true
                editor.apply();

                startActivity(new Intent(Valuem2Activity.this, ResultActivity.class));
                break;

        }
    }
}
