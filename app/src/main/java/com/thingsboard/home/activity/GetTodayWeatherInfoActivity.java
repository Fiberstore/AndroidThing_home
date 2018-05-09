package com.thingsboard.home.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.google.gson.Gson;
import com.thingsboard.home.R;
import com.thingsboard.home.base.BaseActivity;
import com.thingsboard.home.constant.ServerUrl;
import com.thingsboard.home.util.GetCpuTemperature;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import com.thingsboard.home.bean.WeatherInfoBean;
import com.thingsboard.home.util.GetInternetTimeInMillisAnsy;
import com.thingsboard.home.util.SetGpioState;
import com.thingsboard.home.util.TimeUtils;
import com.thingsboard.home.util.Utils;
import com.thingsboard.home.widget.NoticeView;
import com.thingsboard.home.widget.SetWeatherIconInfo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by zhangxiang on 2018/3/9.
 */

public class GetTodayWeatherInfoActivity extends BaseActivity implements GetInternetTimeInMillisAnsy.SetTimeStateInterface {

    @InjectView(R.id.image_today_weather_icon)
    ImageView imageTodayWeatherIcon;
    @InjectView(R.id.template_textview)
    TextView templateTextview;
    @InjectView(R.id.location_textview)
    TextView locationTextview;
    @InjectView(R.id.weather_today_info_textview)
    TextView weatherTodayInfoTextview;
    @InjectView(R.id.timeTextview)
    TextView timeTextview;
    @InjectView(R.id.template_hignt_textview)
    TextView templateHigntTextview;
    @InjectView(R.id.template_low_textview)
    TextView templateLowTextview;
    @InjectView(R.id.windDirection_textview)
    TextView windDirectionTextview;
    @InjectView(R.id.windGrade_textview)
    TextView windGradeTextview;
    @InjectView(R.id.humidity_textview)
    TextView humidityTextview;
    @InjectView(R.id.updata_time_textview)
    TextView updataTimeTextview;
    @InjectView(R.id.motionIndex_textview)
    TextView motionIndex_textview;
    @InjectView(R.id.date_textview)
    TextView dateTextview;
    @InjectView(R.id.weatherParameter_scollview)
    ScrollView weatherParameterScollview;
    @InjectView(R.id.cpu_temp_noticeView)
    NoticeView cpuTempNoticeView;
    private CountDownTimer cdTimer;
    private Gpio openBCM17Gpio, openBCM22Gpio, openBCM27Gpio, openBCM18Gpio;

    @Override
    protected int getContentView() {
        return R.layout.activity_weather_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
        setTimerInfo();
        getWeatherInfo();
        setCpuTempNoticeView();
        initGpio();
    }

