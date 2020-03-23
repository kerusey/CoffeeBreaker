package com.example.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class MilkActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_yes, btn_no;
    Bundle bundle;
    SharedPreferences answer;

    //17 24 51
    @Override
    protected void onStart() {
        super.onStart();
        answer = getSharedPreferences("answer", Context.MODE_PRIVATE);
        bundle = getIntent().getExtras();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk);

        btn_yes = findViewById(R.id.btn_yes);
        btn_no = findViewById(R.id.btn_no);

        btn_yes.setOnClickListener(this);
        btn_no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = answer.edit();
        switch (v.getId()) {
            case R.id.btn_yes:
                editor.putBoolean("milk", true);
                Toast.makeText(MilkActivity.this, "Выбрали кофе с молоком", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_no:
                editor.putBoolean("milk", false);
                Toast.makeText(MilkActivity.this, "Выбрали кофе без молока ", Toast.LENGTH_SHORT).show();
                break;
        }
        editor.apply();
        if (!bundle.getBoolean("editMode")) {
            startActivity(new Intent(MilkActivity.this, VolumeActivity.class).putExtra("editMode", false));
        } else {
            startActivity(new Intent(MilkActivity.this, ResultActivity.class));
            finish();
        }
    }
}
