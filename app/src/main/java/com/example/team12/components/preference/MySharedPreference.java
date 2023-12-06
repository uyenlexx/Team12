package com.example.team12.components.preference;

import android.content.Context;

public class MySharedPreference {
    public static final String REFERENCE_USER = "SECRET_CURRENT_USER";
    private Context context;

    public MySharedPreference(Context mContext) {
        this.context = mContext;
    }

    public void putIntegerValue(String key, int value) {
        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences(REFERENCE_USER, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getIntegerValue(String key) {
        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences(REFERENCE_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, -1);
    }
}
