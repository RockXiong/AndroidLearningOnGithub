package com.xiongda.androidlearning.ListViewLearning;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ListView;

/**
 * Created by xiongda on 2016/11/22.
 */

public class XListView extends ListView
{
    private Context mContext;
    /**
     * 弹性滑动的最大距离
     */
    private int mMaxOverDistance = 80;

    public XListView(Context context)
    {
        super(context);
        mContext = context;
        initView();
    }

    public XListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public XListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    public XListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView();
    }

    private void initView()
    {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        float density = metrics.density;
        mMaxOverDistance = (int) (density * mMaxOverDistance);
    }

    /**
     * Scroll the view with standard behavior for scrolling beyond the normal
     * content boundaries. Views that call this method should override
     * {@link #onOverScrolled(int, int, boolean, boolean)} to respond to the
     * results of an over-scroll operation.
     * <p>
     * Views can use this method to handle any touch or fling-based scrolling.
     *
     * @param deltaX         Change in X in pixels
     * @param deltaY         Change in Y in pixels
     * @param scrollX        Current X scroll value in pixels before applying deltaX
     * @param scrollY        Current Y scroll value in pixels before applying deltaY
     * @param scrollRangeX   Maximum content scroll range along the X axis
     * @param scrollRangeY   Maximum content scroll range along the Y axis
     * @param maxOverScrollX Number of pixels to overscroll by in either direction
     *                       along the X axis.
     * @param maxOverScrollY Number of pixels to overscroll by in either direction
     *                       along the Y axis.
     * @param isTouchEvent   true if this scroll operation is the result of a touch event.
     * @return true if scrolling was clamped to an over-scroll boundary along either
     * axis, false otherwise.
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent)
    {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxOverDistance, isTouchEvent);
    }
}
