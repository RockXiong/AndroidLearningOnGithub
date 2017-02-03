package com.xiongda.ipc_messenger_client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

public class IpcMessengerClientActivity extends Activity
{
    public static final int MESSAGE_FROM_CLIENT = 0X01;
    public static final int MESSAGE_FROM_SERVICE = 0X02;
    public static final int MESSAGE_BIND_RESULT = 0X03;
    private final static String TAG = IpcMessengerClientActivity.class.getSimpleName();
    private Messenger mMessengerService;
    private Messenger mMessengerClient = new Messenger(new MessageHandler());
    private int binded;
    private ServiceConnection mServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder)
        {
            mMessengerService = new Messenger(iBinder);
            if (binded == 0)
            {
                Message msgResult = Message.obtain(null, MESSAGE_BIND_RESULT);
                Bundle bundle = new Bundle();
                bundle.putString("bindResult", "Bind Successfully!");
                msgResult.setData(bundle);
                try
                {
                    mMessengerClient.send(msgResult);
                    binded = -1;
                }
                catch (RemoteException e)
                {
                    e.printStackTrace();
                }

            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_messenger_client);
    }

    public void BindService(View view)
    {
        /**
         * 与应用内跨进程绑定service的区别在于此处的intent设置
         */
        ComponentName componentName = new ComponentName("com.xiongda.ipc_messenger_server", "com.xiongda.ipc_messenger_server.IpcMessengerServerService");//第一个参数为要绑定的服务所在的包名,第二个参数为服务的全名
        Intent intent = new Intent();
        intent.setComponent(componentName);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    public void sendMsgToServer(View view)
    {
        if (null != mMessengerService)
        {
            Message msgResult = Message.obtain(null, MESSAGE_FROM_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString("clientMsg", "Hello Server!I am xiongda!");
            msgResult.setData(bundle);
            msgResult.replyTo = mMessengerClient;
            try
            {
                mMessengerService.send(msgResult);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }
    }

    private static class MessageHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MESSAGE_FROM_CLIENT:
                    break;
                case MESSAGE_FROM_SERVICE:
                    Log.e(TAG, "Received from Server:" + msg.getData().getString("serverMsg"));
                    break;
                case MESSAGE_BIND_RESULT:
                    Log.e(TAG, "Bind result:" + msg.getData().getString("bindResult"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
