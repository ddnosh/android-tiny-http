package com.androidwind.http;

import android.text.TextUtils;

import com.androidwind.task.SimpleTask;
import com.androidwind.task.Task;
import com.androidwind.task.TinyTaskExecutor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class HttpRequest {

    private HttpRequestBuilder builder;

    public HttpRequest(HttpRequestBuilder httpRequestBuilder) {
        this.builder = httpRequestBuilder;
    }

    public void execute() {
        preCheck();

        TinyTaskExecutor.execute(new SimpleTask(builder.priority) {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    conn = getHttpURLConnection();
                    //check bodytype only when the request tag is post
                    if (TinyHttp.HTTP_REQUEST_TYPE_POST.equals(builder.tag)) {
                        conn.setRequestProperty("Content-Type", getBodyType());
                    }
                    //check head
                    if (builder.headMap != null) {
                        getHeader(conn, builder.headMap);
                    }
                    conn.connect();
                    //check body only when the request tag is post
                    if (TinyHttp.HTTP_REQUEST_TYPE_POST.equals(builder.tag)) {
                        String body = getBody(builder.bodyMap);
                        if (!TextUtils.isEmpty(body)) {
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                            writer.write(body);
                            writer.close();
                        }
                    }
                    if (builder.httpCallBack != null) {
                        builder.httpCallBack.onHttpSuccess(getResponse(conn));
                    }
                } catch (Exception e) {
                    if (builder.httpCallBack != null) {
                        builder.httpCallBack.onHttpFail(getResponseWithException(conn, e));
                    }
                }
            }
        });
    }

    /**
     * check before execute
     */
    private void preCheck() {
        if (TextUtils.isEmpty(builder.url)) {
            throw new RuntimeException("http request url should not be empty!");
        }
    }

    private HttpURLConnection getHttpURLConnection() throws IOException {
        URL url = new URL(getUrl());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(builder.tag);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        return conn;
    }

    /**
     * [get method] final url with original url and request params
     *
     * @return
     */
    private String getUrl() {
        if (builder.paramMap != null && builder.paramMap.size() > 0) {
            builder.url = builder.url + "?";
            for (String key : builder.paramMap.keySet()) {
                builder.url = builder.url + key + "=" + builder.paramMap.get(key) + "&";
            }
            builder.url = builder.url.substring(0, builder.url.length() - 1);
        }
        return builder.url;
    }

    /**
     * get head map
     *
     * @param conn
     * @param headerMap
     */
    private void getHeader(HttpURLConnection conn, Map<String, String> headerMap) {
        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                conn.setRequestProperty(key, headerMap.get(key));
            }
        }
    }

    /**
     * [post method] bodyType
     */
    private String getBodyType() {
        return "application/json;charset=utf-8";
    }

    /**
     * [post method] form body map
     */
    private String getBody(Map<String, String> params) {//throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    result.append("&");
                }
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return result.toString();
        } catch (UnsupportedEncodingException e) {
            return null;
        }

    }

    /**
     * get response data
     *
     * @param conn
     * @return
     */
    private HttpResponse getResponse(HttpURLConnection conn) {
        //获取响应
        try {
            HttpResponse httpResponse = new HttpResponse();
            httpResponse.code = conn.getResponseCode();
            httpResponse.message = conn.getResponseMessage();
            httpResponse.contentLength = conn.getContentLength();
            httpResponse.inputStream = conn.getInputStream();
            httpResponse.errorStream = conn.getErrorStream();

            if (httpResponse.code == HttpURLConnection.HTTP_OK) {
                return httpResponse;
            }
        } catch (IOException e) {
            return getResponseWithException(conn, e);
        }
        return null;
    }

    private HttpResponse getResponseWithException(HttpURLConnection conn, Exception e) {
        if (conn != null) {
            conn.disconnect();
        }
        e.printStackTrace();
        HttpResponse response = new HttpResponse();
        response.exception = e;
        return response;
    }
}
