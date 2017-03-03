package com.yu.yuweather.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

import com.yu.yuweather.db.YuWeatherDB;
import com.yu.yuweather.global.ApiConstants;
import com.yu.yuweather.models.Now;
import com.yu.yuweather.utils.DataBaseUtil;
import com.yu.yuweather.utils.HttpsUtil;

import java.util.ArrayList;
import java.util.List;

public class UpdateDataRegularlyService extends JobService {

    public static final int SCHEDULE_CODE = 8;

    private YuWeatherDB yuWeatherDB;
    private List<Now.BasicBean> basicBeanList;

    @Override
    public void onCreate() {
        super.onCreate();
        yuWeatherDB = YuWeatherDB.getInstance(UpdateDataRegularlyService.this);
        basicBeanList = yuWeatherDB.loadAllBasic();
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        if (basicBeanList.size() > 0) {
            updateAllData(jobParameters);
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    private void updateAllData(JobParameters jobParameters) {
        final JobParameters currentJobParameters = jobParameters;
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
                    HttpsUtil.sendHttpsRequest(ApiConstants.GetNowApiAddress(countyId), new HttpsUtil.HttpsCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            // 保存JSON数据到数据库
                            DataBaseUtil.saveJSONToDataBase(false, response, countyId, yuWeatherDB);
                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                // 发送更新数据完成的广播
                sendBroadcast(new Intent("android.intent.action.UPDATE_DATA_FINISH"));
                // 通知系统任务完成
                jobFinished(currentJobParameters, false);
            }
        }).start();
    }
}
