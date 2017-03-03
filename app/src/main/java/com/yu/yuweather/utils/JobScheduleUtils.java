package com.yu.yuweather.utils;


import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

public class JobScheduleUtils {

    /**
     * 任务定期运行一次
     */
    public static void schedule(Context context, Class cls, int scheduleCode, long time) {
        ((JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE)).schedule(
                new JobInfo.Builder(scheduleCode, new ComponentName(context.getPackageName(), cls.getName()))
                        .setPeriodic(time)
                        .build());
    }

    /**
     * 预报每天执行的一次
     */
    public static void scheduleForecastMission(Context context, Class cls, int scheduleCode) {
        ((JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE)).schedule(
                new JobInfo.Builder(scheduleCode, new ComponentName(context.getPackageName(), cls.getName()))
                        // 时间应该是设置的时间
                        .setMinimumLatency(NotificationUtils.nowAndFirstForecastTimeDiffer(context))
                        .build());
    }

    /**
     * 取消任务的执行
     */
    public static void cancel(Context context, int scheduleCode) {
        ((JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE)).cancel(scheduleCode);
    }
}
