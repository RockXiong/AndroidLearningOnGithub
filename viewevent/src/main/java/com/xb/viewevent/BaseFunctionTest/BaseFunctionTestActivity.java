package com.xb.viewevent.BaseFunctionTest;

import android.animation.ObjectAnimator;
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
    private static final String TAG = "TestActivity";

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

    private float mXVelocity = 1000;// m/s
    private float mAccelerationOfGravity = 9.8f;
    private float mScreenW;
    private float mScreenH;
    private float mLastYS;
    private float mLastXS;
    private float mLastYVelocity;
    private int mDuration = 100;//unit:ms
    private float mConvertCoordinateX;
    private float mConvertCoordinateY;

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
        int loc[] = new int[2];
        mVCanvas.getLocationOnScreen(loc);
        mConvertCoordinateX = mVCanvas.getX() / loc[0];
        mConvertCoordinateY = mVCanvas.getY() / loc[1];
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
        return super.onTouchEvent(event);//等同于 return false;因为onTouch中如果返回false,该view的onTouchEvent会被调用
        //        return true;
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
                //                startAnimate();
                break;
        }
    }


    boolean stopAni = false;

    private void startAnimate()
    {
        //TODO 待实现
        //        float[] newLocation = new float[2];
        //        do
        //        {
        //            //        if (stopAni)
        //            //        {
        //            //            return;
        //            //        }
        //            //        mLastYS = mScreenH - y;//Y方向距离
        //            //        mLastYVelocity = Math.sqrt(2 * mLastYS * mAccelerationOfGravity);//Y方向速度
        //            float xS = mXVelocity * mDuration / 1000;//X方向偏移距离
        //            float yS = mLastYVelocity * mDuration / 1000 + 0.5f * mAccelerationOfGravity * (mDuration / 1000) * (mDuration / 1000);//Y方向偏移距离
        //            mLastYVelocity += mAccelerationOfGravity * mDuration / 1000;
        //            int[] oldLocation = new int[2];
        //            mVCanvas.getLocationOnScreen(oldLocation);
        //            newLocation[0] = oldLocation[0] + xS;
        //            newLocation[1] = oldLocation[1] + yS;
        //            //            if (newLocation[1] >= mScreenH)
        //            //            {
        //            //                newLocation[1] = mScreenH;
        //            //                stopAni = true;
        //            //            }
        //            //            if (newLocation[0] >= mScreenW)
        //            //            {
        //            //                newLocation[0] = mScreenW;
        //            //                stopAni = true;
        //            //            }
        //            Log.d(TAG, "oldLocation[0]=" + oldLocation[0] + ";oldLocation[1]=" + oldLocation[1]);
        //            Log.d(TAG, "xS=" + xS + ";yS=" + yS + ";mLastYVelocity=" + mLastYVelocity);
        //            Log.d(TAG, "newLocation[0]=" + newLocation[0] + ";newLocation[1]=" + newLocation[1]);
        //
        //            AnimatorSet animatorSet = new AnimatorSet();
        //            ObjectAnimator xAnimator = ObjectAnimator.ofFloat(mVCanvas, View.TRANSLATION_X, xS);
        //            ObjectAnimator yAnimator = ObjectAnimator.ofFloat(mVCanvas, View.TRANSLATION_Y, yS);
        //
        //            animatorSet.setDuration(1000).playTogether(xAnimator, yAnimator);
        //            animatorSet.start();
        //        } while ((newLocation[1] <= mScreenH) && (newLocation[0] <= mScreenW));

    }

}
