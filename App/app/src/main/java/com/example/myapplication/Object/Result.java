package com.example.myapplication.Object;

import android.content.Context;

public class Result {

    private int sugar, strength, machineId;
    float volume;
    private String coffeeType;
    private boolean milk;

    public Result(Context context, int machineId, String coffeeType, int sugar, boolean milk, float volume, int strength) {
        // SharedPreferences answer = context.getSharedPreferences("answer", Context.MODE_PRIVATE);  //Выбираем файл реестра
        this.machineId = machineId;
        this.coffeeType = coffeeType;
        this.strength = strength;
        this.volume = volume;
        this.milk = milk;
        this.sugar = sugar;
//        this.coffeeType = answer.getString("coffeeType", "espresso");
//        this.strenght = answer.getInt("skbValue", 0);
//        this.volume = (int) answer.getFloat("volume", 0);
//        this.milk = answer.getBoolean("milk", false);
//        this.sugar = answer.getInt("sugar", 0);
    }

}
