package org.thingsboard.sample.gpiocontrol.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;


import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
import org.thingsboard.sample.gpiocontrol.R;
import org.thingsboard.sample.gpiocontrol.base.BaseActivity;
import org.thingsboard.sample.gpiocontrol.util.Utils;
import org.thingsboard.sample.gpiocontrol.util.WriteReadADBShell;


/**
 * @author 作者：张祥 on 2018/3/6 0006.
 *         邮箱：847874028@qq.com
 *         版本：v1.0
 *         功能：
 */

public class MqttTest extends BaseActivity {

    /**
     * 主机名称
     */
    private static final String HOST = "tcp://android_home.mqtt.iot.gz.baidubce.com:1883";
    /**
     * 用户名
     */
    private static final String USERNAME = "android_home/esp8266";
    /**
     * 密码
     */
    private static final String PASSWORD = "tPRVoaQXCDKMLWu+w8LXtqwZ5cXoGTgocCsKVA6eXR8=";

    private MqttAsyncClient mThingsboardMqttClient;
    private static final String TAG = "";
    private TextView connectInfo;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Utils.myLog(msg.obj + "");
            connectInfo.append(msg.obj + "\n");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt_test);
        connectInfo = findViewById(R.id.connect_info);
        initMqtt();
    }

    @Override
    protected void onStart() {
        super.onStart();
        gettemp();
        mqttConnect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mqttDisconnect();
    }

    /**
     * 初始化MQTT
     */
    private void initMqtt() {
        Message handerMessage = new Message();
        try {
            mThingsboardMqttClient = new MqttAsyncClient(HOST, "esp826_1", new MemoryPersistence());
        } catch (MqttException e) {
            Utils.myLog("Unable to create MQTT client" + e);
            handerMessage.obj = "创建MQTT客户端失败";
            handler.sendMessage(handerMessage);
        }
    }

    private void mqttConnect() {

        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setUserName(USERNAME);
        connOpts.setPassword(PASSWORD.toCharArray());
        mThingsboardMqttClient.setCallback(mMqttCallback);
        try {
            mThingsboardMqttClient.connect(connOpts, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Utils.myLog("MQTT 服务器连接成功");
                    Message handerMessage = new Message();
                    handerMessage.obj = "服务器连接成功";
                    handler.sendMessage(handerMessage);
                    try {
                        mThingsboardMqttClient.subscribe("temp/*", 0);
                    } catch (MqttException e) {
                        Utils.myLog("订阅服务异常" + e);
                        handerMessage.obj = "订阅服务异常" + e;
                        handler.sendMessage(handerMessage);
                    }
                    try {
                        mThingsboardMqttClient.publish("temp", sendStringMessage("loginSuccess"));
                    } catch (Exception e) {
                        Utils.myLog("发送消息异常" + e);
                        handerMessage.obj = "发送消息异常" + e;
                        handler.sendMessage(handerMessage);
                    }
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable e) {
                    if (e instanceof MqttException) {
                        MqttException mqttException = (MqttException) e;
                        Utils.myLog(String.format("连接异常2", mqttException.getMessage() + mqttException.getReasonCode()) + e);
                        Message handerMessage = new Message();
                        handerMessage.obj = "连接异常2" + mqttException.getMessage();
                        handler.sendMessage(handerMessage);

                    } else {
                        Utils.myLog(String.format("连接异常服务器异常 %s", e.getMessage()) + e);
                        Message handerMessage = new Message();
                        handerMessage.obj = "连接异常服务器异常" + e.getMessage();
                        handler.sendMessage(handerMessage);
                    }
                }
            });
        } catch (MqttException e) {
            Utils.myLog(String.format("Unable to connect to Thingsboard server: %s, code: %d" + e.getMessage() + e.getReasonCode()) + e);
            Message handerMessage = new Message();
            handerMessage.obj = "连接异常服务器异常" + e.getMessage();
            handler.sendMessage(handerMessage);
        }
    }

    private MqttCallback mMqttCallback = new MqttCallback() {

        @Override
        public void connectionLost(Throwable e) {
            Utils.myLog("Disconnected from Thingsboard server" + e);
            Message handerMessage = new Message();
            handerMessage.obj = "连接异常服务器异常" + e.getMessage();
            handler.sendMessage(handerMessage);
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            Utils.myLog(String.format("Received message from topic [%s]", topic));
            Message handerMessage = new Message();
            handerMessage.obj = "topic" + topic + message.toString();
            handler.sendMessage(handerMessage);
         /*   String requestId = topic.substring("v1/devices/me/rpc/request/".length());
            JSONObject messageData = new JSONObject(new String(message.getPayload()));
            Utils.myLog("message", messageData.toString());
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
            }*/
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            Message handerMessage = new Message();
            handerMessage.obj = "消息发送完成";

        }
    };

    private MqttMessage sendStringMessage(String stringMessage) throws Exception {
        MqttMessage message = new MqttMessage(stringMessage.getBytes());
        return message;
    }

    private MqttMessage getGpiosStatusMessage() throws Exception {
        JSONObject gpioStatus = new JSONObject();
     /*   for (int k : mGpiosMap.keySet()) {
            Gpio gpio = mGpiosMap.get(k);
            boolean value = gpio.getValue();
            gpioStatus.put(k + "", value);
        }*/
        MqttMessage message = new MqttMessage(gpioStatus.toString().getBytes());
        return message;
    }

    private void sendGpioStatus(String requestId) throws Exception {
        mThingsboardMqttClient.publish("v1/devices/me/rpc/response/" + requestId, getGpiosStatusMessage());
    }

    private void updateGpioStatus(int pin, boolean enabled, String requestId) throws Exception {

        JSONObject response = new JSONObject();
        /*Gpio gpio = mGpiosMap.get(pin);
        if (gpio != null) {
            gpio.setValue(enabled);
            response.put(pin + "", gpio.getValue());
        } else {
            response.put(pin + "", false);
        }*/
        MqttMessage message = new MqttMessage(response.toString().getBytes());
        mThingsboardMqttClient.publish("v1/devices/me/rpc/response/" + requestId, message);
        mThingsboardMqttClient.publish("v1/devices/me/attributes", message);
    }

    private void mqttDisconnect() {
        try {
            mThingsboardMqttClient.disconnect();
            Utils.myLog("MQTT client disconnected!");
        } catch (MqttException e) {
            Utils.myLog("Unable to disconnect from the Thingsboard server" + e);
        }
    }


    public void gettemp() {
        timedTask(1, new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                try {
                    String temperatureJSON = jsonObject.put("temperature", WriteReadADBShell.getCPUTemp()).toString();
                    MqttMessage mqttMessage = new MqttMessage(temperatureJSON.getBytes());
                    //  ttsOutput.startPlaySuond(temp+"", GpioControlActivity.this);
                    mThingsboardMqttClient.publish("temp/", mqttMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
