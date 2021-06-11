package com.example.projectfinal.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Helper {
    protected final static int DEFAULT = 0;
    int temp = 0;

    public int readSharedPreference(Context context, String spName,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return temp = sharedPreferences.getInt(key,DEFAULT);
    }

    public void writeSharedPreference(Context context,String ammount,String spName,String key ){

        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, DEFAULT);
        editor.commit();
    }
}
