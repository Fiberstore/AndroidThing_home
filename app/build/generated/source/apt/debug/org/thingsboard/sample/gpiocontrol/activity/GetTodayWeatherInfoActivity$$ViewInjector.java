// Generated code from Butter Knife. Do not modify!
package org.thingsboard.sample.gpiocontrol.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class GetTodayWeatherInfoActivity$$ViewInjector {
  public static void inject(Finder finder, final org.thingsboard.sample.gpiocontrol.activity.GetTodayWeatherInfoActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296324, "field 'imageTodayWeatherIcon'");
    target.imageTodayWeatherIcon = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131296402, "field 'templateTextview'");
    target.templateTextview = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296333, "field 'locationTextview'");
    target.locationTextview = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296423, "field 'weatherTodayInfoTextview'");
    target.weatherTodayInfoTextview = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296408, "field 'timeTextview'");
    target.timeTextview = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296400, "field 'templateHigntTextview'");
    target.templateHigntTextview = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296401, "field 'templateLowTextview'");
    target.templateLowTextview = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296425, "field 'windDirectionTextview'");
    target.windDirectionTextview = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296426, "field 'windGradeTextview'");
    target.windGradeTextview = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296319, "field 'humidityTextview'");
    target.humidityTextview = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296419, "field 'updataTimeTextview'");
    target.updataTimeTextview = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296341, "field 'motionIndex_textview'");
    target.motionIndex_textview = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296304, "field 'dateTextview'");
    target.dateTextview = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296421, "field 'weatherParameterScollview'");
    target.weatherParameterScollview = (android.widget.ScrollView) view;
    view = finder.findRequiredView(source, 2131296301, "field 'cpuTempNoticeView'");
    target.cpuTempNoticeView = (org.thingsboard.sample.gpiocontrol.widget.NoticeView) view;
    view = finder.findRequiredView(source, 2131296357, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked();
        }
      });
    view = finder.findRequiredView(source, 2131296353, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296354, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296355, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296356, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
  }

  public static void reset(org.thingsboard.sample.gpiocontrol.activity.GetTodayWeatherInfoActivity target) {
    target.imageTodayWeatherIcon = null;
    target.templateTextview = null;
    target.locationTextview = null;
    target.weatherTodayInfoTextview = null;
    target.timeTextview = null;
    target.templateHigntTextview = null;
    target.templateLowTextview = null;
    target.windDirectionTextview = null;
    target.windGradeTextview = null;
    target.humidityTextview = null;
    target.updataTimeTextview = null;
    target.motionIndex_textview = null;
    target.dateTextview = null;
    target.weatherParameterScollview = null;
    target.cpuTempNoticeView = null;
  }
}
