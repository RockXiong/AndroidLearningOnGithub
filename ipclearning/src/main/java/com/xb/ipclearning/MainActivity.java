package com.xb.ipclearning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xb.ipclearning.useAIDL.client.UseAIDLActivity;
import com.xb.ipclearning.useContentProvider.ContentProviderActivity;
import com.xb.ipclearning.useMessenger.UseMessengerActivity;
import com.xb.ipclearning.useSocket.TCPClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity
{

    @BindView(R.id.btn_use_aidl)
    Button btnUseAidl;
    @BindView(R.id.btn_use_messenger)
    Button btnUseMessenger;
    @BindView(R.id.btn_use_content_provider)
    Button btnUseContentProvider;
    @BindView(R.id.btn_use_socket)
    Button btnUseSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_use_aidl, R.id.btn_use_messenger, R.id.btn_use_content_provider, R.id.btn_use_socket})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_use_aidl:
                startActivity(UseAIDLActivity.class);
                break;
            case R.id.btn_use_messenger:
                startActivity(UseMessengerActivity.class);
                break;
            case R.id.btn_use_content_provider:
                startActivity(ContentProviderActivity.class);
                break;
            case R.id.btn_use_socket:
                startActivity(TCPClientActivity.class);
                break;
        }
    }

    private void startActivity(Class<?> activityClass)
    {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

}
