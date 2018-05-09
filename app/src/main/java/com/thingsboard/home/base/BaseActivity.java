package com.thingsboard.home.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.thingsboard.home.R;
import com.thingsboard.home.activity.TimeActivity;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxiang on 2018/3/5.
 */

public abstract class BaseActivity extends AppCompatActivity {


    Toolbar toolbar;
    FrameLayout viewContent;
    private Handler handler = new Handler();
    private long time = 1000 * 6;

    public String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        toolbar = findViewById(R.id.toolbar);
        viewContent = findViewById(R.id.viewContent);
        //初始化设置ToolBar，必须放在toolbar调用的方法之前
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //按钮事件，可以用Toolbar的方法修改按钮Icon，也可以跟设置标题一样写在方法里，重写按钮事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//此处返回
            }
        });

        //将继承 TopBarBaseActivity 的布局解析到 FrameLayout里面
        LayoutInflater.from(this).inflate(getContentView(), viewContent);
        init(savedInstanceState);
    }

    //设置标题
    protected void showReturnButton(boolean show) {
        toolbar.setVisibility(show == true ? View.VISIBLE : View.GONE);
    }

    //设置标题
    protected void setTitle(String text) {
        toolbar.setTitle(text);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    //获取继承的布局
    protected abstract int getContentView();

    //代替onCreate初始化，防止重复加载
    protected abstract void init(Bundle savedInstanceState);

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
    public ScheduledExecutorService timedTask(int seconds, Runnable runnable) {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        long initialDelay = 1;
        // 从现在开始1秒钟之后，每隔1秒钟执行一次
        scheduledExecutorService.scheduleAtFixedRate(runnable, initialDelay, seconds, TimeUnit.SECONDS);
        return scheduledExecutorService;
    }
}
