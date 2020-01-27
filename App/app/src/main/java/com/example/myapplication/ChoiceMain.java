//package com.example.myapplication;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//public class ChoiceMain extends AppCompatActivity implements View.OnClickListener {
//Button btn_latte_m, btn_latte, btn_capuccino, btn_americano, btn_expresso;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_choice_main);
//
//        btn_latte_m = findViewById(R.id.btn_latte_m);
//        btn_latte_m.setOnClickListener(ChoiceMain.this);
//
//        btn_latte = findViewById(R.id.btn_latte);
//        btn_latte.setOnClickListener(ChoiceMain.this);
//
//        btn_capuccino = findViewById(R.id.btn_capuccino);
//        btn_capuccino.setOnClickListener(ChoiceMain.this);
//
//        btn_americano = findViewById(R.id.btn_americano);
//        btn_americano.setOnClickListener(ChoiceMain.this);
//
//        btn_expresso = findViewById(R.id.btn_expresso);
//        btn_expresso.setOnClickListener(ChoiceMain.this);
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btn_latte_m:
//                startActivity(new Intent(ChoiceMain.this, SugarActivity.class));
//                break;
//            case R.id.btn_latte:
//                startActivity(new Intent(ChoiceMain.this, SugarActivity.class));
//                break;
//            case R.id.btn_capuccino:
//                startActivity(new Intent(ChoiceMain.this, SugarActivity.class));
//                break;
//            case R.id.btn_americano:
//                startActivity(new Intent(ChoiceMain.this, SugarActivity.class));
//                break;
//            case R.id.btn_expresso:
//                startActivity(new Intent(ChoiceMain.this, SugarActivity.class));
//                break;
//        }
//
//    }
//}
