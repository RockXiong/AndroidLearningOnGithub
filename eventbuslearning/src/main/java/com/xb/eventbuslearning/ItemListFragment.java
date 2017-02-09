package com.xb.eventbuslearning;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ItemListFragment extends ListFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        //开启线程加载列表
//        new Thread()
//        {
//            public void run()
//            {
//                try
//                {
//                    Thread.sleep(2000);//模拟延时
//                    //发布事件,在后台线程发的事件
                    EventBus.getDefault().post(new Event.ItemListEvent(Item.ITEMS));
//                }
//                catch (InterruptedException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Register
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //Unregister
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(Event.ItemListEvent event)
    {
        setListAdapter(new ArrayAdapter<Item>(getActivity(), android.R.layout.simple_list_item_activated_1, android.R.id.text1, event.getItems()));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        EventBus.getDefault().post(getListView().getItemAtPosition(position));
    }
}
