package com.thingsboard.home.activity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.I2cDevice;
import com.google.android.things.pio.PeripheralManager;

import com.thingsboard.home.R;
import com.thingsboard.home.base.BaseActivity;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangxiang on 2018/4/22.
 */

public class I2CInfoActivity extends BaseActivity {

    public String TAG = "-----------" + this.getClass().getSimpleName() + "-----------";
    private PeripheralManager manager;
    // I2C Slave Address
    private static final short I2C_ADDRESS = 0xc0;
    private static final short init1 = 0x32;
    private static final short init2 = 0xd1;
    private static final short init3 = 0xc0;
    private static final short init4 = 0x17;
    private static final short init5= 0x00;

    private I2cDevice mDevice;


    @Override
    protected int getContentView() {
        return R.layout.layout_i2c_connect;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        // initGPio2();

        initGpio();
    }

    private void initGPio2() {
        PeripheralManager manager = PeripheralManager.getInstance();
        List<String> portList = manager.getGpioList();
        if (portList.isEmpty()) {
            Log.i(TAG, "No GPIO port available on this device.");
        } else {
            Log.i(TAG, "List of available ports: " + portList);

            for (int i = 0; i < portList.size(); i++) {

                try {
                    Gpio gpio = manager.openGpio(portList.get(i));
                    // Initialize the pin as a high output
                    gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH);
                    // Low voltage is considered active
                    gpio.setActiveType(Gpio.ACTIVE_HIGH);
                    // Toggle the value to be LOW
                    gpio.setValue(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initGpio() {
        manager = PeripheralManager.getInstance();
        List<String> deviceList = manager.getI2cBusList();
        if (deviceList.isEmpty()) {
            Log.e(TAG, "No I2C bus available on this device.");
        } else {
            Log.e(TAG, "I2C设备列表: " + deviceList);
            openI2CDevice(deviceList.get(0));
        }
    }

    private void openI2CDevice(String i2cName) {
        try {

            mDevice = manager.openI2cDevice(i2cName, I2C_ADDRESS);
            Log.e(TAG, mDevice.getName());
            mDevice.writeRegWord(I2C_ADDRESS, init1);
            mDevice.writeRegWord(I2C_ADDRESS, init2);
            mDevice.writeRegWord(I2C_ADDRESS, init3);
            mDevice.writeRegWord(I2C_ADDRESS, init4);
            mDevice.writeRegWord(I2C_ADDRESS, init5);

            //   short readRegWord = mDevice.readRegWord(I2C_ADDRESS);

         //   Log.e(TAG,readRegWord+"");

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mDevice != null) {
            try {
                mDevice.close();
                mDevice = null;
            } catch (IOException e) {
                Log.w(TAG, "Unable to close I2C device", e);
            }
        }
    }

    @OnClick(R.id.bt_i2c)
    public void onViewClicked() {
        initGpio();
    }
}
