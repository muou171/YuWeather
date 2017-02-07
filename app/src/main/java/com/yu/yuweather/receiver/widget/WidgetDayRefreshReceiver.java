package com.yu.yuweather.receiver.widget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.yu.yuweather.R;
import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.global.ApiConstants;
import com.yu.yuweather.global.DataName;
import com.yu.yuweather.models.Aqi;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.utils.ColorUtils;
import com.yu.yuweather.utils.DataBaseUtil;
import com.yu.yuweather.utils.HttpsUtil;
import com.yu.yuweather.utils.IconUtils;

import java.util.ArrayList;
import java.util.List;

public class WidgetDayRefreshReceiver extends BroadcastReceiver {

    private YuWeatherDB yuWeatherDB;
    private Now.BasicBean basicBean;
    private Now.NowBean nowBean;
    private Aqi.AqiBean aqiBean;

    @Override
    public void onReceive(final Context context, Intent intent) {
        yuWeatherDB = YuWeatherDB.getInstance(context);
        final List<Now.BasicBean> basicBeanList = yuWeatherDB.loadAllBasic();
        // 获取新数据
        final int appWidgetId = intent.getExtras().getInt(DataName.WIDGET_DAY_APP_WIDGET_ID);
        final String countyId = intent.getExtras().getString(DataName.WIDGET_DAY_COUNTY_ID);

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsUtil.sendHttpsRequest(ApiConstants.GetNowApiAddress(countyId), new HttpsUtil.HttpsCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        // 获取当前城市在Basic表的位置
                        int position = 0;
                        for (int i = 0; i < basicBeanList.size(); i++) {
                            position = i;
                            if (countyId.equals(basicBeanList.get(i).getId())) {
                                break;
                            }
                        }
                        // 删除数据库中当前页面所保存的城市的天气信息
                        yuWeatherDB.deleteItemsFromBasic(countyId);
                        // 保存新数据
                        DataBaseUtil.saveJSONToDataBase(response, countyId, yuWeatherDB);
                        // 更新Basic表中的城市顺序
                        if (position < basicBeanList.size() - 1) {
                            List<Now.BasicBean> currentBasicBeanList = yuWeatherDB.loadAllBasic();
                            List<Now.BasicBean> updateBasicBeanList = new ArrayList<>();
                            for (int i = 0; i < currentBasicBeanList.size() - 1; i++) {
                                if (i == position) {
                                    updateBasicBeanList.add(currentBasicBeanList.get(currentBasicBeanList.size() - 1));
                                }
                                updateBasicBeanList.add(currentBasicBeanList.get(i));
                            }
                            yuWeatherDB.updateBasicOrder(updateBasicBeanList);
                        }
                        // 更新Widget界面
                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_day);
                        updateData(context, remoteViews, countyId);
                        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        }).start();
    }

    private void updateData(Context context, RemoteViews remoteViews, String countyId) {
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
                remoteViews.setTextColor(R.id.tv_widget_day_now_cond_txt, ColorUtils.GetResourceColor(context, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_now_tmp, ColorUtils.GetResourceColor(context, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_now_tmp_unit, ColorUtils.GetResourceColor(context, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_basic_city, ColorUtils.GetResourceColor(context, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_basic_update_loc, ColorUtils.GetResourceColor(context, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_now_pcpn, ColorUtils.GetResourceColor(context, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_now_pcpn_unit, ColorUtils.GetResourceColor(context, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_now_hum, ColorUtils.GetResourceColor(context, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_now_hum_unit, ColorUtils.GetResourceColor(context, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_now_wind_dir, ColorUtils.GetResourceColor(context, R.color.colorGrayDark));
                remoteViews.setTextColor(R.id.tv_widget_day_aqi_city_aqi, ColorUtils.GetResourceColor(context, R.color.colorGrayDark));
                remoteViews.setImageViewResource(R.id.iv_widget_day_refresh, R.drawable.ic_widget_refresh_gray);
                remoteViews.setImageViewResource(R.id.iv_widget_day_setting, R.drawable.ic_widget_setting_gray);
                remoteViews.setImageViewResource(R.id.iv_widget_day_umbrella, R.drawable.ic_umbrella_gray);
                remoteViews.setImageViewResource(R.id.iv_widget_day_humidity, R.drawable.ic_humidity_gray);
                remoteViews.setImageViewResource(R.id.iv_widget_day_wind_direction, R.drawable.ic_wind_direction_gray);
                remoteViews.setImageViewResource(R.id.iv_widget_day_air_symbol, R.drawable.ic_air_symbol_gray);
            }
        }
    }
}
