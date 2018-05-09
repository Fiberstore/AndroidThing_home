package com.thingsboard.home.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.things.bluetooth.BluetoothClassFactory;
import com.google.android.things.bluetooth.BluetoothConfigManager;
import com.google.android.things.bluetooth.BluetoothConnectionManager;
import com.google.android.things.bluetooth.BluetoothPairingCallback;
import com.google.android.things.bluetooth.BluetoothProfile;
import com.google.android.things.bluetooth.BluetoothProfileManager;
import com.google.android.things.bluetooth.PairingParams;

import com.thingsboard.home.R;
import com.thingsboard.home.base.BaseActivity;
import com.thingsboard.home.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class BluetoothDeviceListActivity extends BaseActivity {
    @InjectView(R.id.tvPairedDevices)
    TextView tvPairedDevices;
    @InjectView(R.id.lvPairedDevices)
    ListView lvPairedDevices;
    @InjectView(R.id.tvNewDevices)
    TextView tvNewDevices;
    @InjectView(R.id.lvNewDevices)
    ListView lvNewDevices;

    /**
     * Member fields
     */
    private BluetoothAdapter mBluetoothAdapter;

    public static final String EXTRA_DEVICE_ADDRESS = "address";
    public static final int REQUEST_ENABLE_BT = 2;
    public static final int REQUEST_CONNECT_DEVICE = 3;

    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    /**
     * 蓝牙设备存储
     */
    private List<BluetoothDevice> bluetoothDeviceList = new ArrayList<>();
    private BluetoothConnectionManager bluetoothConnectionManager;
    private Set<BluetoothDevice> pairedDevices;
    private List<BluetoothDevice> pairedDevicesList = new ArrayList<>();


    @Override
    protected int getContentView() {
        return R.layout.dialog_bluetooth_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mFindBlueToothReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mFindBlueToothReceiver, filter);
        initBluetooth();
        setBluetooth();
    }

    private void setBluetooth() {

        BluetoothConfigManager bluetoothConfigManager = BluetoothConfigManager.getInstance();
        BluetoothClass deviceClass = BluetoothClassFactory.build(
                BluetoothClass.Service.AUDIO,
                BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER);
        bluetoothConfigManager.setBluetoothClass(deviceClass);

        BluetoothProfileManager manager = BluetoothProfileManager.getInstance();
        List<Integer> enabledProfiles = manager.getEnabledProfiles();
        if (!enabledProfiles.contains(BluetoothProfile.HEALTH)) {
            Utils.myLog("Enabling A2DP sink mode.");
            List<Integer> toDisable = Arrays.asList(BluetoothProfile.A2DP);
            List<Integer> toEnable = Arrays.asList(
                    BluetoothProfile.HEALTH,
                    BluetoothProfile.AVRCP_CONTROLLER);

            manager.enableAndDisableProfiles(toEnable, toDisable);
        }

        bluetoothConnectionManager = BluetoothConnectionManager.getInstance();
        bluetoothConnectionManager.registerPairingCallback(bluetoothPairingCallback);

    }

    private void initBluetooth() {
        // Get the local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Utils.toastShow(this, "Bluetooth is not supported by the device");
        } else {
            // If BT is not on, request that it be enabled.
            // setupChat() will then be called during onActivityResult
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent,
                        REQUEST_ENABLE_BT);
            } else {
                getDeviceList();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothConnectionManager.unregisterPairingCallback(bluetoothPairingCallback);
        // Make sure we're not doing discovery anymore
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        if (mFindBlueToothReceiver != null) {
            unregisterReceiver(mFindBlueToothReceiver);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                // bluetooth is opened
                getDeviceList();
            } else {
                // bluetooth is not open
                Toast.makeText(this, "蓝牙设备不存在", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 开始配对
     */
    private void startPairing(BluetoothDevice remoteDevice) {
        bluetoothConnectionManager.initiatePairing(remoteDevice);
    }


    BluetoothPairingCallback bluetoothPairingCallback = new BluetoothPairingCallback() {

        @Override
        public void onPairingInitiated(BluetoothDevice bluetoothDevice, PairingParams pairingParams) {
            handlePairingRequest(bluetoothDevice, pairingParams);
        }

        @Override
        public void onPaired(BluetoothDevice bluetoothDevice) {
            /**配对成功*/
            Utils.myLog("11111111111");
            connectToA2dp(bluetoothDevice);
        }

        @Override
        public void onUnpaired(BluetoothDevice bluetoothDevice) {
            /**取消配对成功*/
            Utils.myLog("2222222222");
        }

        @Override
        public void onPairingError(BluetoothDevice bluetoothDevice, PairingError error) {
            /**配对失败*/
            Utils.myLog("3333333333");
        }
    };

    private void handlePairingRequest(BluetoothDevice bluetoothDevice, PairingParams pairingParams) {

        Utils.myLog(pairingParams.getPairingType() + "");
        switch (pairingParams.getPairingType()) {
            case PairingParams.PAIRING_VARIANT_DISPLAY_PIN:
            case PairingParams.PAIRING_VARIANT_DISPLAY_PASSKEY:
                // Display the required PIN to the user
                Utils.myLog("Display Passkey - " + pairingParams.getPairingPin());
                break;
            case PairingParams.PAIRING_VARIANT_PIN:
            case PairingParams.PAIRING_VARIANT_PIN_16_DIGITS:
                // Obtain PIN from the user
                String pin = "";
                // Pass the result to complete pairing
                bluetoothConnectionManager.finishPairing(bluetoothDevice, pin);
                break;
            case PairingParams.PAIRING_VARIANT_CONSENT:
            case PairingParams.PAIRING_VARIANT_PASSKEY_CONFIRMATION:
                // Show confirmation of pairing to the user
                // Complete the pairing process
                bluetoothConnectionManager.finishPairing(bluetoothDevice);
                break;
        }
    }

    private void connectToA2dp(BluetoothDevice bluetoothDevice) {
        bluetoothConnectionManager.connect(bluetoothDevice, BluetoothProfile.HEALTH);
    }

    protected void getDeviceList() {
        mPairedDevicesArrayAdapter = new ArrayAdapter<>(this, R.layout.bluetooth_device_name_item);
        mNewDevicesArrayAdapter = new ArrayAdapter<>(this, R.layout.bluetooth_device_name_item);
        lvPairedDevices.setAdapter(mPairedDevicesArrayAdapter);
        lvPairedDevices.setOnItemClickListener(mDeviceClickListener);
        lvNewDevices.setAdapter(mNewDevicesArrayAdapter);
        lvNewDevices.setOnItemClickListener(mDeviceClickListener);
        /**获取已配对的设备*/
        pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            tvPairedDevices.setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                pairedDevicesList.add(device);
            }
        } else {
            String noDevices = "没有配对";
            mPairedDevicesArrayAdapter.add(noDevices);
        }
    }

    /**
     * changes the title when discovery is finished
     */
    private final BroadcastReceiver mFindBlueToothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed
                // already
                /**发现的蓝牙设备*/
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                    bluetoothDeviceList.add(device);
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                tvNewDevices.setText("请选择蓝牙设备");
                Log.i("tag", "finish discovery" + mNewDevicesArrayAdapter.getCount());
                if (mNewDevicesArrayAdapter.getCount() == 0) {
                    String noDevices = "未找到设备";
                    mNewDevicesArrayAdapter.add(noDevices);
                }
            }
        }
    };

    /**
     * 扫描蓝牙设备
     */
    private void discoveryDevice() {
        tvNewDevices.setText("扫描中");
        tvNewDevices.setVisibility(View.VISIBLE);
        lvNewDevices.setVisibility(View.VISIBLE);
        // If we're already discovering, stop it
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        mBluetoothAdapter.startDiscovery();
    }

    /**
     * The on-click listener for all devices in the ListViews
     */
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Cancel discovery because it's costly and we're about to connect
            String info = ((TextView) v).getText().toString();
            String noDevices = "没有设备";
            String noNewDevice = "没有新设备";
            Utils.myLog(info);
            if (!info.equals(noDevices) && !info.equals(noNewDevice)) {
                /**已配对的item*/
                if (av.getAdapter() == mPairedDevicesArrayAdapter) {
                    connectToA2dp(pairedDevicesList.get(arg2));
                } else {
                    /**未配对的item*/
                    BluetoothDevice bluetoothDevice = bluetoothDeviceList.get(arg2);
                    startPairing(bluetoothDevice);
                    mBluetoothAdapter.cancelDiscovery();
                }

             /*   String address = info.substring(info.length() - 17);
                // Create the result Intent and include the MAC address
                Intent intent = new Intent();
                intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
                // Set result and finish this Activity
                setResult(Activity.RESULT_OK, intent);*/

            }

        }
    };

    @OnClick({R.id.closePage_button, R.id.btBluetoothScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.closePage_button:
                this.finish();
                break;
            case R.id.btBluetoothScan:
                view.setVisibility(View.GONE);
                discoveryDevice();
                break;
        }
    }
}
