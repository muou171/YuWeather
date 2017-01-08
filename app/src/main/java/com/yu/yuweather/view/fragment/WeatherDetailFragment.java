package com.yu.yuweather.view.fragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yu.yuweather.R;
import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.models.Aqi;
import com.yu.yuweather.models.DailyForecast;
import com.yu.yuweather.models.HourlyForecast;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.models.Suggestion;
import com.yu.yuweather.receiver.NetworkConnectChangedReceiver;
import com.yu.yuweather.utils.ColorUtils;
import com.yu.yuweather.utils.IconUtils;
import com.yu.yuweather.utils.NetworkUtil;
import com.yu.yuweather.utils.UIUtils;

import java.util.List;

import static com.yu.yuweather.R.id.iv_humidity;
import static com.yu.yuweather.R.id.iv_umbrella;
import static com.yu.yuweather.R.id.iv_wind_direction;

public class WeatherDetailFragment extends Fragment {


    private CardView cvError;
    private ImageView ivNowCondCode;
    private TextView tvBasicUpdateLoc;
    private TextView tvNowCondTxt;
    private TextView tvNowTmpUnit;
    private TextView tvNowTmp;
    private ImageView ivUmbrella;
    private TextView tvNowPcpn;
    private TextView tvNowPcpnUnit;
    private ImageView ivHumidity;
    private TextView tvNowHum;
    private TextView tvNowHumUnit;
    private ImageView ivWindDirection;
    private TextView tvNowWindDir;
    private LinearLayout llContentDetailNow;
    private LinearLayout llDailyForecast;
    private LinearLayout llHourlyForecast;
    private TextView tvAqiCityQlty;
    private TextView tvAqiCityAqi;
    private TextView tvAqiCityPm25;
    private TextView tvAqiCityPm10;
    private TextView tvAqiCityCo;
    private TextView tvAqiCitySo2;
    private TextView tvAqiCityNo2;
    private TextView tvAqiCityO3;
    private TextView tvSuggestionComfBrf;
    private TextView tvSuggestionComfTxt;
    private TextView tvSuggestionUvBrf;
    private TextView tvSuggestionUvTxt;
    private TextView tvSuggestionDrsgBrf;
    private TextView tvSuggestionDrsgTxt;
    private TextView tvSuggestionFluBrf;
    private TextView tvSuggestionFluTxt;
    private TextView tvSuggestionTravBrf;
    private TextView tvSuggestionTravTxt;
    private TextView tvSuggestionSportBrf;
    private TextView tvSuggestionSportTxt;
    private TextView tvSuggestionCwBrf;
    private TextView tvSuggestionCwTxt;
    private ImageView ivDailyDataIcon;
    private TextView tvDailyDataTime;
    private TextView tvDailyDataTemperatureMaxUnit;
    private TextView tvDailyDataTemperatureMax;
    private TextView tvDailyDataTemperatureMinUnit;
    private TextView tvDailyDataTemperatureMin;
    private TextView tvDailyDataSummary;
    private ImageView ivHourlyDataIcon;
    private TextView tvHourlyDataTemperatureUnit;
    private TextView tvHourlyDataTemperature;
    private TextView tvHourlyDataTime;
    private TextView tvHourlyDataSummary;
    private TextView tvHourlyDataPreProbability;
    private TextView tvHourlyDataPreProbabilityUnit;
    private TextView tvHourlyDataHumidity;
    private TextView tvHourlyDataHumidityUnit;
    private TextView tvHourlyDataPressure;
    private TextView tvHourlyDataPressureUnit;

    private Now.BasicBean basicBean;
    private Now.NowBean nowBean;
    private Aqi.AqiBean aqiBean;
    private Suggestion.SuggestionBean suggestionBean;
    private List<DailyForecast.DailyBean.DataBean> dailyForecast;
    private List<HourlyForecast.HourlyBean.DataBean> hourlyForecast;
    private YuWeatherDB yuWeatherDB;
    private NetworkConnectChangedReceiver networkConnectChangedReceiver;

