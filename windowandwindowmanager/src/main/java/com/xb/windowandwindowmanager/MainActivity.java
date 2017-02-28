package com.xb.windowandwindowmanager;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button mButton;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        windowTest();
    }

    private void windowTest()
    {
        mButton = new Button(this);
        mButton.setText("button");
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mLayoutParams.gravity = Gravity.START | Gravity.TOP;
        mLayoutParams.x = 100;
        mLayoutParams.y = 300;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mButton.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int rawX = (int) event.getRawX();
                int rawY = (int) event.getRawY();
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mLayoutParams.x = rawX - mButton.getWidth() / 2;
                        mLayoutParams.y = rawY - mButton.getHeight() / 2;
                        mWindowManager.updateViewLayout(mButton, mLayoutParams);
                        break;
                }
                return false;
            }
        });

        mWindowManager.addView(mButton, mLayoutParams);
        mButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mWindowManager.removeView(v);
                mButton = null;
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (null != mButton)
        {
            mWindowManager.removeView(mButton);
        }
        mWindowManager = null;
        mLayoutParams = null;
        mButton = null;
    }
}
