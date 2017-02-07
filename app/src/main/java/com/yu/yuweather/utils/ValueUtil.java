package com.yu.yuweather.utils;

import android.content.Context;

import com.yu.yuweather.R;
import com.yu.yuweather.models.Now;

import java.util.List;

public class ValueUtil {

    public static String getCityName(Context context, List<Now.BasicBean> basicBeanList, String countyId) {
        String cityName = context.getResources().getString(R.string.setting_choose_forecast_city_summary);
        for (Now.BasicBean basicBean : basicBeanList) {
            if (countyId.equals(basicBean.getId())) {
                cityName = basicBean.getCity();
                break;
            }
        }
        return cityName;
    }
}
