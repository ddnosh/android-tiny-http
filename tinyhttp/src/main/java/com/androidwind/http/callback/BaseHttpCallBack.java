package com.androidwind.http.callback;

import com.androidwind.http.HttpResponse;
import com.androidwind.task.TinyTaskExecutor;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class BaseHttpCallBack<T> implements HttpCallBack {

    public abstract T OnBackground(HttpResponse httpResponse);

    public abstract void OnMainSuccess(T t);

    public abstract void OnMainFail(String errorMessage);

    @Override
    public void onHttpSuccess(HttpResponse httpResponse) {
        final T t = OnBackground(httpResponse);
        TinyTaskExecutor.postToMainThread(new Runnable() {
            @Override
            public void run() {
                OnMainSuccess(t);
            }
        });
    }

    @Override
    public void onHttpFail(HttpResponse httpResponse) {
        final String errorMessage = httpResponse.exception.getMessage();
        TinyTaskExecutor.postToMainThread(new Runnable() {
            @Override
            public void run() {
                OnMainFail(errorMessage);
            }
        });
    }
}
