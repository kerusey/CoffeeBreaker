package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import static com.example.myapplication.Activities.ValuemActivity.PRICE;
import static com.example.myapplication.Activities.ValuemActivity.VALUEM_CHOICE;
import static com.example.myapplication.Activities.ValuemActivity.VALUEM_CHOICE1;
import com.example.myapplication.R;

public class Valuem2Activity extends AppCompatActivity implements View.OnClickListener {
Button btn_22, btn_42;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valuem2);

        btn_22 = findViewById(R.id.btn_22);
        btn_22.setOnClickListener(Valuem2Activity.this);

        btn_42 = findViewById(R.id.btn_42);
        btn_42.setOnClickListener(Valuem2Activity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_2:
                VALUEM_CHOICE=String.valueOf(0.2);
                VALUEM_CHOICE1=0.2;
                PRICE=String.valueOf(120);
                startActivity(new Intent(Valuem2Activity.this, ResultActivity.class));
                break;
            case R.id.btn_4:
                VALUEM_CHOICE1=0.4;
                VALUEM_CHOICE=String.valueOf(0.4);
                PRICE=String.valueOf(240);
                startActivity(new Intent(Valuem2Activity.this, ResultActivity.class));
                break;

        }
    }
}
