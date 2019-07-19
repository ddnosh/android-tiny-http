package com.androidwind.http;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TinyHttp {

    public static String HTTP_REQUEST_TYPE_GET = "GET";
    public static String HTTP_REQUEST_TYPE_POST = "POST";

    public static HttpRequestBuilder get() {
        return new HttpRequestBuilder(HTTP_REQUEST_TYPE_GET);
    }

    public static HttpRequestBuilder post() {
        return new HttpRequestBuilder(HTTP_REQUEST_TYPE_POST);
    }

}
