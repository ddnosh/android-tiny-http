package com.androidwind.http.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.androidwind.http.HttpCallBack;
import com.androidwind.http.TinyHttp;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "MainActivity";

    private TextView tvConsole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvConsole = findViewById(R.id.tv_console);
        Button btnGet = findViewById(R.id.btn_get);
        btnGet.setOnClickListener(this);
        Button btnPost = findViewById(R.id.btn_post);
        btnPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                tvConsole.setText("");
                TinyHttp.get()
                        .url("http://www.kuaidi100.com/query")
                        .param("type", "yuantong")
                        .param("postid", "803977139201993050")
                        .callback(new HttpCallBack<String>() {
                            @Override
                            public void onSuccess(String response) {
                                TestBean bean = toObject(response, TestBean.class);
                                tvConsole.setText(bean.toString());
                            }

                            @Override
                            public void onFail(Exception e) {

                            }
                        })
                        .execute();
                break;
            case R.id.btn_post:
                tvConsole.setText("");
                TinyHttp.post()
                        .url("http://www.kuaidi100.com/query")
                        .callback(new HttpCallBack<String>() {
                            @Override
                            public void onSuccess(String response) {
                                TestBean bean = toObject(response, TestBean.class);
                                tvConsole.setText(bean.toString());
                            }

                            @Override
                            public void onFail(Exception e) {

                            }
                        })
                        .execute();
                break;
        }
    }

    @Nullable
    public static final <T> T toObject(String json, Class<T> cls) {
        if ( null == json ){
            return null;
        }

        try{
            return JSON.parseObject(json, cls);
        }
        catch (Exception e){
            Log.e(TAG, "toObject: " + e.getMessage());
        }
        return null;
    }
}
