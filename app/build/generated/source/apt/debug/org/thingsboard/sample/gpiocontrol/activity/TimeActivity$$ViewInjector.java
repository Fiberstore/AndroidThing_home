// Generated code from Butter Knife. Do not modify!
package org.thingsboard.sample.gpiocontrol.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class TimeActivity$$ViewInjector {
  public static void inject(Finder finder, final org.thingsboard.sample.gpiocontrol.activity.TimeActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296409, "field 'timeTextview'");
    target.timeTextview = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296424, "field 'weekTextview'");
    target.weekTextview = (android.widget.TextView) view;
  }

  public static void reset(org.thingsboard.sample.gpiocontrol.activity.TimeActivity target) {
    target.timeTextview = null;
    target.weekTextview = null;
  }
}
