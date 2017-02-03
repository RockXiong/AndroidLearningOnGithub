package com.xb.ipclearning.useAIDL.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xb.ipclearning.useAIDL.aidl.Book;
import com.xb.ipclearning.useAIDL.aidl.IBookManager;
import com.xb.ipclearning.useAIDL.aidl.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Administrator on 2017/1/24.
 */

public class BookManagerService extends Service
{
    private static final String TAG = "BookManagerService";

    //CopyOnWriteArrayList支持并发读写.AIDL方法是在服务端的Binder线程池执行的,
    // 当多个客户端同时连接的时候,会存在多个线程同时访问的情形,所以我们要在AIDL方
    // 法中处理线程同步.在这里直接使用CopyOnWriteArrayList来进行自动的线程同步.
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    private CopyOnWriteArrayList<Book> mBooksList = new CopyOnWriteArrayList<>();
    //    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mIOnNewBookArrivedListeners = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mIOnNewBookArrivedListeners = new RemoteCallbackList<>();

    private Binder mBinder = new IBookManager.Stub()
    {
        @Override
        public List<Book> getBookList() throws
                                        RemoteException
        {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws
                                       RemoteException
        {
            mBookList.add(book);
        }

        @Override
        public boolean registerIOnNewBookArrivedListener(IOnNewBookArrivedListener listener) throws
                                                                                             RemoteException
        {
            mIOnNewBookArrivedListeners.register(listener);
            return true;
        }

        @Override
        public boolean unRegisterIOnNewBookArrivedListener(IOnNewBookArrivedListener listener) throws
                                                                                               RemoteException
        {
            mIOnNewBookArrivedListeners.unregister(listener);
            Log.e(TAG, "unregister listener succeed.");
            Log.e(TAG, "unregisterListener,current size:" + mIOnNewBookArrivedListeners.getRegisteredCallbackCount());
            return true;
        }

    };

    @Override
    public void onCreate()
    {
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "Java"));
        new Thread(new ServiceWorker()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        /*验证权限*/
        int check = checkCallingOrSelfPermission("com.xb.ipclearning.permission.ACCESS_BOOK_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED)
        {
            return null;
        }
        return mBinder;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mIsServiceDestoryed.set(true);
    }

    private void onNewBookArrived(Book book) throws
                                             RemoteException
    {
        mBookList.add(book);
        final int N = mIOnNewBookArrivedListeners.beginBroadcast();
        for (int i = 0; i < N; i++)
        {
            IOnNewBookArrivedListener listener = mIOnNewBookArrivedListeners.getBroadcastItem(i);
            if (null != listener)
            {
                listener.onNewBookArrived(book);
            }
        }
        mIOnNewBookArrivedListeners.finishBroadcast();
    }

    private class ServiceWorker implements Runnable
    {

        @Override
        public void run()
        {
            while (!mIsServiceDestoryed.get())
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                try
                {
                    onNewBookArrived(new Book(bookId, "new Book#" + bookId));
                }
                catch (RemoteException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}
