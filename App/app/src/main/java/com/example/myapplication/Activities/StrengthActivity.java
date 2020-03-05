package com.example.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class StrengthActivity extends AppCompatActivity {


    Button btn_strong;

    SeekBar skb_straight;             //Выделение памяти под SeekBar
    SharedPreferences.Editor editor;  //Выделение памяти под редактор настроек
    SharedPreferences answer;         //Выделение памяти под реестр настроек

    @Override
    protected void onStart() {
        super.onStart();
        answer  = getSharedPreferences("answer", Context.MODE_PRIVATE); //Выбираем файл реестра

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength);


        btn_strong = findViewById(R.id.btn_strong);
        btn_strong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(StrengthActivity.this, ResultActivity.class));

            }
        });
        skb_straight = findViewById(R.id.skb_straight);

            /*  Устанавливаем слушатель для SeekBar  */
    skb_straight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {  //Получаем информацию после каждого пекредкижения

                    editor = answer.edit();               //Включаем режим редактирования файла
                    editor.putInt("strenght", progress);  //Устанавливаем переменной skbValue значения получаемое с SeekBar
                    editor.apply();                       //Сохроняем настройки

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });


    }

    }



