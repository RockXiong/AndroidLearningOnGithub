package com.xiongda.mvplearning.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xiongda.mvplearning.R;
import com.xiongda.mvplearning.bean.User;
import com.xiongda.mvplearning.presenter.UserLoginPresenter;
import com.xiongda.mvplearning.view.IUserLoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity implements IUserLoginView {

    @BindView(R.id.et_userName)
    EditText mEditTextUserName;
    @BindView(R.id.et_password)
    EditText mEditTextPassword;
    @BindView(R.id.btn_login)
    Button mButtonLogin;
    @BindView(R.id.btn_clear)
    Button mButtonClear;
    @BindView(R.id.progress_bar_loading)
    ProgressBar mProgressBarLoading;

    private UserLoginPresenter mUserLoginPresenter = new UserLoginPresenter(this);

    @OnClick({R.id.btn_clear, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear:
                mUserLoginPresenter.clear();
                break;
            case R.id.btn_login:
                mUserLoginPresenter.login();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public String getUserName() {
        return mEditTextUserName.getText()
                                .toString();
    }

    @Override
    public String getPassword() {
        return mEditTextPassword.getText()
                                .toString();
    }

    @Override
    public void clearUserName() {
        mEditTextUserName.setText("");
    }

    @Override
    public void clearPassword() {
        mEditTextPassword.setText("");
    }

    @Override
    public void showLoading() {
        mProgressBarLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBarLoading.setVisibility(View.GONE);
    }

    @Override
    public void toMainActivity(User user) {
        Toast.makeText(this, user.getUserName() + " login sueccess,to MainActivity.", Toast.LENGTH_SHORT)
             .show();
    }

    @Override
    public void showFailedError() {
        Toast.makeText(this, " login failed.", Toast.LENGTH_SHORT)
             .show();
    }
}
