package com.yu.yuweather.utils;


import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

public class JobScheduleUtils {

    /**
     * 每30分钟定期运行一次
     */
    public static void schedule(Context context, Class cls, int scheduleCode) {
        ((JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE)).schedule(
                new JobInfo.Builder(scheduleCode, new ComponentName(context.getPackageName(), cls.getName()))
                        .setPeriodic((long) (30 * 60 * 1000))
                        .build());
    }

    /**
     * 预报在第一次执行的时间
     */
    public static void scheduleFirstForecastMission(Context context, Class cls, int scheduleCode) {
        ((JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE)).schedule(
                new JobInfo.Builder(scheduleCode, new ComponentName(context.getPackageName(), cls.getName()))
                        // 时间应该是设置的时间
                        .setPeriodic(NotificationUtils.nowAndFirstForecastTimeDiffer(context))
                        .build());
    }

    /**
     * 预报在第一次执行之后的每24小时执行一次
     */
    public static void scheduleForecastMission(Context context, Class cls, int scheduleCode) {
        ((JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE)).schedule(
                new JobInfo.Builder(scheduleCode, new ComponentName(context.getPackageName(), cls.getName()))
                        .setPeriodic((long) (24 * 60 * 60 * 1000))
                        .build());
    }

    /**
     * 取消任务的执行
     */
    public static void cancel(Context context, int scheduleCode) {
        ((JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE)).cancel(scheduleCode);
    }
}
