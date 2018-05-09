package com.thingsboard.home.activity;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.google.android.things.contrib.driver.gps.NmeaGpsDriver;
import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.userdriver.UserDriverManager;
import com.google.android.things.userdriver.location.GnssDriver;

import com.thingsboard.home.R;
import com.thingsboard.home.base.BaseActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by zhangxiang on 2018/4/24.
 */

public class UartGpsActivity extends BaseActivity {

    private PeripheralManager uartManager;
    private UserDriverManager manager;
    private String locationProvider;
    private Location location;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss", Locale.CHINESE);

    @Override
    protected int getContentView() {
        return R.layout.layout_uartgps_activity;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        initGpio();

        getLocation();
    }

    private void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
        //下面注释的代码获取的location为null，所以采用Criteria的方式。
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
            Log.d(TAG, "onCreate: gps=" + locationProvider);
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
            Log.d(TAG, "onCreate: network=" + locationProvider);
        } else {
            Log.d(TAG, "onCreate: 没有可用的位置提供器");
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return;
        }
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);//低精度，如果设置为高精度，依然获取不了location。
        criteria.setAltitudeRequired(true);//不要求海拔
        criteria.setBearingRequired(true);//不要求方位
        criteria.setCostAllowed(true);//允许有花费
        criteria.setPowerRequirement(Criteria.ACCURACY_LOW);//低功耗

        //从可用的位置提供器中，匹配以上标准的最佳提供器
        locationProvider = locationManager.getBestProvider(criteria, true);
        location = locationManager.getLastKnownLocation(locationProvider);
        Log.d(TAG, "onCreate: " + (location == null) + "..");
        if (location != null) {
            Log.d(TAG, "onCreate: location");
            //不为空,显示地理位置经纬度
            showLocation(location);
        }
        //监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
    }

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */
    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "onProviderEnabled: " + provider + ".." + Thread.currentThread().getName());
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "onProviderDisabled: " + provider + ".." + Thread.currentThread().getName());
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "onLocationChanged: " + ".." + Thread.currentThread().getName());
            //如果位置发生变化,重新显示
            showLocation(location);
        }
    };

    private void showLocation(Location location) {
        Log.d(TAG, "定位成功------->" + "location------>经度为：" + location.getLatitude() + "\n纬度为" + location.getLongitude());
        Log.d(TAG, "定位成功------->" + "location------>速度为：" + location.getSpeed() + "\n时间" + location.getTime());
        Log.d(TAG, "定位成功------->" + "location------>高度为：" + location.getAltitude());
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        String time = formatter.format(location.getTime());

        Log.d(TAG, "定位成功------->" + "location------>time为：" + time);

    }

    private void initGNSS() {

        // Create a new driver implementation
        GnssDriver mDriver = new GnssDriver();

        // Register with the framework
        manager = UserDriverManager.getInstance();

    }

    private void initGpio() {

        uartManager = PeripheralManager.getInstance();
        List<String> deviceList = uartManager.getUartDeviceList();
        if (deviceList.isEmpty()) {
            Log.e(TAG, "No UART port available on this device.");
        } else {
            Log.e(TAG, "List of available devices: " + deviceList);
            try {
                NmeaGpsDriver nmeaGpsDriver = new NmeaGpsDriver(this,
                        deviceList.get(1), 38400, 1000);
                nmeaGpsDriver.register();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserDriverManager manager = UserDriverManager.getInstance();
        manager.unregisterGnssDriver();
    }
}
