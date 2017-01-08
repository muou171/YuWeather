package com.yu.yuweather.global;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

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
        // 设置默认字体配置
        Resources resources = getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public void addActivity(BaseActivity activity) {
        activityList.add(activity);
    }

    public void removeActivity(BaseActivity activity) {
        activityList.remove(activity);
    }

    public void finishAllActivity(){
        for (BaseActivity baseActivity : activityList) {
            baseActivity.finish();
        }
    }
}
