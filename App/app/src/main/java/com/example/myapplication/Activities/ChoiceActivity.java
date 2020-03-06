package com.example.myapplication.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ChoiceActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    Button btn_latte_m, btn_latte, btn_cappuccino, btn_americano, btn_espresso;
    SharedPreferences answer;

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
        btn_latte = findViewById(R.id.btn_latte);
        btn_cappuccino = findViewById(R.id.btn_cappuccino);
        btn_americano = findViewById(R.id.btn_americano);
        btn_espresso = findViewById(R.id.btn_espresso);

        btn_latte_m.setOnClickListener(this);
        btn_latte.setOnClickListener(this);
        btn_cappuccino.setOnClickListener(this);
        btn_americano.setOnClickListener(this);
        btn_espresso.setOnClickListener(this);

        btn_latte_m.setOnLongClickListener(this);
        btn_latte.setOnLongClickListener(this);
        btn_cappuccino.setOnLongClickListener(this);
        btn_americano.setOnLongClickListener(this);
        btn_espresso.setOnLongClickListener(this);

    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = answer.edit();
        switch (v.getId()) {
            case R.id.btn_latte_m:
                editor.putString("сoffeeType", "Латте макиато");
                break;
            case R.id.btn_latte:
                editor.putString("сoffeeType", "Латте");
                break;
            case R.id.btn_cappuccino:
                editor.putString("сoffeeType", "Капучино");
                break;
            case R.id.btn_americano:
                editor.putString("сoffeeType", "Американо");
                break;
            case R.id.btn_espresso:
                editor.putString("сoffeeType", "Эспрессо");
                break;
        }
        editor.apply();
        startActivity(new Intent(ChoiceActivity.this, SugarActivity.class).putExtra("editMode", false));
    }

    @Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (v.getId()) {
            case R.id.btn_latte_m:
                builder.setTitle("Латте макиато").setMessage("Ла́тте макиа́то (итал. Latte macchiato ['latːe maˈkːjaˑto] — «запятнанное молоко») — горячий кофейный напиток, приготавливаемый путём вливания в молоко кофе-эспрессо в пропорции 3:1. Итальянское macchia обозначает маленькое пятнышко кофе, остающееся на поверхности молочной пены.");
                break;
            case R.id.btn_latte:
                builder.setTitle("Латте").setMessage("Ла́тте (англизированный и сокращенный вариант от итал. caffè latte [kaffeˈlatte] — «кофе с молоком») — кофейный напиток родом из Италии, состоящий из молока (итал. latte) и кофе эспрессо. Варится на основе молока, образуя в чашке или бокале трёхслойную смесь из кофе, молока и пены.");
                break;
            case R.id.btn_cappuccino:
                builder.setTitle("Капучино").setMessage("Капучи́но (от итал. cappuccino — капуцин) — кофейный напиток итальянской кухни на основе эспрессо с добавлением в него подогретого вспененного молока.");
                break;
            case R.id.btn_americano:
                builder.setTitle("Американо").setMessage("Америка́но (итал. Caffè Americano — кофе по-американски) — способ приготовления кофе, заключающийся в соединении определённого количества горячей воды и эспрессо.\n" +
                        "\n" +
                        "Напиток придумали в Италии ещё во время Второй мировой войны для американцев как аналог американского популярного фильтрового напитка «регуляр». Общим у этих двух напитков были лишь большой объём и не очень концентрированный вкус.");
                break;
            case R.id.btn_espresso:
                builder.setTitle("Эспрессо").setMessage("Эспре́ссо (от итал. espresso) — метод приготовления кофе путём прохождения горячей воды (около 90 °C) под давлением 9 бар через фильтр с молотым кофе.\n" +
                        "\n" +
                        "Эспрессо пользуется большой популярностью во всём мире и, прежде всего, на юге Европы — в Италии, Испании и Португалии.\n" +
                        "\n" +
                        "В России слово «эспрессо» начинает использоваться с начала 1990-х годов, вытеснив название «маленький двойной», использовавшееся ранее.");
                break;
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return false;
    }
}
