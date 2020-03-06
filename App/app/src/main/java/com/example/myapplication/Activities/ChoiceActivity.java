package com.example.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ChoiceActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_latte_m, btn_latte, btn_capuccino, btn_americano, btn_expresso;
    //TODO он скажет
    SharedPreferences answer;
    SharedPreferences.Editor editor;

    //public static String TYPE_CHOICE;
    @Override
    protected void onStart() {
        super.onStart();
        answer = getSharedPreferences("answer", Context.MODE_PRIVATE); //Выбираем файл реестра

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_main);

        btn_latte_m = findViewById(R.id.btn_latte_m);
        btn_latte_m.setOnClickListener(ChoiceActivity.this);

        btn_latte = findViewById(R.id.btn_latte);
        btn_latte.setOnClickListener(ChoiceActivity.this);

        btn_capuccino = findViewById(R.id.btn_capuccino);
        btn_capuccino.setOnClickListener(ChoiceActivity.this);

        btn_americano = findViewById(R.id.btn_americano);
        btn_americano.setOnClickListener(ChoiceActivity.this);

        btn_expresso = findViewById(R.id.btn_expresso);
        btn_expresso.setOnClickListener(ChoiceActivity.this);


    }

    //TODO сохранить какой напиток выбрали
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_latte_m:
                editor = answer.edit();
                editor.putString("сoffeeType", "Латте Макиято");
                editor.apply();
                startActivity(new Intent(ChoiceActivity.this, SugarActivity.class));
                break;
            case R.id.btn_latte:
                editor = answer.edit();
                editor.putString("сoffeeType", "Латте");
                editor.apply();
                startActivity(new Intent(ChoiceActivity.this, SugarActivity.class));
                break;
            case R.id.btn_capuccino:
                editor = answer.edit();
                editor.putString("сoffeeType", "Капучино");
                editor.apply();
                startActivity(new Intent(ChoiceActivity.this, SugarActivity.class));
                break;
            case R.id.btn_americano:
                editor = answer.edit();
                editor.putString("сoffeeType", "Американо");
                editor.apply();
                startActivity(new Intent(ChoiceActivity.this, SugarActivity.class));
                break;
            case R.id.btn_expresso:
                editor = answer.edit();
                editor.putString("сoffeeType", "Экспрессо");
                editor.apply();
                startActivity(new Intent(ChoiceActivity.this, SugarActivity.class));
                break;
        }

    }
}
