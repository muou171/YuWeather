package com.yu.yuweather.global;

import android.app.Application;

import com.yu.yuweather.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class YuWeather extends Application {

    private List<BaseActivity> activityList;


    private static YuWeather instance;
    public static YuWeather getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        instance = this;
        activityList = new ArrayList<>();
    }

    public void addActivity(BaseActivity activity){activityList.add(activity);}
    public void removeActivity(BaseActivity activity){activityList.remove(activity);}
}