    private void initGpio() {

        /**端口设备如下: [
         * BCM10,
         * BCM11,
         * BCM12,
         * BCM13,
         * BCM14,
         * BCM15,
         * BCM16,
         * BCM17,
         * BCM18,
         * BCM19,
         * BCM2,
         * BCM20,
         * BCM21,
         * BCM22,
         * BCM23,
         * BCM24,
         * BCM25,
         * BCM26,
         * BCM27,
         * BCM3,
         * BCM4,
         * BCM5,
         * BCM6,
         * BCM7,
         * BCM8,
         * BCM9]
         */
        PeripheralManager manager = PeripheralManager.getInstance();
        List<String> portList = manager.getGpioList();
        if (portList.isEmpty()) {
            Utils.myLog("GPIO端口没有设备");
        } else {
            Utils.myLog("端口设备如下: " + portList);
            try {
                openBCM17Gpio = manager.openGpio("BCM17");
                openBCM18Gpio = manager.openGpio("BCM18");
                openBCM22Gpio = manager.openGpio("BCM22");
                openBCM27Gpio = manager.openGpio("BCM27");
                SetGpioState.setOutputHight(openBCM17Gpio);
                SetGpioState.setOutputHight(openBCM18Gpio);
                SetGpioState.setOutputHight(openBCM22Gpio);
                SetGpioState.setOutputHight(openBCM27Gpio);
            } catch (Exception e) {

            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (openBCM17Gpio != null || openBCM27Gpio != null || openBCM22Gpio != null || openBCM18Gpio != null) {
            try {
                openBCM17Gpio.close();
                openBCM17Gpio = null;
                openBCM27Gpio.close();
                openBCM27Gpio = null;
                openBCM22Gpio.close();
                openBCM22Gpio = null;
                openBCM18Gpio.close();
                openBCM18Gpio = null;
            } catch (IOException e) {
                Utils.myLog("Unable to close GPIO" + e);
            }
        }
    }

    /**
     * 获取温度信息
     */
    private void setCpuTempNoticeView() {

        new GetCpuTemperature(new GetCpuTemperature.GetCPUTempInfoInterface() {
            @Override
            public void getCpuTempDoubleInfo(final double tempInfo) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cpuTempNoticeView.show("CPU 温度" + tempInfo + "℃");
                    }
                });

            }
        });
    }


    /**
     * 设置时间信息
     */
    private void setTimerInfo() {

        GetInternetTimeInMillisAnsy getInternetTimeInMillisAnsy = new GetInternetTimeInMillisAnsy(this);
        getInternetTimeInMillisAnsy.execute("");
    }

    private void getWeatherInfo() {
        OkHttpUtils
                .get()
                .url(ServerUrl.getAliWeatherUrl)
                .addHeader("Authorization", "APPCODE " + ServerUrl.appCode)
                .addParams("city", "武汉")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Utils.myLog(response);
                        WeatherInfoBean weatherInfoBean = new Gson().fromJson(response, WeatherInfoBean.class);
                        setUiinfo(weatherInfoBean);
                    }
                });
    }

    /**
     * 设置UI信息
     */
    private void setUiinfo(WeatherInfoBean weatherInfoBean) {
        switch (weatherInfoBean.getStatus()) {
            /**正常*/
            case "0":
                WeatherInfoBean.ResultBean weatherInfoBeanResult = weatherInfoBean.getResult();
                SetWeatherIconInfo.setWeatherInfo(this, weatherInfoBeanResult.getImg(), imageTodayWeatherIcon);
                setTemplate(weatherInfoBeanResult.getTemp());
                setLocationInfo(weatherInfoBeanResult.getCity());
                setTodayWeatherInfo(weatherInfoBeanResult.getWeather());
                setWeatherParameterInfo(weatherInfoBeanResult);
                break;
            /**城市和城市ID和城市代号都为空*/
            case "201":
                break;
            /**城市不存在*/
            case "202":
                break;
            /**此城市没有天气信息*/
            case "203":
                break;
            /**没有信息*/
            case "210":
                break;
        }
    }

    /**
     * 设置天气参数信息
     */
    private void setWeatherParameterInfo(WeatherInfoBean.ResultBean weatherInfoBeanResult) {
        /**设置日期*/
        dateTextview.setText(weatherInfoBeanResult.getDate());
        /**设置最高温度*/
        templateHigntTextview.setText(weatherInfoBeanResult.getTemphigh());
        /**设置最低温度*/
        templateLowTextview.setText(weatherInfoBeanResult.getTemplow());
        /**设置风向*/
        windDirectionTextview.setText(weatherInfoBeanResult.getWinddirect());
        /**设置风力等级*/
        windGradeTextview.setText(weatherInfoBeanResult.getWindpower());
        /**设置湿度数据*/
        humidityTextview.setText(weatherInfoBeanResult.getHumidity());
        /**设置天气数据更新时间*/
        updataTimeTextview.setText(weatherInfoBeanResult.getUpdatetime());
        /**设置运动指数*/
        motionIndex_textview.setText(weatherInfoBeanResult.getIndex().get(1).getDetail());
        /**更新时刷新至顶部*/
        weatherParameterScollview.fullScroll(ScrollView.FOCUS_UP);
    }

    private void setTodayWeatherInfo(String weather) {

        weatherTodayInfoTextview.setText(weather);
    }


    private void setLocationInfo(String city) {

        locationTextview.setText(city);
    }

    /**
     * 设置温度
     */
    private void setTemplate(String temp) {
        if (TextUtils.isEmpty(temp)) {
            return;
        }
        templateTextview.setText(temp);
    }




    /**
     * 获取网络时间并设置日历成功的回调
     */
    @Override
    public void setTimeState(boolean state) {

        /**倒计时方法*/
        cdTimer = new CountDownTimer(60000 * 24, 500) {
            @Override
            public void onTick(long l) {
                SimpleDateFormat hourformatter = new SimpleDateFormat("HH:mm:ss", Locale.CHINESE);
                hourformatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
                Calendar calendar = Calendar.getInstance();
                long timeInMillis = calendar.getTimeInMillis();
                String format = hourformatter.format(timeInMillis);
                timeTextview.setText(format);
                /**如果是整点*/
                if (TimeUtils.currentIsTheWholePointOf(calendar)) {
                    /**如果是整点刷新天气*/
                    getWeatherInfo();
                }
            }

            @Override
            public void onFinish() {
                cdTimer.start();
            }
        };
        cdTimer.start();
    }

    /**SetGpioState.setOutputState(openBCM17Gpio, buttonState1);*/
}
