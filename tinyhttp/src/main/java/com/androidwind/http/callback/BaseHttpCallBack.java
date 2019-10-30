package com.androidwind.http.callback;

import com.androidwind.http.HttpResponse;
import com.androidwind.task.TinyTaskExecutor;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class BaseHttpCallBack<T> implements HttpCallBack {

    public abstract T onBackground(HttpResponse httpResponse);

    public abstract void onMainSuccess(T t);

    public abstract void onMainFail(String errorMessage);

    @Override
    public void onHttpSuccess(HttpResponse httpResponse) {
        final T t = onBackground(httpResponse);
        TinyTaskExecutor.postToMainThread(new Runnable() {
            @Override
            public void run() {
                onMainSuccess(t);
            }
        });
    }

    @Override
    public void onHttpFail(HttpResponse httpResponse) {
        final String errorMessage = httpResponse.exception.getMessage();
        TinyTaskExecutor.postToMainThread(new Runnable() {
            @Override
            public void run() {
                onMainFail(errorMessage);
            }
        });
    }
}
