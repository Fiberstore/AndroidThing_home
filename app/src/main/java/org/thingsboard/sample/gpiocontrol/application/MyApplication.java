package org.thingsboard.sample.gpiocontrol.application;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.thingsboard.sample.gpiocontrol.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author 作者：张祥 on 2018/2/1 0001.
 *         邮箱：847874028@qq.com
 *         版本：v1.0
 *         功能：
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误

        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));

        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        // Setting.setShowLog(false);
        super.onCreate();

        initOkHttp();
    }

    private void initOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
