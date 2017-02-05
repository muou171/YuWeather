package com.yu.yuweather.service.widget;

import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.yu.yuweather.R;
import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.global.DataName;
import com.yu.yuweather.models.Aqi;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.receiver.widget.WidgetDayProvider;
import com.yu.yuweather.utils.ColorUtils;
import com.yu.yuweather.utils.IconUtils;
import com.yu.yuweather.view.activity.MainActivity;
import com.yu.yuweather.view.activity.widget.ChooseCityToWidgetDayActivity;

public class WidgetDayService extends JobService {

    public static final int SCHEDULE_CODE = 11;

    private YuWeatherDB yuWeatherDB;
    private Now.BasicBean basicBean;
    private Now.NowBean nowBean;
    private Aqi.AqiBean aqiBean;
    private int currentAppWidgetId = -1;
    private String currentCountyId;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        if (currentAppWidgetId >= 0 && !currentCountyId.isEmpty()) {
            Intent intent = new Intent("android.intent.action.WIDGET_DAY_REFRESH_DATA");
            intent.putExtra(DataName.WIDGET_DAY_APP_WIDGET_ID, currentAppWidgetId);
            intent.putExtra(DataName.WIDGET_DAY_COUNTY_ID, currentCountyId);
            sendBroadcast(intent);
            jobFinished(jobParameters, false);
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        yuWeatherDB = YuWeatherDB.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateAppWidget();
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateAppWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName componentName = new ComponentName(this, WidgetDayProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_day);
            String countyId = yuWeatherDB.getCountyIdInWidgetDay(appWidgetId);
            if (!TextUtils.isEmpty(countyId)) {
                currentAppWidgetId = appWidgetId;
                currentCountyId = countyId;
                updateData(remoteViews, countyId);
                clickToMainActivity(remoteViews, appWidgetId, countyId);
                refreshData(remoteViews, appWidgetId, countyId);
            }
            changeCity(remoteViews, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    private void updateData(RemoteViews remoteViews, String countyId) {
        basicBean = yuWeatherDB.loadBasic(countyId);
        nowBean = yuWeatherDB.loadNow(countyId);
        aqiBean = yuWeatherDB.loadAqi(countyId);

        if (basicBean != null) {
            remoteViews.setTextViewText(R.id.tv_widget_day_basic_city, basicBean.getCity());
            remoteViews.setTextViewText(R.id.tv_widget_day_basic_update_loc, basicBean.getUpdate().getLoc());
        }

        if (nowBean != null) {
            remoteViews.setImageViewResource(R.id.iv_widget_day_background, ColorUtils.GetWidgetDayBackground(nowBean.getCond().getCode()));
            remoteViews.setImageViewResource(R.id.iv_widget_day_now_cond_code, IconUtils.GetCondIconResourcesId(nowBean.getCond().getCode()));
            remoteViews.setTextViewText(R.id.tv_widget_day_now_cond_txt, nowBean.getCond().getTxt());
            remoteViews.setTextViewText(R.id.tv_widget_day_now_tmp, nowBean.getTmp());
            remoteViews.setTextViewText(R.id.tv_widget_day_now_pcpn, nowBean.getPcpn());
            remoteViews.setTextViewText(R.id.tv_widget_day_now_hum, nowBean.getHum());
            remoteViews.setTextViewText(R.id.tv_widget_day_now_wind_dir, nowBean.getWind().getDir());
        }

        if (aqiBean != null) {
            remoteViews.setTextViewText(R.id.tv_widget_day_aqi_city_aqi, aqiBean.getCity().getAqi());
        }

        if (nowBean != null) {
            String code = nowBean.getCond().getCode();
            if (code.equals("400") || code.equals("401") || code.equals("402") || code.equals("403") || code.equals("407")) {
                remoteViews.setTextColor(R.id.tv_widget_day_now_cond_txt, ColorUtils.GetResourceColor(this, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_now_tmp, ColorUtils.GetResourceColor(this, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_now_tmp_unit, ColorUtils.GetResourceColor(this, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_basic_city, ColorUtils.GetResourceColor(this, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_basic_update_loc, ColorUtils.GetResourceColor(this, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_now_pcpn, ColorUtils.GetResourceColor(this, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_now_pcpn_unit, ColorUtils.GetResourceColor(this, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_now_hum, ColorUtils.GetResourceColor(this, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_now_hum_unit, ColorUtils.GetResourceColor(this, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_now_wind_dir, ColorUtils.GetResourceColor(this, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_aqi_city_aqi, ColorUtils.GetResourceColor(this, R.color.colorGrayDark));
                remoteViews.setImageViewResource(R.id.iv_widget_day_refresh, R.drawable.ic_widget_refresh_gray);
                remoteViews.setImageViewResource(R.id.iv_widget_day_setting, R.drawable.ic_widget_setting_gray);
                remoteViews.setImageViewResource(R.id.iv_widget_day_umbrella, R.drawable.ic_umbrella_gray);
                remoteViews.setImageViewResource(R.id.iv_widget_day_humidity, R.drawable.ic_humidity_gray);
                remoteViews.setImageViewResource(R.id.iv_widget_day_wind_direction, R.drawable.ic_wind_direction_gray);
                remoteViews.setImageViewResource(R.id.iv_widget_day_air_symbol, R.drawable.ic_air_symbol_gray);
            }
        }
    }

    private void clickToMainActivity(RemoteViews remoteViews, int appWidgetId, String countyId) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(DataName.WIDGET_DAY_COUNTY_ID, countyId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.rl_widget_day, pendingIntent);
    }

    private void refreshData(RemoteViews remoteViews, int appWidgetId, String countyId) {
        Intent intent = new Intent("android.intent.action.WIDGET_DAY_REFRESH_DATA");
        intent.putExtra(DataName.WIDGET_DAY_APP_WIDGET_ID, appWidgetId);
        intent.putExtra(DataName.WIDGET_DAY_COUNTY_ID, countyId);
        // requestCode设置为当前的appWidgetId，flags设置为PendingIntent.FLAG_UPDATE_CURRENT以响应多个Widget的点击事件
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_widget_day_refresh, pendingIntent);
    }

    private void changeCity(RemoteViews remoteViews, int appWidgetId) {
        Intent intent = new Intent(this, ChooseCityToWidgetDayActivity.class);
        intent.putExtra(DataName.WIDGET_DAY_APP_WIDGET_ID, appWidgetId);
        // requestCode设置为当前的appWidgetId，flags设置为PendingIntent.FLAG_UPDATE_CURRENT以响应多个Widget的点击事件
        PendingIntent pendingIntent = PendingIntent.getActivity(this, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_widget_day_setting, pendingIntent);
    }
}
