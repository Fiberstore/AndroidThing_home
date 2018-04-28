// Generated code from Butter Knife. Do not modify!
package org.thingsboard.sample.gpiocontrol.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class I2CInfoActivity$$ViewInjector {
  public static void inject(Finder finder, final org.thingsboard.sample.gpiocontrol.activity.I2CInfoActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296290, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked();
        }
      });
  }

  public static void reset(org.thingsboard.sample.gpiocontrol.activity.I2CInfoActivity target) {
  }
}
