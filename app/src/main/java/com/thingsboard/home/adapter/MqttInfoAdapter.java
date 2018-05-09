package com.thingsboard.home.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thingsboard.home.R;
import com.thingsboard.home.bean.MqttInfoBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxiang on 2018/3/7.
 */

public class MqttInfoAdapter extends BaseAdapter {

    private Activity activity;
    private List<MqttInfoBean> infoList;
    private final LayoutInflater layoutInflater;
    private ViewHolder viewHolder;

    public MqttInfoAdapter(Activity activity, List<MqttInfoBean> infoList) {
        super();
        this.activity = activity;
        this.infoList = infoList;
        layoutInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return infoList == null ? 0 : infoList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_mqtt_info, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        MqttInfoBean mqttInfoBean = infoList.get(i);
        viewHolder.mqttInfoTopicTextview.setText(mqttInfoBean.getTopic());
        viewHolder.mqttInfoTextview.setText(mqttInfoBean.getInfo());
        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.mqtt_info_textview)
        TextView mqttInfoTextview;
        @InjectView(R.id.mqtt_info_topic_textview)
        TextView mqttInfoTopicTextview;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
