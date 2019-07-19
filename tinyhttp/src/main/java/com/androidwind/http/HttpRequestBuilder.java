package com.androidwind.http;

import android.text.TextUtils;

import com.androidwind.http.callback.HttpCallBack;
import com.androidwind.task.Priority;

import java.util.HashMap;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class HttpRequestBuilder {

    public HttpRequestBuilder(String tag) {
        this.tag = tag;
    }

    public String tag;
    public String url;
    public Priority priority = Priority.NORMAL;
    public HttpCallBack httpCallBack;
    public HashMap<String, String> paramMap = new HashMap<>();
    public HashMap<String, String> bodyMap = new HashMap<>();
    public HashMap<String, String> headMap = new HashMap<>();

    public HttpRequestBuilder url(String url) {
        if (TextUtils.isEmpty(url)) {
            throw new RuntimeException("http request url should not be empty!");
        }
        this.url = url;
        return this;
    }

    public HttpRequestBuilder priority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public HttpRequestBuilder callback(HttpCallBack httpCallBack) {
        this.httpCallBack = httpCallBack;
        return this;
    }

    public HttpRequestBuilder param(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            paramMap.put(key, value);
        }
        return this;
    }

    public HttpRequestBuilder body(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            bodyMap.put(key, value);
        }
        return this;
    }

    public HttpRequestBuilder head(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            headMap.put(key, value);
        }
        return this;
    }

    public void execute() {
        new HttpRequest(this).execute();
    }
}
