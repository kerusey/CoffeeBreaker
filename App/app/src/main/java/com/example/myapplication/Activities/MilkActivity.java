package com.example.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class MilkActivity extends AppCompatActivity implements View.OnClickListener {
Button btn_yes, btn_no;


    SharedPreferences.Editor editor;  //Выделение памяти под редактор настроек
    SharedPreferences answer;

    @Override
    protected void onStart() {
        super.onStart();
        answer  = getSharedPreferences("answer", Context.MODE_PRIVATE);}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_);

        btn_yes = findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(this);

        btn_no = findViewById(R.id.btn_no);
        btn_no.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                editor = answer.edit();               //Включаем режим редактирования файла
                editor.putBoolean("milk", true);
                editor.putString("milkw", "Да");//Устанавливаем переменной skbValue значения получаемое с SeekBar
                editor.apply();

                startActivity(new Intent(MilkActivity.this, ValuemActivity.class));
                break;
            case R.id.btn_no:
                editor = answer.edit();               //Включаем режим редактирования файла
                editor.putBoolean("milk", false);
                editor.putString("milkw", "Нет");//Устанавливаем переменной skbValue значения получаемое с SeekBar
                editor.apply();
                startActivity(new Intent(MilkActivity.this,ValuemActivity.class));
                break;

        }

    }
}
