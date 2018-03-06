package org.thingsboard.sample.gpiocontrol.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.thingsboard.sample.gpiocontrol.base.BaseActivity;
import org.thingsboard.sample.gpiocontrol.util.GetInternetTime;

/**
 * @author 作者：张祥 on 2018/3/5 0005.
 *         邮箱：847874028@qq.com
 *         版本：v1.0
 *         功能：
 */

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**设置系统时间*/
        GetInternetTime.setSystemTime();

    }
}
