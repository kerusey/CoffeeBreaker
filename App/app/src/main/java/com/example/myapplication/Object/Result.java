package com.example.myapplication.Object;

import android.content.Context;
import android.content.SharedPreferences;

public class Result {

    private int shugar, strenght, volume, MachineID;
    private String type;
    private boolean milk;

    public Result(Context context, int MachineID, String type, int shugar, boolean milk, int volume, int strenght) {
        SharedPreferences answer = context.getSharedPreferences("answer", Context.MODE_PRIVATE);  //Выбираем файл реестра
        this.MachineID = MachineID;
        this.type = answer.getString("coffeeType", "espresso");
        this.strenght = answer.getInt("skbValue", 0);
        this.volume = (int) answer.getFloat("volume", 0);
        this.milk = answer.getBoolean("milk", false);
        this.shugar = answer.getInt("sugar", 0);
    }

}
