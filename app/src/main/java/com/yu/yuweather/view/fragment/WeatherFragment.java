package com.yu.yuweather.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yu.yuweather.R;
import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.global.DataName;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.view.activity.BaseActivity;
import com.yu.yuweather.view.activity.MainActivity;
import com.yu.yuweather.view.adapter.WeatherDetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class WeatherFragment extends Fragment {

    private Toolbar tWeatherToolbar;
    private ViewPager vpWeatherContent;
    private YuWeatherDB yuWeatherDB;
    private List<WeatherDetailFragment> weatherDetailFragmentList = new ArrayList<>();
    private List<Now.BasicBean> basicBeanList;

    private OnViewPagerPositionListener onViewPagerPositionListener;
    private WeatherDetailAdapter weatherDetailAdapter;

    public void setOnViewPagerPositionListener(OnViewPagerPositionListener onViewPagerPositionListener) {
        this.onViewPagerPositionListener = onViewPagerPositionListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        initView(view);
        initData();
        initUI();
        initListener();
        return view;
    }

    private void initView(View view) {
        tWeatherToolbar = (Toolbar) view.findViewById(R.id.t_weather_toolbar);
        vpWeatherContent = (ViewPager) view.findViewById(R.id.vp_weather_content);
    }

    private void initData() {
        yuWeatherDB = YuWeatherDB.getInstance(getActivity());
        basicBeanList = yuWeatherDB.loadAllBasic();
        for (int i = 0; i < basicBeanList.size(); i++) {
            Now.BasicBean basicBean = basicBeanList.get(i);
            weatherDetailFragmentList.add(new WeatherDetailFragment(basicBean));
        }
    }

    private void initUI() {
        tWeatherToolbar.setTitle(basicBeanList.get(0).getCity());
        tWeatherToolbar.setNavigationIcon(R.drawable.ic_menu);
        ((BaseActivity) getActivity()).setSupportActionBar(tWeatherToolbar);
        // 因为Fragment嵌套了Fragment，所以应该使用getChildFragmentManager()
        weatherDetailAdapter = new WeatherDetailAdapter(getChildFragmentManager(), weatherDetailFragmentList);
        vpWeatherContent.setAdapter(weatherDetailAdapter);
        vpWeatherContent.setOffscreenPageLimit(basicBeanList.size() - 1);

        Bundle bundle = getArguments();
        int position = bundle.getInt(DataName.POSITION);
        vpWeatherContent.setCurrentItem(position);
        tWeatherToolbar.setTitle(basicBeanList.get(position).getCity());
    }

    private void initListener() {
        vpWeatherContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                tWeatherToolbar.setTitle(basicBeanList.get(position).getCity());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ((MainActivity) getActivity()).setOnSwipeRefreshLayoutOnRefreshListener(new MainActivity.OnSwipeRefreshLayoutOnRefreshListener() {
            @Override
            public void OnSwipeRefreshLayoutOnRefresh() {
                if (onViewPagerPositionListener != null) {
                    onViewPagerPositionListener.onViewPagerPosition(vpWeatherContent.getCurrentItem(), weatherDetailFragmentList);
                }
            }
        });
    }

    public interface OnViewPagerPositionListener {
        void onViewPagerPosition(int position, List<WeatherDetailFragment> weatherDetailFragmentList);
    }
}
