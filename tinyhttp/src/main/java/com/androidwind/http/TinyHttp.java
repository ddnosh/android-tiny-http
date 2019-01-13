package com.androidwind.http;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TinyHttp {

    public static HttpRequestBuilder get() {
        return new HttpRequestBuilder("GET");
    }

    public static HttpRequestBuilder post() {
        return new HttpRequestBuilder("POST");
    }

}
