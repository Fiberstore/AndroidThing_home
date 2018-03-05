package org.thingsboard.sample.gpiocontrol.activity;

import android.os.Bundle;
import android.widget.TextView;

import org.thingsboard.sample.gpiocontrol.R;
import org.thingsboard.sample.gpiocontrol.base.BaseActivity;
import org.thingsboard.sample.gpiocontrol.util.GetInternetTime;

/**
 * Created by zhangxiang on 2018/3/5.
 */

public class TimeActivity extends BaseActivity {

    private TextView timeTextview;
    private TextView weekTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        timeTextview = findViewById(R.id.time_textview);
        weekTextview = findViewById(R.id.week_textview);
        getTimeData();
    }

    /**
     * 定时任务
     */
    private void getTimeData() {
        timedTask(1, new Runnable() {
            @Override
            public void run() {
                final String formatTime = GetInternetTime.getFormatTimeHour();
                final String week = GetInternetTime.getWeek();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeTextview.setText(formatTime);
                        weekTextview.setText(week);
                    }
                });
            }
        });
    }
}
