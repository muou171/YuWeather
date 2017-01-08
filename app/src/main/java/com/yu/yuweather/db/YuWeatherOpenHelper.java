package com.yu.yuweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yu.yuweather.global.DataName;

public class YuWeatherOpenHelper extends SQLiteOpenHelper {

    /**
     * 城市基本信息表建表语句
     */
    private static final String CREATE_BASIC = "CREATE TABLE " + DataName.BASIC + " ("
            + "_id TEXT PRIMARY KEY, "
            + "city TEXT, "
            + "lat TEXT, "
            + "lon TEXT, "
            + "loc TEXT)";
    /**
     * 实况天气表建表语句
     */
    private static final String CREATE_NOW = "CREATE TABLE " + DataName.NOW + " ("
            + "_id TEXT PRIMARY KEY, "
            + "code TEXT, "
            + "txt TEXT, "
            + "hum TEXT, "
            + "pcpn TEXT, "
            + "tmp TEXT, "
            + "dir TEXT)";
    /**
     * 空气质量指数表建表语句
     */
    private static final String CREATE_AQI = "CREATE TABLE " + DataName.AQI + " ("
            + "_id TEXT PRIMARY KEY, "
            + "aqi TEXT, "
            + "co TEXT, "
            + "no2 TEXT, "
            + "o3 TEXT, "
            + "pm10 TEXT, "
            + "pm25 TEXT, "
            + "qlty TEXT, "
            + "so2 TEXT)";
    /**
     * 生活指数表建表语句
     */
    private static final String CREATE_SUGGESTION = "CREATE TABLE " + DataName.SUGGESTION + " ("
            + "_id TEXT PRIMARY KEY, "
            + "comf_brf TEXT, " + "comf_txt TEXT, "
            + "cw_brf TEXT, " + "cw_txt TEXT, "
            + "drsg_brf TEXT, " + "drsg_txt TEXT, "
            + "flu_brf TEXT, " + "flu_txt TEXT, "
            + "sport_brf TEXT, " + "sport_txt TEXT, "
            + "trav_brf TEXT, " + "trav_txt TEXT, "
            + "uv_brf TEXT, " + "uv_txt TEXT)";

    /**
     * 每日天气预报表建表语句
     */
    private static final String CREATE_DAILY_FORECAST = "CREATE TABLE " + DataName.DAILY_FORECAST + " ("
            + "_id TEXT, "
            + "time TEXT, "
            + "summary TEXT, "
            + "icon TEXT, "
            + "sunriseTime TEXT, "
            + "sunsetTime TEXT, "
            + "precipProbability TEXT, "
            + "temperatureMin TEXT, "
            + "temperatureMinTime TEXT, "
            + "temperatureMax TEXT, "
            + "temperatureMaxTime TEXT, "
            + "humidity TEXT, "
            + "pressure TEXT)";
    /**
     * 每小时天气预报表建表语句
     */
    private static final String CREATE_HOURLY_FORECAST = "CREATE TABLE " + DataName.HOURLY_FORECAST + " ("
            + "_id TEXT, "
            + "time TEXT, "
            + "summary TEXT, "
            + "icon TEXT, "
            + "precipProbability TEXT, "
            + "temperature TEXT, "
            + "humidity TEXT, "
            + "pressure TEXT)";

    /**
     * WidgetDay的CountyId与appWidgetId对应表建表语句
     */
    private static final String CREATE_WIDGET_DAY = "CREATE TABLE " + DataName.WIDGET_DAY + " ("
            + "appWidgetId TEXT PRIMARY KEY, "
            + "_id TEXT)";

    public YuWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 先导入的数据库版本为1，要往该数据库添加这些表
        sqLiteDatabase.execSQL(CREATE_BASIC);// 创建城市基本信息表
        sqLiteDatabase.execSQL(CREATE_AQI);// 创建空气质量指数表
        sqLiteDatabase.execSQL(CREATE_SUGGESTION);// 创建生活指数表
        sqLiteDatabase.execSQL(CREATE_NOW);// 创建实况天气表
        sqLiteDatabase.execSQL(CREATE_DAILY_FORECAST);// 创建每日天气预报表
        sqLiteDatabase.execSQL(CREATE_HOURLY_FORECAST);// 创建每小时天气预报表
        sqLiteDatabase.execSQL(CREATE_WIDGET_DAY);
    }
}
