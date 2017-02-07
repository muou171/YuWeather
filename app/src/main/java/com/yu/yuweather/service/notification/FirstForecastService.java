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
import android.widget.RemoteViews;

import com.yu.yuweather.R;
import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.global.ApiConstants;
import com.yu.yuweather.global.DataName;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.utils.DataBaseUtil;
import com.yu.yuweather.utils.HttpsUtil;
import com.yu.yuweather.utils.IconUtils;
import com.yu.yuweather.utils.NotificationUtils;
import com.yu.yuweather.utils.PrefUtils;
import com.yu.yuweather.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class FirstForecastService extends JobService {

    public static final int SCHEDULE_CODE = 12;
    private YuWeatherDB yuWeatherDB;
    private String countyId;
    private int index;

    @Override
    public void onCreate() {
        super.onCreate();
        yuWeatherDB = YuWeatherDB.getInstance(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(FirstForecastService.this);
        List<Now.BasicBean> basicBeanList = yuWeatherDB.loadAllBasic();
        countyId = sharedPreferences.getString(getString(R.string.key_choose_forecast_city), basicBeanList.get(0).getId());
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
        if (!countyId.isEmpty()) {
            updateData(jobParameters);
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    private void updateData(JobParameters jobParameters) {
        final JobParameters currentJobParameters = jobParameters;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsUtil.sendHttpsRequest(ApiConstants.GetNowApiAddress(countyId), new HttpsUtil.HttpsCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        // 删除数据库中当前页面所保存的城市的天气信息
                        yuWeatherDB.deleteItemsFromBasic(countyId);
                        DataBaseUtil.saveJSONToDataBase(response, countyId, yuWeatherDB);
                        // 更新Basic表中的城市顺序
                        List<Now.BasicBean> currentBasicBeanList = yuWeatherDB.loadAllBasic();
                        List<Now.BasicBean> updateBasicBeanList = new ArrayList<>();
                        if (currentBasicBeanList.size() > 1) {
                            for (int i = 0; i < currentBasicBeanList.size() - 1; i++) {
                                if (i == index) {
                                    updateBasicBeanList.add(currentBasicBeanList.get(currentBasicBeanList.size() - 1));
                                }
                                updateBasicBeanList.add(currentBasicBeanList.get(i));
                            }
                            yuWeatherDB.updateBasicOrder(updateBasicBeanList);
                            // 保存更新数据的时间
                            PrefUtils.setLong(FirstForecastService.this, DataName.LAST_TIME, System.currentTimeMillis());
                        }
                        // 预报通知
                        notifyForecast(currentJobParameters);
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }).start();
    }

    private void notifyForecast(JobParameters currentJobParameters) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(FirstForecastService.this);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_forecast);

        Now.BasicBean basicBean = yuWeatherDB.loadBasic(countyId);
        Now.NowBean nowBean = yuWeatherDB.loadNow(countyId);
        remoteViews.setImageViewResource(R.id.iv_notification_forecast_icon, IconUtils.GetCondIconGrayResourcesId(nowBean.getCond().getCode()));
        remoteViews.setTextViewText(R.id.tv_notification_forecast_now_cond_txt, nowBean.getCond().getTxt());
        remoteViews.setTextViewText(R.id.tv_notification_forecast_basic_city, basicBean.getCity());
        remoteViews.setTextViewText(R.id.tv_notification_forecast_basic_update_loc, basicBean.getUpdate().getLoc());
        remoteViews.setTextViewText(R.id.tv_notification_forecast_now_tmp, nowBean.getTmp());

        Intent intent = new Intent(FirstForecastService.this, MainActivity.class);
        intent.putExtra(DataName.ACTIVITY_INTERFACE, DataName.FORECAST_NOTIFICATION);
        intent.putExtra(DataName.FORECAST_CITY_INDEX, index);
        PendingIntent pendingIntent = PendingIntent.getActivity(FirstForecastService.this, 0, intent, 0);

        builder.setContent(remoteViews)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setDefaults(Notification.DEFAULT_VIBRATE);
        notificationManager.notify(NotificationUtils.FORECAST_ID, builder.build());
        jobFinished(currentJobParameters, false);
        // 停止第一次的预报服务，开启24小时的周期服务
        NotificationUtils.stopFirstForecastService(FirstForecastService.this);
        NotificationUtils.startForecastService(FirstForecastService.this);
    }


}
