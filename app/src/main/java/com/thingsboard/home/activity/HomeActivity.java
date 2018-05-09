package com.thingsboard.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.thingsboard.home.R;
import com.thingsboard.home.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author 作者：张祥 on 2018/3/5 0005.
 * 邮箱：847874028@qq.com
 * 版本：v1.0
 * 功能：
 */

public class HomeActivity extends BaseActivity {

    @InjectView(R.id.bt_time)
    Button btTime;
    @InjectView(R.id.bt_gps)
    Button btGps;
    @InjectView(R.id.bt_kugou)
    Button btKugou;
    @InjectView(R.id.bt_mqtt)
    Button btMqtt;
    @InjectView(R.id.bt_i2c)
    Button btI2c;
    @InjectView(R.id.bt_uart)
    Button btUart;

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        showReturnButton(false);
        setTitle("首页");
    }


    @OnClick({R.id.bt_time, R.id.bt_gps, R.id.bt_kugou, R.id.bt_mqtt, R.id.bt_i2c, R.id.bt_uart, R.id.bt_svg,R.id.bt_blueTooth,R.id.bt_welcome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_time:
                startActivity(new Intent(this, GetTodayWeatherInfoActivity.class));
                break;
            case R.id.bt_gps:
                startActivity(new Intent(this, UartGpsActivity.class));
                break;
            case R.id.bt_kugou:
                startActivity(new Intent(this, KuGouMusicActivity.class));
                break;
            case R.id.bt_mqtt:
                startActivity(new Intent(this, ConnectMqttActivity.class));
                break;
            case R.id.bt_i2c:
                startActivity(new Intent(this, I2CInfoActivity.class));
                break;
            case R.id.bt_uart:
                break;
            case R.id.bt_svg:
                startActivity(new Intent(this, SvgTest.class));
                break;
            case R.id.bt_blueTooth:
                startActivity(new Intent(this, BluetoothDeviceListActivity.class));
                break;
            case R.id.bt_welcome:
                startActivity(new Intent(this, TimeActivity.class));
                break;
        }
    }
}
