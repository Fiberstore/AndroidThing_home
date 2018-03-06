package org.thingsboard.sample.gpiocontrol.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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


/**
 * @author 作者：张祥 on 2018/3/6 0006.
 *         邮箱：847874028@qq.com
 *         版本：v1.0
 *         功能：
 */

public class MqttTest extends BaseActivity {

    /**主机名称*/
    private static final String HOST = "tcp://android_home.mqtt.iot.gz.baidubce.com:1883";
    /**用户名*/
    private static final String USERNAME = "android_home/esp8266";
    /**密码*/
    private static final String PASSWORD = "tPRVoaQXCDKMLWu+w8LXtqwZ5cXoGTgocCsKVA6eXR8=";

    private MqttAsyncClient mThingsboardMqttClient;
    private static final String TAG = MqttTest.class.getSimpleName();
    private TextView connectInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt_test);
        connectInfo = findViewById(R.id.connect_info);
        initMqtt();
    }


    @Override
    protected void onStart() {
        super.onStart();
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
        try {
            mThingsboardMqttClient = new MqttAsyncClient(HOST, "esp826_1", new MemoryPersistence());
        } catch (MqttException e) {
            Log.e("TAG:", "Unable to create MQTT client", e);
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
                    Log.i(TAG, "MQTT client connected!");
                    connectInfo.setText("服务器连接成功");
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
                        Log.e(TAG, String.format("Unable to connect to Thingsboard server: %s, code: %d", mqttException.getMessage(), mqttException.getReasonCode()), e);
                        connectInfo.setText(String.format("连接异常2: %s, code: %d", mqttException.getMessage(),
                                mqttException.getReasonCode()));

                    } else {
                        Log.e(TAG, String.format("Unable to connect to Thingsboard server: %s", e.getMessage()), e);
                        connectInfo.setText(String.format("连接异常2: %s, code: %d", e.getMessage()));

                    }
                }
            });
        } catch (MqttException e) {
            Log.e(TAG, String.format("Unable to connect to Thingsboard server: %s, code: %d", e.getMessage(), e.getReasonCode()), e);
            connectInfo.setText(String.format("连接异常1: %s, code: %d", e.getMessage(), e.getReasonCode()));

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
            Log.i(TAG, "MQTT client disconnected!");
        } catch (MqttException e) {
            Log.e(TAG, "Unable to disconnect from the Thingsboard server", e);
        }
    }

}
