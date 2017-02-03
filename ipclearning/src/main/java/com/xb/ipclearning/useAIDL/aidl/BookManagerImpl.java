package com.xb.ipclearning.useAIDL.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/**
 * Created by Administrator on 2017/1/24.
 */

public class BookManagerImpl extends Binder implements IBookManager_M
{
    public BookManagerImpl()
    {
        this.attachInterface(this, DESCRIPTOR);
    }

    /**
     * Cast an IBinder object into an IBookManager interface,generating a proxy if need.
     *
     * @param obj
     * @return
     */
    public static IBookManager_M asInterface(IBinder obj)
    {
        if (null == obj)
        {
            return null;
        }
        IInterface iInterface = obj.queryLocalInterface(DESCRIPTOR);
        if ((null != iInterface) && (iInterface instanceof IBookManager_M))
        {
            return (IBookManager_M) iInterface;
        }
        return new BookManagerImpl.Proxy(obj);
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws
                                                                                 RemoteException
    {
        switch (code)
        {
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSACTION_getBookList:
                data.enforceInterface(DESCRIPTOR);
                List<Book> result = this.getBookList();
                reply.writeNoException();
                reply.writeTypedList(result);
                return true;
            case TRANSACTION_addBook:
                data.enforceInterface(DESCRIPTOR);
                Book arg0;
                if (0 != data.readInt())
                {
                    arg0 = Book.CREATOR.createFromParcel(data);
                } else
                {
                    arg0 = null;
                }
                this.addBook(arg0);
                reply.writeNoException();
                return true;

        }
        return super.onTransact(code, data, reply, flags);
    }

    @Override
    public List<Book> getBookList() throws
                                    RemoteException
    {
        //TODO 待实现
        return null;
    }

    @Override
    public void addBook(Book book) throws
                                   RemoteException
    {
        //TODO 待实现
    }

    @Override
    public void registerIOnNewBookArrivedListener(IOnNewBookArrivedListener listener)
    {

    }

    @Override
    public void unRegisterIOnNewBookArrivedListener(IOnNewBookArrivedListener listener)
    {

    }

    @Override
    public IBinder asBinder()
    {
        return this;
    }

    private static class Proxy implements IBookManager_M
    {
        private IBinder mRemote;

        Proxy(IBinder obj)
        {
            mRemote = obj;
        }

        @Override
        public List<Book> getBookList() throws
                                        RemoteException
        {
            Parcel data = Parcel.obtain();//输入数据
            Parcel reply = Parcel.obtain();//输出数据
            List<Book> result;
            try
            {
                data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getBookList, data, reply, 0);
                reply.readException();
                result = reply.createTypedArrayList(Book.CREATOR);
            }
            finally
            {
                reply.recycle();
                data.recycle();
            }
            return result;
        }

        @Override
        public void addBook(Book book) throws
                                       RemoteException
        {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try
            {
                data.writeInterfaceToken(DESCRIPTOR);
                if (null != book)
                {
                    data.writeInt(1);
                    book.writeToParcel(data, 0);
                } else
                {
                    data.writeInt(0);
                }
                mRemote.transact(TRANSACTION_addBook, data, reply, 0);
                reply.readException();
            }
            finally
            {
                data.recycle();
                reply.recycle();
            }
        }

        @Override
        public void registerIOnNewBookArrivedListener(IOnNewBookArrivedListener listener)
        {

        }

        @Override
        public void unRegisterIOnNewBookArrivedListener(IOnNewBookArrivedListener listener)
        {

        }

        @Override
        public IBinder asBinder()
        {
            return mRemote;
        }

        private String getInterfaceDescriptor()
        {

            return DESCRIPTOR;
        }

    }
}
