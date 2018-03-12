package org.thingsboard.sample.gpiocontrol.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.things.device.TimeManager;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.thingsboard.sample.gpiocontrol.R;
import org.thingsboard.sample.gpiocontrol.base.BaseActivity;
import org.thingsboard.sample.gpiocontrol.bean.WeatherInfoBean;
import org.thingsboard.sample.gpiocontrol.constant.ServerUrl;
import org.thingsboard.sample.gpiocontrol.util.GetInternetTime;
import org.thingsboard.sample.gpiocontrol.util.GetInternetTimeInMillisAnsy;
import org.thingsboard.sample.gpiocontrol.util.ShowImage;
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
    @InjectView(R.id.time_textview)
    TextView timeTextview;
    @InjectView(R.id.weather_today_info_textview)
    TextView weatherTodayInfoTextview;
    @InjectView(R.id.blueToothState_textview)
    TextView blueToothStateTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);
        ButterKnife.inject(this);
        setTimerInfo();
        getWeatherInfo();

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
        //setTimeInfo();
        switch (weatherInfoBean.getStatus()) {
            /**正常*/
            case "0":
                WeatherInfoBean.ResultBean weatherInfoBeanResult = weatherInfoBean.getResult();
                //setWeatherInfo(weatherInfoBeanResult.getImg());
                setTemplate(weatherInfoBeanResult.getTemp());
                setLocationInfo(weatherInfoBeanResult.getCity());
                setTodayWeatherInfo(weatherInfoBeanResult.getWeather());
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

    private void setTodayWeatherInfo(String weather) {

        weatherTodayInfoTextview.setText(weather);
    }

    /**
     * 设置时间信息
     */
    private void setTimeInfo() {
        timedTask(1, new Runnable() {
            @Override
            public void run() {
                final String formatTime = GetInternetTime.getFormatTimeHour();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeTextview.setText(formatTime);
                    }
                });
            }
        });
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
                ShowImage.showImage(this, iconArray[0], imageTodayWeatherIcon);
                break;
            case "1":
                ShowImage.showImage(this, iconArray[1], imageTodayWeatherIcon);
                break;
            case "2":
                ShowImage.showImage(this, iconArray[2], imageTodayWeatherIcon);
                break;
            case "3":
                ShowImage.showImage(this, iconArray[3], imageTodayWeatherIcon);
                break;
            case "4":
                ShowImage.showImage(this, iconArray[4], imageTodayWeatherIcon);
                break;
            case "5":
                ShowImage.showImage(this, iconArray[5], imageTodayWeatherIcon);
                break;
            case "6":
                ShowImage.showImage(this, iconArray[6], imageTodayWeatherIcon);
                break;
            case "7":
                ShowImage.showImage(this, iconArray[7], imageTodayWeatherIcon);
                break;
            case "8":
                ShowImage.showImage(this, iconArray[8], imageTodayWeatherIcon);
                break;
            case "9":
                ShowImage.showImage(this, iconArray[9], imageTodayWeatherIcon);
                break;
            case "10":
                ShowImage.showImage(this, iconArray[10], imageTodayWeatherIcon);
                break;
            case "11":
                ShowImage.showImage(this, iconArray[11], imageTodayWeatherIcon);
                break;
            case "12":
                ShowImage.showImage(this, iconArray[12], imageTodayWeatherIcon);
                break;
            case "13":
                ShowImage.showImage(this, iconArray[13], imageTodayWeatherIcon);
                break;
            case "14":
                ShowImage.showImage(this, iconArray[14], imageTodayWeatherIcon);
                break;
            case "15":
                ShowImage.showImage(this, iconArray[15], imageTodayWeatherIcon);
                break;
            case "16":
                ShowImage.showImage(this, iconArray[16], imageTodayWeatherIcon);
                break;
            case "17":
                ShowImage.showImage(this, iconArray[17], imageTodayWeatherIcon);
                break;
            case "18":
                ShowImage.showImage(this, iconArray[18], imageTodayWeatherIcon);
                break;
            case "19":
                ShowImage.showImage(this, iconArray[19], imageTodayWeatherIcon);
                break;
            case "20":
                ShowImage.showImage(this, iconArray[20], imageTodayWeatherIcon);
                break;
            case "21":
                ShowImage.showImage(this, iconArray[21], imageTodayWeatherIcon);
                break;
            case "22":
                ShowImage.showImage(this, iconArray[22], imageTodayWeatherIcon);
                break;
            case "23":
                ShowImage.showImage(this, iconArray[23], imageTodayWeatherIcon);
                break;
            case "24":
                ShowImage.showImage(this, iconArray[24], imageTodayWeatherIcon);
                break;
            case "25":
                ShowImage.showImage(this, iconArray[25], imageTodayWeatherIcon);
                break;
            case "26":
                ShowImage.showImage(this, iconArray[26], imageTodayWeatherIcon);
                break;
            case "27":
                ShowImage.showImage(this, iconArray[27], imageTodayWeatherIcon);
                break;
            case "28":
                ShowImage.showImage(this, iconArray[28], imageTodayWeatherIcon);
                break;
            case "29":
                ShowImage.showImage(this, iconArray[29], imageTodayWeatherIcon);
                break;
            case "30":
                ShowImage.showImage(this, iconArray[30], imageTodayWeatherIcon);
                break;
            case "31":
                ShowImage.showImage(this, iconArray[31], imageTodayWeatherIcon);
                break;
            case "32":
                ShowImage.showImage(this, iconArray[32], imageTodayWeatherIcon);
                break;
            case "33":
                ShowImage.showImage(this, iconArray[33], imageTodayWeatherIcon);
                break;
            case "34":
                ShowImage.showImage(this, iconArray[34], imageTodayWeatherIcon);
                break;
            case "35":
                ShowImage.showImage(this, iconArray[35], imageTodayWeatherIcon);
                break;
            case "36":
                ShowImage.showImage(this, iconArray[36], imageTodayWeatherIcon);
                break;
            case "37":
                ShowImage.showImage(this, iconArray[37], imageTodayWeatherIcon);
                break;
            case "38":
                ShowImage.showImage(this, iconArray[38], imageTodayWeatherIcon);
                break;
            case "39":
                ShowImage.showImage(this, iconArray[39], imageTodayWeatherIcon);
                break;
            case "49":
                ShowImage.showImage(this, iconArray[40], imageTodayWeatherIcon);
                break;
            case "53":
                ShowImage.showImage(this, iconArray[41], imageTodayWeatherIcon);
                break;
            case "54":
                ShowImage.showImage(this, iconArray[42], imageTodayWeatherIcon);
                break;
            case "55":
                ShowImage.showImage(this, iconArray[43], imageTodayWeatherIcon);
                break;
            case "56":
                ShowImage.showImage(this, iconArray[44], imageTodayWeatherIcon);
                break;
            case "57":
                ShowImage.showImage(this, iconArray[45], imageTodayWeatherIcon);
                break;
            case "58":
                ShowImage.showImage(this, iconArray[46], imageTodayWeatherIcon);
                break;
            case "99":
                ShowImage.showImage(this, iconArray[47], imageTodayWeatherIcon);
                break;
            case "301":
                ShowImage.showImage(this, iconArray[48], imageTodayWeatherIcon);
                break;
            case "302":
                ShowImage.showImage(this, iconArray[49], imageTodayWeatherIcon);
                break;
            default:
                ShowImage.showImage(this, iconArray[47], imageTodayWeatherIcon);
                break;
        }
    }

    @OnClick(R.id.getBlueToothDevice)
    public void onViewClicked() {


    }

    @Override
    public void setTimeState(boolean state) {
        SimpleDateFormat hourformatter = new SimpleDateFormat("HH:mm:ss", Locale.CHINESE);
        timedTask(1, new Runnable() {
            @Override
            public void run() {
                Log.e("vvvv", "1111");
                // hourformatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
                // Calendar calendar = Calendar.getInstance();
                // long timeInMillis = calendar.getTimeInMillis();
                // final String format = hourformatter.format(timeInMillis);
                //  timeTextview.setText(new );
            }
        });
    }
}
