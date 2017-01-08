package com.yu.yuweather.view.activity.widget;

import android.content.Intent;

import com.yu.yuweather.service.widget.WidgetDayService;

public class ChooseCityToWidgetDayActivity extends BaseChooseCityToAppWidgetActivity {
    @Override
    protected Intent GetAppWidgetIntent() {
        return new Intent(this, WidgetDayService.class);
    }
}
