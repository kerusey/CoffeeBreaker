package com.example.myapplication.Object;

import android.content.Context;
import android.content.SharedPreferences;

public class Token {
    private String token;

    public Token(Context context, String token) {
        SharedPreferences answer = context.getSharedPreferences("answer", Context.MODE_PRIVATE);
        this.token = answer.getString("token", token);
    }
}
