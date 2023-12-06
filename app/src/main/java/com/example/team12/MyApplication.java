package com.example.team12;

import com.example.team12.components.preference.DataLocalManager;

public class MyApplication extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
