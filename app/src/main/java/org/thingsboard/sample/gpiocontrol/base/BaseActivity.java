package org.thingsboard.sample.gpiocontrol.base;

import android.app.Activity;


import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangxiang on 2018/3/5.
 */

public class BaseActivity extends Activity {



    /**
     * 获取CPU温度信息
     */
    public void timedTask(int seconds, Runnable runnable) {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        long initialDelay = 1;
        // 从现在开始1秒钟之后，每隔1秒钟执行一次job1
        scheduledExecutorService.scheduleAtFixedRate(runnable, initialDelay, seconds, TimeUnit.SECONDS);
    }
}
