package com.xb.notificationlearning;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.btn_add)
    Button mBtnAdd;
    @BindView(R.id.btn_udpate)
    Button mBtnUdpate;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    int mId;

    @OnClick({R.id.btn_add, R.id.btn_udpate, R.id.btn_delete})
    public void onClick(View view) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        switch (view.getId()) {
            case R.id.btn_add:
                Notification notificationAdd = new Notification.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                                                                             .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                                                             .setWhen(System.currentTimeMillis())
                                                                             .setContentTitle("I Am Title")
                                                                             .setContentText("I Am Content")
                                                                             .setAutoCancel(true)
                                                                             .build();

                manager.notify(++mId, notificationAdd);
                break;
            case R.id.btn_udpate:
                break;
            case R.id.btn_delete:
                break;
        }
    }
}
