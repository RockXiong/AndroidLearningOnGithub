package com.xb.ipclearning.useAIDL.aidl;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

/**
 * Created by Administrator on 2017/1/24.
 */

public interface IBookManager_M extends IInterface
{
    String DESCRIPTOR = "com.xb.ipclearning.useAIDL.aidl.IBookManager_M";
    int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION;
    int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;

    List<Book> getBookList() throws
                             RemoteException;

    void addBook(Book book) throws
                            RemoteException;

    void registerIOnNewBookArrivedListener(IOnNewBookArrivedListener listener);

    void unRegisterIOnNewBookArrivedListener(IOnNewBookArrivedListener listener);

}
