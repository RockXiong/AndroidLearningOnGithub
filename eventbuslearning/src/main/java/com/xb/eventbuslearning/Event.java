package com.xb.eventbuslearning;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */

public class Event
{
    /**
     * 加载列表事件
     */
    public static class ItemListEvent
    {
        private List<Item> mItems;

        public ItemListEvent(List<Item> items)
        {
            this.mItems = items;
        }

        public List<Item> getItems()
        {
            return mItems;
        }

    }

}
