package org.thingsboard.sample.gpiocontrol.activity;

import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.things.device.ScreenManager;

import org.thingsboard.sample.gpiocontrol.R;
import org.thingsboard.sample.gpiocontrol.base.BaseActivity;
import org.thingsboard.sample.gpiocontrol.util.TimeUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxiang on 2018/3/5.
 */

public class TimeActivity extends BaseActivity {

    @InjectView(R.id.time_textview)
    TextView timeTextview;
    @InjectView(R.id.week_textview)
    TextView weekTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        ButterKnife.inject(this);
        getTimeData();
    }


    /**
     * 定时任务
     */
    private void getTimeData() {
        timedTask(1, new Runnable() {
            @Override
            public void run() {
                final String formatTime = TimeUtils.getFormatTimeHour();
                final String week = TimeUtils.getWeek();
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // this.finish();
        return super.onTouchEvent(event);
    }
}
