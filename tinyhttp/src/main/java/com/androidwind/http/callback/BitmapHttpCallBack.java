package com.androidwind.http.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.androidwind.http.HttpResponse;
import com.androidwind.http.util.EncryptUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class BitmapHttpCallBack extends BaseHttpCallBack<Bitmap> {
    String fileDir;
    CacheMode cacheMode;
    FileOutputStream fos;

    public BitmapHttpCallBack outputDir(String fileDir) {
        this.fileDir = fileDir;
        return this;
    }

    public BitmapHttpCallBack cache(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
        return this;
    }

    @Override
    public Bitmap onBackground(HttpResponse httpResponse) {
        if (cacheMode == CacheMode.DISK) {
            return cacheWithFile(httpResponse.inputStream, httpResponse.url);
        } else {
            return cacheWithMemory(httpResponse.inputStream);
        }
    }

    private Bitmap cacheWithMemory(InputStream inputStream) {
        try {
            byte[] bytesInputStream = getBytesInputStream(inputStream);
            return BitmapFactory.decodeByteArray(bytesInputStream, 0, bytesInputStream.length);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private Bitmap cacheWithFile(InputStream inputStream, String url) {
        String fileName = EncryptUtil.genMD5(url);
        File file = new File(fileDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(fileDir, "TinyHttp[image]_" + fileName);
        //判断Disk缓存
        if (file.exists()) { //已经存在
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                return BitmapFactory.decodeStream(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
        //
        try {
            fos = new FileOutputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
            }

        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getBytesInputStream(InputStream is) throws IOException {

        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[512];
        int len;
        while ((len = is.read(buff)) != -1) {

            arrayOutputStream.write(buff, 0, len);
        }

        is.close();
        arrayOutputStream.close();

        return arrayOutputStream.toByteArray();

    }
}
