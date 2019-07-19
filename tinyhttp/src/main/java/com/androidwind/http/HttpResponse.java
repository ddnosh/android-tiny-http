package com.androidwind.http;

import java.io.InputStream;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class HttpResponse {

    public int code;

    public String message;

    public InputStream inputStream;

    public InputStream errorStream;

    public Exception exception;
}
