package com.thingsboard.home.bean;

/**
 * Created by zhangxiang on 2018/3/7.
 */

public class MqttInfoBean {


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private String topic;
    private String info;
}
