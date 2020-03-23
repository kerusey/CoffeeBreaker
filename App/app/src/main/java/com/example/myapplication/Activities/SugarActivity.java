package com.example.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class SugarActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_0, btn_1, btn_2, btn_3, btn_4;
    SharedPreferences answer;
    Bundle bundle;

    @Override
    protected void onStart() {
        super.onStart();
        answer = getSharedPreferences("answer", Context.MODE_PRIVATE); //Выбираем файл реестра, и говорим о том, что доступ есть только у нашей программы
        bundle = getIntent().getExtras();//объявляем намерение на чтение данных из контеёнера данных
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
                Toast.makeText(SugarActivity.this, "Выбрали кофе без сахара", Toast.LENGTH_SHORT).show();
                editor.putInt("sugar", 0);  //Устанавливаем переменной btnValue значения true
                break;
            case R.id.btn_1:
                Toast.makeText(SugarActivity.this, "Выбрали кофе с 1 лож.сахара", Toast.LENGTH_SHORT).show();
                editor.putInt("sugar", 1);  //Устанавливаем переменной btnValue значения true
                break;
            case R.id.btn_2:
                Toast.makeText(SugarActivity.this, "Выбрали кофе с 2 лож.сахара", Toast.LENGTH_SHORT).show();
                editor.putInt("sugar", 2);  //Устанавливаем переменной btnValue значения true
                break;
            case R.id.btn_3:
                Toast.makeText(SugarActivity.this, "Выбрали кофе с 3 лож.сахара", Toast.LENGTH_SHORT).show();
                editor.putInt("sugar", 3);  //Устанавливаем переменной btnValue значения true
                break;
            case R.id.btn_4:
                Toast.makeText(SugarActivity.this, "Выбрали кофе с 4 лож.сахара", Toast.LENGTH_SHORT).show();
                editor.putInt("sugar", 4);  //Устанавливаем переменной btnValue значения true
                break;
        }
        editor.apply();// применить измиенения и закрытт редактор
        if (!bundle.getBoolean("editMode"))//editMode- переменная, которая заполняется в странице результатов, по умолчанию это false
            startActivity(new Intent(SugarActivity.this, MilkActivity.class).putExtra("editMode", false));//переходим в следущую активити
        else {
            startActivity(new Intent(SugarActivity.this, ResultActivity.class));//возвращаемся обратно на страницу резельтатов
            finish();//стирание страницы
        }
    }
}