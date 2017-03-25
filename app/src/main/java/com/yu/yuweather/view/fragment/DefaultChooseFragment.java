package com.yu.yuweather.view.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yu.yuweather.R;
import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.global.ApiConstants;
import com.yu.yuweather.global.DataName;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.utils.DataBaseUtil;
import com.yu.yuweather.utils.HttpsUtil;
import com.yu.yuweather.utils.LocationUtils;
import com.yu.yuweather.utils.NetworkUtil;
import com.yu.yuweather.utils.PrefUtils;
import com.yu.yuweather.utils.UIUtils;
import com.yu.yuweather.view.activity.BaseActivity;
import com.yu.yuweather.view.activity.ChooseAreaActivity;
import com.yu.yuweather.view.activity.CityManagementAndSettingActivity;
import com.yu.yuweather.view.activity.MainActivity;

import static com.yu.yuweather.R.id.et_choose_area;

public class DefaultChooseFragment extends Fragment implements View.OnClickListener {

    private EditText etChooseArea;
    private TextView tvChooseArea;
    private Toolbar tDefaultChooseToolbar;
    private YuWeatherDB yuWeatherDB;
    private ImageButton ibChooseAreaSearch;
    private ImageButton ibChooseAreaLocation;
    private LocationManager locationManager;
    private ProgressDialog progressDialog;

    private static final int requestCode = 900;

