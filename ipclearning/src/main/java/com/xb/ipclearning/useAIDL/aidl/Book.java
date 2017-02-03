package com.xb.ipclearning.useAIDL.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/24.
 */

public class Book implements Parcelable, Serializable
{
    private static final long serialVersionUID = 4432281713063287812L;
    public int bookId;
    public String bookName;

    public Book(int bookId, String bookName)
    {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(bookId);
        dest.writeString(bookName);
    }

    public static final Creator<Book> CREATOR = new Parcelable.Creator<Book>()
    {

        @Override
        public Book createFromParcel(Parcel source)
        {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size)
        {
            return new Book[size];
        }
    };

    private Book(Parcel in)
    {
        bookId = in.readInt();
        bookName = in.readString();
    }

    @Override
    public String toString()
    {
        return "bookId=" + bookId + ",bookName=" + bookName;
    }
}
