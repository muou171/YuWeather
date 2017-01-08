package com.yu.yuweather.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.yu.yuweather.R;
import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.global.ApiConstants;
import com.yu.yuweather.global.DataName;
import com.yu.yuweather.itemtouch.CustomizeItemTouchHelper;
import com.yu.yuweather.itemtouch.CustomizeItemTouchHelperCallback;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.utils.DataBaseUtil;
import com.yu.yuweather.utils.HttpsUtil;
import com.yu.yuweather.utils.PrefUtils;
import com.yu.yuweather.view.activity.ChooseAreaActivity;
import com.yu.yuweather.view.activity.CityManagementAndSettingActivity;
import com.yu.yuweather.view.activity.MainActivity;
import com.yu.yuweather.view.adapter.CityManagementAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CityManagementFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rvCityManagement;
    private ImageButton ibCityManagementAddCity;
    private SwipeRefreshLayout srlCityManagement;
    private YuWeatherDB yuWeatherDB;
    private List<Now.BasicBean> basicBeanList;
    private CustomizeItemTouchHelperCallback customizeItemTouchHelperCallback;
    private CustomizeItemTouchHelper customizeItemTouchHelper;
    private CityManagementAdapter cityManagementAdapter;

    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_management, container, false);
        initView(view);
        initData();
        initUI();
        initListener();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (basicBeanList.size() <= 0) {
            // 禁用下拉刷新控件
            srlCityManagement.setEnabled(false);
        } else {
            // 启用下拉刷新控件
            srlCityManagement.setEnabled(true);
        }
    }

    private void initView(View view) {
        rvCityManagement = (RecyclerView) view.findViewById(R.id.rv_city_management);
        ibCityManagementAddCity = (ImageButton) view.findViewById(R.id.ib_city_management_add_city);
        srlCityManagement = (SwipeRefreshLayout) view.findViewById(R.id.srl_city_management);
    }

    private void initData() {
        yuWeatherDB = YuWeatherDB.getInstance(getActivity());
        basicBeanList = yuWeatherDB.loadAllBasic();
        customizeItemTouchHelperCallback = new CustomizeItemTouchHelperCallback(srlCityManagement);
        customizeItemTouchHelper = new CustomizeItemTouchHelper(customizeItemTouchHelperCallback);
    }

    private void initUI() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCityManagement.setLayoutManager(linearLayoutManager);
        cityManagementAdapter = new CityManagementAdapter(basicBeanList, yuWeatherDB, getActivity());
        rvCityManagement.setAdapter(cityManagementAdapter);
        customizeItemTouchHelper.attachToRecyclerView(rvCityManagement);
    }

    private void initListener() {
        ibCityManagementAddCity.setOnClickListener(this);
        ((CityManagementAndSettingActivity) getActivity()).setOnCityManagementAndSettingActivityBackPressedListener(new CityManagementAndSettingActivity.OnCityManagementAndSettingActivityBackPressedListener() {
            @Override
            public void setBackPressed() {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(DataName.WIDGET_DAY_COUNTY_ID, "");
                startActivity(intent);
                getActivity().finish();
            }
        });
        customizeItemTouchHelperCallback.setOnItemTouchCallbackListener(new CustomizeItemTouchHelperCallback.OnItemTouchCallbackListener() {
            @Override
            public void onSwiped(int adapterPosition) {
                // 从数据库中删除该城市
                String id = yuWeatherDB.loadAllBasic().get(adapterPosition).getId();
                yuWeatherDB.deleteCity(id);
                // 从数据源中删除
                basicBeanList.remove(adapterPosition);
                // 判断保存的最后的城市与要删除的城市是否相同
                int lastPosition = PrefUtils.getInt(getContext(), DataName.LAST_POSITION, 0);
                if (lastPosition == adapterPosition) {
                    PrefUtils.setInt(getContext(), DataName.LAST_POSITION, 0);
                }
                cityManagementAdapter.notifyItemRemoved(adapterPosition);
            }

            @Override
            public boolean onMove(int srcPosition, int targetPosition) {
                // 更新数据源的位置
                Collections.swap(basicBeanList, srcPosition, targetPosition);
                // 更新Basic数据库中两个城市的先后顺序
                yuWeatherDB.updateBasicOrder(basicBeanList);
                // 判断保存的最后的城市与要移动的城市是否相同
                int lastPosition = PrefUtils.getInt(getContext(), DataName.LAST_POSITION, 0);
                if (lastPosition == srcPosition) {
                    PrefUtils.setInt(getContext(), DataName.LAST_POSITION, targetPosition);
                }
                cityManagementAdapter.notifyItemMoved(srcPosition, targetPosition);
                return false;
            }
        });
        cityManagementAdapter.setOnItemClickListener(new CityManagementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(DataName.ACTIVITY_INTERFACE, DataName.CITY_MANAGEMENT_FRAGMENT);
                intent.putExtra(DataName.POSITION, position);
                startActivity(intent);
                getActivity().finish();
            }
        });
        srlCityManagement.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 联网获取所有城市的天气信息
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
                                // 刷新Adapter，不显示下拉刷新控件
                                basicBeanList = yuWeatherDB.loadAllBasic();
                                cityManagementAdapter.updateBasicBeanList(basicBeanList);
                                cityManagementAdapter.notifyDataSetChanged();
                                srlCityManagement.setRefreshing(false);
                                // 保存更新数据的时间
                                PrefUtils.setLong(getContext(), DataName.LAST_TIME, System.currentTimeMillis());
                            }
                        });
                    }
                }).start();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_city_management_add_city:
                Intent intent = new Intent(getActivity(), ChooseAreaActivity.class);
                intent.putExtra(DataName.ACTIVITY_INTERFACE, DataName.CITY_MANAGEMENT_FRAGMENT);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }
}
