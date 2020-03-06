package com.example.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ValuemActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_2, btn_4;
    SharedPreferences.Editor editor;  //Выделение памяти под редактор настроек
    SharedPreferences answer;

    //TODO он скажет
    @Override
    protected void onStart() {
        super.onStart();
        answer = getSharedPreferences("answer", Context.MODE_PRIVATE);
    }
//public static Double VALUEM_CHOICE1;


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
                editor = answer.edit();
                editor.putInt("volume", 2);//Включаем режим редактирования файла
                editor.putInt("price", 120);
                editor.putString("valuem", "0.2л");//Устанавливаем переменной btnValue значения true
                editor.apply();
                startActivity(new Intent(ValuemActivity.this, StrengthActivity.class));
                break;
            case R.id.btn_4:
                editor = answer.edit();
                editor.putInt("volume", 3);//Включаем режим редактирования файла
                editor.putInt("price", 240);
                editor.putString("valuem", "0.4л");//Устанавливаем переменной btnValue значения true
                editor.apply();
                startActivity(new Intent(ValuemActivity.this, StrengthActivity.class));
                break;

        }
    }
}
