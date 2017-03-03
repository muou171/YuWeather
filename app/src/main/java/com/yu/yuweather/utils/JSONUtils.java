package com.yu.yuweather.utils;

import com.google.gson.Gson;
import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.models.Aqi;
import com.yu.yuweather.models.DailyForecast;
import com.yu.yuweather.models.HourlyForecast;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.models.Suggestion;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JSONUtils {

    /**
     * 解析天气Api返回的NowJSON数据，并把结果保存到数据库中
     *
     * @param isFirst 是否第一次保存到数据库
     */
    public static void NowJSONSaveToDataBase(boolean isFirst, String response, YuWeatherDB yuWeatherDB) {
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(response);
            String strResponse = jsonObject.getJSONArray("HeWeather5").getString(0);
            Now now = gson.fromJson(strResponse, Now.class);
            // 保存到数据库
            Now.BasicBean basicBean = now.getBasic();
            String id = basicBean.getId();
            basicBean.getUpdate().setLoc(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis())));
            yuWeatherDB.saveBasicBean(isFirst, basicBean);
            Now.NowBean nowBean = now.getNow();
            yuWeatherDB.saveNowBean(nowBean, id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析天气Api返回的AqiJSON数据，并把结果保存到数据库中
     */
    public static void AqiJSONSaveToDataBase(String response, String countyId, YuWeatherDB yuWeatherDB) {
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(response);
            String strResponse = jsonObject.getJSONArray("HeWeather5").getString(0);
            Aqi aqi = gson.fromJson(strResponse, Aqi.class);
            // 保存到数据库
            Aqi.AqiBean aqiBean = aqi.getAqi();
            yuWeatherDB.saveAqiBean(aqiBean, countyId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析天气Api返回的SuggestionJSON数据，并把结果保存到数据库中
     */
    public static void SuggestionJSONSaveToDataBase(String response, String countyId, YuWeatherDB yuWeatherDB) {
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(response);
            String strResponse = jsonObject.getJSONArray("HeWeather5").getString(0);
            Suggestion suggestion = gson.fromJson(strResponse, Suggestion.class);
            // 保存到数据库
            Suggestion.SuggestionBean suggestionBean = suggestion.getSuggestion();
            yuWeatherDB.saveSuggestionBean(suggestionBean, countyId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析天气Api返回的DailyForecastJSON数据，并把结果保存到数据库中
     */
    public static void DailyForecastJSONSaveToDataBase(String response, String countyId, YuWeatherDB yuWeatherDB) {
        Gson gson = new Gson();
        DailyForecast dailyForecast = gson.fromJson(response, DailyForecast.class);
        List<DailyForecast.DailyBean.DataBean> dataBeanList = dailyForecast.getDaily().getData();
        for (int i = 0; i < dataBeanList.size(); i++) {
            // 保存到数据库
            DailyForecast.DailyBean.DataBean dataBean = dataBeanList.get(i);
            yuWeatherDB.saveDailyForecastBean(dataBean, countyId);
        }
    }

    /**
     * 解析天气Api返回的HourlyForecastJSON数据，并把结果保存到数据库中
     */
    public static void HourlyForecastJSONSaveToDataBase(String response, String countyId, YuWeatherDB yuWeatherDB) {
        Gson gson = new Gson();
        HourlyForecast hourlyForecast = gson.fromJson(response, HourlyForecast.class);
        List<HourlyForecast.HourlyBean.DataBean> dataBeanList = hourlyForecast.getHourly().getData();
        String time = dataBeanList.get(0).getTime();
        String day = Utils.ssTimeToDataTimeDay(time);
        for (int i = 0; i < dataBeanList.size(); i++) {
            // 保存到数据库
            HourlyForecast.HourlyBean.DataBean dataBean = dataBeanList.get(i);
            String currentTime = dataBean.getTime();
            String currentDay = Utils.ssTimeToDataTimeDay(currentTime);
            if (currentDay.equals(day)) {
                yuWeatherDB.saveHourlyForecastBean(dataBean, countyId);
            } else {
                continue;
            }
        }
    }


    /**
     * 解析定位Api返回的JSON数据，获取到当前的县名
     */
    public static String LocationJSONGetCounty(String response) {
        String county = null;
        try {
            JSONObject jsonObject = new JSONObject(response);
            county = jsonObject.getJSONObject("regeocode").getJSONObject("addressComponent").getString("district");
            county = county.substring(0, county.length() - 1);
            return county;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return county;
    }
}
