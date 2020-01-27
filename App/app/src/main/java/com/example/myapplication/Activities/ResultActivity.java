package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

import static com.example.myapplication.Activities.ChoiceActivity.TYPE_CHOICE;
import static com.example.myapplication.Activities.MilkActivity.MILK_CHOICE;
import static com.example.myapplication.Activities.MilkActivity.MILK_CHOICE1;
import static com.example.myapplication.Activities.StrengthActivity.STRENGTH_CHOICE;
import static com.example.myapplication.Activities.StrengthActivity.STRENGTH_CHOICE1;
import static com.example.myapplication.Activities.SugarActivity.SUGAR_QTY;
import static com.example.myapplication.Activities.SugarActivity.SUGAR_QTY1;
import static com.example.myapplication.Activities.ValuemActivity.PRICE;
import static com.example.myapplication.Activities.ValuemActivity.VALUEM_CHOICE;
import static com.example.myapplication.Activities.ValuemActivity.VALUEM_CHOICE1;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_accept, btn_m, btn_s, btn_v, btn_st;
    public static int shugar;
    public static boolean milk;
    public static double volume;
    public static int strength;
    public static String napitok;


    TextView txt_sugar, txt_milk, txt_valuem, txt_strength, txt_type, txt_price, txt_napitok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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
        txt_sugar.setText(SUGAR_QTY);
        shugar=SUGAR_QTY1;

        txt_milk = findViewById(R.id.txt_milk);
        txt_milk.setText(MILK_CHOICE);
        milk=MILK_CHOICE1;

        txt_valuem = findViewById(R.id.txt_valuem);
        txt_valuem.setText(VALUEM_CHOICE);
        volume =VALUEM_CHOICE1;

        txt_strength = findViewById(R.id.txt_strength);
        txt_strength.setText(STRENGTH_CHOICE);
        strength=STRENGTH_CHOICE1;

        txt_type = findViewById(R.id.txt_type);
        txt_type.setText(TYPE_CHOICE);
        napitok=TYPE_CHOICE;

        txt_price = findViewById(R.id.txt_price);
        txt_price.setText(PRICE);

        txt_napitok= findViewById(R.id.txt_napitok);
        txt_napitok.setText(napitok);



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
