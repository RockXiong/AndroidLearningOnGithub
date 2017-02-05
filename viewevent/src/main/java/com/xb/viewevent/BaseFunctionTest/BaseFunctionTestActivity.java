package com.xb.viewevent.BaseFunctionTest;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xb.viewevent.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseFunctionTestActivity extends AppCompatActivity implements View.OnTouchListener
{

    @BindView(R.id.v_canvas)
    View mVCanvas;
    @BindView(R.id.tv_velocity_value_x)
    TextView mTvVelocityValueX;
    @BindView(R.id.tv_velocity_value_y)
    TextView mTvVelocityValueY;
    @BindView(R.id.btn_view_animation)
    Button mBtnViewAnimation;
    @BindView(R.id.tv_touch_value_x)
    TextView mTvTouchValueX;
    @BindView(R.id.tv_touch_value_y)
    TextView mTvTouchValueY;

    private float mXVelocity = 10;
    private float mAccelerationOfGravity = 9.8f;
    private float mScreenW;
    private float mScreenH;
    private float mLastYS;
    private double mLastYVelocity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_function_text);
        ButterKnife.bind(this);
        mVCanvas.setOnTouchListener(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenW = displayMetrics.widthPixels;
        mScreenH = displayMetrics.heightPixels;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (v.getId())
        {
            case R.id.v_canvas:
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_MOVE:
                    {
                        VelocityTracker velocityTracker = VelocityTracker.obtain();
                        velocityTracker.addMovement(event);
                        velocityTracker.computeCurrentVelocity(1000);
                        float xVelocity = velocityTracker.getXVelocity();
                        float yVelocity = velocityTracker.getYVelocity();
                        mTvVelocityValueX.setText(xVelocity + "");
                        mTvVelocityValueY.setText(yVelocity + "");
                        velocityTracker.clear();
                        velocityTracker.recycle();
                    }
                    break;
                    case MotionEvent.ACTION_UP:
                    {
                        mTvTouchValueX.setText(event.getX() + ";" + mVCanvas.getLeft());
                        mTvTouchValueY.setText(event.getY() + ";" + mVCanvas.getTop());
                    }
                    break;
                }
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    @OnClick(R.id.btn_view_animation)
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_view_animation:
                ObjectAnimator.ofFloat(mVCanvas, "translationX", 0, 100, 300).setDuration(100).start();
                //                AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.my_anim);
                //                mVCanvas.startAnimation(animationSet);

                break;
        }
    }

    Path mPath = new Path();

    private void startAnimate(float x, float y)
    {
        //TODO 实现自由落体以及抛物线
        实现自由落体以及抛物线
        mLastYS = mScreenH - y;//Y方向距离
        mLastYVelocity = Math.sqrt(2 * mLastYS * mAccelerationOfGravity);//Y方向速度
        double xS = mXVelocity * (mLastYVelocity / mAccelerationOfGravity);//X方向偏移距离
    }

}
