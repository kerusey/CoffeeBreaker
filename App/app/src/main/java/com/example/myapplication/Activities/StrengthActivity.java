package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.myapplication.R;

public class StrengthActivity extends AppCompatActivity {
    SeekBar seekBar;
    Button btn_strong;
    public static String STRENGTH_CHOICE;
    public static int STRENGTH_CHOICE1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength);

        btn_strong = findViewById(R.id.btn_strong);
        btn_strong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(StrengthActivity.this, ResultActivity.class));

            }
        });

        seekBar= findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged (SeekBar seekBar,int progress,
            boolean fromUser){
                STRENGTH_CHOICE = String.valueOf(seekBar.getProgress());
                STRENGTH_CHOICE1=seekBar.getProgress();
                Toast.makeText(getApplicationContext(), "Степень:" + progress, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStartTrackingTouch (SeekBar seekBar){
                Toast.makeText(getApplicationContext(), "Регулируйте ползунком ", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStopTrackingTouch (SeekBar seekBar){
                Toast.makeText(getApplicationContext(), "Подтвердите!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
