package com.xb.viewevent.BaseFunctionTest;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/2/5.
 */

public class MySlideView extends View
{
    private static final String TAG = "MySlideView";
    private float mLastX;
    private float mLastY;

    public MySlideView(Context context)
    {
        super(context);
    }

    public MySlideView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MySlideView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
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
                float deltaX = x - mLastX;
                float deltaY = y - mLastY;
                Log.e(TAG, "move:deltaX=" + deltaX + ";deltaY=" + deltaY);
                Path path = new Path();
                path.moveTo(getTranslationX() + deltaX, getTranslationY() + deltaY);
                ObjectAnimator.ofFloat(this, "translationX", "translationY", path).start();
            }
            break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }
}
