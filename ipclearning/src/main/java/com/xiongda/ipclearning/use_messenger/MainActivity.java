package com.xiongda.ipclearning.use_messenger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
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

import com.xb.ipclearning.R;


public class MainActivity extends Activity
{
    private static final String TAG = MainActivity.class.getSimpleName();
    private Messenger mMessengerService;
    private Messenger mMessageReply = new Messenger(new MessageHandler());
    private ServiceConnection mConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder)
        {
            /**
             * 绑定成功后用服务端返回的IBinder创建一个Messenger对象,通过该Messenger对象可以向服务端发送消息
             */
            mMessengerService = new Messenger(iBinder);
            sendMessage("Hello xiongda");
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
        setContentView(R.layout.activity_main);
        BindMyService();
    }

    /**
     * 绑定服务端进程的Service
     */
    private void BindMyService()
    {
        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 点击界面SendMessage按钮向RemoteService发送消息
     *
     * @param view
     */
    public void onClickSendMessage(View view)
    {
        //        BindMyService();
        sendMessage("Hello xiongdaxiongda");
    }

    private void sendMessage(String msgData)
    {
        if (null != mMessengerService)
        {
            Message msg = Message.obtain(null, MyConstants.MESSAGE_FROM_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString("msg", msgData);
            msg.replyTo = mMessageReply;//通过replyTo将mMessageReply传递给服务端,服务端通过这个replyTo参数来回应客户端
            msg.setData(bundle);
            try
            {
                mMessengerService.send(msg);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        unbindService(mConnection);
        super.onDestroy();
    }

    /**
     * 创建一个自定义的handler用于处理接收自服务端的消息
     */
    private static class MessageHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MyConstants.MESSAGE_FROM_SERVICE://接收RemoteService回复的消息
                    Log.e(TAG, "Receive msg from RemoteService:" + msg.getData().getString("msg"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
