package com.yu.yuweather.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yu.yuweather.utils.NotificationUtils;

import java.util.List;

public class UpdateDataFinishReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(30);
        for (ActivityManager.RunningServiceInfo runningService : runningServices) {
            if (runningService.service.getClassName().equals("com.yu.yuweather.service.notification.NotificationService")) {
                NotificationUtils.startNotificationService(context);
                break;
            }
        }
    }
}