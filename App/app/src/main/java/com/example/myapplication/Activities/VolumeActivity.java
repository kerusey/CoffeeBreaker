package com.example.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class VolumeActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_2, btn_4;
    SharedPreferences answer;
    Bundle bundle;

    @Override
    protected void onStart() {
        super.onStart();
        answer = getSharedPreferences("answer", Context.MODE_PRIVATE);
        bundle = getIntent().getExtras();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);

        btn_2 = findViewById(R.id.btn_2);
        btn_4 = findViewById(R.id.btn_4);

        btn_2.setOnClickListener(this);
        btn_4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = answer.edit();
        switch (v.getId()) {
            case R.id.btn_2:
                editor.putFloat("volume", 0.2F);//Включаем режим редактирования файла
                break;
            case R.id.btn_4:
                editor.putFloat("volume", 0.4F);//Включаем режим редактирования файла
                break;
        }
        editor.apply();
        if (!bundle.getBoolean("editMode"))
            startActivity(new Intent(VolumeActivity.this, StrengthActivity.class).putExtra("editMode", false));
        else {
            startActivity(new Intent(VolumeActivity.this, ResultActivity.class));
            finish();
        }
    }
}
