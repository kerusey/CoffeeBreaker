package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class ChoiceActivity extends AppCompatActivity implements View.OnClickListener {
Button btn_latte_m, btn_latte, btn_capuccino, btn_americano, btn_expresso;
public static String TYPE_CHOICE;
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
        switch (v.getId()){
            case R.id.btn_latte_m:
                TYPE_CHOICE="Латте Макиато";
                startActivity(new Intent(ChoiceActivity.this, SugarActivity.class));
                break;
            case R.id.btn_latte:
                TYPE_CHOICE="Латте";
                startActivity(new Intent(ChoiceActivity.this, SugarActivity.class));
                break;
            case R.id.btn_capuccino:
                TYPE_CHOICE="Капучино";
                startActivity(new Intent(ChoiceActivity.this, SugarActivity.class));
                break;
            case R.id.btn_americano:
                TYPE_CHOICE="Американо";
                startActivity(new Intent(ChoiceActivity.this, SugarActivity.class));
                break;
            case R.id.btn_expresso:
                TYPE_CHOICE="Экспрессо";
                startActivity(new Intent(ChoiceActivity.this, SugarActivity.class));
                break;
        }

    }
}
