package com.xiongda.androidlearning.ListViewLearning.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiongda.androidlearning.R;

import java.util.List;

/**
 * Created by xiongda on 2016/11/21.
 */

public class ListViewAdapter extends BaseAdapter
{
    private final List<String> mData;
    private LayoutInflater inflater;

    public ListViewAdapter(Context context, List<String> data)
    {
        this.inflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public int getCount()
    {
        return this.mData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.mData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        ViewHolder viewHolder = null;
        //判断是否缓存
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_view_item, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.text_view);
            convertView.setTag(viewHolder);
        } else
        {
            //通过tag找到缓存的布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setBackgroundResource(R.mipmap.ic_launcher);
        viewHolder.textView.setText(mData.get(position));
        return convertView;
    }

    private static final class ViewHolder
    {
        ImageView imageView;
        TextView textView;
    }
}
