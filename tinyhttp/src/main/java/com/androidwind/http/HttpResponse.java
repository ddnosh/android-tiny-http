package com.androidwind.http;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class HttpResponse {

    public int code;

    public String message;

    public String rawData;

    public Exception exception;

    public HttpResponse() {
    }

    public HttpResponse(int code, String message, String rawData) {
        this.code = code;
        this.message = message;
        this.rawData = rawData;
    }
}
