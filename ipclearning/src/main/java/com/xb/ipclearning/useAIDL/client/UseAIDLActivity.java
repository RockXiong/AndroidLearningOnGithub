package com.xb.ipclearning.useAIDL.client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xb.ipclearning.R;
import com.xb.ipclearning.useAIDL.aidl.Book;
import com.xb.ipclearning.useAIDL.aidl.IBookManager;
import com.xb.ipclearning.useAIDL.aidl.IOnNewBookArrivedListener;
import com.xb.ipclearning.useAIDL.service.BookManagerService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UseAIDLActivity extends Activity
{

    private static final String TAG = "UseAIDLActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;


    @BindView(R.id.btn_bind_service)
    Button mBtnBindService;
    @BindView(R.id.btn_use_messenger)
    Button mBtnUseMessenger;
    @BindView(R.id.tv_title_result)
    TextView mTvTitleResult;
    @BindView(R.id.tv_value_result)
    TextView mTvValueResult;

    IBookManager mIBookManager;
    @BindView(R.id.sv_text)
    ScrollView mSvText;
    //    IBookManager_M mIBookManager;

    /**
     * 死亡代理,当到服务端的Binder断裂(Binder死亡)时回调此接口的方法
     */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient()
    {
        @Override
        public void binderDied()
        {
            if (null == mIBookManager)
            {
                return;
            }
            mIBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mIBookManager = null;
            bindService(new Intent(UseAIDLActivity.this, BookManagerService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
            Log.e(TAG, "binderDied.Thread name:" + Thread.currentThread().getName());
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mIBookManager = IBookManager.Stub.asInterface(service);
            try
            {
                mIBookManager.registerIOnNewBookArrivedListener(mIOnNewBookArrivedListener);
                //注册死亡代理
                service.linkToDeath(mDeathRecipient, 0);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
            //            mIBookManager = BookManagerImpl.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            mIBookManager = null;
            //            bindService(new Intent(UseAIDLActivity.this, BookManagerService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
            Log.e(TAG, "onServiceDisconnected.Thread name:" + Thread.currentThread().getName());
        }
    };
    private IOnNewBookArrivedListener mIOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub()
    {
        @Override
        public void onNewBookArrived(Book newBook) throws
                                                   RemoteException
        {
            //onNewBookArrived方法运行在客户端的Binder线程池中,所以不能在这里直接访问UI相关的内容
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, newBook).sendToTarget();
        }
    };
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

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    mTvValueResult.append("\nReceive new book:" + msg.obj.toString());
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        ButterKnife.bind(this);
        mTvValueResult.addTextChangedListener(mTextWatcher);
    }

    @OnClick({R.id.btn_bind_service, R.id.btn_use_messenger})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_bind_service:
                Intent intent = new Intent(this, BookManagerService.class);
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                mTvTitleResult.setText("Bind Successfully!");
                break;
            case R.id.btn_use_messenger:
                try
                {
                    mIBookManager.addBook(new Book(3, "Android Developer"));//服务端方法可能比较耗时,不要放在UI线程执行
                    List<Book> list = mIBookManager.getBookList();
                    String str = "query book list,list type:" + list.getClass().getCanonicalName() + "\n";
                    for (Book book : list)
                    {
                        str += "list:\n" + book.toString() + "\n";
                    }
                    mTvValueResult.setText(str);
                }
                catch (RemoteException e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        if (null != mIBookManager && mIBookManager.asBinder().isBinderAlive())
        {
            try
            {
                Log.e(TAG, "unregister listener:" + mIOnNewBookArrivedListener);
                mIBookManager.unRegisterIOnNewBookArrivedListener(mIOnNewBookArrivedListener);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}
