package com.yu.yuweather.utils;

import com.yu.yuweather.R;

public class IconUtils {
    public static int GetCondIconResourcesId(String nowCondCode) {
        if (nowCondCode.equals("100")) {
            return R.drawable.ic_sunny;
        } else if (nowCondCode.equals("101")) {
            return R.drawable.ic_cloudy;
        } else if (nowCondCode.equals("102")) {
            return R.drawable.ic_few_clouds;
        } else if (nowCondCode.equals("103")) {
            return R.drawable.ic_partly_cloudy;
        } else if (nowCondCode.equals("104")) {
            return R.drawable.ic_overcast;
        } else if (nowCondCode.equals("200")) {
            return R.drawable.ic_windy;
        } else if (nowCondCode.equals("201")) {
            return R.drawable.ic_windy;
        } else if (nowCondCode.equals("202")) {
            return R.drawable.ic_windy;
        } else if (nowCondCode.equals("203")) {
            return R.drawable.ic_windy;
        } else if (nowCondCode.equals("204")) {
            return R.drawable.ic_windy;
        } else if (nowCondCode.equals("205")) {
            return R.drawable.ic_strong_breeze;
        } else if (nowCondCode.equals("206")) {
            return R.drawable.ic_strong_breeze;
        } else if (nowCondCode.equals("207")) {
            return R.drawable.ic_strong_breeze;
        } else if (nowCondCode.equals("208")) {
            return R.drawable.ic_strong_gale;
        } else if (nowCondCode.equals("209")) {
            return R.drawable.ic_strong_gale;
        } else if (nowCondCode.equals("210")) {
            return R.drawable.ic_strong_gale;
        } else if (nowCondCode.equals("211")) {
            return R.drawable.ic_strong_gale;
        } else if (nowCondCode.equals("212")) {
            return R.drawable.ic_strong_gale;
        } else if (nowCondCode.equals("213")) {
            return R.drawable.ic_strong_gale;
        } else if (nowCondCode.equals("300")) {
            return R.drawable.ic_shower_rain;
        } else if (nowCondCode.equals("301")) {
            return R.drawable.ic_shower_rain;
        } else if (nowCondCode.equals("302")) {
            return R.drawable.ic_thundershower;
        } else if (nowCondCode.equals("303")) {
            return R.drawable.ic_thundershower;
        } else if (nowCondCode.equals("304")) {
            return R.drawable.ic_hail;
        } else if (nowCondCode.equals("305")) {
            return R.drawable.ic_light_rain;
        } else if (nowCondCode.equals("306")) {
            return R.drawable.ic_moderate_rain;
        } else if (nowCondCode.equals("307")) {
            return R.drawable.ic_heavy_rain;
        } else if (nowCondCode.equals("308")) {
            return R.drawable.ic_heavy_rain;
        } else if (nowCondCode.equals("309")) {
            return R.drawable.ic_light_rain;
        } else if (nowCondCode.equals("310")) {
            return R.drawable.ic_heavy_rain;
        } else if (nowCondCode.equals("311")) {
            return R.drawable.ic_heavy_rain;
        } else if (nowCondCode.equals("312")) {
            return R.drawable.ic_heavy_rain;
        } else if (nowCondCode.equals("313")) {
            return R.drawable.ic_hail;
        } else if (nowCondCode.equals("400")) {
            return R.drawable.ic_light_snow;
        } else if (nowCondCode.equals("401")) {
            return R.drawable.ic_moderate_snow;
        } else if (nowCondCode.equals("402")) {
            return R.drawable.ic_heavy_snow;
        } else if (nowCondCode.equals("403")) {
            return R.drawable.ic_heavy_snow;
        } else if (nowCondCode.equals("404")) {
            return R.drawable.ic_sleet;
        } else if (nowCondCode.equals("405")) {
            return R.drawable.ic_sleet;
        } else if (nowCondCode.equals("406")) {
            return R.drawable.ic_sleet;
        } else if (nowCondCode.equals("407")) {
            return R.drawable.ic_light_snow;
        } else if (nowCondCode.equals("500")) {
            return R.drawable.ic_mist;
        } else if (nowCondCode.equals("501")) {
            return R.drawable.ic_foggy;
        } else if (nowCondCode.equals("502")) {
            return R.drawable.ic_haze;
        } else if (nowCondCode.equals("503")) {
            return R.drawable.ic_sand;
        } else if (nowCondCode.equals("504")) {
            return R.drawable.ic_dust;
        } else if (nowCondCode.equals("507")) {
            return R.drawable.ic_duststorm;
        } else if (nowCondCode.equals("508")) {
            return R.drawable.ic_sandstorm;
        } else if (nowCondCode.equals("900")) {
            return R.drawable.ic_hot;
        } else if (nowCondCode.equals("901")) {
            return R.drawable.ic_cold;
        } else {
            return R.drawable.ic_unknown;
        }
    }

    public static int GetForecastIconResourcesId(String icon) {
        if (icon.contains("clear")) {
            return R.drawable.ic_sunny;
        } else if (icon.contains("rain")) {
            return R.drawable.ic_heavy_rain;
        } else if (icon.contains("snow")) {
            return R.drawable.ic_heavy_snow;
        } else if (icon.contains("sleet")) {
            return R.drawable.ic_sleet;
        } else if (icon.contains("wind")) {
            return R.drawable.ic_windy;
        } else if (icon.contains("fog")) {
            return R.drawable.ic_foggy;
        } else if (icon.contains("cloudy")) {
            return R.drawable.ic_cloudy;
        } else if (icon.contains("partly-cloudy")) {
            return R.drawable.ic_partly_cloudy;
        } else {
            return R.drawable.ic_unknown;
        }
    }
}
