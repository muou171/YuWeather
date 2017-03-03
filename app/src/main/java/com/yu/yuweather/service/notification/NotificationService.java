package com.yu.yuweather.service.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.yu.yuweather.R;
import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.global.DataName;
import com.yu.yuweather.models.DailyForecast;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.receiver.UpdateDataFinishReceiver;
import com.yu.yuweather.utils.IconUtils;
import com.yu.yuweather.utils.NotificationUtils;
import com.yu.yuweather.view.activity.MainActivity;

import java.util.List;

public class NotificationService extends JobService {

    public static final int SCHEDULE_CODE = 13;
    public static final int REQUEST_CODE = 113;
    private YuWeatherDB yuWeatherDB;
    private String countyId;
    private int index;
    private UpdateDataFinishReceiver updateDataFinishReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        yuWeatherDB = YuWeatherDB.getInstance(NotificationService.this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NotificationService.this);
        List<Now.BasicBean> basicBeanList = yuWeatherDB.loadAllBasic();
        countyId = sharedPreferences.getString(getString(R.string.key_choose_notification_city), basicBeanList.get(0).getId());
        Now.BasicBean forecastBasicBean = yuWeatherDB.loadBasic(countyId);
        index = -1;
        for (Now.BasicBean basicBean : basicBeanList) {
            index++;
            if (forecastBasicBean.basicBeanEquals(basicBean)) {
                break;
            }
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NotificationService.this);
        List<Now.BasicBean> basicBeanList = yuWeatherDB.loadAllBasic();
        countyId = sharedPreferences.getString(getString(R.string.key_choose_notification_city), basicBeanList.get(0).getId());
        Now.BasicBean forecastBasicBean = yuWeatherDB.loadBasic(countyId);
        index = -1;
        for (Now.BasicBean basicBean : basicBeanList) {
            index++;
            if (forecastBasicBean.basicBeanEquals(basicBean)) {
                break;
            }
        }
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        if (!TextUtils.isEmpty(countyId)) {
            notifyNotification();
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    private void notifyNotification() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NotificationService.this);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_forecast);
        RemoteViews bigRemoteViews = new RemoteViews(getPackageName(), R.layout.notification_notification);

        Now.BasicBean basicBean = yuWeatherDB.loadBasic(countyId);
        Now.NowBean nowBean = yuWeatherDB.loadNow(countyId);
        List<DailyForecast.DailyBean.DataBean> dailyForecastList = yuWeatherDB.loadDailyForecast(countyId);

        remoteViews.setImageViewResource(R.id.iv_notification_forecast_icon, IconUtils.GetCondIconGrayResourcesId(nowBean.getCond().getCode()));
        remoteViews.setTextViewText(R.id.tv_notification_forecast_now_cond_txt, nowBean.getCond().getTxt());
        remoteViews.setTextViewText(R.id.tv_notification_forecast_basic_city, basicBean.getCity());
        remoteViews.setTextViewText(R.id.tv_notification_forecast_basic_update_loc, basicBean.getUpdate().getLoc());
        remoteViews.setTextViewText(R.id.tv_notification_forecast_now_tmp, nowBean.getTmp());

        bigRemoteViews.setImageViewResource(R.id.iv_notification_forecast_icon, IconUtils.GetCondIconGrayResourcesId(nowBean.getCond().getCode()));
        bigRemoteViews.setTextViewText(R.id.tv_notification_forecast_now_cond_txt, nowBean.getCond().getTxt());
        bigRemoteViews.setTextViewText(R.id.tv_notification_forecast_basic_city, basicBean.getCity());
        bigRemoteViews.setTextViewText(R.id.tv_notification_forecast_basic_update_loc, basicBean.getUpdate().getLoc());
        bigRemoteViews.setTextViewText(R.id.tv_notification_forecast_now_tmp, nowBean.getTmp());
        DailyForecast.DailyBean.DataBean dailyForecastDay1 = dailyForecastList.get(1);
        bigRemoteViews.setTextViewText(R.id.tv_notification_day1_daily_time, dailyForecastDay1.getTime());
        bigRemoteViews.setImageViewResource(R.id.iv_notification_day1_daily_icon, IconUtils.GetForecastIconGrayResourcesId(dailyForecastDay1.getIcon()));
        bigRemoteViews.setTextViewText(R.id.tv_notification_day1_daily_temperature_min, dailyForecastDay1.getTemperatureMin());
        bigRemoteViews.setTextViewText(R.id.tv_notification_day1_daily_temperature_max, dailyForecastDay1.getTemperatureMax());
        DailyForecast.DailyBean.DataBean dailyForecastDay2 = dailyForecastList.get(2);
        bigRemoteViews.setTextViewText(R.id.tv_notification_day2_daily_time, dailyForecastDay2.getTime());
        bigRemoteViews.setImageViewResource(R.id.iv_notification_day2_daily_icon, IconUtils.GetForecastIconGrayResourcesId(dailyForecastDay2.getIcon()));
        bigRemoteViews.setTextViewText(R.id.tv_notification_day2_daily_temperature_min, dailyForecastDay2.getTemperatureMin());
        bigRemoteViews.setTextViewText(R.id.tv_notification_day2_daily_temperature_max, dailyForecastDay2.getTemperatureMax());
        DailyForecast.DailyBean.DataBean dailyForecastDay3 = dailyForecastList.get(3);
        bigRemoteViews.setTextViewText(R.id.tv_notification_day3_daily_time, dailyForecastDay3.getTime());
        bigRemoteViews.setImageViewResource(R.id.iv_notification_day3_daily_icon, IconUtils.GetForecastIconGrayResourcesId(dailyForecastDay3.getIcon()));
        bigRemoteViews.setTextViewText(R.id.tv_notification_day3_daily_temperature_min, dailyForecastDay3.getTemperatureMin());
        bigRemoteViews.setTextViewText(R.id.tv_notification_day3_daily_temperature_max, dailyForecastDay3.getTemperatureMax());
        DailyForecast.DailyBean.DataBean dailyForecastDay4 = dailyForecastList.get(4);
        bigRemoteViews.setTextViewText(R.id.tv_notification_day4_daily_time, dailyForecastDay4.getTime());
        bigRemoteViews.setImageViewResource(R.id.iv_notification_day4_daily_icon, IconUtils.GetForecastIconGrayResourcesId(dailyForecastDay4.getIcon()));
        bigRemoteViews.setTextViewText(R.id.tv_notification_day4_daily_temperature_min, dailyForecastDay4.getTemperatureMin());
        bigRemoteViews.setTextViewText(R.id.tv_notification_day4_daily_temperature_max, dailyForecastDay4.getTemperatureMax());

        Intent intent = new Intent(NotificationService.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(DataName.ACTIVITY_INTERFACE, DataName.NOTIFICATION_NOTIFICATION);
        intent.putExtra(DataName.NOTIFICATION_CITY_INDEX, index);
        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationService.this, REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setCustomContentView(remoteViews)
                .setCustomBigContentView(bigRemoteViews)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setDefaults(Notification.DEFAULT_VIBRATE);

        if (sharedPreferences.getBoolean(getString(R.string.key_notification_hide_icon), false)) {
            builder.setSmallIcon(R.drawable.ic_launcher_clearness);
            builder.setPriority(NotificationCompat.PRIORITY_MIN);
        } else {
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }


        if (sharedPreferences.getBoolean(getString(R.string.key_notification_can_cleared), false)) {
            builder.setAutoCancel(true);
        } else {
            builder.setOngoing(true);
        }

        notificationManager.notify(NotificationUtils.NOTIFICATION_ID, builder.build());
    }
}
