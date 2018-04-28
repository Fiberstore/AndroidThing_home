// Generated code from Butter Knife. Do not modify!
package org.thingsboard.sample.gpiocontrol.device.bluetooth;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class BluetoothDeviceListActivity$$ViewInjector {
  public static void inject(Finder finder, final org.thingsboard.sample.gpiocontrol.device.bluetooth.BluetoothDeviceListActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296416, "field 'tvPairedDevices'");
    target.tvPairedDevices = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296335, "field 'lvPairedDevices'");
    target.lvPairedDevices = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131296415, "field 'tvNewDevices'");
    target.tvNewDevices = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131296334, "field 'lvNewDevices'");
    target.lvNewDevices = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131296297, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296289, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
  }

  public static void reset(org.thingsboard.sample.gpiocontrol.device.bluetooth.BluetoothDeviceListActivity target) {
    target.tvPairedDevices = null;
    target.lvPairedDevices = null;
    target.tvNewDevices = null;
    target.lvNewDevices = null;
  }
}
