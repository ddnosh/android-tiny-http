package com.androidwind.http.callback;

import com.androidwind.http.HttpResponse;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface HttpCallBack {

    void onHttpSuccess(HttpResponse httpResponse);

    void onHttpFail(HttpResponse httpResponse);
}
