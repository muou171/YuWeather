package com.yu.yuweather.service.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.yu.yuweather.R;
import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.global.DataName;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.utils.IconUtils;
import com.yu.yuweather.utils.NotificationUtils;
import com.yu.yuweather.view.activity.MainActivity;

import java.util.List;

public class ForecastService extends JobService {

    public static final int SCHEDULE_CODE = 13;
    private YuWeatherDB yuWeatherDB;
    private String countyId;

    @Override
    public void onCreate() {
        super.onCreate();
        yuWeatherDB = YuWeatherDB.getInstance(this);
        List<Now.BasicBean> basicBeanList = yuWeatherDB.loadAllBasic();
        countyId = basicBeanList.get(0).getId();
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_forecast);
        if (!countyId.isEmpty()) {
            Now.BasicBean basicBean = yuWeatherDB.loadBasic(countyId);
            Now.NowBean nowBean = yuWeatherDB.loadNow(countyId);
            remoteViews.setImageViewResource(R.id.iv_notification_forecast_icon, IconUtils.GetCondIconGrayResourcesId(nowBean.getCond().getCode()));
            remoteViews.setTextViewText(R.id.tv_notification_forecast_now_cond_txt, nowBean.getCond().getTxt());
            remoteViews.setTextViewText(R.id.tv_notification_forecast_basic_city, basicBean.getCity());
            remoteViews.setTextViewText(R.id.tv_notification_forecast_basic_update_loc, basicBean.getUpdate().getLoc());
            remoteViews.setTextViewText(R.id.tv_notification_forecast_now_tmp, nowBean.getTmp());
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(DataName.ACTIVITY_INTERFACE, DataName.FORECAST_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder.setContent(remoteViews)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setDefaults(Notification.DEFAULT_VIBRATE);
        notificationManager.notify(NotificationUtils.FORECAST_ID, builder.build());
        jobFinished(jobParameters, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
