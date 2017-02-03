package com.xb.ipclearning;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/23.
 */

public class User implements Serializable
{

    private static final long serialVersionUID = 6223025555308564244L;
    private int userId;
    private String userName;
    private boolean isMale;

    public User(int userId, String userName, boolean isMale)
    {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    public boolean isMale()
    {
        return isMale;
    }

    public void setMale(boolean male)
    {
        isMale = male;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    @Override
    public String toString()
    {
        return "userId=" + getUserId() + ",userName=" + getUserName() + ",isMale=" + isMale();
    }
}
