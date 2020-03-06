package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MenuActivity;
import com.example.myapplication.R;

public class OkayActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_return, btn_remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okay);
        btn_return = findViewById(R.id.btn_return);
        btn_return.setOnClickListener(this);
        btn_remember = findViewById(R.id.btn_remember);
        btn_remember.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_return:
                startActivity(new Intent(OkayActivity.this, MenuActivity.class));
                break;
            case R.id.btn_remember:
                startActivity(new Intent(OkayActivity.this, RememberActivity.class));
                break;
        }
    }
}

