package com.androidwind.http.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.androidwind.http.HttpResponse;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class ImageHttpCallBack extends BaseHttpCallBack<Bitmap> {
    @Override
    public Bitmap OnBackground(HttpResponse httpResponse) {
        return BitmapFactory.decodeStream(httpResponse.inputStream);
    }
}
