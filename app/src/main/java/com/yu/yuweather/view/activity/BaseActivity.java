package com.yu.yuweather.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yu.yuweather.global.DataName;
import com.yu.yuweather.global.YuWeather;
import com.yu.yuweather.utils.PrefUtils;

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YuWeather.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        YuWeather.getInstance().removeActivity(this);
        PrefUtils.setLong(this, DataName.LAST_TIME, System.currentTimeMillis());
    }
}