    public WeatherDetailFragment(Now.BasicBean basicBean) {
        this.basicBean = basicBean;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_detail, container, false);
        networkConnectChangedReceiver = new NetworkConnectChangedReceiver();
        initView(view);
        initData();
        initUI();
        initListener();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        getActivity().registerReceiver(networkConnectChangedReceiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(networkConnectChangedReceiver);
    }

    private void initView(View view) {
        cvError = (CardView) view.findViewById(R.id.cv_error);

        ivNowCondCode = (ImageView) view.findViewById(R.id.iv_now_cond_code);
        tvBasicUpdateLoc = (TextView) view.findViewById(R.id.tv_basic_update_loc);
        tvNowCondTxt = (TextView) view.findViewById(R.id.tv_now_cond_txt);
        tvNowTmpUnit = (TextView) view.findViewById(R.id.tv_now_tmp_unit);
        tvNowTmp = (TextView) view.findViewById(R.id.tv_now_tmp);
        ivUmbrella = (ImageView) view.findViewById(iv_umbrella);
        tvNowPcpn = (TextView) view.findViewById(R.id.tv_now_pcpn);
        tvNowPcpnUnit = (TextView) view.findViewById(R.id.tv_now_pcpn_unit);
        ivHumidity = (ImageView) view.findViewById(iv_humidity);
        tvNowHum = (TextView) view.findViewById(R.id.tv_now_hum);
        tvNowHumUnit = (TextView) view.findViewById(R.id.tv_now_hum_unit);
        ivWindDirection = (ImageView) view.findViewById(iv_wind_direction);
        tvNowWindDir = (TextView) view.findViewById(R.id.tv_now_wind_dir);
        llContentDetailNow = (LinearLayout) view.findViewById(R.id.ll_content_detail_now);
        llDailyForecast = (LinearLayout) view.findViewById(R.id.ll_daily_forecast);
        llHourlyForecast = (LinearLayout) view.findViewById(R.id.ll_hourly_forecast);

        tvAqiCityQlty = (TextView) view.findViewById(R.id.tv_aqi_city_qlty);
        tvAqiCityAqi = (TextView) view.findViewById(R.id.tv_aqi_city_aqi);
        tvAqiCityPm25 = (TextView) view.findViewById(R.id.tv_aqi_city_pm25);
        tvAqiCityPm10 = (TextView) view.findViewById(R.id.tv_aqi_city_pm10);
        tvAqiCityCo = (TextView) view.findViewById(R.id.tv_aqi_city_co);
        tvAqiCitySo2 = (TextView) view.findViewById(R.id.tv_aqi_city_so2);
        tvAqiCityNo2 = (TextView) view.findViewById(R.id.tv_aqi_city_no2);
        tvAqiCityO3 = (TextView) view.findViewById(R.id.tv_aqi_city_O3);

        tvSuggestionComfBrf = (TextView) view.findViewById(R.id.tv_suggestion_comf_brf);
        tvSuggestionComfTxt = (TextView) view.findViewById(R.id.tv_suggestion_comf_txt);
        tvSuggestionUvBrf = (TextView) view.findViewById(R.id.tv_suggestion_uv_brf);
        tvSuggestionUvTxt = (TextView) view.findViewById(R.id.tv_suggestion_uv_txt);
        tvSuggestionDrsgBrf = (TextView) view.findViewById(R.id.tv_suggestion_drsg_brf);
        tvSuggestionDrsgTxt = (TextView) view.findViewById(R.id.tv_suggestion_drsg_txt);
        tvSuggestionFluBrf = (TextView) view.findViewById(R.id.tv_suggestion_flu_brf);
        tvSuggestionFluTxt = (TextView) view.findViewById(R.id.tv_suggestion_flu_txt);
        tvSuggestionTravBrf = (TextView) view.findViewById(R.id.tv_suggestion_trav_brf);
        tvSuggestionTravTxt = (TextView) view.findViewById(R.id.tv_suggestion_trav_txt);
        tvSuggestionSportBrf = (TextView) view.findViewById(R.id.tv_suggestion_sport_brf);
        tvSuggestionSportTxt = (TextView) view.findViewById(R.id.tv_suggestion_sport_txt);
        tvSuggestionCwBrf = (TextView) view.findViewById(R.id.tv_suggestion_cw_brf);
        tvSuggestionCwTxt = (TextView) view.findViewById(R.id.tv_suggestion_cw_txt);
    }

