package com.xb.ipclearning.useContentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/1/24.
 */

public class BookProvider extends ContentProvider
{
    private static final String TAG = "BookProvider";

    private static final String AUTHORITY = "com.xb.ipclearning.useContentProvider.BookProvider";

    private static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    private static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

    private static final int BOOK_URI_CODE = 0;
    private static final int USER_URI_CODE = 1;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;

    static
    {

        URI_MATCHER.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        URI_MATCHER.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    @Override
    public boolean onCreate()
    {
        Log.e(TAG, "onCreate,current thread:" + Thread.currentThread().getName());
        mContext = getContext();
        mSQLiteDatabase = new DbOpenHelper(mContext).getWritableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        String table = getTableName(uri);
        if (null == table)
        {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        Log.e(TAG, "query,current thread:" + Thread.currentThread().getName());
        return mSQLiteDatabase.query(table, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri)
    {
        Log.e(TAG, "getType,current thread:" + Thread.currentThread().getName());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values)
    {
        Log.e(TAG, "insert,current thread:" + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (null == table)
        {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        long i = mSQLiteDatabase.insert(table, null, values);
        if (i > 0)
        {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs)
    {
        Log.e(TAG, "delete,current thread:" + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (null == table)
        {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        int i = mSQLiteDatabase.delete(table, selection, selectionArgs);
        if (i > 0)
        {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return i;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        Log.e(TAG, "update,current thread:" + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (null == table)
        {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        int i = mSQLiteDatabase.update(table, values, selection, selectionArgs);
        if (i > 0)
        {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return i;
    }

    private String getTableName(Uri uri)
    {
        String tableName = null;
        switch (URI_MATCHER.match(uri))
        {
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
        }
        return tableName;
    }
}
