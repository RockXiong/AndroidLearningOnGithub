package com.xiongda.mvplearning.biz;

import com.xiongda.mvplearning.bean.User;

/**
 * Author: xiongda
 * Created on 2017/8/27.
 * Introduction:
 */

public interface OnLoginListener {
    void loginSuccess(User user);

    void loginFailed();
}
