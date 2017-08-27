package com.xiongda.mvplearning.presenter;

import com.xiongda.mvplearning.bean.User;
import com.xiongda.mvplearning.biz.IUserBiz;
import com.xiongda.mvplearning.biz.OnLoginListener;
import com.xiongda.mvplearning.biz.UserBiz;
import com.xiongda.mvplearning.view.IUserLoginView;

import android.os.Handler;
import android.os.Looper;

/**
 * Author: xiongda
 * Created on 2017/8/27.
 * Introduction:
 */

public class UserLoginPresenter {
    
    private IUserBiz mIUserBiz;
    private IUserLoginView mIUserLoginView;
    private Handler mHandler=new Handler(Looper.getMainLooper());
    public UserLoginPresenter(IUserLoginView userLoginView) {
        this.mIUserLoginView=userLoginView;
        this.mIUserBiz=new UserBiz();
    }
    public void login(){
        mIUserLoginView.showLoading();
        mIUserBiz.login(mIUserLoginView.getUserName(), mIUserLoginView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(final User user) {
                // 需要在UI线程中执行
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mIUserLoginView.toMainActivity(user);
                        mIUserLoginView.hideLoading();
                    }
                });
            }

            @Override
            public void loginFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mIUserLoginView.showFailedError();
                        mIUserLoginView.hideLoading();
                    }
                });
            }
        });
    }
    public void clear(){
        mIUserLoginView.clearPassword();
        mIUserLoginView.clearUserName();
    }
}
