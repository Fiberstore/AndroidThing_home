/**
 * Copyright © 2016 The Thingsboard Authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.sample.gpiocontrol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONException;
import org.json.JSONObject;
import org.thingsboard.sample.gpiocontrol.tts.TtsOutput;
import org.thingsboard.sample.gpiocontrol.util.GetInternetTime;
import org.thingsboard.sample.gpiocontrol.util.WriteReadADBShell;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GpioControlActivity extends Activity implements InitListener, SynthesizerListener {

    private static final String TAG = GpioControlActivity.class.getSimpleName();

    private static final String THINGSBOARD_HOST = "192.168.191.1";
    private static final String ACCESS_TOKEN = "fs.com_android_ting";

    private static final Map<Integer, String> gpioPinout = new HashMap<>();

    static {
        gpioPinout.put(7, "BCM4");
        gpioPinout.put(11, "BCM17");
        gpioPinout.put(12, "BCM18");
        gpioPinout.put(13, "BCM27");
        gpioPinout.put(15, "BCM22");
        gpioPinout.put(16, "BCM23");
        gpioPinout.put(18, "BCM24");
        gpioPinout.put(22, "BCM25");
        gpioPinout.put(29, "BCM5");
        gpioPinout.put(31, "BCM6");
        gpioPinout.put(32, "BCM12");
        gpioPinout.put(33, "BCM13");
        gpioPinout.put(35, "BCM19");
        gpioPinout.put(36, "BCM16");
        gpioPinout.put(37, "BCM26");
        gpioPinout.put(38, "BCM20");
        gpioPinout.put(40, "BCM21");
    }

    private Map<Integer, Gpio> mGpiosMap = new HashMap<>();
    private MqttAsyncClient mThingsboardMqttClient;
    private TtsOutput ttsOutput;
    // 缓冲进度
    private int mPercentForBuffering = 0;
    // 播放进度
    private int mPercentForPlaying = 0;
    private TextView cpuTemp_textview;

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            double temp = (double) msg.obj;
            cpuTemp_textview.setText(temp + "℃");
        }
    };
    private TextView time_textview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cpuTemp_textview = findViewById(R.id.cpu_temp);
        time_textview = findViewById(R.id.time_textview);
        SeekBar seekBar=findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("press", progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        findViewById(R.id.startActivity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ee", "2222");
                Intent intent = new Intent(GpioControlActivity.this, NewActivity.class);
                startActivity(intent);
            }
        });
      //  setSystemTime();
        // initMqttAndTts();

    }

    private void setSystemTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(GetInternetTime.getInternetTime());
    }

    private void initMqttAndTts() {
        ttsOutput = new TtsOutput(this);
        ttsOutput.init(this);
        PeripheralManagerService manager = new PeripheralManagerService();
        for (int k : gpioPinout.keySet()) {
            String gpioName = gpioPinout.get(k);
            try {
                Gpio gpio = manager.openGpio(gpioName);
                gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                Log.d(TAG, String.format("[%d] Gpio [%s] value: %b", k, gpioName, gpio.getValue()));
                mGpiosMap.put(k, gpio);
            } catch (IOException e) {
                Log.w(TAG, "Unable to access GPIO", e);
            }
        }

        // Initialize MQTT client
        try {
            mThingsboardMqttClient = new MqttAsyncClient("tcp://" + THINGSBOARD_HOST + ":1883", "Raspberry Pi 3", new MemoryPersistence());
        } catch (MqttException e) {
            Log.e(TAG, "Unable to create MQTT client", e);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        // mqttConnect();
      //  getDeviceTemplate(1);
    }

    /**
     * 获取CPU温度信息
     */
    private void getDeviceTemplate(int seconds) {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        long initialDelay = 1;
        // 从现在开始1秒钟之后，每隔1秒钟执行一次job1
        scheduledExecutorService.scheduleAtFixedRate(new RemindTask(), initialDelay, seconds, TimeUnit.SECONDS);
    }

    class RemindTask implements Runnable {
        @Override
        public void run() {
            String read = WriteReadADBShell.read("/sys/class/thermal/thermal_zone0/temp");
            double temp = Integer.parseInt(read) / 1000.0;
            Log.e("temp:", temp + "");
            getNetTime();
            Message message = new Message();
            message.obj = temp;
            myHandler.sendMessage(message);
          /*  JSONObject jsonObject = new JSONObject();
            try {
                String temperatureJSON = jsonObject.put("temperature", temp).toString();
                MqttMessage mqttMessage = new MqttMessage(temperatureJSON.getBytes());
                //  ttsOutput.startPlaySuond(temp+"", GpioControlActivity.this);
                mThingsboardMqttClient.publish("v1/devices/me/attributes", mqttMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // mqttDisconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (mGpiosMap != null) {
            for (Gpio gpio : mGpiosMap.values()) {
                try {
                    gpio.close();
                } catch (IOException e) {
                    Log.w(TAG, "Unable to close GPIO", e);
                }
            }
            mGpiosMap = null;
        }
    }

    private void mqttConnect() {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setUserName(ACCESS_TOKEN);
        mThingsboardMqttClient.setCallback(mMqttCallback);
        try {
            mThingsboardMqttClient.connect(connOpts, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i(TAG, "MQTT client connected!");
                    try {
                        mThingsboardMqttClient.subscribe("v1/devices/me/rpc/request/+", 0);
                    } catch (MqttException e) {
                        Log.e(TAG, "Unable to subscribe to rpc requests topic", e);
                    }
                    try {
                        mThingsboardMqttClient.publish("v1/devices/me/attributes", getGpiosStatusMessage());
                    } catch (Exception e) {
                        Log.e(TAG, "Unable to publish GPIO status to Thingsboard server", e);
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable e) {
                    if (e instanceof MqttException) {
                        MqttException mqttException = (MqttException) e;
                        Log.e(TAG, String.format("Unable to connect to Thingsboard server: %s, code: %d", mqttException.getMessage(),
                                mqttException.getReasonCode()), e);
                    } else {
                        Log.e(TAG, String.format("Unable to connect to Thingsboard server: %s", e.getMessage()), e);
                    }
                }
            });
        } catch (MqttException e) {
            Log.e(TAG, String.format("Unable to connect to Thingsboard server: %s, code: %d", e.getMessage(), e.getReasonCode()), e);
        }
    }

    private void mqttDisconnect() {
        try {
            mThingsboardMqttClient.disconnect();
            Log.i(TAG, "MQTT client disconnected!");
        } catch (MqttException e) {
            Log.e(TAG, "Unable to disconnect from the Thingsboard server", e);
        }
    }

    private MqttCallback mMqttCallback = new MqttCallback() {

        @Override
        public void connectionLost(Throwable e) {
            Log.e(TAG, "Disconnected from Thingsboard server", e);
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            Log.d(TAG, String.format("Received message from topic [%s]", topic));
            String requestId = topic.substring("v1/devices/me/rpc/request/".length());
            JSONObject messageData = new JSONObject(new String(message.getPayload()));
            Log.e("message", messageData.toString());
            String method = messageData.getString("method");
            if (method != null) {
                if (method.equals("getGpioStatus")) {
                    sendGpioStatus(requestId);
                } else if (method.equals("setGpioStatus")) {
                    JSONObject params = messageData.getJSONObject("params");
                    Integer pin = params.getInt("pin");
                    boolean enabled = params.getBoolean("enabled");
                    if (pin != null) {
                        updateGpioStatus(pin, enabled, requestId);
                    }
                } else {
                    //Client acts as an echo service
                    mThingsboardMqttClient.publish("v1/devices/me/rpc/response/" + requestId, message);
                }
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
        }
    };

    private MqttMessage getGpiosStatusMessage() throws Exception {
        JSONObject gpioStatus = new JSONObject();
        for (int k : mGpiosMap.keySet()) {
            Gpio gpio = mGpiosMap.get(k);
            boolean value = gpio.getValue();
            gpioStatus.put(k + "", value);
        }
        MqttMessage message = new MqttMessage(gpioStatus.toString().getBytes());
        return message;
    }

    private void sendGpioStatus(String requestId) throws Exception {
        mThingsboardMqttClient.publish("v1/devices/me/rpc/response/" + requestId, getGpiosStatusMessage());
    }

    private void updateGpioStatus(int pin, boolean enabled, String requestId) throws Exception {


        /**语音输出*/
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(enabled == true ? "打开" : "关闭");
        stringBuilder.append(pin + " ");
        ttsOutput.startPlaySuond(stringBuilder.toString(), this);

        Log.e("tts", stringBuilder.toString());

        JSONObject response = new JSONObject();
        Gpio gpio = mGpiosMap.get(pin);
        if (gpio != null) {
            gpio.setValue(enabled);
            response.put(pin + "", gpio.getValue());
        } else {
            response.put(pin + "", false);
        }
        MqttMessage message = new MqttMessage(response.toString().getBytes());
        mThingsboardMqttClient.publish("v1/devices/me/rpc/response/" + requestId, message);
        mThingsboardMqttClient.publish("v1/devices/me/attributes", message);
    }

    @Override
    public void onInit(int code) {
        Log.d(TAG, "InitListener init() code = " + code);
        if (code != ErrorCode.SUCCESS) {
            Log.e("tts_init", "初始化失败,错误码：" + code);
        } else {
            // 初始化成功，之后可以调用startSpeaking方法
            // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
            // 正确的做法是将onCreate中的startSpeaking调用移至这里
        }
    }

    @Override
    public void onSpeakBegin() {

    }

    @Override
    public void onBufferProgress(int percent, int beginPos, int endPos,
                                 String info) {

    }

    @Override
    public void onSpeakPaused() {

    }

    @Override
    public void onSpeakResumed() {

    }

    @Override
    public void onSpeakProgress(int percent, int beginPos, int endPos) {
        // 合成进度
        mPercentForBuffering = percent;
        Log.e("tts", String.format(getString(R.string.tts_toast_format),
                mPercentForBuffering, mPercentForPlaying));
    }

    @Override
    public void onCompleted(SpeechError speechError) {
        if (speechError == null) {
            Log.e("tts", "播放完成");
        } else if (speechError != null) {
            Log.e("tts", speechError.getPlainDescription(true));
        }
    }

    @Override
    public void onEvent(int i, int i1, int i2, Bundle bundle) {

    }

    private void getNetTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss", Locale.CHINESE);
        final String format = formatter.format(GetInternetTime.getInternetTime());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                time_textview.setText(format);
            }
        });
    }
}
