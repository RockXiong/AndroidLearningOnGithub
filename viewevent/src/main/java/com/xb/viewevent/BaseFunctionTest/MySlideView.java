package com.xb.viewevent.BaseFunctionTest;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/2/5.
 */

public class MySlideView extends ImageView
{
    private static final String TAG = "MySlideView";
    private float mLastXF;
    private float mLastYF;
    private Context mContext;
    Scroller mScroller;

    GestureDetector mGestureDetector;
    GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener()
    {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {

            Log.e(TAG, "onScroll");
            smoothScrollTo((int) distanceX, (int) distanceY);
            return true;
        }
    };

    public MySlideView(Context context)
    {
        super(context);
        this.mContext = context;
        mScroller = new Scroller(mContext);
        mGestureDetector = new GestureDetector(context, mOnGestureListener);
    }

    public MySlideView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;
        mScroller = new Scroller(mContext);
        mGestureDetector = new GestureDetector(context, mOnGestureListener);
    }

    public MySlideView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mScroller = new Scroller(mContext);
        mGestureDetector = new GestureDetector(context, mOnGestureListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getRawX();
        float y = event.getRawY();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
            {
                float deltaX = x - mLastXF;
                float deltaY = y - mLastYF;
                Log.e(TAG, "move:deltaX=" + deltaX + ";deltaY=" + deltaY);
                Path path = new Path();
                path.moveTo(getTranslationX() + deltaX, getTranslationY() + deltaY);
                ObjectAnimator.ofFloat(this, "translationX", "translationY", path).start();
                //                smoothScrollTo((int) -event.getX(), (int) -event.getY());
            }
            break;
            default:
                break;
        }
        mLastXF = x;
        mLastYF = y;
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }


    }

    int mLastX;
    int mLastY;

    /**
     * 缓慢滚动到指定位置
     *
     * @param destX
     * @param destY
     */
    private void smoothScrollTo(int destX, int destY)
    {

        int scrollX = mLastX;
        int scrollY = mLastY;
        int deltaX = destX + mLastX;
        int deltaY = destY + mLastY;
        mLastX = deltaX;
        mLastY = deltaY;
        //1000ms 内滑向destX,效果就是慢慢滑动
        mScroller.startScroll(scrollX, scrollY, deltaX, deltaY, 0);
        invalidate();//该方法会导致view重绘
    }


}
