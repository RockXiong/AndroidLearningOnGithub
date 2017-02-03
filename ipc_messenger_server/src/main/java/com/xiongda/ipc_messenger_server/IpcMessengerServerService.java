package com.xiongda.ipc_messenger_server;

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
 * Created by xiongda on 2016/12/15.
 */

public class IpcMessengerServerService extends Service
{
    public static final int MESSAGE_FROM_CLIENT = 0X01;
    public static final int MESSAGE_FROM_SERVICE = 0X02;
    private static final String TAG = IpcMessengerServerService.class.getSimpleName();
    private Messenger mMessenger = new Messenger(new MessageHandler());

    @Override
    public IBinder onBind(Intent intent)
    {
        return mMessenger.getBinder();
    }

    private static class MessageHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MESSAGE_FROM_CLIENT:
                    Log.e(TAG, "Receive message from client:" + msg.getData().getString("clientMsg"));
                    Messenger replyTo = msg.replyTo;
                    if (null != replyTo)
                    {
                        Message message = Message.obtain(null, MESSAGE_FROM_SERVICE);
                        Bundle data = new Bundle();
                        data.putString("serverMsg", "Yea I am Server,I have received your message.");
                        message.setData(data);
                        try
                        {
                            replyTo.send(message);
                        }
                        catch (RemoteException e)
                        {
                            e.printStackTrace();
                        }
                    }
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
