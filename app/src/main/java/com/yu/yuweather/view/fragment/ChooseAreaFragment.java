package com.yu.yuweather.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.yu.yuweather.R;
import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.global.ApiConstants;
import com.yu.yuweather.global.DataName;
import com.yu.yuweather.models.City;
import com.yu.yuweather.models.County;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.models.Province;
import com.yu.yuweather.utils.DataBaseUtil;
import com.yu.yuweather.utils.HttpsUtil;
import com.yu.yuweather.utils.NetworkUtil;
import com.yu.yuweather.utils.PrefUtils;
import com.yu.yuweather.utils.UIUtils;
import com.yu.yuweather.view.activity.BaseActivity;
import com.yu.yuweather.view.activity.ChooseAreaActivity;
import com.yu.yuweather.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ChooseAreaFragment extends Fragment {

    private ListView lvChoose;
    private ArrayAdapter<String> adapter;
    private YuWeatherDB yuWeatherDB;
    private List<String> dataList = new ArrayList<>();
    private Handler handler = new Handler();
    private ProgressDialog progressDialog;

    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;

    /**
     * 省列表
     */
    private List<Province> provinceList;
    /**
     * 市列表
     */
    private List<City> cityList;
    /**
     * 县列表
     */
    private List<County> countyList;
    /**
     * 选中的省份
     */
    private Province selectedProvince;
    /**
     * 选中的城市
     */
    private City selectedCity;
    /**
     * 当前选中的级别
     */
    private int currentLevel;
    private Toolbar tChooseToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_area, container, false);
        initView(view);
        initUI();
        initListener();
        queryProvinces();
        return view;
    }

    private void initView(View view) {
        lvChoose = (ListView) view.findViewById(R.id.lv_choose);
        tChooseToolbar = (Toolbar) view.findViewById(R.id.t_choose_toolbar);
        progressDialog = new ProgressDialog(getActivity());
    }

    private void initUI() {
        yuWeatherDB = YuWeatherDB.getInstance(getContext());
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        lvChoose.setAdapter(adapter);
        tChooseToolbar.setTitle("");
        tChooseToolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        ((BaseActivity) getActivity()).setSupportActionBar(tChooseToolbar);
    }

    private void initListener() {
        lvChoose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(i);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(i);
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    // 转到主界面，并往数据库添加该城市
                    if (NetworkUtil.isNetworkAvailable(getActivity())) {
                        final String countyCode = countyList.get(i).getCountyCode();
                        Now.BasicBean basicBean = yuWeatherDB.loadBasic(countyCode);
                        if (basicBean == null) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    HttpsUtil.sendHttpsRequest(ApiConstants.GetNowApiAddress(countyCode),
                                            new HttpsUtil.HttpsCallbackListener() {
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
                                                    DataBaseUtil.saveJSONToDataBase(response, countyCode, yuWeatherDB);
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
                    } else {
                        UIUtils.showNetworkErrorAlertDialog(getActivity());
                    }
                }
            }
        });
        tChooseToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                } else if (currentLevel == LEVEL_PROVINCE) {
                    changFragment();
                }
            }
        });
        ((ChooseAreaActivity) getActivity()).setOnChooseAreaActivityBackPressedListener(new ChooseAreaActivity.OnChooseAreaActivityBackPressedListener() {
            @Override
            public void setBackPressed() {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                } else if (currentLevel == LEVEL_PROVINCE) {
                    changFragment();
                }
            }
        });
    }

    private void changFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_choose_area, new DefaultChooseFragment());
        fragmentTransaction.commit();
    }

    /**
     * 从数据库查询全国所有的省
     */
    private void queryProvinces() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                provinceList = yuWeatherDB.loadProvinces();
                dataList.clear();
                for (Province province : provinceList) {
                    dataList.add(province.getProvinceName());
                }
                adapter.notifyDataSetChanged();
                lvChoose.setSelection(0);
                tChooseToolbar.setTitle("选择城市");
                currentLevel = LEVEL_PROVINCE;
            }
        });
    }

    /**
     * 从数据库查询选中省内所有的市
     */
    private void queryCities() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                cityList = yuWeatherDB.loadCities(selectedProvince.getId());
                dataList.clear();
                for (City city : cityList) {
                    dataList.add(city.getCityName());
                }
                adapter.notifyDataSetChanged();
                lvChoose.setSelection(0);
                tChooseToolbar.setTitle(selectedProvince.getProvinceName());
                currentLevel = LEVEL_CITY;
            }
        });
    }

    /**
     * 从数据库查询选中市内所有的县
     */
    private void queryCounties() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                countyList = yuWeatherDB.loadCounties(selectedCity.getId());
                dataList.clear();
                for (County county : countyList) {
                    dataList.add(county.getCountyName());
                }
                adapter.notifyDataSetChanged();
                lvChoose.setSelection(0);
                tChooseToolbar.setTitle(selectedCity.getCityName());
                currentLevel = LEVEL_COUNTY;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (progressDialog != null) {
            UIUtils.dismissWaitProgressDialog(progressDialog);
        }
    }
}