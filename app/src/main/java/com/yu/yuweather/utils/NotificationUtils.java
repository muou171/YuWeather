package com.yu.yuweather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.yu.yuweather.R;
import com.yu.yuweather.service.notification.ForecastService;
import com.yu.yuweather.service.notification.NotificationService;

import java.util.Calendar;

public class NotificationUtils {

    public static final int NOTIFICATION_ID = 21;
    public static final int FORECAST_ID = 22;

    public static void startForecastService(Context context) {
        stopForecastService(context);
        JobScheduleUtils.scheduleForecastMission(context, ForecastService.class, ForecastService.SCHEDULE_CODE);

    }

    public static void stopForecastService(Context context) {
        JobScheduleUtils.cancel(context, ForecastService.SCHEDULE_CODE);
    }

    public static void startNotificationService(Context context) {
        JobScheduleUtils.schedule(context, NotificationService.class, NotificationService.SCHEDULE_CODE, 0);
    }

    public static void stopNotificationService(Context context) {
        JobScheduleUtils.cancel(context, NotificationService.SCHEDULE_CODE);
    }

    public static long nowAndFirstForecastTimeDiffer(Context context) {
        Calendar calendar = Calendar.getInstance();
        long currentTime = calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000
                + calendar.get(Calendar.MINUTE) * 60 * 1000
                + calendar.get(Calendar.SECOND) * 1000;

        long settingTime;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String[] times = sharedPreferences.getString(context.getString(R.string.key_set_forecast_time), "07:00").split(":");
        settingTime = Integer.parseInt(times[0]) * 60 * 60 * 1000
                + Integer.parseInt(times[1]) * 60 * 1000;

        long timeDiffer = 0;
        if (currentTime > settingTime) {
            timeDiffer = 24 * 60 * 60 * 1000 + (settingTime - currentTime);
        } else if (currentTime < settingTime) {
            timeDiffer = settingTime - currentTime;
        }

        return timeDiffer;
    }
}
