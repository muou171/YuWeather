package com.yu.yuweather.utils;

import android.content.Context;

import com.yu.yuweather.service.UpdateDataRegularlyService;

public class ServiceUtils {

    public static void startUpdateDataRegularlyService(Context context) {
        stopUpdateDataRegularlyService(context);
        JobScheduleUtils.schedule(context, UpdateDataRegularlyService.class, UpdateDataRegularlyService.SCHEDULE_CODE);
    }

    public static void stopUpdateDataRegularlyService(Context context) {
        JobScheduleUtils.cancel(context, UpdateDataRegularlyService.SCHEDULE_CODE);
    }
}
