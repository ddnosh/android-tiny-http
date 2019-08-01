package com.androidwind.http.sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.androidwind.http.HttpUtil;
import com.androidwind.http.callback.FileHttpCallBack;
import com.androidwind.http.TinyHttp;
import com.androidwind.http.callback.BitmapHttpCallBack;
import com.androidwind.http.callback.ImageHttpCallBack;
import com.androidwind.http.callback.StringHttpCallBack;
import com.androidwind.task.Priority;
import com.androidwind.task.Task;
import com.androidwind.task.TinyTaskExecutor;

import java.io.File;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "MainActivity";

    private TextView tvConsole;
    private ImageView ivConsole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvConsole = findViewById(R.id.tv_console);
        ivConsole = findViewById(R.id.imageView);
        Button btnGet = findViewById(R.id.btn_get);
        btnGet.setOnClickListener(this);
        Button btnPost = findViewById(R.id.btn_post);
        btnPost.setOnClickListener(this);
        Button btnPostImage1 = findViewById(R.id.btn_post_image1);
        btnPostImage1.setOnClickListener(this);
        Button btnPostImage2 = findViewById(R.id.btn_post_image2);
        btnPostImage2.setOnClickListener(this);
        Button btnPostFile = findViewById(R.id.btn_post_file);
        btnPostFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                tvConsole.setText("");
                TinyHttp.get()
                        .url("http://www.kuaidi100.com/query")
                        .priority(Priority.HIGH)
                        .param("type", "yuantong")
                        .param("postid", "803977139201993050")
                        .callback(new StringHttpCallBack() {
                            @Override
                            public void OnMainSuccess(String response) {
                                TestBean bean = toObject(response, TestBean.class);
                                tvConsole.setText(bean.toString());
                            }

                            @Override
                            public void OnMainFail(String errorMessage) {

                            }
                        })
                        .execute();
                break;
            case R.id.btn_post:
                tvConsole.setText("");
                TinyHttp.post()
                        .url("http://www.kuaidi100.com/query")
                        .callback(new StringHttpCallBack() {

                            @Override
                            public void OnMainSuccess(String response) {
                                TestBean bean = toObject(response, TestBean.class);
                                tvConsole.setText(bean.toString());
                            }

                            @Override
                            public void OnMainFail(String errorMessage) {

                            }
                        })
                        .execute();
                break;
            case R.id.btn_post_image1:
                TinyHttp.get()
                        .url("https://abc.2008php.com/2013_Website_appreciate/2013-04-11/20130411233130.jpg")
                        .callback(new BitmapHttpCallBack(HttpUtil.getLogDir(getApplicationContext())) {
                            @Override
                            public void OnMainSuccess(Bitmap bitmap) {
                                ivConsole.setImageBitmap(bitmap);
                            }

                            @Override
                            public void OnMainFail(String errorMessage) {

                            }
                        }).execute();
                break;
            case R.id.btn_post_image2:
                TinyHttp.get()
                        .url("https://www.baidu.com/img/bd_logo1.png")
                        .callback(new ImageHttpCallBack() {
                            @Override
                            public void OnMainSuccess(final InputStream inputStream) {
                                TinyTaskExecutor.execute(new Task<Bitmap>() {
                                    @Override
                                    public Bitmap doInBackground() {
                                        return BitmapFactory.decodeStream(inputStream);
                                    }

                                    @Override
                                    public void onSuccess(Bitmap bitmap) {
                                        ivConsole.setImageBitmap(bitmap);
                                    }

                                    @Override
                                    public void onFail(Throwable throwable) {

                                    }
                                });
                            }

                            @Override
                            public void OnMainFail(String errorMessage) {

                            }
                        }).execute();
                break;
            case R.id.btn_post_file:
                TinyHttp.get()
                        .url("https://ce7ce9c885b5c04b6771ea454e096946.dd.cdntips.com/imtt.dd.qq.com/16891/apk/A8E429783EA97261455968A16F0DF44C.apk")
                        .callback(new FileHttpCallBack(HttpUtil.getLogDir(getApplicationContext()), "TinyHttp[file]_" + System.currentTimeMillis()) {
                            @Override
                            public void onMainProgress(float progress, long total) {
                                Log.i(TAG, "downloaded percent = " + progress + ", total size = " + total);
                            }

                            @Override
                            public void OnMainSuccess(File file) {
                                Toast.makeText(MainActivity.this, file.getName() + "has already downloaded!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void OnMainFail(String errorMessage) {

                            }
                        }).execute();
                break;
        }
    }

    @Nullable
    public static final <T> T toObject(String json, Class<T> cls) {
        if (null == json) {
            return null;
        }

        try {
            return JSON.parseObject(json, cls);
        } catch (Exception e) {
            Log.e(TAG, "toObject: " + e.getMessage());
        }
        return null;
    }
}
