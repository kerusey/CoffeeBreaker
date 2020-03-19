package com.example.myapplication.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.Random;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_accept, btn_m, btn_s, btn_v, btn_st;
    TextView txt_sugar, txt_milk, txt_volume, txt_strength, txt_type, txt_price;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        SharedPreferences answer = getSharedPreferences("answer", Context.MODE_PRIVATE);

        btn_accept = findViewById(R.id.btn_accept);
        btn_m = findViewById(R.id.btn_m);
        btn_s = findViewById(R.id.btn_s);
        btn_v = findViewById(R.id.btn_v);
        btn_st = findViewById(R.id.btn_st);
        txt_sugar = findViewById(R.id.txt_sugar);

        btn_accept.setOnClickListener(ResultActivity.this);
        btn_m.setOnClickListener(ResultActivity.this);
        btn_s.setOnClickListener(ResultActivity.this);
        btn_v.setOnClickListener(ResultActivity.this);
        btn_st.setOnClickListener(ResultActivity.this);

        txt_sugar.setText(String.format("%d", answer.getInt("sugar", -1)));

        txt_milk = findViewById(R.id.txt_milk);
        if (!answer.getBoolean("milk", true))
            txt_milk.setText("Нет");
        else
            txt_milk.setText("Да");

        txt_volume = findViewById(R.id.txt_volume);
        if (answer.getFloat("volume", -0F) != 0.2F)
            txt_volume.setText("0.4 Л");
        else
            txt_volume.setText("0.2 Л");

        txt_strength = findViewById(R.id.txt_strength);
        if (answer.getInt("strength", -1) <= 0)
            txt_strength.setText("0");
        else
            txt_strength.setText(String.format("%d", answer.getInt("strength", -1)));

        txt_type = findViewById(R.id.txt_coffee_type);
        txt_type.setText(String.format("%s", answer.getString("coffeeType", null)));

        txt_price = findViewById(R.id.txt_price);
        txt_price.setText((new Random().nextInt(999) + 1) + "\u20bd");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_accept:
                startActivity(new Intent(ResultActivity.this, OkayActivity.class));
                break;
            case R.id.btn_s:
                startActivity(new Intent(ResultActivity.this, SugarActivity.class).putExtra("editMode", true));
                break;
            case R.id.btn_m:
                startActivity(new Intent(ResultActivity.this, MilkActivity.class).putExtra("editMode", true));
                break;
            case R.id.btn_v:
                startActivity(new Intent(ResultActivity.this, VolumeActivity.class).putExtra("editMode", true));
                break;
            case R.id.btn_st:
                startActivity(new Intent(ResultActivity.this, StrengthActivity.class));
                break;
        }
    }


}