    private Handler handler = new Handler();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default_choose, container, false);
        initView(view);
        initUI();
        initListener();
        return view;
    }

    private void initView(View view) {
        etChooseArea = (EditText) view.findViewById(et_choose_area);
        tvChooseArea = (TextView) view.findViewById(R.id.tv_choose_area);
        tDefaultChooseToolbar = (Toolbar) view.findViewById(R.id.t_default_choose_toolbar);
        ibChooseAreaSearch = (ImageButton) view.findViewById(R.id.ib_choose_area_search);
        ibChooseAreaLocation = (ImageButton) view.findViewById(R.id.ib_choose_area_location);
        progressDialog = new ProgressDialog(getActivity());
    }

    private void initUI() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        yuWeatherDB = YuWeatherDB.getInstance(getContext());
        tDefaultChooseToolbar.setTitle("");
        tDefaultChooseToolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        ((BaseActivity) getActivity()).setSupportActionBar(tDefaultChooseToolbar);
    }

    private void initListener() {
        tvChooseArea.setOnClickListener(this);
        ibChooseAreaSearch.setOnClickListener(this);
        ibChooseAreaLocation.setOnClickListener(this);
        tDefaultChooseToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTheReturn();
            }
        });
        ((ChooseAreaActivity) getActivity()).setOnChooseAreaActivityBackPressedListener(new ChooseAreaActivity.OnChooseAreaActivityBackPressedListener() {
            @Override
            public void setBackPressed() {
                setTheReturn();
            }
        });
        etChooseArea.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                searchCounty();
                return false;
            }
        });
    }

    private void setTheReturn() {
        String activityInterface = getActivity().getIntent().getStringExtra(DataName.ACTIVITY_INTERFACE);
        if (activityInterface.equals(DataName.MAIN_ACTIVITY)) {
            startActivity(new Intent(getActivity(), MainActivity.class));
        } else if (activityInterface.equals(DataName.CITY_MANAGEMENT_FRAGMENT)) {
            Intent intent = new Intent(getActivity(), CityManagementAndSettingActivity.class);
            intent.putExtra(DataName.ACTIVITY_INTERFACE, DataName.CITY_MANAGEMENT_FRAGMENT);
            startActivity(intent);
        }
        getActivity().finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_area:
                changeFragment();
                break;
            case R.id.ib_choose_area_search:
                if (NetworkUtil.isNetworkAvailable(getActivity())) {
                    searchCounty();
                } else {
                    UIUtils.showNetworkErrorAlertDialog(getActivity());
                }
                break;
            case R.id.ib_choose_area_location:
                if (!NetworkUtil.isNetworkAvailable(getActivity())) {
                    UIUtils.showNetworkErrorAlertDialog(getActivity());
                } else {
                    // 判断是否开启GPS功能
                    if (!LocationUtils.isGPSAvailable(getActivity())) {
                        UIUtils.showGPSErrorAlertDialog(getActivity());
                        return;
                    }
                    // 判断是否有位置信息权限
                    // 获取到位置信息，联网查询数据
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        requestPermissions();
                        return;
                    }
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        LocationUtils.GetTheLocation(location, new LocationUtils.GetLocationListener() {
                            @Override
                            public void getLocation(String county) {
                                // 查询数据库是否存在该城市
                                String countyId = yuWeatherDB.isExistCounty(county);
                                if (!TextUtils.isEmpty(countyId)) {
                                    // 联网查询数据，加入到数据库，并转到主界面
                                    getWeatherData(countyId);
                                } else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), "未成功定位当前城市，请手动输入或者手动选择", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
                // 添加监听
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
                locationManager.removeUpdates(locationListener);
                break;
        }
    }

    // 运行时权限申请
    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, requestCode);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                LocationUtils.GetTheLocation(location, new LocationUtils.GetLocationListener() {
                    @Override
                    public void getLocation(String county) {
                        // 查询数据库是否存在该城市
                        String countyId = yuWeatherDB.isExistCounty(county);
                        if (!TextUtils.isEmpty(countyId)) {
                            // 联网查询数据，加入到数据库，并转到主界面
                            getWeatherData(countyId);
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "未成功定位当前城市，请手动输入或者手动选择", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                return;
            }
            Location location = locationManager.getLastKnownLocation(s);
            if (location != null) {
                LocationUtils.GetTheLocation(location, new LocationUtils.GetLocationListener() {
                    @Override
                    public void getLocation(String county) {
                        // 查询数据库是否存在该城市
                        String countyId = yuWeatherDB.isExistCounty(county);
                        if (!TextUtils.isEmpty(countyId)) {
                            // 联网查询数据，加入到数据库，并转到主界面
                            getWeatherData(countyId);
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "未成功定位当前城市，请手动输入或者手动选择", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        }
    };

    private void searchCounty() {
        String countyName = etChooseArea.getText().toString();
        if (TextUtils.isEmpty(countyName)) {
            Toast.makeText(getActivity(), "请输入城市", Toast.LENGTH_SHORT).show();
            return;
        }
        final String countyId = yuWeatherDB.isExistCounty(countyName);
        if (!TextUtils.isEmpty(countyId)) {
            // 联网查询数据，加入到数据库，并转到主界面
            getWeatherData(countyId);
        } else {
            // 告知用户未找到
            Toast.makeText(getActivity(), R.string.search_city_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void changeFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_choose_area, new ChooseAreaFragment());
        fragmentTransaction.commit();
    }

    public void getWeatherData(final String countyId) {
        Now.BasicBean basicBean = yuWeatherDB.loadBasic(countyId);
        if (basicBean == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpsUtil.sendHttpsRequest(ApiConstants.GetNowApiAddress(countyId), new HttpsUtil.HttpsCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            // 显示等待的对话框
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    UIUtils.showWaitProgressDialog(progressDialog);
                                }
                            });
                            // 保存JSON数据到数据库
                            DataBaseUtil.saveJSONToDataBase(true, response, countyId, yuWeatherDB);
                            // 保存更新数据的时间
                            PrefUtils.setLong(getContext(), DataName.LAST_TIME, System.currentTimeMillis());
                            // 转到天气界面
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtra(DataName.ACTIVITY_INTERFACE, DataName.CHOOSE_AREA_ACTIVITY);
                            startActivity(intent);
                            getActivity().finish();
                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }).start();
        } else {
            Toast.makeText(getActivity(), "已经添加该城市", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (progressDialog != null) {
            UIUtils.dismissWaitProgressDialog(progressDialog);
        }
    }
}
