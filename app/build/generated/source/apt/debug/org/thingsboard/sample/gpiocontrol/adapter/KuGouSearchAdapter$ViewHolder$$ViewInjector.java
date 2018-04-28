// Generated code from Butter Knife. Do not modify!
package org.thingsboard.sample.gpiocontrol.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class KuGouSearchAdapter$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final org.thingsboard.sample.gpiocontrol.adapter.KuGouSearchAdapter.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296327, "field 'ivMusicHome'");
    target.ivMusicHome = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131296346, "field 'musicName'");
    target.musicName = (android.widget.TextView) view;
  }

  public static void reset(org.thingsboard.sample.gpiocontrol.adapter.KuGouSearchAdapter.ViewHolder target) {
    target.ivMusicHome = null;
    target.musicName = null;
  }
}
