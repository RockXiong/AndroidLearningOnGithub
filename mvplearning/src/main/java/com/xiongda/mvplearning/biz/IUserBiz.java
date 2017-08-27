package com.xiongda.mvplearning.biz;

/**
 * Author: xiongda
 * Created on 2017/8/27.
 * Introduction:
 */

public interface IUserBiz {
    void  login(String userName,String password,OnLoginListener loginListener);
}
