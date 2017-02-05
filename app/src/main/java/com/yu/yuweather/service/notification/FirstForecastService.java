package com.yu.yuweather.service.notification;

import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.yu.yuweather.R;
import com.yu.yuweather.utils.NotificationUtils;

public class FirstForecastService extends JobService {

    public static final int SCHEDULE_CODE = 12;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("TAG", "onStartJob: -----------------");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_day);
        builder.setContent(remoteViews)
                .setStyle(bigPictureStyle)
                .setSmallIcon(R.mipmap.ic_launcher);
        notificationManager.notify(100, builder.build());
        jobFinished(jobParameters, false);
        // 停止第一次的预报服务，开启24小时的周期服务
        NotificationUtils.stopFirstForecastService(FirstForecastService.this);
        NotificationUtils.startForecastService(FirstForecastService.this);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
