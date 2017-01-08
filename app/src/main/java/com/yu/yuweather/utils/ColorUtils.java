package com.yu.yuweather.utils;

import android.content.Context;
import android.content.res.ColorStateList;

import com.yu.yuweather.R;

public class ColorUtils {
    public static int GetNowBackgroundColor(String nowCondCode) {
        if (nowCondCode.equals("100")) {
            return R.color.colorSunnyDark;
        } else if (nowCondCode.equals("101") || nowCondCode.equals("102") || nowCondCode.equals("104")) {
            return R.color.colorCloudyDark;
        } else if (nowCondCode.equals("103")) {
            return R.color.colorPartlyCloudyDark;
        } else if (nowCondCode.equals("200") || nowCondCode.equals("201") || nowCondCode.equals("202") || nowCondCode.equals("203") ||
                nowCondCode.equals("204") || nowCondCode.equals("205") || nowCondCode.equals("206") || nowCondCode.equals("207") || nowCondCode.equals("208") ||
                nowCondCode.equals("209") || nowCondCode.equals("210") || nowCondCode.equals("211") || nowCondCode.equals("212") || nowCondCode.equals("213")) {
            return R.color.colorWindDark;
        } else if (nowCondCode.equals("300") || nowCondCode.equals("301") || nowCondCode.equals("302") || nowCondCode.equals("303") ||
                nowCondCode.equals("304") || nowCondCode.equals("305") || nowCondCode.equals("306") || nowCondCode.equals("307") || nowCondCode.equals("308") ||
                nowCondCode.equals("309") || nowCondCode.equals("310") || nowCondCode.equals("311") || nowCondCode.equals("312") || nowCondCode.equals("313")) {
            return R.color.colorRainDark;
        } else if (nowCondCode.equals("400") || nowCondCode.equals("401") || nowCondCode.equals("402") ||
                nowCondCode.equals("403") || nowCondCode.equals("407")) {
            return R.color.colorSnowDark;
        } else if (nowCondCode.equals("404") || nowCondCode.equals("405") || nowCondCode.equals("406")) {
            return R.color.colorSleetDark;
        } else if (nowCondCode.equals("500") || nowCondCode.equals("501") || nowCondCode.equals("502") || nowCondCode.equals("503") || nowCondCode.equals("504") ||
                nowCondCode.equals("505") || nowCondCode.equals("506") || nowCondCode.equals("507") || nowCondCode.equals("508")) {
            return R.color.colorFogDark;
        } else if (nowCondCode.equals("900")) {
            return R.color.colorSunnyDark;
        } else if (nowCondCode.equals("901")) {
            return R.color.colorRainDark;
        } else {
            return R.color.colorUnknownDark;
        }
    }

    public static int GetWidgetDayBackground(String nowCondCode) {
        if (nowCondCode.equals("100")) {
            return R.drawable.shape_corner_round_sunny;
        } else if (nowCondCode.equals("101") || nowCondCode.equals("102") || nowCondCode.equals("104")) {
            return R.drawable.shape_corner_round_cloudy;
        } else if (nowCondCode.equals("103")) {
            return R.drawable.shape_corner_round_partly_cloudy;
        } else if (nowCondCode.equals("200") || nowCondCode.equals("201") || nowCondCode.equals("202") || nowCondCode.equals("203") ||
                nowCondCode.equals("204") || nowCondCode.equals("205") || nowCondCode.equals("206") || nowCondCode.equals("207") || nowCondCode.equals("208") ||
                nowCondCode.equals("209") || nowCondCode.equals("210") || nowCondCode.equals("211") || nowCondCode.equals("212") || nowCondCode.equals("213")) {
            return R.drawable.shape_corner_round_wind;
        } else if (nowCondCode.equals("300") || nowCondCode.equals("301") || nowCondCode.equals("302") || nowCondCode.equals("303") ||
                nowCondCode.equals("304") || nowCondCode.equals("305") || nowCondCode.equals("306") || nowCondCode.equals("307") || nowCondCode.equals("308") ||
                nowCondCode.equals("309") || nowCondCode.equals("310") || nowCondCode.equals("311") || nowCondCode.equals("312") || nowCondCode.equals("313")) {
            return R.drawable.shape_corner_round_rain;
        } else if (nowCondCode.equals("400") || nowCondCode.equals("401") || nowCondCode.equals("402") ||
                nowCondCode.equals("403") || nowCondCode.equals("407")) {
            return R.drawable.shape_corner_round_snow;
        } else if (nowCondCode.equals("404") || nowCondCode.equals("405") || nowCondCode.equals("406")) {
            return R.drawable.shape_corner_round_sleet;
        } else if (nowCondCode.equals("500") || nowCondCode.equals("501") || nowCondCode.equals("502") || nowCondCode.equals("503") || nowCondCode.equals("504") ||
                nowCondCode.equals("505") || nowCondCode.equals("506") || nowCondCode.equals("507") || nowCondCode.equals("508")) {
            return R.drawable.shape_corner_round_fog;
        } else if (nowCondCode.equals("900")) {
            return R.drawable.shape_corner_round_sunny;
        } else if (nowCondCode.equals("901")) {
            return R.drawable.shape_corner_round_rain;
        } else {
            return R.drawable.shape_corner_round_unknown;
        }
    }

    private static int i = 0;

    public static int GetForecastBackgroundColor(String icon) {
        if (icon.contains("clear")) {
            if ((i % 2) == 0) {
                i++;
                return R.color.colorSunnyDark;
            } else {
                i++;
                return R.color.colorSunnyLight;
            }
        } else if (icon.contains("rain")) {
            if ((i % 2) == 0) {
                i++;
                return R.color.colorRainDark;
            } else {
                i++;
                return R.color.colorRainLight;
            }
        } else if (icon.contains("snow")) {
            if ((i % 2) == 0) {
                i++;
                return R.color.colorSnowDark;
            } else {
                i++;
                return R.color.colorSnowLight;
            }
        } else if (icon.contains("sleet")) {
            if ((i % 2) == 0) {
                i++;
                return R.color.colorSleetDark;
            } else {
                i++;
                return R.color.colorSleetLight;
            }
        } else if (icon.contains("wind")) {
            if ((i % 2) == 0) {
                i++;
                return R.color.colorWindDark;
            } else {
                i++;
                return R.color.colorWindLight;
            }
        } else if (icon.contains("fog")) {
            if ((i % 2) == 0) {
                i++;
                return R.color.colorFogDark;
            } else {
                i++;
                return R.color.colorFogLight;
            }
        } else if (icon.contains("cloudy")) {
            if ((i % 2) == 0) {
                i++;
                return R.color.colorCloudyDark;
            } else {
                i++;
                return R.color.colorCloudyLight;
            }
        } else if (icon.contains("partly-cloudy")) {
            if ((i % 2) == 0) {
                i++;
                return R.color.colorPartlyCloudyDark;
            } else {
                i++;
                return R.color.colorPartlyCloudyLight;
            }
        } else {
            if ((i % 2) == 0) {
                i++;
                return R.color.colorUnknownDark;
            } else {
                i++;
                return R.color.colorUnknownLight;
            }
        }
    }

    public static ColorStateList GetResourceColorStateList(Context context, int id) {
        return context.getResources().getColorStateList(id);
    }

    public static int GetResourceColor(Context context,int id){
        return context.getResources().getColor(id);
    }
}
