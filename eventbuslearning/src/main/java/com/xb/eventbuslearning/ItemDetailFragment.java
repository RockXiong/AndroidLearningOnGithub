package com.xb.eventbuslearning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ItemDetailFragment extends Fragment
{
    private TextView tvDetail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //register
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //unregister
        EventBus.getDefault().unregister(this);
    }

    /**
     * List点击时会发送些事件,接收到事件后更新详情
     *
     * @param item
     */
    @Subscribe
    public void onEventMainThread(Item item)
    {
        if (null != item)
        {
            tvDetail.setText(item.content);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        tvDetail = (TextView) rootView.findViewById(R.id.item_detail);
        return rootView;
    }
}
