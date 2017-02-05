package com.yu.yuweather.view.fragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.widget.TimePicker;

import com.yu.yuweather.R;
import com.yu.yuweather.global.DataName;
import com.yu.yuweather.utils.NotificationUtils;
import com.yu.yuweather.view.activity.CityManagementAndSettingActivity;
import com.yu.yuweather.view.activity.MainActivity;

import java.util.Calendar;

public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        initForecastPart(sharedPreferences);
        initNotificationPart(sharedPreferences);
        initListener();
    }

    // 初始化预报的部分
    private void initForecastPart(SharedPreferences sharedPreferences) {
        Preference setForecastTime = findPreference(getString(R.string.key_set_forecast_time));
        setForecastTime.setSummary(sharedPreferences.getString(getString(R.string.key_set_forecast_time), "07:00"));

        if (sharedPreferences.getBoolean(getString(R.string.key_forecast_time), false)) {
            setForecastTime.setEnabled(true);
        } else {
            setForecastTime.setEnabled(false);
        }
    }

    // 初始化通知的部分
    private void initNotificationPart(SharedPreferences sharedPreferences) {
        Preference notificationCanCleared = findPreference(getString(R.string.key_notification_can_cleared));
        Preference notificationHideIcon = findPreference(getString(R.string.key_notification_hide_icon));

        if (sharedPreferences.getBoolean(getString(R.string.key_notification), false)) {
            notificationCanCleared.setEnabled(true);
            notificationHideIcon.setEnabled(true);
        } else {
            notificationCanCleared.setEnabled(false);
            notificationHideIcon.setEnabled(false);
        }
    }

    private void initListener() {
        ((CityManagementAndSettingActivity) getActivity()).setOnCityManagementAndSettingActivityBackPressedListener(new CityManagementAndSettingActivity.OnCityManagementAndSettingActivityBackPressedListener() {
            @Override
            public void setBackPressed() {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(DataName.WIDGET_DAY_COUNTY_ID, "");
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (preference.getKey().equals(getString(R.string.key_forecast_time))) {
            initForecastPart(sharedPreferences);
            if (sharedPreferences.getBoolean(getString(R.string.key_forecast_time), false)) {
                // 开启每日预报的通知
                NotificationUtils.startFirstForecastService(getActivity());
            } else {
                // 关闭每日预报的通知
                NotificationUtils.stopFirstForecastService(getActivity());
                NotificationUtils.stopForecastService(getActivity());
            }
            return true;
        } else if (preference.getKey().equals(getString(R.string.key_set_forecast_time))) {
            showSetForecastTime(sharedPreferences);
            return true;
        } else if (preference.getKey().equals(getString(R.string.key_notification))) {
            initNotificationPart(sharedPreferences);
            if (sharedPreferences.getBoolean(getString(R.string.key_notification), false)) {
                // 开启通知栏显示通知
            } else {
                // 关闭通知栏显示通知
            }
            return true;
        } else if (preference.getKey().equals(getString(R.string.key_notification_can_cleared))) {
            return true;
        } else if (preference.getKey().equals(getString(R.string.key_notification_hide_icon))) {
            return true;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    // 显示设置预报时间的对话框
    private void showSetForecastTime(final SharedPreferences sharedPreferences) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                String hour;
                String minute;
                if (i < 10) {
                    hour = "0" + i;
                } else {
                    hour = "" + i;
                }
                if (i1 < 10) {
                    minute = "0" + i1;
                } else {
                    minute = "" + i1;
                }
                editor.putString(getActivity().getString(R.string.key_set_forecast_time), hour + ":" + minute);
                editor.apply();
                initForecastPart(sharedPreferences);
                NotificationUtils.stopFirstForecastService(getActivity());
                NotificationUtils.stopForecastService(getActivity());
                NotificationUtils.startFirstForecastService(getActivity());
            }
        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
        timePickerDialog.setCanceledOnTouchOutside(true);
        timePickerDialog.show();
    }
}
