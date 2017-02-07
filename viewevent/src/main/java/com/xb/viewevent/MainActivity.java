package com.xb.viewevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xb.viewevent.BaseFunctionTest.BaseFunctionTestActivity;
import com.xb.viewevent.SlidingConflict.SlidingConflictActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_base_function_test, R.id.btn_sliding_conflict})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_base_function_test:
                jumpActivity(BaseFunctionTestActivity.class);
                break;
            case R.id.btn_sliding_conflict:
                jumpActivity(SlidingConflictActivity.class);
                break;
        }
    }

    private void jumpActivity(Class<?> activity)
    {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

}
