package com.yu.yuweather.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yu.yuweather.R;
import com.yu.yuweather.models.DailyForecast;

public class UIUtils {

    /**
     * 显示网络错误的对话框
     */
    public static void showNetworkErrorAlertDialog(final Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).
                setTitle("无网络连接").setMessage("需要网络连接获取数据").setNegativeButton("取消", null)
                .setPositiveButton("打开 WI-FI 设定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .create();
        alertDialog.show();
    }

    /**
     * 显示GPS错误的对话框
     */
    public static void showGPSErrorAlertDialog(final Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).
                setTitle("未打开位置信息").setMessage("需要位置信息获取位置").setNegativeButton("取消", null)
                .setPositiveButton("打开位置信息", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .create();
        alertDialog.show();
    }

    /**
     * 显示等待的进度对话框
     */
    public static void showWaitProgressDialog(ProgressDialog progressDialog) {
        progressDialog.setMessage("正在获取数据");
        progressDialog.show();
    }

    /**
     * 关闭等待的进度对话框
     */
    public static void dismissWaitProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 显示每日预报的天气详情的对话框
     */
    public static void showDailyForecastDetailDialog(Activity activity, DailyForecast.DailyBean.DataBean dataBean) {
        Dialog dialog = new Dialog(activity);
        View view = View.inflate(activity, R.layout.dialog_daily_forecast_detail, null);
        ImageView ivDailyDataSunriseIcon = (ImageView) view.findViewById(R.id.iv_daily_data_sunrise_icon);
        TextView tvDailyDataSunriseTime = (TextView) view.findViewById(R.id.tv_daily_data_sunrise_time);
        ImageView ivDailyDataSunsetIcon = (ImageView) view.findViewById(R.id.iv_daily_data_sunset_icon);
        TextView tvDailyDataSunsetTime = (TextView) view.findViewById(R.id.tv_daily_data_sunset_time);
        TextView tvDailyDataTemperatureMinPrompt = (TextView) view.findViewById(R.id.tv_daily_data_temperature_min_prompt);
        TextView tvDailyDataTemperatureMin = (TextView) view.findViewById(R.id.tv_daily_data_temperature_min);
        TextView tvDailyDataTemperatureMinUnit = (TextView) view.findViewById(R.id.tv_daily_data_temperature_min_unit);
        TextView tvDailyDataTemperatureMinTimePrompt = (TextView) view.findViewById(R.id.tv_daily_data_temperature_min_time_prompt);
        TextView tvDailyDataTemperatureMinTime = (TextView) view.findViewById(R.id.tv_daily_data_temperature_min_time);
        TextView tvDailyDataTemperatureMaxPrompt = (TextView) view.findViewById(R.id.tv_daily_data_temperature_max_prompt);
        TextView tvDailyDataTemperatureMax = (TextView) view.findViewById(R.id.tv_daily_data_temperature_max);
        TextView tvDailyDataTemperatureMaxUnit = (TextView) view.findViewById(R.id.tv_daily_data_temperature_max_unit);
        TextView tvDailyDataTemperatureMaxTimePrompt = (TextView) view.findViewById(R.id.tv_daily_data_temperature_max_time_prompt);
        TextView tvDailyDataTemperatureMaxTime = (TextView) view.findViewById(R.id.tv_daily_data_temperature_max_time);
        ImageView ivUmbrella = (ImageView) view.findViewById(R.id.iv_umbrella);
        TextView tvDailyDataPreProbability = (TextView) view.findViewById(R.id.tv_daily_data_pre_probability);
        TextView tvDailyDataPreProbabilityUnit = (TextView) view.findViewById(R.id.tv_daily_data_pre_probability_unit);
        ImageView ivHumidity = (ImageView) view.findViewById(R.id.iv_humidity);
        TextView tvDailyDataHumidity = (TextView) view.findViewById(R.id.tv_daily_data_humidity);
        TextView tvDailyDataHumidityUnit = (TextView) view.findViewById(R.id.tv_daily_data_humidity_unit);
        ImageView ivPressure = (ImageView) view.findViewById(R.id.iv_pressure);
        TextView tvDailyDataPressure = (TextView) view.findViewById(R.id.tv_daily_data_pressure);
        TextView tvDailyDataPressureUnit = (TextView) view.findViewById(R.id.tv_daily_data_pressure_unit);

        String icon = dataBean.getIcon();
        if (icon.equals("snow")) {
            ivDailyDataSunriseIcon.setImageResource(R.drawable.ic_sunrise_gray);
            tvDailyDataSunriseTime.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayDark));
            ivDailyDataSunsetIcon.setImageResource(R.drawable.ic_sunset_gray);
            tvDailyDataSunsetTime.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayDark));
            tvDailyDataTemperatureMinPrompt.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayDark));
            tvDailyDataTemperatureMin.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayDark));
            tvDailyDataTemperatureMinUnit.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayDark));
            tvDailyDataTemperatureMinTimePrompt.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayDark));
            tvDailyDataTemperatureMinTime.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayDark));
            tvDailyDataTemperatureMaxPrompt.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayDark));
            tvDailyDataTemperatureMax.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayDark));
            tvDailyDataTemperatureMaxUnit.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayDark));
            tvDailyDataTemperatureMaxTimePrompt.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayDark));
            tvDailyDataTemperatureMaxTime.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayDark));
            ivUmbrella.setImageResource(R.drawable.ic_umbrella_gray);
            tvDailyDataPreProbability.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayLight));
            tvDailyDataPreProbabilityUnit.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayLight));
            ivHumidity.setImageResource(R.drawable.ic_humidity_gray);
            tvDailyDataHumidity.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayLight));
            tvDailyDataHumidityUnit.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayLight));
            ivPressure.setImageResource(R.drawable.ic_pressure_gray);
            tvDailyDataPressure.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayLight));
            tvDailyDataPressureUnit.setTextColor(ColorUtils.GetResourceColorStateList(activity, R.color.colorGrayLight));
        }
        tvDailyDataSunriseTime.setText(dataBean.getSunriseTime());
        tvDailyDataSunsetTime.setText(dataBean.getSunsetTime());
        tvDailyDataTemperatureMin.setText(dataBean.getTemperatureMin());
        tvDailyDataTemperatureMinTime.setText(dataBean.getTemperatureMinTime());
        tvDailyDataTemperatureMax.setText(dataBean.getTemperatureMax());
        tvDailyDataTemperatureMaxTime.setText(dataBean.getTemperatureMaxTime());
        tvDailyDataPreProbability.setText(dataBean.getPrecipProbability());
        tvDailyDataHumidity.setText(dataBean.getHumidity());
        tvDailyDataPressure.setText(dataBean.getPressure());

        view.setBackgroundResource(ColorUtils.GetForecastBackgroundColor(icon));
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        params.width = displayMetrics.widthPixels;
        params.height = (int) (displayMetrics.heightPixels * 0.3);
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }
}
