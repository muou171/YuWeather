package com.yu.yuweather.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.yu.yuweather.R;
import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.global.ApiConstants;
import com.yu.yuweather.global.DataName;
import com.yu.yuweather.global.YuWeather;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.utils.DataBaseUtil;
import com.yu.yuweather.utils.HttpsUtil;
import com.yu.yuweather.utils.NotificationUtils;
import com.yu.yuweather.utils.PrefUtils;
import com.yu.yuweather.utils.ServiceUtils;
import com.yu.yuweather.utils.UIUtils;
import com.yu.yuweather.view.fragment.DefaultMainFragment;
import com.yu.yuweather.view.fragment.WeatherDetailFragment;
import com.yu.yuweather.view.fragment.WeatherFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private NavigationView nvMenu;
    private DrawerLayout dlMainDrawerLayout;
    private ImageButton ibAddCity;
    private SwipeRefreshLayout srlMainRefresh;
    private YuWeatherDB yuWeatherDB;
    private List<Now.BasicBean> basicBeanList;

    private Handler handler = new Handler();
    private FragmentManager supportFragmentManager = getSupportFragmentManager();

    private OnSwipeRefreshLayoutOnRefreshListener onSwipeRefreshLayoutOnRefreshListener;

    public void setOnSwipeRefreshLayoutOnRefreshListener(OnSwipeRefreshLayoutOnRefreshListener onSwipeRefreshLayoutOnRefreshListener) {
        this.onSwipeRefreshLayoutOnRefreshListener = onSwipeRefreshLayoutOnRefreshListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        YuWeather.getInstance().finishAllActivity();
        initService();
        super.onCreate(savedInstanceState);
        // 导入数据库
        importDatabase();
        setContentView(R.layout.activity_main);
        initView();
        updateAllData();
        initUI();
        initListener();
    }

    private void initService() {
        // 开启定时更新数据的服务
        ServiceUtils.stopUpdateDataRegularlyService(this);
        ServiceUtils.startUpdateDataRegularlyService(this);

        // 判断是否开启预报的服务
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean(getString(R.string.key_forecast_time), false)) {
            NotificationUtils.stopFirstForecastService(this);
            NotificationUtils.stopForecastService(this);
            NotificationUtils.startFirstForecastService(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        int cityCount = YuWeatherDB.getInstance(this).getCityCount();
        if (cityCount <= 0) {
            // 从未添加过城市，显示添加的按钮
            ibAddCity.setVisibility(View.VISIBLE);
            // 禁用下拉刷新控件
            srlMainRefresh.setEnabled(false);
        } else {
            // 隐藏添加的按钮，显示天气界面
            ibAddCity.setVisibility(View.GONE);
            // 启用下拉刷新控件
            srlMainRefresh.setEnabled(true);
            int lastPosition = PrefUtils.getInt(this, DataName.LAST_POSITION, 0);
            Intent intent = this.getIntent();
            String activityInterface = intent.getStringExtra(DataName.ACTIVITY_INTERFACE);
            if (TextUtils.isEmpty(activityInterface)) {
                changFragment(0);
            } else if (activityInterface.equals(DataName.CITY_MANAGEMENT_FRAGMENT)) {
                int position = intent.getIntExtra(DataName.POSITION, lastPosition);
                changFragment(position);
            } else if (activityInterface.equals(DataName.CHOOSE_AREA_ACTIVITY)) {
                changFragment(yuWeatherDB.loadAllBasic().size() - 1);
            } else if (activityInterface.equals(DataName.FORECAST_NOTIFICATION)) {
                int index = intent.getIntExtra(DataName.FORECAST_CITY_INDEX, lastPosition);
                changFragment(index);
            } else if (activityInterface.equals(DataName.DAY_WIDGET)) {
                String widgetDayCountyId = intent.getStringExtra(DataName.WIDGET_DAY_COUNTY_ID);
                if (!TextUtils.isEmpty(widgetDayCountyId)) {
                    for (int i = 0; i < basicBeanList.size(); i++) {
                        if (widgetDayCountyId.equals(basicBeanList.get(i).getId())) {
                            lastPosition = i;
                            break;
                        }
                    }
                }
                changFragment(lastPosition);
            }
        }
    }

    private void initView() {
        nvMenu = (NavigationView) findViewById(R.id.nv_menu);
        dlMainDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawerLayout);
        ibAddCity = (ImageButton) findViewById(R.id.ib_add_city);
        srlMainRefresh = (SwipeRefreshLayout) findViewById(R.id.srl_main_refresh);
        yuWeatherDB = YuWeatherDB.getInstance(this);
        basicBeanList = yuWeatherDB.loadAllBasic();
    }

    private void initUI() {
        setDefaultFragment();
        nvMenu.setItemIconTintList(null);
    }

    private void initListener() {
        ibAddCity.setOnClickListener(this);
        nvMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = new Intent(MainActivity.this, CityManagementAndSettingActivity.class);
                switch (item.getItemId()) {
                    case R.id.city_management:
                        intent.putExtra(DataName.ACTIVITY_INTERFACE, DataName.CITY_MANAGEMENT_FRAGMENT);
                        break;
                    case R.id.setting:
                        intent.putExtra(DataName.ACTIVITY_INTERFACE, DataName.SETTING_FRAGMENT);
                        break;
                }
                startActivity(intent);
                finish();
                return true;
            }
        });
        srlMainRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (onSwipeRefreshLayoutOnRefreshListener != null) {
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_content);
                    if (fragment instanceof WeatherFragment) {
                        final WeatherFragment weatherFragment = (WeatherFragment) fragment;
                        weatherFragment.setOnViewPagerPositionListener(new WeatherFragment.OnViewPagerPositionListener() {
                            @Override
                            public void onViewPagerPosition(final int position, final List<WeatherDetailFragment> weatherDetailFragmentList) {
                                final List<Now.BasicBean> basicBeanList = yuWeatherDB.loadAllBasic();
                                Now.BasicBean basicBean = basicBeanList.get(position);
                                final String countyId = basicBean.getId();
                                // 联网获取该城市的天气数据
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        HttpsUtil.sendHttpsRequest(ApiConstants.GetNowApiAddress(countyId), new HttpsUtil.HttpsCallbackListener() {
                                            @Override
                                            public void onFinish(String response) {
                                                // 删除数据库中当前页面所保存的城市的天气信息
                                                yuWeatherDB.deleteItemsFromBasic(countyId);
                                                DataBaseUtil.saveJSONToDataBase(response, countyId, yuWeatherDB);
                                                handler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        // 更新Basic表中的城市顺序
                                                        if (position < basicBeanList.size() - 1) {
                                                            List<Now.BasicBean> currentBasicBeanList = yuWeatherDB.loadAllBasic();
                                                            List<Now.BasicBean> updateBasicBeanList = new ArrayList<>();
                                                            for (int i = 0; i < currentBasicBeanList.size() - 1; i++) {
                                                                if (i == position) {
                                                                    updateBasicBeanList.add(currentBasicBeanList.get(currentBasicBeanList.size() - 1));
                                                                }
                                                                updateBasicBeanList.add(currentBasicBeanList.get(i));
                                                            }
                                                            yuWeatherDB.updateBasicOrder(updateBasicBeanList);
                                                        }
                                                        weatherDetailFragmentList.get(position).UpdateData();
                                                        // 不显示下拉刷新的控件
                                                        srlMainRefresh.setRefreshing(false);
                                                        // 保存更新数据的时间
                                                        PrefUtils.setLong(MainActivity.this, DataName.LAST_TIME, System.currentTimeMillis());
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onError(Exception e) {
                                                e.printStackTrace();
                                                handler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        // 不显示下拉刷新控件
                                                        srlMainRefresh.setRefreshing(false);
                                                        // 显示网络错误的对话框
                                                        UIUtils.showNetworkErrorAlertDialog(MainActivity.this);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }).start();
                            }
                        });
                        onSwipeRefreshLayoutOnRefreshListener.OnSwipeRefreshLayoutOnRefresh();
                    }
                }
            }
        });
    }

    /**
     * 60分钟后未打开过应用，自动获取新的数据
     */
    private void updateAllData() {
        long lastTime = PrefUtils.getLong(this, DataName.LAST_TIME, System.currentTimeMillis());
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >= (1000 * 60 * 60)) {
            srlMainRefresh.setRefreshing(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<String> countyIdList = new ArrayList<>();
                    for (int i = 0; i < basicBeanList.size(); i++) {
                        String id = basicBeanList.get(i).getId();
                        countyIdList.add(id);
                    }
                    for (int i = 0; i < countyIdList.size(); i++) {
                        final String countyId = countyIdList.get(i);
                        final int finalI = i;
                        HttpsUtil.sendHttpsRequest(ApiConstants.GetNowApiAddress(countyId), new HttpsUtil.HttpsCallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                // 删除该城市在数据库中的数据
                                yuWeatherDB.deleteItemsFromBasic(countyId);
                                DataBaseUtil.saveJSONToDataBase(response, countyId, yuWeatherDB);
                                // 更新Basic表的数据库中的城市排序
                                Now.BasicBean basicBean = yuWeatherDB.loadBasic(countyId);
                                basicBeanList.set(finalI, basicBean);
                                yuWeatherDB.updateBasicOrder(basicBeanList);
                            }

                            @Override
                            public void onError(Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 不显示下拉刷新控件
                            srlMainRefresh.setRefreshing(false);
                            // 保存更新数据的时间
                            PrefUtils.setLong(MainActivity.this, DataName.LAST_TIME, System.currentTimeMillis());
                            int lastPosition = PrefUtils.getInt(MainActivity.this, DataName.LAST_POSITION, 0);
                            changFragment(lastPosition);
                        }
                    });
                }
            }).start();
        }
    }

    private void importDatabase() {
        // 判断是否已经存在数据库，如果不存在就从资源中把数据库文件导入
        String DB_PATH = "/data/data/" + getPackageName() + "/databases/";
        String DB_NAME = "YuWeather.db";
        // 检查数据库是否存在
        if (new File(DB_PATH + DB_NAME).exists() == false) {
            // 检查databases文件夹是否存在
            File f = new File(DB_PATH);
            if (!f.exists()) {
                f.mkdir();
            }
            try {
                InputStream is = getBaseContext().getAssets().open(DB_NAME);
                FileOutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
                // 文件写入
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                // 关闭流
                os.flush();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setDefaultFragment() {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_content, new DefaultMainFragment());
        fragmentTransaction.commit();
    }

    private void changFragment(int position) {
        PrefUtils.setInt(this, DataName.LAST_POSITION, position);
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt(DataName.POSITION, position);
        WeatherFragment weatherFragment = new WeatherFragment();
        weatherFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fl_content, weatherFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dlMainDrawerLayout.openDrawer(nvMenu);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_add_city:
                Intent intent = new Intent(MainActivity.this, ChooseAreaActivity.class);
                intent.putExtra(DataName.ACTIVITY_INTERFACE, DataName.MAIN_ACTIVITY);
                startActivity(intent);
                finish();
                break;
        }
    }

    public interface OnSwipeRefreshLayoutOnRefreshListener {
        void OnSwipeRefreshLayoutOnRefresh();
    }
}
