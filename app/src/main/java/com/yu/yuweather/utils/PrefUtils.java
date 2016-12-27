package com.yu.yuweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yu.yuweather.global.DataName;

public class PrefUtils {
    public static long getLong(Context context, String key, Long defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DataName.CONFIG, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defValue);
    }

    public static void setLong(Context context, String key, Long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DataName.CONFIG, Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(key, value).commit();
    }
}
