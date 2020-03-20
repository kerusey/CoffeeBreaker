package com.example.myapplication.Object;

import android.content.Context;
import android.content.SharedPreferences;

public class StatusScan {
    private String status;

    public StatusScan(Context context, String status) {
        SharedPreferences answer = context.getSharedPreferences("answer", Context.MODE_PRIVATE);  //Выбираем файл реестра
        this.status = answer.getString("status", status);

    }
}
