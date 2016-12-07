package com.yu.yuweather.utils;

import android.content.ContentValues;
import android.text.TextUtils;

import java.text.SimpleDateFormat;

public class Utils {

    public static String ssTimeToDataTimeDay(String ssTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        return simpleDateFormat.format(Long.valueOf(ssTime + "000"));
    }

    public static String ssTimeToDataTimeMonthAndDay(String ssTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        return simpleDateFormat.format(Long.valueOf(ssTime + "000"));
    }

    public static String ssTimeToDataTimeHourAndMinute(String ssTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(Long.valueOf(ssTime + "000"));
    }

    public static String DecimalFormatInteger(String decimal) {
        return String.valueOf((int) (Double.valueOf(decimal) + 0.5));
    }

    public static String ProbabilityFormatPercentageInteger(String probability) {
        return String.valueOf((int) (Double.valueOf(probability) * 100));
    }

    public static String BasicUpdateLocSub(String loc) {
        return loc.substring(loc.length() - 5);
    }

    public static ContentValues WeatherContentIsEmptyHandle(ContentValues values, String key, String value) {
        if (TextUtils.isEmpty(value)) {
            values.put(key, "N/A");
        } else {
            values.put(key, value);
        }
        return values;
    }
}
