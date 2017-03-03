package com.yu.yuweather.view.fragment;

import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.widget.TimePicker;
import android.widget.Toast;

import com.yu.yuweather.R;
import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.global.DataName;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.utils.NotificationUtils;
import com.yu.yuweather.utils.ValueUtil;
import com.yu.yuweather.view.activity.CityManagementAndSettingActivity;
import com.yu.yuweather.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SettingFragment extends PreferenceFragment {

    private YuWeatherDB yuWeatherDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        yuWeatherDB = YuWeatherDB.getInstance(getActivity());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        initForecastPart(sharedPreferences);
        initNotificationPart(sharedPreferences);
        initListener();
    }

    // 初始化预报的部分
    private void initForecastPart(SharedPreferences sharedPreferences) {
        CheckBoxPreference forecastTimeSwitch = (CheckBoxPreference) findPreference(getString(R.string.key_forecast_time));
        Preference setForecastTime = findPreference(getString(R.string.key_set_forecast_time));
        setForecastTime.setSummary(sharedPreferences.getString(getString(R.string.key_set_forecast_time), "07:00"));

        ListPreference chooseForecastCity = (ListPreference) findPreference(getString(R.string.key_choose_forecast_city));

        final List<Now.BasicBean> basicBeanList = yuWeatherDB.loadAllBasic();
        List<String> cityEntries = new ArrayList<>();
        List<String> countyIdEntryValues = new ArrayList<>();
        for (Now.BasicBean basicBean : basicBeanList) {
            cityEntries.add(basicBean.getCity());
            countyIdEntryValues.add(basicBean.getId());
        }
        chooseForecastCity.setEntries(cityEntries.toArray(new String[]{}));
        chooseForecastCity.setEntryValues(countyIdEntryValues.toArray(new String[]{}));

        if (sharedPreferences.getBoolean(getString(R.string.key_forecast_time), false)) {
            setForecastTime.setEnabled(true);
            chooseForecastCity.setEnabled(true);
            if (basicBeanList.size() == 0) {
                forecastTimeSwitch.setChecked(false);
                Toast.makeText(getActivity(), "请添加至少一个城市", Toast.LENGTH_SHORT).show();
                setForecastTime.setEnabled(false);
                chooseForecastCity.setEnabled(false);
            } else {
                String countyId = sharedPreferences.getString(getString(R.string.key_choose_forecast_city), countyIdEntryValues.get(0));
                chooseForecastCity.setSummary(ValueUtil.getCityName(getActivity(), basicBeanList, countyId));
                chooseForecastCity.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object o) {
                        preference.setSummary(ValueUtil.getCityName(getActivity(), basicBeanList, (String) o));
                        return true;
                    }
                });
            }
        } else {
            setForecastTime.setEnabled(false);
            chooseForecastCity.setEnabled(false);
            chooseForecastCity.setSummary(getString(R.string.setting_choose_forecast_city_summary));
            if (basicBeanList.size() > 0) {
                sharedPreferences.edit().putString(getString(R.string.key_choose_forecast_city), countyIdEntryValues.get(0)).apply();
            }
        }
    }

    // 初始化通知的部分
    private void initNotificationPart(SharedPreferences sharedPreferences) {
        CheckBoxPreference notificationSwitch = (CheckBoxPreference) findPreference(getString(R.string.key_notification));
        CheckBoxPreference notificationCanCleared = (CheckBoxPreference) findPreference(getString(R.string.key_notification_can_cleared));
        CheckBoxPreference notificationHideIcon = (CheckBoxPreference) findPreference(getString(R.string.key_notification_hide_icon));

        ListPreference chooseNotificationCity = (ListPreference) findPreference(getString(R.string.key_choose_notification_city));

        final List<Now.BasicBean> basicBeanList = yuWeatherDB.loadAllBasic();
        List<String> cityEntries = new ArrayList<>();
        List<String> countyIdEntryValues = new ArrayList<>();
        for (Now.BasicBean basicBean : basicBeanList) {
            cityEntries.add(basicBean.getCity());
            countyIdEntryValues.add(basicBean.getId());
        }
        chooseNotificationCity.setEntries(cityEntries.toArray(new String[]{}));
        chooseNotificationCity.setEntryValues(countyIdEntryValues.toArray(new String[]{}));

        if (sharedPreferences.getBoolean(getString(R.string.key_notification), false)) {
            notificationCanCleared.setEnabled(true);
            notificationHideIcon.setEnabled(true);
            chooseNotificationCity.setEnabled(true);
            if (basicBeanList.size() == 0) {
                notificationSwitch.setChecked(false);
                Toast.makeText(getActivity(), "请添加至少一个城市", Toast.LENGTH_SHORT).show();
                notificationCanCleared.setEnabled(false);
                notificationHideIcon.setEnabled(false);
                chooseNotificationCity.setEnabled(false);
            } else {
                String countyId = sharedPreferences.getString(getString(R.string.key_choose_notification_city), countyIdEntryValues.get(0));
                chooseNotificationCity.setSummary(ValueUtil.getCityName(getActivity(), basicBeanList, countyId));
                chooseNotificationCity.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object o) {
                        preference.setSummary(ValueUtil.getCityName(getActivity(), basicBeanList, (String) o));
                        return true;
                    }
                });
            }
        } else {
            notificationCanCleared.setEnabled(false);
            notificationHideIcon.setEnabled(false);
            chooseNotificationCity.setEnabled(false);
            chooseNotificationCity.setSummary(getString(R.string.setting_choose_notification_city_summary));
            if (basicBeanList.size() > 0) {
                sharedPreferences.edit().putString(getString(R.string.key_choose_notification_city), countyIdEntryValues.get(0)).apply();
            }
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
                if (yuWeatherDB.loadAllBasic().size() > 0) {
                    // 开启每日预报的通知
                    NotificationUtils.startForecastService(getActivity());
                }
            } else {
                // 关闭每日预报的通知
                NotificationUtils.stopForecastService(getActivity());
                ((NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE))
                        .cancel(NotificationUtils.FORECAST_ID);
            }
            return true;
        } else if (preference.getKey().equals(getString(R.string.key_choose_forecast_city))) {
            initForecastPart(sharedPreferences);
            NotificationUtils.startForecastService(getActivity());
            return true;
        } else if (preference.getKey().equals(getString(R.string.key_set_forecast_time))) {
            showSetForecastTime(sharedPreferences);
            return true;
        } else if (preference.getKey().equals(getString(R.string.key_notification))) {
            initNotificationPart(sharedPreferences);
            if (sharedPreferences.getBoolean(getString(R.string.key_notification), false)) {
                // 开启通知栏显示通知
                NotificationUtils.startNotificationService(getActivity());
            } else {
                // 关闭通知栏显示通知
                NotificationUtils.stopNotificationService(getActivity());
                ((NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE))
                        .cancel(NotificationUtils.NOTIFICATION_ID);
            }
            return true;
        } else if (preference.getKey().equals(getString(R.string.key_choose_notification_city))) {
            initNotificationPart(sharedPreferences);
            ((NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE))
                    .cancel(NotificationUtils.NOTIFICATION_ID);
            NotificationUtils.startNotificationService(getActivity());
            return true;
        } else if (preference.getKey().equals(getString(R.string.key_notification_can_cleared))) {
            ((NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE))
                    .cancel(NotificationUtils.NOTIFICATION_ID);
            NotificationUtils.startNotificationService(getActivity());
            return true;
        } else if (preference.getKey().equals(getString(R.string.key_notification_hide_icon))) {
            ((NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE))
                    .cancel(NotificationUtils.NOTIFICATION_ID);
            NotificationUtils.startNotificationService(getActivity());
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
                NotificationUtils.startForecastService(getActivity());
            }
        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
        timePickerDialog.setCanceledOnTouchOutside(true);
        timePickerDialog.show();
    }

}
