package com.androidwind.http.callback;

import com.androidwind.http.HttpResponse;

import java.io.InputStream;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class ImageHttpCallBack extends BaseHttpCallBack<InputStream> {
    @Override
    public InputStream onBackground(HttpResponse httpResponse) {
        return httpResponse.inputStream;
    }
}
