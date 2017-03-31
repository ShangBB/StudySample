package com.shangbb.studysample.sample.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.shangbb.studysample.IBookManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @Fuction: 服务端的AIDLService.java
 * @Author: BBShang
 * @Date: 2017/3/24 19:08
 */

public class AidlService extends Service {

    public final String TAG = this.getClass().getSimpleName();

    //包含Book对象的list
    private List<Book> mBooks = new ArrayList<>();

    //由AIDL文件生成的BookManager
    private final IBookManager.Stub mBookManager = new IBookManager.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            synchronized (this) {
                Log.d(TAG, "invoking getBooks() method , now the list is : " + mBooks.toString());
                if (mBooks != null) {
                    return mBooks;
                }
                return new ArrayList<>();
            }
        }

        @Override
        public Book getBook() throws RemoteException {
            return null;
        }

        @Override
        public int getBookCount() throws RemoteException {
            return 0;
        }

        @Override
        public void setBookPrice(Book book, int price) throws RemoteException {

        }

        @Override
        public void setBookName(Book book, String name) throws RemoteException {

        }


        @Override
        public void addBookIn(Book book) throws RemoteException {
            synchronized (this) {
                if (mBooks == null) {
                    mBooks = new ArrayList<>();
                }
                if (book == null) {
                    Log.d(TAG, "Book is null in In");
                    book = new Book();
                }
                //尝试修改book的参数，主要是为了观察其到客户端的反馈
                book.setName(book.getName() + "[上架]");
                if (!mBooks.contains(book)) {
                    mBooks.add(book);
                }
                //打印mBooks列表，观察客户端传过来的值
                Log.d(TAG, "invoking addBooks() method , now the list is : " + mBooks.toString());
            }
        }

        @Override
        public void addBookOut(Book book) throws RemoteException {
            synchronized (this) {
                if (mBooks == null) {
                    mBooks = new ArrayList<>();
                }
                if (book == null) {
                    Log.d(TAG, "Book is null in Out");
                    book = new Book();
                }
                //尝试修改book的参数，主要是为了观察其到客户端的反馈
                book.setName(book.getName() + "[上架]");
                if (!mBooks.contains(book)) {
                    mBooks.add(book);
                }
                //打印mBooks列表，观察客户端传过来的值
                Log.d(TAG, "invoking addBooks() method , now the list is : " + mBooks.toString());
            }
        }

        @Override
        public void addBookInout(Book book) throws RemoteException {
            synchronized (this) {
                if (mBooks == null) {
                    mBooks = new ArrayList<>();
                }
                if (book == null) {
                    Log.d(TAG, "Book is null in Inout");
                    book = new Book();
                }
                //尝试修改book的参数，主要是为了观察其到客户端的反馈
                book.setName(book.getName() + "[上架]");
                if (!mBooks.contains(book)) {
                    mBooks.add(book);
                }
                //打印mBooks列表，观察客户端传过来的值
                Log.d(TAG, "invoking addBooks() method , now the list is : " + mBooks.toString());
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Book book = new Book();
        book.setName("Android开发艺术探索");
        book.setPrice(28);
        mBooks.add(book);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(getClass().getSimpleName(), String.format("on bind,intent = %s", intent.toString()));
        return mBookManager;
    }
}
