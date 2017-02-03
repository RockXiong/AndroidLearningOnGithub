package com.xb.ipclearning.useSocket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by xiongda on 2017/2/3.
 */

public class TCPService extends Service
{
    public static final int SERVER_PORT = 8868;
    private static final String TAG = "TCPService";
    private boolean mIsServiceDestroyed = false;
    private String[] mDefinedMessages = new String[]{"hello", "hello 1", "hello 2", "hello 3", "how are you?", "where are you from?"};

    @Override
    public void onCreate()
    {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mIsServiceDestroyed = true;
    }

    private void responseClient(Socket client) throws
                                               IOException
    {
        //used to receive client's messages
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //used to send messages to client
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
        printWriter.println("Welcome to my chat room!");
        while (!mIsServiceDestroyed)
        {
            String str = reader.readLine();
            Log.d(TAG, "msg from client:" + str);
            if (null == str)
            {
                //client disconnects
                break;
            }
            int i = new Random().nextInt(mDefinedMessages.length);
            String msg = mDefinedMessages[i];
            printWriter.println(msg);
            Log.d(TAG, "send:" + msg);
        }
        Log.d(TAG, "client quit.");
        printWriter.close();
        reader.close();
        client.close();

    }

    private class TcpServer implements Runnable
    {

        @Override
        public void run()
        {
            ServerSocket serverSocket = null;
            //listen local port 8868
            try
            {
                serverSocket = new ServerSocket(SERVER_PORT);
            }
            catch (IOException e)
            {
                Log.e(TAG, "establish tcp server failed,port:" + SERVER_PORT);
                e.printStackTrace();
                return;
            }
            while (!mIsServiceDestroyed)
            {
                //receive the requests from clients
                try
                {
                    final Socket client = serverSocket.accept();
                    Log.d(TAG, "accept");
                    new Thread()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                responseClient(client);
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


}
