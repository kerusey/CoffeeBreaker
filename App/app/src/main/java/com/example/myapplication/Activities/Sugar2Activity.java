package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import static com.example.myapplication.Activities.SugarActivity.SUGAR_QTY1;
import com.example.myapplication.R;

import javax.xml.transform.Result;

public class Sugar2Activity extends AppCompatActivity implements View.OnClickListener {

    Button btn_01, btn_11, btn_21, btn_31, btn_41;
    public static String SUGAR_QTY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar);

        btn_01 = findViewById(R.id.btn_01);
        btn_01.setOnClickListener(Sugar2Activity.this);

        btn_11 = findViewById(R.id.btn_11);
        btn_11.setOnClickListener(Sugar2Activity.this);

        btn_21 = findViewById(R.id.btn_21);
        btn_21.setOnClickListener(Sugar2Activity.this);

        btn_31 = findViewById(R.id.btn_31);
        btn_31.setOnClickListener(Sugar2Activity.this);

        btn_41 = findViewById(R.id.btn_41);
        btn_41.setOnClickListener(Sugar2Activity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_01:
                SUGAR_QTY = String.valueOf(0);
                SUGAR_QTY1=0;
                startActivity(new Intent(Sugar2Activity.this, ResultActivity.class));
                break;
            case R.id.btn_11:
                SUGAR_QTY = String.valueOf(1);
                SUGAR_QTY1=1;
                startActivity(new Intent(Sugar2Activity.this, ResultActivity.class));
                break;
            case R.id.btn_21:
                SUGAR_QTY1=2;
                SUGAR_QTY = String.valueOf(2);
                startActivity(new Intent(Sugar2Activity.this, ResultActivity.class));
                break;
            case R.id.btn_31:
                SUGAR_QTY1=3;
                SUGAR_QTY = String.valueOf(3);
                startActivity(new Intent(Sugar2Activity.this, ResultActivity.class));
                break;
            case R.id.btn_41:
                SUGAR_QTY1=4;
                SUGAR_QTY = String.valueOf(4);
                startActivity(new Intent(Sugar2Activity.this, ResultActivity.class));
                break;
        }


    }
}
