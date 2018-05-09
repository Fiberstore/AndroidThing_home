package com.thingsboard.home.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;


import com.thingsboard.home.R;
import com.thingsboard.home.base.BaseActivity;


import com.thingsboard.home.util.TimeUtils;

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
    protected int getContentView() {
        return R.layout.activity_time;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        showReturnButton(false);
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
        this.finish();
        return super.onTouchEvent(event);
    }
}
