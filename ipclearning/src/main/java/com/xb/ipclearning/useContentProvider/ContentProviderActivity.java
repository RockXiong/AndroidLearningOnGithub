package com.xb.ipclearning.useContentProvider;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xb.ipclearning.R;
import com.xb.ipclearning.useAIDL.aidl.Book;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentProviderActivity extends Activity
{
    @BindView(R.id.btn_insert)
    Button mBtnInsert;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;
    @BindView(R.id.btn_update)
    Button mBtnUpdate;
    @BindView(R.id.btn_query)
    Button mBtnQuery;
    @BindView(R.id.tv_value_result)
    TextView mTvValueResult;
    @BindView(R.id.sv_text)
    ScrollView mSvText;

    String baseUri = "content://com.xb.ipclearning.useContentProvider.BookProvider/";

    Uri mBookUri = Uri.parse(baseUri + "book");
    Uri mUserUri = Uri.parse(baseUri + "user");

    private TextWatcher mTextWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }

        @Override
        public void afterTextChanged(Editable s)
        {
            mSvText.fullScroll(ScrollView.FOCUS_DOWN);
            //            mSvText.smoothScrollTo(0, mSvText.getBottom());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        ButterKnife.bind(this);
        mTvValueResult.addTextChangedListener(mTextWatcher);
    }

    @OnClick({R.id.btn_insert, R.id.btn_delete, R.id.btn_update, R.id.btn_query})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_insert:
                ContentValues values = new ContentValues();
                long t = System.currentTimeMillis();
                int id = (int) (t - (t / 100000) * 100000);
                values.put("_id", id);
                values.put("name", "帅是怎样炼成的" + id);
                getContentResolver().insert(mBookUri, values);
                queryBooks();
                break;
            case R.id.btn_delete:
                Cursor cursor2 = getContentResolver().query(mBookUri, new String[]{"_id", "name"}, null, null, null);
                if (cursor2 != null)
                {
                    if (cursor2.moveToLast())
                    {
                        int i = getContentResolver().delete(mBookUri, "_id=?", new String[]{cursor2.getString(cursor2.getColumnIndex("_id"))});
                        if (i > 0)
                        {
                            queryBooks();
                        }
                    }
                    cursor2.close();
                }
                break;
            case R.id.btn_update:
                Cursor cursor3 = getContentResolver().query(mBookUri, new String[]{"_id", "name"}, null, null, null);
                if (cursor3 != null)
                {
                    if (cursor3.moveToLast())
                    {
                        ContentValues values1 = new ContentValues();
                        values1.put("name", "帅得难以自拔" + cursor3.getInt(cursor3.getColumnIndex("_id")));
                        int i = getContentResolver().update(mBookUri, values1, "_id=?", new String[]{cursor3.getString(cursor3.getColumnIndex("_id"))});
                        if (i > 0)
                        {
                            queryBooks();
                        }
                    }
                    cursor3.close();
                }
                break;
            case R.id.btn_query:
                queryBooks();
                break;
        }
    }

    private void queryBooks()
    {
        Cursor cursor1 = getContentResolver().query(mBookUri, new String[]{"_id", "name"}, null, null, null);
        if (cursor1 != null)
        {
            mTvValueResult.setText("");
            while (cursor1.moveToNext())
            {
                Book book = new Book(cursor1.getInt(cursor1.getColumnIndex("_id")), cursor1.getString(cursor1.getColumnIndex("name")));
                mTvValueResult.append("\nInsert book:" + book.toString());
            }
            cursor1.close();
        }
    }
}
