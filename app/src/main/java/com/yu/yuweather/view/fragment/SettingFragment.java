package com.yu.yuweather.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yu.yuweather.global.DataName;
import com.yu.yuweather.view.activity.CityManagementAndSettingActivity;
import com.yu.yuweather.view.activity.MainActivity;

public class SettingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initListener();
        return super.onCreateView(inflater, container, savedInstanceState);
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
}
