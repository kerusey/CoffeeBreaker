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

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_accept, btn_m, btn_s, btn_v, btn_st;
    SharedPreferences answer;



    TextView txt_sugar, txt_milk, txt_valuem, txt_strength, txt_type, txt_price;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_result);
        answer = getSharedPreferences("answer", Context.MODE_PRIVATE);

        btn_accept = findViewById(R.id.btn_accept);
        btn_accept.setOnClickListener(ResultActivity.this);

        btn_m = findViewById(R.id.btn_m);
        btn_m.setOnClickListener(ResultActivity.this);

        btn_s = findViewById(R.id.btn_s);
        btn_s.setOnClickListener(ResultActivity.this);

        btn_v = findViewById(R.id.btn_v);
        btn_v.setOnClickListener(ResultActivity.this);


        btn_st = findViewById(R.id.btn_st);
        btn_st.setOnClickListener(ResultActivity.this);

        txt_sugar = findViewById(R.id.txt_sugar);
        txt_sugar.setText(String.format("%d", answer.getInt("shugar", -1)));

        txt_milk = findViewById(R.id.txt_milk);
        txt_milk.setText(String.format("%s", answer.getString("milkw", null)));

        txt_valuem = findViewById(R.id.txt_valuem);
        txt_valuem.setText(String.format("%s", answer.getString("valuem", null)));

        txt_strength = findViewById(R.id.txt_strength);
        txt_strength.setText(String.format("%d", answer.getInt("strenght", -1)));

        txt_type = findViewById(R.id.txt_type);
        txt_type.setText(String.format("%s", answer.getString("сoffeeType", null)));

        txt_price = findViewById(R.id.txt_price);
        txt_price.setText(String.format("%d", answer.getInt("price", -1)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_accept:

                startActivity(new Intent(ResultActivity.this, OkayActivity.class));
                break;
            case R.id.btn_s:

                startActivity(new Intent(ResultActivity.this, Sugar2Activity.class));
                break;
            case R.id.btn_m:

                startActivity(new Intent(ResultActivity.this, Milk2Activity.class));
                break;
            case R.id.btn_v:

                startActivity(new Intent(ResultActivity.this, Valuem2Activity.class));
                break;
            case R.id.btn_st:

                startActivity(new Intent(ResultActivity.this, StrengthActivity.class));
                break;
        }
    }

}
