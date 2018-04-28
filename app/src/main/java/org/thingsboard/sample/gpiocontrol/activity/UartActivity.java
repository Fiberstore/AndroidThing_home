package org.thingsboard.sample.gpiocontrol.activity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.things.contrib.driver.gps.NmeaGpsDriver;
import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.UartDevice;
import com.google.android.things.pio.UartDeviceCallback;
import com.google.android.things.userdriver.UserDriverManager;
import com.google.android.things.userdriver.location.GnssDriver;

import org.thingsboard.sample.gpiocontrol.base.BaseActivity;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhangxiang on 2018/4/24.
 */

public class UartActivity extends BaseActivity implements UartDeviceCallback {

    private PeripheralManager uartManager;
    private UartDevice mDevice;
    private UserDriverManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.layout_uartgps_activity);

         //   initGNSS();
            initGpio();
    }

    private void initGNSS() {

        // Create a new driver implementation
        GnssDriver  mDriver = new GnssDriver();

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
                NmeaGpsDriver nmeaGpsDriver=new NmeaGpsDriver(this,
                        deviceList.get(1), 38400, 1000);
                nmeaGpsDriver.register();
            } catch (IOException e) {
                e.printStackTrace();
            }
               //connectUart(deviceList.get(1));
        }
    }

    private void connectUart(String uartName) {
        try {
            mDevice = uartManager.openUartDevice(uartName);
            mDevice.setBaudrate(38400);
            mDevice.setDataSize(8);
            mDevice.setParity(UartDevice.PARITY_NONE);
            mDevice.setStopBits(1);
            mDevice.setHardwareFlowControl(UartDevice.HW_FLOW_CONTROL_AUTO_RTSCTS);
            mDevice.registerUartDeviceCallback(this);
            Log.e(TAG, "串口打开成功");
        } catch (IOException e) {
            Log.e(TAG, "Unable to access UART device", e);
        }
    }

    public void readUartBuffer(UartDevice uart) throws IOException {
        // Maximum amount of data to read at one time

        final int maxCount = 1024*100;
        byte[] buffer = new byte[maxCount];
        int count;
        while ((count = uart.read(buffer, buffer.length)) > 0) {
            Log.e(TAG, "Read " + count + " bytes from peripheral");
            String info = new String(buffer, "UTF-8");
            Log.e("GPS_Info", info);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserDriverManager manager = UserDriverManager.getInstance();
        manager.unregisterGnssDriver();
        if (mDevice != null) {
            try {
                mDevice.close();
                mDevice = null;
            } catch (IOException e) {
                Log.w(TAG, "Unable to close UART device", e);
            }
        }
    }

    @Override
    public boolean onUartDeviceDataAvailable(UartDevice uartDevice) {

        try {
            readUartBuffer(uartDevice);
        } catch (IOException e) {
            Log.w(TAG, "Unable to access UART device", e);
        }

        return true;
    }

    @Override
    public void onUartDeviceError(UartDevice uart, int error) {
        Log.w(TAG, uart + ": Error event " + error);
    }
}
