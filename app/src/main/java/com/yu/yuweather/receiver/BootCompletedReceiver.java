package com.yu.yuweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.yu.yuweather.R;
import com.yu.yuweather.utils.NotificationUtils;
import com.yu.yuweather.utils.ServiceUtils;

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case Intent.ACTION_BOOT_COMPLETED:
                ServiceUtils.startUpdateDataRegularlyService(context);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (sharedPreferences.getBoolean(context.getString(R.string.key_forecast_time), false)) {
                    NotificationUtils.startForecastService(context);
                }
        }
    }
}
