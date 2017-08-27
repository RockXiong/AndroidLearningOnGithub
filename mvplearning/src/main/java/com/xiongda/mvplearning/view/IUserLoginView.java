package com.xiongda.mvplearning.view;

import com.xiongda.mvplearning.bean.User;

/**
 * Author: xiongda
 * Created on 2017/8/27.
 * Introduction:
 */

public interface IUserLoginView {
    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError();
}