    private void initData() {
        yuWeatherDB = YuWeatherDB.getInstance(getActivity());
        String countyId = basicBean.getId();
        basicBean = yuWeatherDB.loadBasic(countyId);
        nowBean = yuWeatherDB.loadNow(countyId);
        aqiBean = yuWeatherDB.loadAqi(countyId);
        suggestionBean = yuWeatherDB.loadSuggestion(countyId);
        dailyForecast = yuWeatherDB.loadDailyForecast(countyId);
        hourlyForecast = yuWeatherDB.loadHourlyForecast(countyId);
    }

    private void initUI() {
        // 判断是否显示网络错误
        if (NetworkUtil.isNetworkAvailable(getActivity())) {
            cvError.setVisibility(View.GONE);
        } else {
            cvError.setVisibility(View.VISIBLE);
        }

        // 天气概况数据填充
        if (nowBean != null) {
            String code = nowBean.getCond().getCode();
            if (code.equals("400") || code.equals("401") || code.equals("402") || code.equals("403") || code.equals("407")) {
                tvBasicUpdateLoc.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayLight));
                tvNowCondTxt.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayDark));
                tvNowTmpUnit.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayDark));
                tvNowTmp.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayDark));
                ivUmbrella.setImageResource(R.drawable.ic_umbrella_gray);
                tvNowPcpn.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayLight));
                tvNowPcpnUnit.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayLight));
                ivHumidity.setImageResource(R.drawable.ic_humidity_gray);
                tvNowHum.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayLight));
                tvNowHumUnit.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayLight));
                ivWindDirection.setImageResource(R.drawable.ic_wind_direction_gray);
                tvNowWindDir.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayLight));
            }
            ivNowCondCode.setImageResource(IconUtils.GetCondIconResourcesId(code));
            tvBasicUpdateLoc.setText(basicBean.getUpdate().getLoc());
            tvNowCondTxt.setText(nowBean.getCond().getTxt());
            tvNowTmp.setText(nowBean.getTmp());
            tvNowPcpn.setText(nowBean.getPcpn());
            tvNowHum.setText(nowBean.getHum());
            tvNowWindDir.setText(nowBean.getWind().getDir());
            llContentDetailNow.setBackgroundResource(ColorUtils.GetNowBackgroundColor(code));
        }

        if (suggestionBean != null) {
            // 生活指数数据填充
            tvSuggestionComfBrf.setText(suggestionBean.getComf().getBrf());
            tvSuggestionComfTxt.setText(suggestionBean.getComf().getTxt());
            tvSuggestionCwBrf.setText(suggestionBean.getCw().getBrf());
            tvSuggestionCwTxt.setText(suggestionBean.getCw().getTxt());
            tvSuggestionDrsgBrf.setText(suggestionBean.getDrsg().getBrf());
            tvSuggestionDrsgTxt.setText(suggestionBean.getDrsg().getTxt());
            tvSuggestionFluBrf.setText(suggestionBean.getFlu().getBrf());
            tvSuggestionFluTxt.setText(suggestionBean.getFlu().getTxt());
            tvSuggestionSportBrf.setText(suggestionBean.getSport().getBrf());
            tvSuggestionSportTxt.setText(suggestionBean.getSport().getTxt());
            tvSuggestionTravBrf.setText(suggestionBean.getTrav().getBrf());
            tvSuggestionTravTxt.setText(suggestionBean.getTrav().getTxt());
            tvSuggestionUvBrf.setText(suggestionBean.getUv().getBrf());
            tvSuggestionUvTxt.setText(suggestionBean.getUv().getTxt());
        }

        if (aqiBean != null) {
            // 空气质量指数数据填充
            tvAqiCityAqi.setText(aqiBean.getCity().getAqi());
            tvAqiCityQlty.setText(aqiBean.getCity().getQlty());
            tvAqiCityPm25.setText(aqiBean.getCity().getPm25());
            tvAqiCityPm10.setText(aqiBean.getCity().getPm10());
            tvAqiCityCo.setText(aqiBean.getCity().getCo());
            tvAqiCitySo2.setText(aqiBean.getCity().getSo2());
            tvAqiCityNo2.setText(aqiBean.getCity().getNo2());
            tvAqiCityO3.setText(aqiBean.getCity().getO3());
        }

        if (dailyForecast != null) {
            // 每日预报数据填充
            llDailyForecast.removeAllViews();
            for (int i = 0; i < dailyForecast.size(); i++) {
                final DailyForecast.DailyBean.DataBean dataBean = dailyForecast.get(i);
                View view = View.inflate(getActivity(), R.layout.content_detail_daily_forecast, null);
                ivDailyDataIcon = (ImageView) view.findViewById(R.id.iv_daily_data_icon);
                tvDailyDataTime = (TextView) view.findViewById(R.id.tv_daily_data_time);
                tvDailyDataTemperatureMaxUnit = (TextView) view.findViewById(R.id.tv_daily_data_temperature_max_unit);
                tvDailyDataTemperatureMax = (TextView) view.findViewById(R.id.tv_daily_data_temperature_max);
                tvDailyDataTemperatureMinUnit = (TextView) view.findViewById(R.id.tv_daily_data_temperature_min_unit);
                tvDailyDataTemperatureMin = (TextView) view.findViewById(R.id.tv_daily_data_temperature_min);
                tvDailyDataSummary = (TextView) view.findViewById(R.id.tv_daily_data_summary);

                String icon = dataBean.getIcon();
                if (icon.equals("snow")) {
                    tvDailyDataTime.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayDark));
                    tvDailyDataTemperatureMaxUnit.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayDark));
                    tvDailyDataTemperatureMax.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayDark));
                    tvDailyDataTemperatureMin.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayDark));
                    tvDailyDataTemperatureMinUnit.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayDark));
                    tvDailyDataSummary.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayLight));
                }
                ivDailyDataIcon.setImageResource(IconUtils.GetForecastIconResourcesId(icon));
                tvDailyDataTemperatureMax.setText(dataBean.getTemperatureMax());
                tvDailyDataTemperatureMin.setText(dataBean.getTemperatureMin());
                tvDailyDataTime.setText(dataBean.getTime());
                tvDailyDataSummary.setText(dataBean.getSummary());
                view.setBackgroundResource(ColorUtils.GetForecastBackgroundColor(icon));
                view.setElevation(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UIUtils.showDailyForecastDetailDialog(getActivity(), dataBean);
                    }
                });
                llDailyForecast.addView(view);
            }
        }

        if (hourlyForecast != null) {
            // 当天每小时预报数据填充
            llHourlyForecast.removeAllViews();
            for (int i = 0; i < hourlyForecast.size(); i++) {
                HourlyForecast.HourlyBean.DataBean dataBean = hourlyForecast.get(i);
                View view = View.inflate(getActivity(), R.layout.content_detail_hourly_forecast, null);
                ivHourlyDataIcon = (ImageView) view.findViewById(R.id.iv_hourly_data_icon);
                tvHourlyDataTemperatureUnit = (TextView) view.findViewById(R.id.tv_hourly_data_temperature_unit);
                tvHourlyDataTemperature = (TextView) view.findViewById(R.id.tv_hourly_data_temperature);
                tvHourlyDataTime = (TextView) view.findViewById(R.id.tv_hourly_data_time);
                tvHourlyDataSummary = (TextView) view.findViewById(R.id.tv_hourly_data_summary);

                ImageView ivUmbrella = (ImageView) view.findViewById(R.id.iv_umbrella);
                tvHourlyDataPreProbability = (TextView) view.findViewById(R.id.tv_hourly_data_pre_probability);
                tvHourlyDataPreProbabilityUnit = (TextView) view.findViewById(R.id.tv_hourly_data_pre_probability_unit);
                ImageView ivHumidity = (ImageView) view.findViewById(R.id.iv_humidity);
                tvHourlyDataHumidity = (TextView) view.findViewById(R.id.tv_hourly_data_humidity);
                tvHourlyDataHumidityUnit = (TextView) view.findViewById(R.id.tv_hourly_data_humidity_unit);
                ImageView ivPressure = (ImageView) view.findViewById(R.id.iv_pressure);
                tvHourlyDataPressure = (TextView) view.findViewById(R.id.tv_hourly_data_pressure);
                tvHourlyDataPressureUnit = (TextView) view.findViewById(R.id.tv_hourly_data_pressure_unit);

                final LinearLayout llContentDetailHourlyForecastMore = (LinearLayout) view.findViewById(R.id.ll_content_detail_hourly_forecast_more);

                String icon = dataBean.getIcon();
                if (icon.equals("snow")) {
                    tvHourlyDataTemperatureUnit.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayDark));
                    tvHourlyDataTemperature.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayDark));
                    tvHourlyDataTime.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayDark));
                    tvHourlyDataSummary.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayLight));
                    ivUmbrella.setImageResource(R.drawable.ic_umbrella_gray);
                    tvHourlyDataPreProbability.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayLight));
                    tvHourlyDataPreProbabilityUnit.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayLight));
                    ivHumidity.setImageResource(R.drawable.ic_humidity_gray);
                    tvHourlyDataHumidity.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayLight));
                    tvHourlyDataHumidityUnit.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayLight));
                    ivPressure.setImageResource(R.drawable.ic_pressure_gray);
                    tvHourlyDataPressure.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayLight));
                    tvHourlyDataPressureUnit.setTextColor(ColorUtils.GetResourceColorStateList(getActivity(), R.color.colorGrayLight));
                }
                tvHourlyDataPreProbability.setText(dataBean.getPrecipProbability());
                tvHourlyDataHumidity.setText(dataBean.getHumidity());
                tvHourlyDataPressure.setText(dataBean.getPressure());

                ivHourlyDataIcon.setImageResource(IconUtils.GetForecastIconResourcesId(icon));
                tvHourlyDataTime.setText(dataBean.getTime());
                tvHourlyDataTemperature.setText(dataBean.getTemperature());
                tvHourlyDataSummary.setText(dataBean.getSummary());
                view.setBackgroundResource(ColorUtils.GetForecastBackgroundColor(icon));
                view.setElevation(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (llContentDetailHourlyForecastMore.getVisibility() == View.GONE) {
                            llContentDetailHourlyForecastMore.setVisibility(View.VISIBLE);
                        } else {
                            llContentDetailHourlyForecastMore.setVisibility(View.GONE);
                        }
                    }
                });
                llHourlyForecast.addView(view);
            }
        }
    }

    private void initListener() {
        cvError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtils.showNetworkErrorAlertDialog(getActivity());
            }
        });
        networkConnectChangedReceiver.setOnNetworkConnectChangedListener(new NetworkConnectChangedReceiver.OnNetworkConnectChangedListener() {
            @Override
            public void OnNetworkConnectChanged(boolean isNetworkConnected) {
                if (isNetworkConnected) {
                    cvError.setVisibility(View.GONE);
                } else {
                    cvError.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void UpdateData() {
        initData();
        initUI();
    }
}