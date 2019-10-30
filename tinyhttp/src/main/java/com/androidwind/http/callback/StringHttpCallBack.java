package com.androidwind.http.callback;

import com.androidwind.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class StringHttpCallBack extends BaseHttpCallBack<String> {
    @Override
    public String onBackground(HttpResponse httpResponse) {
        try {
            StringBuilder respRawDataBuild = new StringBuilder();
            BufferedReader reader;
            try {
                reader = new BufferedReader(new InputStreamReader(httpResponse.inputStream));
            } catch (Exception ex) {
                reader = new BufferedReader(new InputStreamReader(httpResponse.errorStream));
            }
            String tmpStr;
            while ((tmpStr = reader.readLine()) != null) {
                respRawDataBuild.append(tmpStr);
            }
            return respRawDataBuild.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
