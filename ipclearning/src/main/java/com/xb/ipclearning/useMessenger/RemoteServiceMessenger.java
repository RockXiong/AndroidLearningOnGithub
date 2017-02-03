package com.xb.ipclearning.useMessenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xb.ipclearning.useAIDL.aidl.Book;
import com.xb.ipclearning.UserParcelable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Administrator on 2017/1/23.
 */

public class RemoteServiceMessenger extends Service
{
    Messenger mMessenger = new Messenger(sHandler);

    private static Handler sHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {

            switch (msg.what)
            {
                case 0:
                    Messenger messenger = msg.replyTo;
                    String str = msg.getData().getString("TestString");
                    str += "\n\nThis message is replied from Service RemoteServiceMessenger.";
                    Bundle bundle = new Bundle();
                    bundle.putString("TestString", str);

                    /*序列化一个对象的过程--仅作测试*/
                    UserParcelable user = new UserParcelable(12, "XiongDa", true, new Book(10, "nidaye"));
                    Log.e("TTT", UseMessengerActivity.PATH);
                    try
                    {
                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(UseMessengerActivity.PATH));

                        oos.writeObject(user);
                        oos.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    Message message = new Message();
                    message.what = 0;
                    message.setData(bundle);
                    try
                    {
                        messenger.send(message);
                    }
                    catch (RemoteException e)
                    {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return mMessenger.getBinder();
    }

}
