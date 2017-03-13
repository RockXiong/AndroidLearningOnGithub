package com.xb.bluetoothlearning.wiget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xb.bluetoothlearning.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/9.
 */

public class MyDialog extends Dialog
{
    @BindView(R.id.et_device_name)
    EditText mEtDeviceName;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    private IMyDialogListener mIMyDialogListener;

    public MyDialog(@NonNull Context context, @Nullable IMyDialogListener myDialogListener)
    {
        super(context);
        this.mIMyDialogListener = myDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_bluetooth_name);
        ButterKnife.bind(this);
        setCancelable(false);
    }

    @OnClick({R.id.btn_cancel, R.id.btn_ok})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_cancel:
                mIMyDialogListener.onCancel();
                break;
            case R.id.btn_ok:
                if (TextUtils.isEmpty(mEtDeviceName.getText().toString()))
                {
                    return;
                }
                mIMyDialogListener.onOk(mEtDeviceName.getText().toString());
                dismiss();
                break;
        }
    }

    public interface IMyDialogListener
    {

        void onCancel();

        void onOk(String str);
    }
}
