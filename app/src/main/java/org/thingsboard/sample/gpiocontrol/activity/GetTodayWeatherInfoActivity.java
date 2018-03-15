package org.thingsboard.sample.gpiocontrol.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.thingsboard.sample.gpiocontrol.R;
import org.thingsboard.sample.gpiocontrol.base.BaseActivity;
import org.thingsboard.sample.gpiocontrol.bean.WeatherInfoBean;
import org.thingsboard.sample.gpiocontrol.constant.ServerUrl;
import org.thingsboard.sample.gpiocontrol.device.bluetooth.BluetoothDeviceList;
import org.thingsboard.sample.gpiocontrol.util.GetInternetTimeInMillisAnsy;
import org.thingsboard.sample.gpiocontrol.util.TimeUtils;
import org.thingsboard.sample.gpiocontrol.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
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
    @InjectView(R.id.videoView)
    VideoView videoView;
    private CountDownTimer cdTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);
        ButterKnife.inject(this);
        setTimerInfo();
        getWeatherInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.setVideoURI(Uri.parse("http://alcdn.hls.xiaoka.tv/2017427/14b/7b3/Jzq08Sl8BbyELNTo/index.m3u8"));
        videoView.start();
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
                setWeatherInfo(weatherInfoBeanResult.getImg());
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
        templateTextview.setText(temp);
    }

    /**
     * 设置天气信息
     */
    private void setWeatherInfo(String image) {

        /**0 晴
         1 多云
         2 阴
         3 阵雨
         4 雷阵雨
         5 雷阵雨伴有冰雹
         6 雨夹雪
         7 小雨
         8 中雨
         9 大雨
         10 暴雨
         11 大暴雨
         12 特大暴雨
         13 阵雪
         14 小雪
         15 中雪
         16 大雪
         17 暴雪
         18 雾
         19 冻雨
         20 沙尘暴
         21 小雨-中雨
         22 中雨-大雨
         23 大雨-暴雨
         24 暴雨-大暴雨
         25 大暴雨-特大暴雨
         26 小雪-中雪
         27 中雪-大雪
         28 大雪-暴雪
         29 浮尘
         30 扬沙
         31 强沙尘暴
         32 浓雾
         39 台风
         49 强浓雾
         53 霾
         54 中毒霾
         55 重度霾
         56 严重霾
         57 大雾
         58 特强浓雾
         99 无
         301 雨
         302 雪*/
        int[] iconArray = getResources().getIntArray(R.array.weather_icon_array);
        switch (image) {
            case "0":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_0_sun);
                break;
            case "1":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_1_duoyun);
                break;
            case "2":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_2_ying);
                break;
            case "3":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_3_zhenyu);
                break;
            case "4":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_4_leizhenyu);
                break;
            case "5":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_5_leizhenyu_bingbao);
                break;
            case "6":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_6_yujiaxue);
                break;
            case "7":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_7_xiaoyu);
                break;
            case "8":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_8_zhongyu);
                break;
            case "9":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_9_dayu);
                break;
            case "10":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_10_baoyu);
                break;
            case "11":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_11_dabaoyu);
                break;
            case "12":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_12_tedabaoyu);
                break;
            case "13":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_13_zhenxue);
                break;
            case "14":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_14_xiaoxue);
                break;
            case "15":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_15_zhongxue);
                break;
            case "16":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_16_daxue);
                break;
            case "17":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_17_baoxue);
                break;
            case "18":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_18_wu);
                break;
            case "19":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_19_dongyu);
                break;
            case "20":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_20_shachengbao);
                break;
            case "21":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_21_xiaoyu_zhonyu);
                break;
            case "22":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_22_zhongyu_dayu);
                break;
            case "23":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_23_dayu_baoyu);
                break;
            case "24":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_24_baoyu_dabaoyu);
                break;
            case "25":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_25_dabao_tedabaoyu);
                break;
            case "26":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_26_xiaoxue_zhongxue);
                break;
            case "27":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_27_zhongxue_daxue);
                break;
            case "28":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_28_daxue_baoxue);
                break;
            case "29":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_29_fucheng);
                break;
            case "30":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_30_yangchen);
                break;
            case "31":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_31_qiangshachen);
                break;
            case "32":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_32_nongwu);
                break;
            case "49":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_49_qiangnongwu);
                break;
            case "53":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_53_mai);
                break;
            case "54":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_54_zhongdumai);
                break;
            case "55":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_55_zzhongdumai);
                break;
            case "56":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_56_yanzhongmai);
                break;
            case "57":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_57_dawu);
                break;
            case "58":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_58_tedanongwu);
                break;
            case "99":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_99_non);
                break;
            case "301":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_301_yu);
                break;
            case "302":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_302_xue);
                break;
            default:
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_7_xiaoyu);
                break;
        }
    }
/*
    @OnClick(R.id.getBlueToothDevice)
    public void onViewClicked() {

        startActivity(new Intent(this, BluetoothDeviceList.class));
    }*/


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
}
