package com.example.myapplication.Object;

import android.content.Context;
import android.content.SharedPreferences;

public class Result {

    private int id;
    private String choice, milk;
    private float volume;
    private int sugar, strenght, price;

    public Result(Context context, int id, String choice, int sugar, String milk, float volume, int price, int strenght) {
        SharedPreferences answer = context.getSharedPreferences("answer", Context.MODE_PRIVATE);  //Выбираем файл реестра
        this.id = id;
        this.choice = answer.getString("сoffeeType", null);
        this.sugar = answer.getInt("sugar", -1);
        this.milk = answer.getString("milk", null);
        this.volume = answer.getFloat("valuem", 0F);
        this.price = answer.getInt("price", 0);
        this.strenght = answer.getInt("skbValue", -1);
    }

}
