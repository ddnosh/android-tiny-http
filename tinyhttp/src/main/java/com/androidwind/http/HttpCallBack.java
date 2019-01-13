package com.androidwind.http;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface HttpCallBack<T> {

    void onSuccess(T t);

    void onFail(Exception e);
}
