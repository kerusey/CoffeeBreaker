package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activities.ConfigManager;
import com.example.myapplication.Activities.MenuActivity;


public class MainActivity extends AppCompatActivity {
    ConfigManager cfgManager;
    ImageButton btn_login;
    SharedPreferences.Editor editor;  //Выделение памяти под редактор настроек
    SharedPreferences answer;
    @Override
    protected void onStart() {
        super.onStart();


        answer  = getSharedPreferences("answer", Context.MODE_PRIVATE); //Выбираем файл реестра
        ConfigManager.permissionCheck(this);
        editor = answer.edit();  //Включаем режим редактирования файла
        editor.clear();          //Чистим реестар
        editor.apply();          //Сохроняем настройки
    }


    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, MenuActivity.class));

           }
       });
    }}
