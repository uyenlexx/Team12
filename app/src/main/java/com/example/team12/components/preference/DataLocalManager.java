package com.example.team12.components.preference;

import android.content.Context;

public class DataLocalManager {
    private static final String USER_LOGGED_IN = "USER_LOGGED_IN";
    private static DataLocalManager instance;
    private static MySharedPreference mySharedPreferences;

    public static void init(Context context) {
        instance = new DataLocalManager();
        mySharedPreferences = new MySharedPreference(context);
    }

    public static DataLocalManager getInstance() {
        if (instance == null) {
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setUserLoggedIn(int userLoggedIn) {
        DataLocalManager.getInstance().mySharedPreferences.putIntegerValue(USER_LOGGED_IN, userLoggedIn);
    }

    public static int getUserLoggedIn() {
        return DataLocalManager.getInstance().mySharedPreferences.getIntegerValue(USER_LOGGED_IN);
    }
}
