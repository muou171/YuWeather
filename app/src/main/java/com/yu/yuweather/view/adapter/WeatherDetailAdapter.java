package com.yu.yuweather.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yu.yuweather.view.fragment.WeatherDetailFragment;

import java.util.List;

public class WeatherDetailAdapter extends FragmentPagerAdapter {
    List<WeatherDetailFragment> weatherDetailFragmentList;

    public WeatherDetailAdapter(FragmentManager fm, List<WeatherDetailFragment> weatherDetailFragmentList) {
        super(fm);
        this.weatherDetailFragmentList = weatherDetailFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return weatherDetailFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return weatherDetailFragmentList.size();
    }
}
