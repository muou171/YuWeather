package com.yu.yuweather.utils;

import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.global.ApiConstants;
import com.yu.yuweather.models.Now;

public class DataBaseUtil {
    public static void saveJSONToDataBase(String response, final String countyId, final YuWeatherDB yuWeatherDB) {
        // 保存NowJSON数据到数据库
        JSONUtils.NowJSONSaveToDataBase(response, yuWeatherDB);
        Now.BasicBean basicBean = yuWeatherDB.loadBasic(countyId);
        String lat = basicBean.getLat();
        String lon = basicBean.getLon();
        HttpsUtil.sendHttpsRequest(ApiConstants.GetAqiApiAddress(countyId), new HttpsUtil.HttpsCallbackListener() {
            @Override
            public void onFinish(String response) {
                // 保存AqiJSON数据到数据库
                JSONUtils.AqiJSONSaveToDataBase(response, countyId, yuWeatherDB);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        HttpsUtil.sendHttpsRequest(ApiConstants.GetSuggestionApiAddress(countyId), new HttpsUtil.HttpsCallbackListener() {
            @Override
            public void onFinish(String response) {
                // 保存SuggestionJSON数据到数据库
                JSONUtils.SuggestionJSONSaveToDataBase(response, countyId, yuWeatherDB);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        HttpsUtil.sendHttpsRequest(ApiConstants.GetDailyApiAddress(lat, lon), new HttpsUtil.HttpsCallbackListener() {
            @Override
            public void onFinish(String response) {
                // 保存DailyForecastJSON数据到数据库
                JSONUtils.DailyForecastJSONSaveToDataBase(response, countyId, yuWeatherDB);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        HttpsUtil.sendHttpsRequest(ApiConstants.GetHourlyApiAddress(lat, lon), new HttpsUtil.HttpsCallbackListener() {
            @Override
            public void onFinish(String response) {
                // 保存HourlyForecastJSON数据到数据库
                JSONUtils.HourlyForecastJSONSaveToDataBase(response, countyId, yuWeatherDB);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
