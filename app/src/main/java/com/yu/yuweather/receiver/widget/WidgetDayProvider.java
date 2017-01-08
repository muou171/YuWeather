package com.yu.yuweather.receiver.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.service.widget.WidgetDayService;

public class WidgetDayProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Intent intent = new Intent(context, WidgetDayService.class);
        context.startService(intent);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        YuWeatherDB yuWeatherDB = YuWeatherDB.getInstance(context);
        yuWeatherDB.deleteItemsFromWidgetDay(appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        context.stopService(new Intent(context, WidgetDayService.class));
    }
}
