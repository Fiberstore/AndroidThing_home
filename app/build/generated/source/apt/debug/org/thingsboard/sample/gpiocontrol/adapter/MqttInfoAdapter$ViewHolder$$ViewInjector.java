// Generated code from Butter Knife. Do not modify!
package org.thingsboard.sample.gpiocontrol.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MqttInfoAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final org.thingsboard.sample.gpiocontrol.adapter.MqttInfoAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296343, "field 'mqttInfoTextview'");
    target.mqttInfoTextview = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296344, "field 'mqttInfoTopicTextview'");
    target.mqttInfoTopicTextview = (android.widget.TextView) view;
  }

  public static void reset(org.thingsboard.sample.gpiocontrol.adapter.MqttInfoAdapter.ViewHolder target) {
    target.mqttInfoTextview = null;
    target.mqttInfoTopicTextview = null;
  }
}
