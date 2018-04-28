// Generated code from Butter Knife. Do not modify!
package org.thingsboard.sample.gpiocontrol.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class KuGouMusicActivity$$ViewInjector {
  public static void inject(Finder finder, final org.thingsboard.sample.gpiocontrol.activity.KuGouMusicActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296337, "field 'lvKugou'");
    target.lvKugou = (android.widget.ListView) view;
  }

  public static void reset(org.thingsboard.sample.gpiocontrol.activity.KuGouMusicActivity target) {
    target.lvKugou = null;
  }
}
