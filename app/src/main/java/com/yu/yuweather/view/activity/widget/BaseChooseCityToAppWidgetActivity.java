package com.yu.yuweather.view.activity.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yu.yuweather.R;
import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.global.DataName;
import com.yu.yuweather.global.YuWeather;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.view.adapter.ChooseCityToAppWidgetAdapter;

import java.util.List;

public abstract class BaseChooseCityToAppWidgetActivity extends AppCompatActivity {
    private RecyclerView rvChooseCityToAppWidget;
    private YuWeatherDB yuWeatherDB;
    private List<Now.BasicBean> basicBeanList;
    private ChooseCityToAppWidgetAdapter chooseCityToAppWidgetAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city_to_app_widget);
        initView();
        initData();
        initUI();
        initListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void initView() {
        rvChooseCityToAppWidget = (RecyclerView) findViewById(R.id.rv_choose_city_to_app_widget);
    }

    private void initData() {
        yuWeatherDB = YuWeatherDB.getInstance(this);
        basicBeanList = yuWeatherDB.loadAllBasic();
    }

    private void initUI() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvChooseCityToAppWidget.setLayoutManager(linearLayoutManager);
        chooseCityToAppWidgetAdapter = new ChooseCityToAppWidgetAdapter(basicBeanList, yuWeatherDB, this);
        rvChooseCityToAppWidget.setAdapter(chooseCityToAppWidgetAdapter);
    }

    private void initListener() {
        chooseCityToAppWidgetAdapter.setOnItemClickListener(new ChooseCityToAppWidgetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                int appWidgetId = 0;
                if (extras != null) {
                    appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                }

                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                setResult(RESULT_OK, resultValue);
                if (appWidgetId != 0) {
                    yuWeatherDB.saveWidgetDay(appWidgetId, basicBeanList.get(position).getId());
                } else {
                    int currentAppWidgetId = extras.getInt(DataName.WIDGET_DAY_APP_WIDGET_ID);
                    yuWeatherDB.updateItemFromWidgetDay(currentAppWidgetId, basicBeanList.get(position).getId());
                }

                Intent appWidgetIntent = GetAppWidgetIntent();
                startService(appWidgetIntent);
                YuWeather.getInstance().finishAllActivity();
                finish();
            }
        });
    }

    protected abstract Intent GetAppWidgetIntent();
}
