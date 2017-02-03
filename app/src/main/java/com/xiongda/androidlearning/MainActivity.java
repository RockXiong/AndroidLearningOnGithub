package com.xiongda.androidlearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xiongda.androidlearning.ListViewLearning.ListViewLearningActivity;

public class MainActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void listViewBtnClick(View view)
    {
        Intent intent = new Intent(MainActivity.this, ListViewLearningActivity.class);
        startActivity(intent);
    }
}
