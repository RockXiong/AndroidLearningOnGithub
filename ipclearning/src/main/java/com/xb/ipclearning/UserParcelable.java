package com.xb.ipclearning;

import android.os.Parcel;
import android.os.Parcelable;

import com.xb.ipclearning.useAIDL.aidl.Book;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/24.
 */

public class UserParcelable implements Parcelable, Serializable
{
    private static final long serialVersionUID = -4799992774454620379L;
    public int userId;
    public String userName;
    public boolean isMale;
    public Book mBook;

    public UserParcelable(int userId, String userName, boolean isMale, Book book)
    {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
        this.mBook = book;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeInt(isMale ? 1 : 0);
        dest.writeParcelable(mBook, 0);
    }

    public static final Parcelable.Creator<UserParcelable> CREATOR = new Parcelable.Creator<UserParcelable>()
    {

        @Override
        public UserParcelable createFromParcel(Parcel source)
        {
            return new UserParcelable(source);
        }

        @Override
        public UserParcelable[] newArray(int size)
        {
            return new UserParcelable[size];
        }
    };

    private UserParcelable(Parcel in)
    {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readInt() == 1;
        mBook = in.readParcelable(Thread.currentThread().getContextClassLoader());
    }

}
