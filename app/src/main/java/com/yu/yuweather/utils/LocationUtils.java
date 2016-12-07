package com.yu.yuweather.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.text.TextUtils;

import com.yu.yuweather.global.ApiConstants;

public class LocationUtils {
    public static void GetTheLocation(Location location, final GetLocationListener listener) {
        // 经度
        final double longitude = location.getLongitude();
        // 维度
        final double latitude = location.getLatitude();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsUtil.sendHttpsRequest(ApiConstants.GetLocationApiAddress(latitude, longitude)
                        , new HttpsUtil.HttpsCallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                String county = JSONUtils.LocationJSONGetCounty(response);
                                if (!TextUtils.isEmpty(county)) {
                                    listener.getLocation(county);
                                }
                            }

                            @Override
                            public void onError(Exception e) {
                                e.printStackTrace();
                            }
                        });
            }
        }).start();
    }

    public static boolean isGPSAvailable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        }
        return false;
    }

    public interface GetLocationListener {
        void getLocation(String county);
    }
}
