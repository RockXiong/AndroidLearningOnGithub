package com.xb.bluetoothlearning.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xb.bluetoothlearning.MyBluetoothDevice;
import com.xb.bluetoothlearning.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/20.
 */

public class DeviceListAdapter extends BaseAdapter
{
    private ArrayList<MyBluetoothDevice> mBluetoothDevices;

    private Context mContext;

    public DeviceListAdapter(@NonNull Context context, ArrayList<MyBluetoothDevice> bluetoothDevices)
    {
        mContext = context;

        mBluetoothDevices = bluetoothDevices;
    }

    @Override
    public int getCount()
    {
        return mBluetoothDevices.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mBluetoothDevices.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (null == convertView)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bluetooth_devices, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTvDeviceName.setText(TextUtils.isEmpty(mBluetoothDevices.get(position).getName()) ? "未知设备" : mBluetoothDevices.get(position).getName());
        viewHolder.mTvMacAddress.setText(mBluetoothDevices.get(position).getAddress());
        viewHolder.mTvRssi.setText(String.valueOf(mBluetoothDevices.get(position).getRssi()));

        return convertView;
    }

    static class ViewHolder
    {
        @BindView(R.id.tv_device_name)
        TextView mTvDeviceName;
        @BindView(R.id.tv_rssi)
        TextView mTvRssi;
        @BindView(R.id.tv_mac_address)
        TextView mTvMacAddress;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }


}
