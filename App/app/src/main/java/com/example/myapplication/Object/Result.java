package com.example.myapplication.Object;

import android.content.Context;
import android.content.SharedPreferences;

public class Result {

    private int sugar, strenght, volume, MachineId;
    private String coffeeType;
    private boolean milk;

    public Result(Context context, int MachineID, String type, int shugar, boolean milk, int volume, int strenght) {
        SharedPreferences answer = context.getSharedPreferences("answer", Context.MODE_PRIVATE);  //Выбираем файл реестра
        this.MachineId = MachineID;
        this.coffeeType = answer.getString("coffeeType", "espresso");
        this.strenght = answer.getInt("skbValue", 0);
        this.volume = (int) answer.getFloat("volume", 0);
        this.milk = answer.getBoolean("milk", false);
        this.sugar = answer.getInt("sugar", 0);
    }

}
