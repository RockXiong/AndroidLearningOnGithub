package com.xiongda.androidlearning.ListViewLearning;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xiongda.androidlearning.ListViewLearning.adapter.ListViewAdapter;
import com.xiongda.androidlearning.R;

import java.util.ArrayList;

public class ListViewLearningActivity extends AppCompatActivity
{

    ListView listView;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ListViewAdapter adapter;
    Toolbar mToolbar;
    private ObjectAnimator mAnimator;
    private int mTouchSlop;
    private float mFirstY;
    private float mCurrentY;
    private boolean mShow = true;
    private int mDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_learning);
        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        initView();
    }

    private void initView()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("Learning ListView");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });
        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty_btn_add));//设置空数据时要显示的布局
        addHeader(listView);
        adapter = new ListViewAdapter(this, stringArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                //                listView.setSelection(i + 8);//将listView跳转至第i+8项开始显示
                //                listView.smoothScrollBy(300,100);//实现平滑滚动
                listView.smoothScrollToPosition(i + 10);
            }
        });

        listView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        mFirstY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurrentY = motionEvent.getY();
                        if (mCurrentY > mFirstY)
                        {
                            mDirection = 0;
                        } else if (mFirstY > mCurrentY)
                        {
                            mDirection = 1;
                        }
                        if (mDirection == 1)
                        {
                            if (mShow)
                            {
                                toolbarAnim(1);//hide
                                mShow = !mShow;
                            }
                        } else if (mDirection == 0)
                        {
                            if (!mShow)
                            {
                                toolbarAnim(0);//show
                                mShow = !mShow;
                            }
                        }
                        break;
                }
                return false;
            }
        });

    }

    private void toolbarAnim(int flag)
    {
        if (mAnimator != null && mAnimator.isRunning())
        {
            mAnimator.cancel();
        }
        if (flag == 0)
        {
            mAnimator = ObjectAnimator.ofFloat(mToolbar, "translationY", mToolbar.getTranslationY(), 0);
        } else
        {
            mAnimator = ObjectAnimator.ofFloat(mToolbar, "translationY", mToolbar.getTranslationY(), -mToolbar.getHeight());
        }
        mAnimator.start();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.requestLayout();

    }

    private void addHeader(ListView listView)
    {
        View header = new View(this);
        header.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.toolbar_max_height)));
        listView.addHeaderView(header);
    }

    private ArrayList<String> genDatas(int count)
    {
        ArrayList<String> stringArrayList = new ArrayList<>();
        int i = 0;
        while (i++ < count)
        {
            stringArrayList.add(getString(R.string.list_view_item_text) + i);
        }
        return stringArrayList;
    }

    public void addData(View view)
    {
        stringArrayList.addAll(genDatas(50));
        adapter.notifyDataSetChanged();
    }
}
