package org.thingsboard.sample.gpiocontrol.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import org.thingsboard.sample.gpiocontrol.R;
import org.thingsboard.sample.gpiocontrol.base.BaseActivity;

/**
 * Created by zhangxiang on 2018/3/4.
 */
public class NewActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        findViewById(R.id.finish_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewActivity.this.finish();
            }
        });
    }
}
