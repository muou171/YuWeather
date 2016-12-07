package com.yu.yuweather.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yu.yuweather.R;
import com.yu.yuweather.global.DataName;
import com.yu.yuweather.view.fragment.CityManagementFragment;
import com.yu.yuweather.view.fragment.SettingFragment;

public class CityManagementAndSettingActivity extends BaseActivity {

    private OnCityManagementAndSettingActivityBackPressedListener onCityManagementAndSettingActivityBackPressedListener;
    private Toolbar tCityManagementAndSettingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_management_and_setting);
        initView();
        initUI();
        initListener();
        // 判断显示的Fragment
        String activityInterface = getIntent().getStringExtra(DataName.ACTIVITY_INTERFACE);
        if (activityInterface.equals(DataName.CITY_MANAGEMENT_FRAGMENT)) {
            changCityManagementFragment();
        } else if (activityInterface.equals(DataName.SETTING_FRAGMENT)) {
            changSettingFragment();
        }
    }

    private void initView() {
        tCityManagementAndSettingToolbar = (Toolbar) findViewById(R.id.t_city_management_and_setting_toolbar);
    }

    private void initUI() {
        tCityManagementAndSettingToolbar.setTitle("");
        tCityManagementAndSettingToolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        setSupportActionBar(tCityManagementAndSettingToolbar);
    }

    private void initListener() {
        tCityManagementAndSettingToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CityManagementAndSettingActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void changCityManagementFragment() {
        tCityManagementAndSettingToolbar.setTitle("城市管理");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_city_management_and_setting, new CityManagementFragment());
        fragmentTransaction.commit();
    }

    private void changSettingFragment() {
        tCityManagementAndSettingToolbar.setTitle("设置");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_city_management_and_setting, new SettingFragment());
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        if (onCityManagementAndSettingActivityBackPressedListener != null) {
            onCityManagementAndSettingActivityBackPressedListener.setBackPressed();
        }
    }

    public void setOnCityManagementAndSettingActivityBackPressedListener(OnCityManagementAndSettingActivityBackPressedListener onCityManagementAndSettingActivityBackPressedListener) {
        this.onCityManagementAndSettingActivityBackPressedListener = onCityManagementAndSettingActivityBackPressedListener;
    }

    public interface OnCityManagementAndSettingActivityBackPressedListener {
        void setBackPressed();
    }
}
