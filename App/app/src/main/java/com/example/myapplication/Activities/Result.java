package com.example.myapplication.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class Result extends AppCompatActivity {

    int id;
    String choice,  milk;
    float volume;
    int sugar, strenght, price;
    SharedPreferences answer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
         answer = getSharedPreferences("answer", Context.MODE_PRIVATE);  //Выбираем файл реестра
    }

    public Result(int id, String choice, int sugar, String milk, float volume, int price, int strenght){
        this.id = id;
        this.choice = answer.getString("сoffeeType", null);
        this.sugar = answer.getInt("sugar", -1);
        this.milk = answer.getString("milk", null);
        this.volume = answer.getFloat("valuem", 0F);
        this.price = answer.getInt("price", 0);
        this.strenght = answer.getInt("skbValue",-1 );
    }

}
