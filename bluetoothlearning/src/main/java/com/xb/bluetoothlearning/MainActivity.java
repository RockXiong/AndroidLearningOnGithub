package com.xb.bluetoothlearning;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.xb.bluetoothlearning.wiget.MyDialog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MainActivity extends Activity {
    private static final String TAG = "BluetoothMainActivity";
    private static final int CODE_BLUETOOTH_ENABLE = 0x01;
    private static final String BUTTON_TEXT_SCAN_ON = "Start Scan";
    private static final String BUTTON_TEXT_SCAN_OFF = "Stop Scan";

    @BindView(R.id.btn_scan)
    Button mBtnScan;
    @BindView(R.id.sw_switch_bluetooth)
    Switch mSwSwitchBluetooth;
    @BindView(R.id.sw_switch_discoverable)
    Switch mSwSwitchDiscoverable;
    @BindView(R.id.rl_bluetooth_switch)
    RelativeLayout mRlBluetoothSwitch;
    @BindView(R.id.rl_discovery_switch)
    RelativeLayout mRlDiscoverySwitch;
    @BindView(R.id.tv_value_device_name)
    TextView mTvValueDeviceName;
    @BindView(R.id.rl_device_name)
    RelativeLayout mRlDeviceName;
    @BindView(R.id.ll_paired_device)
    LinearLayout mLlPairedDevice;
    @BindView(R.id.ll_available_device)
    LinearLayout mLlAvailableDevice;
    @BindView(R.id.ll_paired_device_container)
    LinearLayout mLlPairedDeviceContainer;
    @BindView(R.id.ll_available_device_container)
    LinearLayout mLlAvailableDeviceContainer;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager mBluetoothManager;
    private BluetoothLeScanner mBluetoothLeScanner;
    private BluetoothGatt mBluetoothGatt;


    private ArrayList<MyBluetoothDevice> mAvailableDevices = new ArrayList<>();
    private ArrayList<MyBluetoothDevice> mPairedDevices = new ArrayList<>();
    private HashMap<String, RelativeLayout> mRlAvailableDevices = new HashMap<>();
    private HashMap<String, RelativeLayout> mRlPairedDevices = new HashMap<>();
    private Handler mScanHandler = new Handler();
    private boolean isScanning;
    private MyDialog mMyDialog;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.equals(mSwSwitchBluetooth)) {

            }
        }
    };

    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            Log.e(TAG, "results=" + results.toString());
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.e(TAG, "搜索失败!");
        }

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                BluetoothDevice device = result.getDevice();
                MyBluetoothDevice myBluetoothDevice = new MyBluetoothDevice(device, result.getRssi());
                //                if (mAvailableDevices.indexOf(myBluetoothDevice) < 0)
                //                {
                //                    mAvailableDevices.add(myBluetoothDevice);
                //                } else
                //                {
                //                    mAvailableDevices.get(mAvailableDevices.indexOf(myBluetoothDevice)).setRssi(result.getRssi());
                //                }
                addViewItem(mLlAvailableDeviceContainer, myBluetoothDevice);


                Log.e(TAG, "Scan record=" + result.getScanRecord());
                Log.e(TAG, "Raw data=" + Arrays.toString(result.getScanRecord().getBytes()));

                Log.e(TAG, "Scan device: name=" + device.getName() + ",address=" + device.getAddress() + ",RSSI=" + result.getRssi());
            }
        }
    };

    private void parseData(byte[] advertisedData) {
        byte len;
        byte type;
        int index = 0;
        while (advertisedData[index] != 0) {
            len = advertisedData[index];
            type = advertisedData[index + 1];
            switch (type) {
                case 0x01:
                    break;
                case 0x02:
                    break;
                case 0x03:
                    break;
                case 0x04:
                    break;
                case 0x05:
                    break;
                case 0x06:
                    break;
                case 0x07:
                    break;
                case 0x08:
                    break;
                case 0x09:
                    break;
                case 0x0A:
                    break;
                case 0x0D:
                    break;
                case 0x0E:
                    break;
                case 0x0F:
                    break;
                case 0x10:
                    break;
                case 0x11:
                    break;
                case 0x12:
                    break;
                case 0x14:
                    break;
                case 0x15:
                    break;
                case 0x16:
                    break;
                case 0x17:
                    break;
                case 0x18:
                    break;
                case 0x19:
                    break;
                case -1:
                    break;

            }
            index += (len + 1);

            if (index >= advertisedData.length) {
                break;
            }
        }
    }

    private Runnable runnableScan = new Runnable() {
        @Override
        public void run() {
            if (null == mScanCallback) {
                return;
            }
            stopScan();
            mBtnScan.setText(BUTTON_TEXT_SCAN_ON);
        }
    };

    private void parseRawData(byte[] datas) {
        byte len;
        byte type;
        int index = 0;
        String deviceName = "";
        int deviceCode = 0;
        int manufacturer = 0;
        while (datas[index] != 0) {
            len = datas[index];
            type = datas[index + 1];
            Log.e(TAG, "len=" + len + ";type=" + type);
            switch (type) {
                case 0x08:
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBluetoothReceiver();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (null != mBluetoothManager) {
            //            mBluetoothAdapter = mBluetoothManager.getAdapter();//这种方式也可以
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (null == mBluetoothAdapter) {
                Toast.makeText(this, "该设备不支持蓝牙", Toast.LENGTH_SHORT).show();
                return;
            }
            mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        }
        mSwSwitchBluetooth.setChecked(mBluetoothAdapter.isEnabled());
        bluetoothEnable(mBluetoothAdapter.isEnabled());
        mTvValueDeviceName.setText(mBluetoothAdapter.getName());
        mSwSwitchDiscoverable.setChecked(mBluetoothAdapter.getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE);
        findPairedDevices();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBluetoothAdapter.isEnabled()) {
            scanBluetooth();
        }
    }

    private void openBluetooth() {
        if (null != mBluetoothAdapter && !mBluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, CODE_BLUETOOTH_ENABLE);
        }
    }

    private void closeBluetooth() {
        if (null == mBluetoothAdapter) {
            return;
        }
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        if (isScanning) {
            if (null == mBluetoothLeScanner || null == mScanCallback) {
                return;
            }
            mBluetoothLeScanner.stopScan(mScanCallback);
        }
        if (mBluetoothAdapter.disable()) {
            mRlDiscoverySwitch.setEnabled(false);
        }
    }

    private void scanBluetooth() {
        if (null == mBluetoothAdapter) {
            throw new RuntimeException("mBluetoothAdapter can not be null!");
        }
        if (null == mScanCallback) {
            throw new RuntimeException("mScanCallback can not be null!");
        }
        mBtnScan.setText(BUTTON_TEXT_SCAN_OFF);
        if (null == mBluetoothLeScanner) {
            mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        }
        if (isScanning) {
            mBluetoothLeScanner.stopScan(mScanCallback);
        }

        mLlAvailableDeviceContainer.removeAllViews();
        mRlAvailableDevices.clear();
        mAvailableDevices.clear();

        ScanSettings scanSettings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build();
        mBluetoothLeScanner.startScan(null, scanSettings, mScanCallback);
        mScanHandler.postDelayed(runnableScan, 12000);
        startDiscovery();
        findPairedDevices();
    }

    private void loadBluetoothInfo() {
        if (null == mBluetoothAdapter) {
            return;
        }
        Log.e(TAG, "本地蓝牙地址:" + mBluetoothAdapter.getAddress());
        Log.e(TAG, "本地蓝牙名称:" + mBluetoothAdapter.getName());
        Log.e(TAG, "本地蓝牙适配器状态:" + mBluetoothAdapter.getState());
        Log.e(TAG, mBluetoothAdapter.getBluetoothLeAdvertiser().toString());
    }

    /**
     * 使本机蓝牙300s内处于可见状态
     */
    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 1);
            startActivity(discoverableIntent);
        }
    }

    private void setDiscoverable(Boolean discoverable) {
        if (discoverable)//通过反射调用BluetoothAdapter中隐藏的方法
        {
            try {
                Method setDiscoverableTimeout = BluetoothAdapter.class.getMethod("setDiscoverableTimeout", int.class);
                setDiscoverableTimeout.setAccessible(true);

                Method setScanMode = BluetoothAdapter.class.getMethod("setScanMode", int.class, int.class);
                setScanMode.setAccessible(true);

                setDiscoverableTimeout.invoke(mBluetoothAdapter, 0);
                setScanMode.invoke(mBluetoothAdapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE, 0);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Method setDiscoverableTimeout = BluetoothAdapter.class.getMethod("setDiscoverableTimeout", int.class);
                setDiscoverableTimeout.setAccessible(true);

                Method setScanMode = BluetoothAdapter.class.getMethod("setScanMode", int.class, int.class);
                setScanMode.setAccessible(true);

                setDiscoverableTimeout.invoke(mBluetoothAdapter, 1);
                setScanMode.invoke(mBluetoothAdapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE, 1);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 查找已匹配的设备
     */
    private void findPairedDevices() {
        if (null == mBluetoothAdapter) {
            return;
        }
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                Log.e(TAG, "paired device:" + device.getName() + ", " + device.getAddress() + ", " + Arrays.toString(device.getUuids()));
                addViewItem(mLlPairedDeviceContainer, new MyBluetoothDevice(device, 0));
            }
        }
    }

    private void addViewItem(ViewGroup viewParent, MyBluetoothDevice myBluetoothDevice) {
        RelativeLayout relativeLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.item_bluetooth_devices, null);
        relativeLayout.setTag(myBluetoothDevice.getAddress());
        ((TextView) relativeLayout.findViewById(R.id.tv_device_name)).setText(myBluetoothDevice.getName());
        ((TextView) relativeLayout.findViewById(R.id.tv_mac_address)).setText(myBluetoothDevice.getAddress());
        TextView textViewRssi = (TextView) relativeLayout.findViewById(R.id.tv_rssi);
        textViewRssi.setText(String.valueOf(myBluetoothDevice.getRssi()));
        if (viewParent.getId() == R.id.ll_paired_device_container) {
            if (mRlPairedDevices.containsKey(myBluetoothDevice.getAddress())) {
                ((TextView) (mRlPairedDevices.get(myBluetoothDevice.getAddress()).findViewById(R.id.tv_rssi))).setText(String.valueOf(myBluetoothDevice.getRssi()));
                return;
            }
            mRlPairedDevices.put(myBluetoothDevice.getAddress(), relativeLayout);
        } else {
            if (mRlAvailableDevices.containsKey(myBluetoothDevice.getAddress())) {
                ((TextView) (mRlAvailableDevices.get(myBluetoothDevice.getAddress()).findViewById(R.id.tv_rssi))).setText(String.valueOf(myBluetoothDevice.getRssi()));
                return;
            }
            mRlAvailableDevices.put(myBluetoothDevice.getAddress(), relativeLayout);
        }
        viewParent.addView(relativeLayout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_BLUETOOTH_ENABLE) {
            if (resultCode == RESULT_OK) {
                //                mTvBluetoothState.setText("ON");
                //                mBtnSwitchBluetooth.setText("Close Bluetooth");
                bluetoothEnable(true);
                loadBluetoothInfo();
                scanBluetooth();
            } else {
                finish();
                System.exit(-1);
                bluetoothEnable(false);
            }
        }
    }

    private void bluetoothEnable(boolean bluetoothEnable) {
        mRlDiscoverySwitch.setEnabled(bluetoothEnable);
        mSwSwitchDiscoverable.setEnabled(bluetoothEnable);
        mBtnScan.setEnabled(bluetoothEnable);
    }

    @OnClick({R.id.btn_scan, R.id.rl_device_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scan:
                if (!mBluetoothAdapter.isEnabled() || null == mBluetoothLeScanner) {
                    return;
                } else {
                    if (mBtnScan.getText().toString().equals(BUTTON_TEXT_SCAN_OFF)) {
                        stopScan();
                        mBtnScan.setText(BUTTON_TEXT_SCAN_ON);
                    } else {
                        scanBluetooth();
                    }
                }
                break;
            case R.id.rl_device_name: {
                if (null != mMyDialog) {
                    if (mMyDialog.isShowing()) {
                        mMyDialog.dismiss();
                        return;
                    }
                    mMyDialog.show();
                } else {
                    mMyDialog = createMyDialog();
                }
            }
            break;
        }
    }

    private MyDialog createMyDialog() {
        return new MyDialog(this, new MyDialog.IMyDialogListener() {
            @Override
            public void onCancel() {
                if (null != mMyDialog && mMyDialog.isShowing()) {
                    mMyDialog.dismiss();
                }
            }

            @Override
            public void onOk(String str) {
                updateBluetoothDeviceName(str);
                if (null != mMyDialog && mMyDialog.isShowing()) {
                    mMyDialog.dismiss();
                }
            }
        });
    }

    private void updateBluetoothDeviceName(String str) {

    }

    @OnCheckedChanged({R.id.sw_switch_bluetooth, R.id.sw_switch_discoverable})
    public void onCheckChanged(CompoundButton compoundButton, boolean isChecked) {
        if (null == mBluetoothAdapter) {
            return;
        }
        switch (compoundButton.getId()) {
            case R.id.sw_switch_bluetooth:
                if (isChecked) {
                    if (!mBluetoothAdapter.isEnabled()) {
                        openBluetooth();
                    }
                } else {
                    if (mBluetoothAdapter.isEnabled()) {
                        closeBluetooth();
                        bluetoothEnable(false);
                    }
                }
                break;

            case R.id.sw_switch_discoverable:
                setDiscoverable(isChecked);
                Toast.makeText(this, "Discoverable " + (isChecked ? "ON" : "OFF"), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void stopScan() {
        mBluetoothLeScanner.stopScan(mScanCallback);
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    private void startDiscovery() {
        if (null == mBluetoothAdapter) {
            return;
        }
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        if (mBluetoothAdapter.startDiscovery()) {
            Log.e(TAG, "Start discovery successfully!");
        } else {
            Log.e(TAG, "Failed to start discovery!");
        }
    }

    private void registerBluetoothReceiver() {
        //注册一个广播,用于接收发现设备的结果
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBroadcastReceiver, intentFilter);

        //搜索结束后调用onReceive
        intentFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case BluetoothDevice.ACTION_FOUND://发现设备
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    int rssi = intent.getExtras().getShort(BluetoothDevice.EXTRA_RSSI);
                    MyBluetoothDevice myBluetoothDevice = new MyBluetoothDevice(device, rssi);
                    //                    if (mAvailableDevices.indexOf(myBluetoothDevice) < 0)
                    //                    {
                    //                        mAvailableDevices.add(myBluetoothDevice);
                    //                    } else
                    //                    {
                    //                        mAvailableDevices.get(mAvailableDevices.indexOf(myBluetoothDevice)).setRssi(rssi);
                    //                    }
                    addViewItem(mLlAvailableDeviceContainer, myBluetoothDevice);
                    Log.e(TAG, "Find device:" + "name=" + device.getName() + ",address=" + device.getAddress() + ",RSSI=" + rssi);
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED://搜索结束
                    Log.e(TAG, "Find device:finish.");
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScanHandler.removeCallbacks(runnableScan);
        if (null != mBluetoothAdapter && mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothAdapter = null;
        }
        unregisterReceiver(mBroadcastReceiver);
        if (null != mAvailableDevices) {
            mAvailableDevices.clear();
            mAvailableDevices = null;
        }
        if (null != mBluetoothLeScanner && null != mScanCallback) {
            mBluetoothLeScanner.stopScan(mScanCallback);
        }
        mBluetoothLeScanner = null;
        mScanCallback = null;
        mScanHandler = null;
    }


}
