package com.xb.bluetoothlearning;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by Administrator on 2017/2/20.
 */

public class MyBluetoothDevice
{
    private BluetoothDevice mBluetoothDevice;
    int mRssi;

    MyBluetoothDevice(@NonNull BluetoothDevice bluetoothDevice, int rssi)
    {
        mBluetoothDevice = bluetoothDevice;
        mRssi = rssi;
    }

    public int getRssi()
    {
        return mRssi;
    }

    public String getName()
    {
        return TextUtils.isEmpty(mBluetoothDevice.getName()) ? "未知设备" : mBluetoothDevice.getName();
    }

    public String getAddress()
    {
        return mBluetoothDevice.getAddress();
    }
    public BluetoothDevice getBluetoothDevice()
    {
        return mBluetoothDevice;
    }
    public void setRssi(int rssi)
    {
        mRssi = rssi;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof MyBluetoothDevice))
        {
            return false;
        }
        MyBluetoothDevice myBluetoothDevice = (MyBluetoothDevice) obj;
        return this.getBluetoothDevice().equals(myBluetoothDevice.getBluetoothDevice());
    }
}
