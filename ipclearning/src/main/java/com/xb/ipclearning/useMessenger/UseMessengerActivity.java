package com.xb.ipclearning.useMessenger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xb.ipclearning.R;
import com.xb.ipclearning.UserParcelable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UseMessengerActivity extends Activity
{

    @BindView(R.id.btn_use_messenger)
    Button mBtnUseMessenger;
    @BindView(R.id.tv_value_result)
    TextView mTvValueResult;
    @BindView(R.id.btn_bind_service)
    Button mBtnBindService;

    Messenger mMessenger;
    private MessengerHandler mMessengerHandler = new MessengerHandler(this);
    public static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/cache.txt";

    private static class MessengerHandler extends Handler
    {
        WeakReference<UseMessengerActivity> mActivityWeakReference;

        MessengerHandler(UseMessengerActivity activityWeakReference)
        {
            mActivityWeakReference = new WeakReference<>(activityWeakReference);
        }

        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0:
                    String str = msg.getData().getString("TestString");
                    //                    mTvValueResult.setText(str);

                    /*反序列化对象的过程--仅作测试*/
                    try
                    {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH));
                        //                        User user = (User) ois.readObject();
                        UserParcelable userParcelable = (UserParcelable) ois.readObject();
                        str += "\n" + userParcelable.toString();
                    }
                    catch (IOException e)
                    {
                        str += "\n fault!\n" + e.getMessage();
                        e.printStackTrace();
                    }
                    catch (ClassNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                    mActivityWeakReference.get().mTvValueResult.setText(str);
                    break;
            }
            super.handleMessage(msg);
        }
    }

    Messenger mReplyMessenger = new Messenger(mMessengerHandler);

    ServiceConnection mServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mMessenger = new android.os.Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_bind_service, R.id.btn_use_messenger})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_bind_service:
                Intent intent = new Intent(UseMessengerActivity.this, RemoteServiceMessenger.class);
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                mTvValueResult.setText("Bind Service Successfully!");
                break;
            case R.id.btn_use_messenger:
                sendMessage();
                break;
        }
    }

    private void sendMessage()
    {
        Message msg = new Message();
        msg.replyTo = mReplyMessenger;
        msg.what = 0;
        Bundle bundle = new Bundle();
        bundle.putString("TestString", "This message is from Client UseMessengerActivity.");
        msg.setData(bundle);
        try
        {
            mMessenger.send(msg);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        String str = mTvValueResult.getText().toString();
        str += "\n" + "This message is from Client UseMessengerActivity.";
        mTvValueResult.setText(str);
    }

    @Override
    protected void onDestroy()
    {
        unbindService(mServiceConnection);
        mReplyMessenger = null;
        mMessengerHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
