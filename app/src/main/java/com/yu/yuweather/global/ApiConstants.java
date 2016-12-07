package com.yu.yuweather.global;

public class ApiConstants {
    private static final String H_WEATHER_API_DEFAULT = "https://api.heweather.com/v5/";
    private static final String H_WEATHER_API_KEY = "&key=56e14e6d477e44ab95c51ad4aeb20dec";
    private static final String D_WEATHER_API_DEFAULT = "https://api.darksky.net/forecast/";
    private static final String D_WEATHER_API_KEY = "a6423d62716988e34cc3c71f4aae719c/";
    private static final String LOCATION_API_DEFAULT = "https://restapi.amap.com/v3/geocode/regeo?output=json&location=";
    private static final String LOCATION_API_KEY = "&key=4dfb7607a28b2ddb5f8e3859623b0c46";

    public static String GetNowApiAddress(String countyId) {
        return H_WEATHER_API_DEFAULT + "now?city=" + countyId + H_WEATHER_API_KEY;
    }

    public static String GetAqiApiAddress(String countyId) {
        return H_WEATHER_API_DEFAULT + "aqi?city=" + countyId + H_WEATHER_API_KEY;
    }

    public static String GetSuggestionApiAddress(String countyId) {
        return H_WEATHER_API_DEFAULT + "suggestion?city=" + countyId + H_WEATHER_API_KEY;
    }

    public static String GetDailyApiAddress(String latitude, String longitude) {
        return D_WEATHER_API_DEFAULT + D_WEATHER_API_KEY + latitude + "," + longitude + "?lang=zh&units=si&exclude=currently,minutely,alerts,flags,hourly";
    }

    public static String GetHourlyApiAddress(String latitude, String longitude) {
        return D_WEATHER_API_DEFAULT + D_WEATHER_API_KEY + latitude + "," + longitude + "?lang=zh&units=si&exclude=currently,minutely,alerts,flags,daily";
    }

    public static String GetLocationApiAddress(double latitude, double longitude) {
        return LOCATION_API_DEFAULT + longitude + "," + latitude + LOCATION_API_KEY;
    }

}
