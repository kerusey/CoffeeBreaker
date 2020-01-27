package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import static com.example.myapplication.Activities.ChoiceActivity.TYPE_CHOICE;
import static com.example.myapplication.Activities.MilkActivity.MILK_CHOICE;
import static com.example.myapplication.Activities.StrengthActivity.STRENGTH_CHOICE;
import static com.example.myapplication.Activities.SugarActivity.SUGAR_QTY;
import static com.example.myapplication.Activities.ValuemActivity.PRICE;
import static com.example.myapplication.Activities.ValuemActivity.VALUEM_CHOICE;

public class ResultActivity extends AppCompatActivity {
    Button btn_accept;

    TextView txt_sugar, txt_milk, txt_valuem, txt_strength, txt_type, txt_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        btn_accept = findViewById(R.id.btn_accept);
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ResultActivity.this, OkayActivity.class));

            }
        });
        txt_sugar = findViewById(R.id.txt_sugar);
        txt_sugar.setText(SUGAR_QTY);

        txt_milk = findViewById(R.id.txt_milk);
        txt_milk.setText(MILK_CHOICE);

        txt_valuem = findViewById(R.id.txt_valuem);
        txt_valuem.setText(VALUEM_CHOICE);

        txt_strength = findViewById(R.id.txt_strength);
        txt_strength.setText(STRENGTH_CHOICE);

        txt_type = findViewById(R.id.txt_type);
        txt_type.setText(TYPE_CHOICE);

        txt_price = findViewById(R.id.txt_price);
        txt_price.setText(PRICE);

    }

}
