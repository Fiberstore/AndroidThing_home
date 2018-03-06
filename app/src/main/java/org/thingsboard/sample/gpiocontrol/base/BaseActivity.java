package org.thingsboard.sample.gpiocontrol.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;


import org.thingsboard.sample.gpiocontrol.activity.TimeActivity;
import org.thingsboard.sample.gpiocontrol.util.Utils;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangxiang on 2018/3/5.
 */

public class BaseActivity extends Activity {


    private Handler handler = new Handler();
    private long time = 1000 * 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //跳转到广告页面
            startActivity(new Intent(BaseActivity.this, TimeActivity.class));
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.removeCallbacks(runnable);
                break;
            case MotionEvent.ACTION_UP:
                startTimePage();
                break;
        }*/
        return super.onTouchEvent(event);
    }

    public void startTimePage() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, time);
    }

    /**
     * 获取CPU温度信息
     */
    public void timedTask(int seconds, Runnable runnable) {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        long initialDelay = 1;
        // 从现在开始1秒钟之后，每隔1秒钟执行一次
        scheduledExecutorService.scheduleAtFixedRate(runnable, initialDelay, seconds, TimeUnit.SECONDS);
    }
}
