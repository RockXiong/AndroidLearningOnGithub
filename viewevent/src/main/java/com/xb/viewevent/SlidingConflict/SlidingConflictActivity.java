package com.xb.viewevent.SlidingConflict;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xb.viewevent.R;

import java.util.ArrayList;

public class SlidingConflictActivity extends AppCompatActivity
{

    private static final String TAG = "SlidingConflictActivity";
    private HorizontalScrollViewEx mListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_conflic);
        Log.d(TAG, "onCreate");
        initView();
    }

    private void initView()
    {
        LayoutInflater inflater = getLayoutInflater();
        mListContainer = (HorizontalScrollViewEx) findViewById(R.id.container);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int screenW = displayMetrics.widthPixels;
        final int screenH = displayMetrics.heightPixels;

        for (int i = 0; i < 3; i++)
        {
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.sliding_conflict_view, mListContainer, false);
            layout.getLayoutParams().width = screenW;
            TextView textView = (TextView) layout.findViewById(R.id.tv_title);
            textView.setText("page " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 200));
            createList(layout);
            mListContainer.addView(layout);
        }
    }

    private void createList(ViewGroup layout)
    {
        ListView listView = (ListView) layout.findViewById(R.id.lv_sliding);
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++)
        {
            datas.add("name " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);
    }
}
