package com.xiongda.ipclearning.use_messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * 创建一个Service来处理客户端的连接请求
 * Created by xiongda on 2016/12/14.
 */

public class RemoteService extends Service
{
    private static final String TAG = RemoteService.class.getSimpleName();
    /**
     * 创建一个绑定了自定义Handler的Messenger并在onBind中返回mMessenger的binder
     * 作用是将客户端发送的消息传递给MessengerHandler处理
     */
    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Override
    public IBinder onBind(Intent intent)
    {
        return mMessenger.getBinder();//这里一定要返回mMessenger的内部的Binder对象
    }

    /**
     * 自定义handler用于处理接收到的消息
     */
    private static class MessengerHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MyConstants.MESSAGE_FROM_CLIENT:
                    Log.e(TAG, "Receive message from Client:" + msg.getData().getString("msg"));
                    Messenger messengerReply = msg.replyTo;
                    if (null != messengerReply)//接收到客户端的消息之后,回复一条消息
                    {
                        Message replyMsg = Message.obtain(null, MyConstants.MESSAGE_FROM_SERVICE);
                        Bundle data = new Bundle();
                        data.putString("msg", "Yeah This is RemoteService,I've received your message.");
                        replyMsg.setData(data);
                        try
                        {
                            messengerReply.send(replyMsg);//向客户端回复消息
                        }
                        catch (RemoteException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }

    }
}
