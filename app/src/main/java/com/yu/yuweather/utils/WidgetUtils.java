package com.yu.yuweather.utils;

import android.content.Context;

import com.yu.yuweather.service.widget.WidgetDayService;

public class WidgetUtils {

    public static void startWidgetDayService(Context context) {
        stopWidgetDayService(context);
        JobScheduleUtils.schedule(context, WidgetDayService.class, WidgetDayService.SCHEDULE_CODE, (long) (30 * 60 * 1000));
    }

    public static void stopWidgetDayService(Context context) {
        JobScheduleUtils.cancel(context, WidgetDayService.SCHEDULE_CODE);
    }
}
