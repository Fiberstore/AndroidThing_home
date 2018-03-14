package org.thingsboard.sample.gpiocontrol.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.thingsboard.sample.gpiocontrol.R;
import org.thingsboard.sample.gpiocontrol.base.BaseActivity;
import org.thingsboard.sample.gpiocontrol.bean.WeatherInfoBean;
import org.thingsboard.sample.gpiocontrol.constant.ServerUrl;
import org.thingsboard.sample.gpiocontrol.util.GetInternetTimeInMillisAnsy;
import org.thingsboard.sample.gpiocontrol.util.ShowImage;
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
    @InjectView(R.id.blueToothState_textview)
    TextView blueToothStateTextview;
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
    private CountDownTimer cdTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);
        ButterKnife.inject(this);
        setTimerInfo();
        getWeatherInfo();
        initBluetoothState();
    }

    private void initBluetoothState() {

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
            default:
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

    /**
     * 设置今日天气信息
     */
    private void setTodayWeatherInfo(String weather) {

        weatherTodayInfoTextview.setText(weather);
    }

    /**
     * 设置城市信息
     */
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
        int[] iconArray = getResources().getIntArray(R.array.weather_icon_array);
        switch (image) {
            case "0":
                /**0 晴*/
                ShowImage.showImage(this, iconArray[0], imageTodayWeatherIcon);
                break;
            case "1":
                /** 1 多云*/
                ShowImage.showImage(this, iconArray[1], imageTodayWeatherIcon);
                break;
            case "2":
                /**2 阴*/
                ShowImage.showImage(this, iconArray[2], imageTodayWeatherIcon);
                break;
            case "3":
                /**3 阵雨*/
                ShowImage.showImage(this, iconArray[3], imageTodayWeatherIcon);
                break;
            case "4":
                /**4 雷阵雨*/
                ShowImage.showImage(this, iconArray[4], imageTodayWeatherIcon);
                break;
            case "5":
                /**5 雷阵雨伴有冰雹*/
                ShowImage.showImage(this, iconArray[5], imageTodayWeatherIcon);
                break;
            case "6":
                /**6 雨夹雪*/
                ShowImage.showImage(this, iconArray[6], imageTodayWeatherIcon);
                break;
            case "7":
                /** 7 小雨*/
                ShowImage.showImage(this, iconArray[7], imageTodayWeatherIcon);
                break;
            case "8":
                /**8 中雨*/
                ShowImage.showImage(this, iconArray[8], imageTodayWeatherIcon);
                break;
            case "9":
                /** 9 大雨*/
                ShowImage.showImage(this, iconArray[9], imageTodayWeatherIcon);
                break;
            case "10":
                /**10 暴雨*/
                ShowImage.showImage(this, iconArray[10], imageTodayWeatherIcon);
                break;
            case "11":
                /**11 大暴雨*/
                ShowImage.showImage(this, iconArray[11], imageTodayWeatherIcon);
                break;
            case "12":
                /**12 特大暴雨*/
                ShowImage.showImage(this, iconArray[12], imageTodayWeatherIcon);
                break;
            case "13":
                /**13 阵雪*/
                ShowImage.showImage(this, iconArray[13], imageTodayWeatherIcon);
                break;
            case "14":
                /**14 小雪*/
                ShowImage.showImage(this, iconArray[14], imageTodayWeatherIcon);
                break;
            case "15":
                /**15 中雪*/
                ShowImage.showImage(this, iconArray[15], imageTodayWeatherIcon);
                break;
            case "16":
                /**16 大雪*/
                ShowImage.showImage(this, iconArray[16], imageTodayWeatherIcon);
                break;
            case "17":
                /** 17 暴雪*/
                ShowImage.showImage(this, iconArray[17], imageTodayWeatherIcon);
                break;
            case "18":
                /**18 雾*/
                ShowImage.showImage(this, iconArray[18], imageTodayWeatherIcon);
                break;
            case "19":
                /**19 冻雨*/
                ShowImage.showImage(this, iconArray[19], imageTodayWeatherIcon);
                break;
            case "20":
                /**20 沙尘暴*/
                ShowImage.showImage(this, iconArray[20], imageTodayWeatherIcon);
                break;
            case "21":
                /** 21 小雨-中雨*/
                ShowImage.showImage(this, iconArray[21], imageTodayWeatherIcon);
                break;
            case "22":
                /**22 中雨-大雨*/
                ShowImage.showImage(this, iconArray[22], imageTodayWeatherIcon);
                break;
            case "23":
                /**23 大雨-暴雨*/
                ShowImage.showImage(this, iconArray[23], imageTodayWeatherIcon);
                break;
            case "24":
                /**24 暴雨-大暴雨*/
                ShowImage.showImage(this, iconArray[24], imageTodayWeatherIcon);
                break;
            case "25":
                /**25 大暴雨-特大暴雨*/
                ShowImage.showImage(this, iconArray[25], imageTodayWeatherIcon);
                break;
            case "26":
                /**26 小雪-中雪*/
                ShowImage.showImage(this, iconArray[26], imageTodayWeatherIcon);
                break;
            case "27":
                /**27 中雪-大雪*/
                ShowImage.showImage(this, iconArray[27], imageTodayWeatherIcon);
                break;
            case "28":
                /** 28 大雪-暴雪*/
                ShowImage.showImage(this, iconArray[28], imageTodayWeatherIcon);
                break;
            case "29":
                /**29 浮尘*/
                ShowImage.showImage(this, iconArray[29], imageTodayWeatherIcon);
                break;
            case "30":
                /**30 扬沙*/
                ShowImage.showImage(this, iconArray[30], imageTodayWeatherIcon);
                break;
            case "31":
                /** 31 强沙尘暴*/
                ShowImage.showImage(this, iconArray[31], imageTodayWeatherIcon);
                break;
            case "32":
                /** 32 浓雾*/
                ShowImage.showImage(this, iconArray[32], imageTodayWeatherIcon);
                break;
            case "39":
                /** 39 台风*/
                ShowImage.showImage(this, iconArray[33], imageTodayWeatherIcon);
                break;
            case "49":
                /** 49 强浓雾*/
                ShowImage.showImage(this, iconArray[34], imageTodayWeatherIcon);
                break;
            case "53":
                /**  53 霾*/
                ShowImage.showImage(this, iconArray[35], imageTodayWeatherIcon);
                break;
            case "54":
                /** 54 中毒霾*/
                ShowImage.showImage(this, iconArray[36], imageTodayWeatherIcon);
                break;
            case "55":
                /** 55 重度霾*/
                ShowImage.showImage(this, iconArray[37], imageTodayWeatherIcon);
                break;
            case "56":
                /** 56 严重霾*/
                ShowImage.showImage(this, iconArray[38], imageTodayWeatherIcon);
                break;
            case "57":
                /** 57 大雾*/
                ShowImage.showImage(this, iconArray[39], imageTodayWeatherIcon);
                break;
            case "58":
                /**58 特强浓雾*/
                ShowImage.showImage(this, iconArray[40], imageTodayWeatherIcon);
                break;
            case "99":
                /** 99 无*/
                ShowImage.showImage(this, iconArray[41], imageTodayWeatherIcon);
                break;
            case "301":
                /** 301 雨*/
                ShowImage.showImage(this, iconArray[42], imageTodayWeatherIcon);
                break;
            case "302":
                /**302 雪*/
                ShowImage.showImage(this, iconArray[43], imageTodayWeatherIcon);
                break;
            default:
                /**无*/
                ShowImage.showImage(this, iconArray[42], imageTodayWeatherIcon);
                break;
        }
    }

    @OnClick(R.id.getBlueToothDevice)
    public void onViewClicked() {


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
                /**如果是整点刷新天气*/
                if (TimeUtils.currentIsTheWholePointOf(calendar)) {
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
