package com.xiongda.mvplearning.biz;

import com.xiongda.mvplearning.bean.User;

/**
 * Author: xiongda
 * Created on 2017/8/27.
 * Introduction:
 */

public class UserBiz implements IUserBiz {
    @Override
    public void login(final String userName, final String password, final OnLoginListener loginListener) {
        // 模拟子线程耗时操作
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                // 模拟登陆成功
                if("xiongda".equals(userName)&&"xiongda".equals(password)){
                    User user=new User();
                    user.setPassword(password);
                    user.setUserName(userName);
                    loginListener.loginSuccess(user);
                }else {
                    loginListener.loginFailed();
                }
            }
            
        }.start();
    }
}
