package com.xb.ipclearning.useSocket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xb.ipclearning.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TCPClientActivity extends Activity
{

    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;
    private static final int MESSAGE_SOCKET_CONNECTED_FAILED = 3;


    @BindView(R.id.btn_bind_service)
    Button btnBindService;
    @BindView(R.id.btn_use_messenger)
    Button btnUseMessenger;
    @BindView(R.id.tv_value_result)
    TextView tvValueResult;
    @BindView(R.id.sv_text)
    ScrollView mSvText;
    @BindView(R.id.et_send_message)
    EditText mEditText;

    private PrintWriter mPrintWriter;
    private Socket mClientSocket;
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MESSAGE_RECEIVE_NEW_MSG:
                    tvValueResult.append((String) msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    btnUseMessenger.setEnabled(true);
                    tvValueResult.append("\nconnect server success!");
                    break;
                case MESSAGE_SOCKET_CONNECTED_FAILED:
                    tvValueResult.append("\nconnect server failed!retry...");
                    break;
            }
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
            new Handler().post(new Runnable()
            {
                @Override
                public void run()
                {
                    /* 该方法不能直接被调用
                     因为Android很多函数都是基于消息队列来同步，所以需要一部操作，
                     addView完之后，不等于马上就会显示，而是在队列中等待处理，虽然很快，但是如果立即调用fullScroll， view可能还没有显示出来，所以会失败
                     应该通过handler在新线程中更新
                     */
                    mSvText.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
            //            mSvText.smoothScrollTo(0, mSvText.getBottom());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpclient);
        ButterKnife.bind(this);
        tvValueResult.addTextChangedListener(mTextWatcher);


    }

    @OnClick({R.id.btn_bind_service, R.id.btn_use_messenger})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_bind_service:
                Intent intent = new Intent(this, TCPService.class);
                startService(intent);
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        connectTcpServer();
                    }

                }.start();
                break;
            case R.id.btn_use_messenger:
                final String msg = mEditText.getText().toString();
                if (!TextUtils.isEmpty(msg) && mPrintWriter != null)
                {
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            mPrintWriter.println(msg);
                        }
                    }).start();
                    mEditText.setText("");
                    String time = formatDateTime(System.currentTimeMillis());
                    final String shownMsg = "\nself " + time + ":" + msg;
                    tvValueResult.append(shownMsg);
                }
                break;
        }
    }

    private String formatDateTime(long l)
    {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(l));
    }

    @Override
    protected void onDestroy()
    {
        if (null != mClientSocket)
        {
            try
            {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    private void connectTcpServer()
    {
        Socket socket = null;
        while (socket == null)
        {
            try
            {
                socket = new Socket("localhost", TCPService.SERVER_PORT);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
            }
            catch (IOException e)
            {
                SystemClock.sleep(1000);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED_FAILED);
                e.printStackTrace();
            }
        }
        //receive message from server
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!TCPClientActivity.this.isFinishing())
            {
                String msg = br.readLine();
                if (null != msg)
                {
                    String time = formatDateTime(System.currentTimeMillis());
                    final String shownMsg = "\nserver " + time + ":" + msg;
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, shownMsg).sendToTarget();
                }
            }
            mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, "quit...").sendToTarget();
            mPrintWriter.close();
            br.close();
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
