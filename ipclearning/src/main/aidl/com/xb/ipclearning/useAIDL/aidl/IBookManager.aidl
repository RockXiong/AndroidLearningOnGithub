// IBookManager.aidl
package com.xb.ipclearning.useAIDL.aidl;

// Declare any non-default types here with import statements

import com.xb.ipclearning.useAIDL.aidl.Book;
import com.xb.ipclearning.useAIDL.aidl.IOnNewBookArrivedListener;

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
            List<Book> getBookList();

            void addBook(in Book book);

            boolean registerIOnNewBookArrivedListener(IOnNewBookArrivedListener listener);

            boolean unRegisterIOnNewBookArrivedListener(IOnNewBookArrivedListener listener);
}
