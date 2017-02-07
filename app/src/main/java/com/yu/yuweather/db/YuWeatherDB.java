package com.yu.yuweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.yu.yuweather.R;
import com.yu.yuweather.global.DataName;
import com.yu.yuweather.models.Aqi;
import com.yu.yuweather.models.City;
import com.yu.yuweather.models.County;
import com.yu.yuweather.models.DailyForecast;
import com.yu.yuweather.models.HourlyForecast;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.models.Province;
import com.yu.yuweather.models.Suggestion;
import com.yu.yuweather.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class YuWeatherDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "YuWeather.db";
    /**
     * 数据库版本
     */
    public static final int VERSION = 2;// 版本为2，因为需要把资源中数据库先导入到对应的文件夹中

    private static YuWeatherDB yuWeatherDB;
    private SQLiteDatabase db;

    private YuWeatherDB(Context context) {
        YuWeatherOpenHelper dbHelper = new YuWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static YuWeatherDB getInstance(Context context) {
        if (yuWeatherDB == null) {
            yuWeatherDB = new YuWeatherDB(context);
        }
        return yuWeatherDB;
    }

    /**
     * 从数据库读取全国所有的省份信息。
     */
    public List<Province> loadProvinces() {
        List<Province> list = new ArrayList<>();
        Cursor cursor = db.query(DataName.PROVINCE, null, null, null, null, null, "_id");
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getString(cursor.getColumnIndex("_id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 从数据库读取某省下所有的城市信息。
     */
    public List<City> loadCities(String provinceId) {
        List<City> list = new ArrayList<>();
        Cursor cursor = db.query(DataName.CITY, null, "province_id = ?", new String[]{provinceId}, null, null, "_id");
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getString(cursor.getColumnIndex("_id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 从数据库读取某城市下所有的县信息。
     */
    public List<County> loadCounties(String cityId) {
        List<County> list = new ArrayList<>();
        Cursor cursor = db.query(DataName.COUNTY, null, "city_id = ?", new String[]{cityId}, null, null, "_id");
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getString(cursor.getColumnIndex("_id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 根据城市名判断是否存在于数据库，如果存在就返回该城市的ID
     */
    public String isExistCounty(String countyName) {
        Cursor cursor = db.query(DataName.COUNTY, null, "county_name = ?", new String[]{countyName}, null, null, null);
        if (cursor.getCount() <= 0) {
            return null;
        }
        cursor.moveToFirst();
        String id = cursor.getString(cursor.getColumnIndex("_id"));
        cursor.close();
        return id;
    }

    /**
     * 将BasicBean实例存储到数据库。
     */
    public void saveBasicBean(Now.BasicBean basicBean) {
        if (basicBean != null) {
            ContentValues values = new ContentValues();
            values.put("_id", basicBean.getId());
            values.put("city", basicBean.getCity());
            values.put("lat", basicBean.getLat());
            values.put("lon", basicBean.getLon());
            values.put("loc", Utils.BasicUpdateLocSub(basicBean.getUpdate().getLoc()));
            db.insert(DataName.BASIC, null, values);
        }
    }

    /**
     * 从数据库返回所有城市基本信息
     */
    public List<Now.BasicBean> loadAllBasic() {
        List<Now.BasicBean> list = new ArrayList<>();
        Cursor cursor = db.query(DataName.BASIC, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Now.BasicBean basicBean = new Now.BasicBean();
                basicBean.setId(cursor.getString(cursor.getColumnIndex("_id")));
                basicBean.setCity(cursor.getString(cursor.getColumnIndex("city")));
                basicBean.setLat(cursor.getString(cursor.getColumnIndex("lat")));
                basicBean.setLon(cursor.getString(cursor.getColumnIndex("lon")));
                Now.BasicBean.UpdateBean updateBean = new Now.BasicBean.UpdateBean();
                updateBean.setLoc(cursor.getString(cursor.getColumnIndex("loc")));
                basicBean.setUpdate(updateBean);
                list.add(basicBean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 根据城市ID从数据库返回城市基本信息
     */
    public Now.BasicBean loadBasic(String id) {
        Now.BasicBean basicBean = null;
        Cursor cursor = db.query(DataName.BASIC, null, "_id = ?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                basicBean = new Now.BasicBean();
                basicBean.setId(cursor.getString(cursor.getColumnIndex("_id")));
                basicBean.setCity(cursor.getString(cursor.getColumnIndex("city")));
                basicBean.setLat(cursor.getString(cursor.getColumnIndex("lat")));
                basicBean.setLon(cursor.getString(cursor.getColumnIndex("lon")));
                Now.BasicBean.UpdateBean updateBean = new Now.BasicBean.UpdateBean();
                updateBean.setLoc(cursor.getString(cursor.getColumnIndex("loc")));
                basicBean.setUpdate(updateBean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return basicBean;
    }

    /**
     * 将NowBean实例存储到数据库
     */
    public void saveNowBean(Now.NowBean nowBean, String id) {
        if (nowBean != null && !TextUtils.isEmpty(id)) {
            ContentValues values = new ContentValues();
            values.put("_id", id);
            values.put("code", nowBean.getCond().getCode());
            values.put("txt", nowBean.getCond().getTxt());
            values.put("hum", nowBean.getHum());
            values.put("pcpn", nowBean.getPcpn());
            values.put("tmp", nowBean.getTmp());
            values.put("dir", nowBean.getWind().getDir());
            db.insert(DataName.NOW, null, values);
        }
    }

    /**
     * 从数据库返回实况天气
     */
    public Now.NowBean loadNow(String id) {
        Now.NowBean nowBean = null;
        Cursor cursor = db.query(DataName.NOW, null, "_id = ?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                nowBean = new Now.NowBean();
                Now.NowBean.CondBean condBean = new Now.NowBean.CondBean();
                condBean.setCode(cursor.getString(cursor.getColumnIndex("code")));
                condBean.setTxt(cursor.getString(cursor.getColumnIndex("txt")));
                nowBean.setCond(condBean);
                nowBean.setHum(cursor.getString(cursor.getColumnIndex("hum")));
                nowBean.setPcpn(cursor.getString(cursor.getColumnIndex("pcpn")));
                nowBean.setTmp(cursor.getString(cursor.getColumnIndex("tmp")));
                Now.NowBean.WindBean windBean = new Now.NowBean.WindBean();
                windBean.setDir(cursor.getString(cursor.getColumnIndex("dir")));
                nowBean.setWind(windBean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return nowBean;
    }

    /**
     * 将AqiBean实例存储到数据库
     */
    public void saveAqiBean(Aqi.AqiBean aqiBean, String id) {
        if (aqiBean != null && !TextUtils.isEmpty(id)) {
            ContentValues values = new ContentValues();
            values.put("_id", id);
            values = Utils.WeatherContentIsEmptyHandle(values, "aqi", aqiBean.getCity().getAqi());
            values = Utils.WeatherContentIsEmptyHandle(values, "co", aqiBean.getCity().getCo());
            values = Utils.WeatherContentIsEmptyHandle(values, "no2", aqiBean.getCity().getNo2());
            values = Utils.WeatherContentIsEmptyHandle(values, "o3", aqiBean.getCity().getO3());
            values = Utils.WeatherContentIsEmptyHandle(values, "pm10", aqiBean.getCity().getPm10());
            values = Utils.WeatherContentIsEmptyHandle(values, "pm25", aqiBean.getCity().getPm25());
            values = Utils.WeatherContentIsEmptyHandle(values, "qlty", aqiBean.getCity().getQlty());
            values = Utils.WeatherContentIsEmptyHandle(values, "so2", aqiBean.getCity().getSo2());
            db.insert(DataName.AQI, null, values);
        }
    }

    /**
     * 从数据库返回空气质量指数
     */
    public Aqi.AqiBean loadAqi(String id) {
        Aqi.AqiBean aqiBean = null;
        Cursor cursor = db.query(DataName.AQI, null, "_id = ?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                aqiBean = new Aqi.AqiBean();
                Aqi.AqiBean.CityBean cityBean = new Aqi.AqiBean.CityBean();
                cityBean.setAqi(cursor.getString(cursor.getColumnIndex("aqi")));
                cityBean.setCo(cursor.getString(cursor.getColumnIndex("co")));
                cityBean.setNo2(cursor.getString(cursor.getColumnIndex("no2")));
                cityBean.setO3(cursor.getString(cursor.getColumnIndex("o3")));
                cityBean.setPm10(cursor.getString(cursor.getColumnIndex("pm10")));
                cityBean.setPm25(cursor.getString(cursor.getColumnIndex("pm25")));
                cityBean.setQlty(cursor.getString(cursor.getColumnIndex("qlty")));
                cityBean.setSo2(cursor.getString(cursor.getColumnIndex("so2")));
                aqiBean.setCity(cityBean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return aqiBean;
    }

    /**
     * 将SuggestionBean实例存储到数据库
     */
    public void saveSuggestionBean(Suggestion.SuggestionBean suggestionBean, String id) {
        if (suggestionBean != null && !TextUtils.isEmpty(id)) {
            ContentValues values = new ContentValues();
            values.put("_id", id);
            values.put("comf_brf", suggestionBean.getComf().getBrf());
            values.put("comf_txt", suggestionBean.getComf().getTxt());
            values.put("cw_brf", suggestionBean.getCw().getBrf());
            values.put("cw_txt", suggestionBean.getCw().getTxt());
            values.put("drsg_brf", suggestionBean.getDrsg().getBrf());
            values.put("drsg_txt", suggestionBean.getDrsg().getTxt());
            values.put("flu_brf", suggestionBean.getFlu().getBrf());
            values.put("flu_txt", suggestionBean.getFlu().getTxt());
            values.put("sport_brf", suggestionBean.getSport().getBrf());
            values.put("sport_txt", suggestionBean.getSport().getTxt());
            values.put("trav_brf", suggestionBean.getTrav().getBrf());
            values.put("trav_txt", suggestionBean.getTrav().getTxt());
            values.put("uv_brf", suggestionBean.getUv().getBrf());
            values.put("uv_txt", suggestionBean.getUv().getTxt());
            db.insert(DataName.SUGGESTION, null, values);
        }
    }

    /**
     * 从数据库返回生活指数
     */
    public Suggestion.SuggestionBean loadSuggestion(String id) {
        Suggestion.SuggestionBean suggestionBean = null;
        Cursor cursor = db.query(DataName.SUGGESTION, null, "_id = ?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                suggestionBean = new Suggestion.SuggestionBean();
                Suggestion.SuggestionBean.ComfBean comfBean = new Suggestion.SuggestionBean.ComfBean();
                comfBean.setBrf(cursor.getString(cursor.getColumnIndex("comf_brf")));
                comfBean.setTxt(cursor.getString(cursor.getColumnIndex("comf_txt")));
                suggestionBean.setComf(comfBean);
                Suggestion.SuggestionBean.CwBean cwBean = new Suggestion.SuggestionBean.CwBean();
                cwBean.setBrf(cursor.getString(cursor.getColumnIndex("cw_brf")));
                cwBean.setTxt(cursor.getString(cursor.getColumnIndex("cw_txt")));
                suggestionBean.setCw(cwBean);
                Suggestion.SuggestionBean.DrsgBean drsgBean = new Suggestion.SuggestionBean.DrsgBean();
                drsgBean.setBrf(cursor.getString(cursor.getColumnIndex("drsg_brf")));
                drsgBean.setTxt(cursor.getString(cursor.getColumnIndex("drsg_txt")));
                suggestionBean.setDrsg(drsgBean);
                Suggestion.SuggestionBean.FluBean fluBean = new Suggestion.SuggestionBean.FluBean();
                fluBean.setBrf(cursor.getString(cursor.getColumnIndex("flu_brf")));
                fluBean.setTxt(cursor.getString(cursor.getColumnIndex("flu_txt")));
                suggestionBean.setFlu(fluBean);
                Suggestion.SuggestionBean.SportBean sportBean = new Suggestion.SuggestionBean.SportBean();
                sportBean.setBrf(cursor.getString(cursor.getColumnIndex("sport_brf")));
                sportBean.setTxt(cursor.getString(cursor.getColumnIndex("sport_txt")));
                suggestionBean.setSport(sportBean);
                Suggestion.SuggestionBean.TravBean travBean = new Suggestion.SuggestionBean.TravBean();
                travBean.setBrf(cursor.getString(cursor.getColumnIndex("trav_brf")));
                travBean.setTxt(cursor.getString(cursor.getColumnIndex("trav_txt")));
                suggestionBean.setTrav(travBean);
                Suggestion.SuggestionBean.UvBean uvBean = new Suggestion.SuggestionBean.UvBean();
                uvBean.setBrf(cursor.getString(cursor.getColumnIndex("uv_brf")));
                uvBean.setTxt(cursor.getString(cursor.getColumnIndex("uv_txt")));
                suggestionBean.setUv(uvBean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return suggestionBean;
    }

    /**
     * 将DailyForecast.DailyBean.DataBean实例存储到数据库
     */
    public void saveDailyForecastBean(DailyForecast.DailyBean.DataBean dataBean, String id) {
        if (dataBean != null && !TextUtils.isEmpty(id)) {
            ContentValues values = new ContentValues();
            values.put("_id", id);
            values.put("time", Utils.ssTimeToDataTimeMonthAndDay(dataBean.getTime()));
            values.put("summary", dataBean.getSummary());
            values.put("icon", dataBean.getIcon());
            values.put("sunriseTime", Utils.ssTimeToDataTimeHourAndMinute(dataBean.getSunriseTime()));
            values.put("sunsetTime", Utils.ssTimeToDataTimeHourAndMinute(dataBean.getSunsetTime()));
            values.put("precipProbability", Utils.ProbabilityFormatPercentageInteger(dataBean.getPrecipProbability()));
            values.put("temperatureMin", Utils.DecimalFormatInteger(dataBean.getTemperatureMin()));
            values.put("temperatureMinTime", Utils.ssTimeToDataTimeHourAndMinute(dataBean.getTemperatureMinTime()));
            values.put("temperatureMax", Utils.DecimalFormatInteger(dataBean.getTemperatureMax()));
            values.put("temperatureMaxTime", Utils.ssTimeToDataTimeHourAndMinute(dataBean.getTemperatureMaxTime()));
            values.put("humidity", Utils.ProbabilityFormatPercentageInteger(dataBean.getHumidity()));
            values.put("pressure", Utils.DecimalFormatInteger(dataBean.getPressure()));
            db.insert(DataName.DAILY_FORECAST, null, values);
        }
    }

    /**
     * 从数据库返回每日天气预报
     */
    public List<DailyForecast.DailyBean.DataBean> loadDailyForecast(String id) {
        List<DailyForecast.DailyBean.DataBean> list = new ArrayList<>();
        Cursor cursor = db.query(DataName.DAILY_FORECAST, null, "_id = ?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                DailyForecast.DailyBean.DataBean dataBean = new DailyForecast.DailyBean.DataBean();
                dataBean.setTime(cursor.getString(cursor.getColumnIndex("time")));
                dataBean.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
                dataBean.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
                dataBean.setSunriseTime(cursor.getString(cursor.getColumnIndex("sunriseTime")));
                dataBean.setSunsetTime(cursor.getString(cursor.getColumnIndex("sunsetTime")));
                dataBean.setPrecipProbability(cursor.getString(cursor.getColumnIndex("precipProbability")));
                dataBean.setTemperatureMin(cursor.getString(cursor.getColumnIndex("temperatureMin")));
                dataBean.setTemperatureMinTime(cursor.getString(cursor.getColumnIndex("temperatureMinTime")));
                dataBean.setTemperatureMax(cursor.getString(cursor.getColumnIndex("temperatureMax")));
                dataBean.setTemperatureMaxTime(cursor.getString(cursor.getColumnIndex("temperatureMaxTime")));
                dataBean.setHumidity(cursor.getString(cursor.getColumnIndex("humidity")));
                dataBean.setPressure(cursor.getString(cursor.getColumnIndex("pressure")));
                list.add(dataBean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 将HourlyForecast.HourlyBean.DataBean实例存储到数据库
     */
    public void saveHourlyForecastBean(HourlyForecast.HourlyBean.DataBean dataBean, String id) {
        if (dataBean != null && !TextUtils.isEmpty(id)) {
            ContentValues values = new ContentValues();
            values.put("_id", id);
            values.put("time", Utils.ssTimeToDataTimeHourAndMinute(dataBean.getTime()));
            values.put("summary", dataBean.getSummary());
            values.put("icon", dataBean.getIcon());
            values.put("precipProbability", Utils.ProbabilityFormatPercentageInteger(dataBean.getPrecipProbability()));
            values.put("temperature", Utils.DecimalFormatInteger(dataBean.getTemperature()));
            values.put("humidity", Utils.ProbabilityFormatPercentageInteger(dataBean.getHumidity()));
            values.put("pressure", Utils.DecimalFormatInteger(dataBean.getPressure()));
            db.insert(DataName.HOURLY_FORECAST, null, values);
        }
    }

    /**
     * 从数据库返回当天每小时天气预报
     */
    public List<HourlyForecast.HourlyBean.DataBean> loadHourlyForecast(String id) {
        List<HourlyForecast.HourlyBean.DataBean> list = new ArrayList<>();
        Cursor cursor = db.query(DataName.HOURLY_FORECAST, null, "_id = ?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                HourlyForecast.HourlyBean.DataBean dataBean = new HourlyForecast.HourlyBean.DataBean();
                dataBean.setTime(cursor.getString(cursor.getColumnIndex("time")));
                dataBean.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
                dataBean.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
                dataBean.setPrecipProbability(cursor.getString(cursor.getColumnIndex("precipProbability")));
                dataBean.setTemperature(cursor.getString(cursor.getColumnIndex("temperature")));
                dataBean.setHumidity(cursor.getString(cursor.getColumnIndex("humidity")));
                dataBean.setPressure(cursor.getString(cursor.getColumnIndex("pressure")));
                list.add(dataBean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 从数据库中删除不需要的城市，相应的也得从其它表中删除该城市的数据
     */
    public void deleteCity(Context context, String id) {
        if (!TextUtils.isEmpty(id)) {
            db.delete(DataName.BASIC, "_id = ?", new String[]{id});
            db.delete(DataName.AQI, "_id = ?", new String[]{id});
            db.delete(DataName.SUGGESTION, "_id = ?", new String[]{id});
            db.delete(DataName.NOW, "_id = ?", new String[]{id});
            db.delete(DataName.DAILY_FORECAST, "_id = ?", new String[]{id});
            db.delete(DataName.HOURLY_FORECAST, "_id = ?", new String[]{id});
            List<Now.BasicBean> basicBeanList = loadAllBasic();
            if (basicBeanList.size() > 0) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                String countyId = sharedPreferences.getString(
                        context.getString(R.string.key_choose_forecast_city), basicBeanList.get(0).getId());
                if (countyId.equals(id)) {
                    sharedPreferences.edit().putString(
                            context.getString(R.string.key_choose_forecast_city), basicBeanList.get(0).getId()).apply();
                }
            }
        }
    }

    /**
     * 根据城市ID，从Basic表删除对应的数据
     */
    public void deleteItemsFromBasic(String id) {
        if (!TextUtils.isEmpty(id)) {
            db.delete(DataName.BASIC, "_id = ?", new String[]{id});
        }
    }

    /**
     * 根据城市ID，从Aqi表删除对应的数据
     */
    public void deleteItemsFromAqi(String id) {
        if (!TextUtils.isEmpty(id)) {
            db.delete(DataName.AQI, "_id = ?", new String[]{id});
        }
    }

    /**
     * 根据城市ID，从Suggestion表删除对应的数据
     */
    public void deleteItemsFromSuggestion(String id) {
        if (!TextUtils.isEmpty(id)) {
            db.delete(DataName.SUGGESTION, "_id = ?", new String[]{id});
        }
    }

    /**
     * 根据城市ID，从Now表删除对应的数据
     */
    public void deleteItemsFromNow(String id) {
        if (!TextUtils.isEmpty(id)) {
            db.delete(DataName.NOW, "_id = ?", new String[]{id});
        }
    }

    /**
     * 根据城市ID，从DailyForecast表删除对应的数据
     */
    public void deleteItemsFromDailyForecast(String id) {
        if (!TextUtils.isEmpty(id)) {
            db.delete(DataName.DAILY_FORECAST, "_id = ?", new String[]{id});
        }
    }

    /**
     * 根据城市ID，从HourlyForecast表删除对应的数据
     */
    public void deleteItemsFromHourlyForecast(String id) {
        if (!TextUtils.isEmpty(id)) {
            db.delete(DataName.HOURLY_FORECAST, "_id = ?", new String[]{id});
        }
    }

    /**
     * 从数据库返回数据库存在的城市的数目
     */
    public int getCityCount() {
        Cursor cursor = db.query(DataName.BASIC, null, null, null, null, null, null);
        return cursor.getCount();
    }

    /**
     * 更新Basic数据库的城市顺序
     */
    public void updateBasicOrder(List<Now.BasicBean> basicBeanList) {
        db.delete(DataName.BASIC, null, null);
        for (int i = 0; i < basicBeanList.size(); i++) {
            saveBasicBean(basicBeanList.get(i));
        }
    }

    /**
     * 保存WidgetDay中appWidgetId与countyId的对应关系
     */
    public void saveWidgetDay(int appWidgetId, String countyId) {
        if (appWidgetId != 0 && !TextUtils.isEmpty(countyId)) {
            ContentValues values = new ContentValues();
            values.put("appWidgetId", String.valueOf(appWidgetId));
            values.put("_id", countyId);
            db.insert(DataName.WIDGET_DAY, null, values);
        }
    }

    /**
     * 通过appWidgetId获取CountyId
     */
    public String getCountyIdInWidgetDay(int appWidgetId) {
        String countyId = null;
        if (appWidgetId != 0) {
            Cursor cursor = db.query(DataName.WIDGET_DAY, null, "appWidgetId = ?", new String[]{String.valueOf(appWidgetId)}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    countyId = cursor.getString(cursor.getColumnIndex("_id"));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return countyId;
    }

    /**
     * 根据appWidgetId数组，从WidgetDay表删除对应的数据
     */
    public void deleteItemsFromWidgetDay(int[] appWidgetIds) {
        if (appWidgetIds.length > 0) {
            for (int i = 0; i < appWidgetIds.length; i++) {
                db.delete(DataName.WIDGET_DAY, "appWidgetId = ?", new String[]{String.valueOf(appWidgetIds[i])});
            }
        }
    }

    public void updateItemFromWidgetDay(int appWidgetId, String countyId) {
        if (appWidgetId != 0 && !TextUtils.isEmpty(countyId)) {
            ContentValues values = new ContentValues();
            values.put("appWidgetId", String.valueOf(appWidgetId));
            values.put("_id", countyId);
            db.update(DataName.WIDGET_DAY, values, "appWidgetId = ?", new String[]{String.valueOf(appWidgetId)});
        }
    }
}
