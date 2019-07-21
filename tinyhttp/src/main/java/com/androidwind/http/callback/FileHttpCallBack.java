package com.androidwind.http.callback;

import com.androidwind.http.HttpResponse;
import com.androidwind.task.TinyTaskExecutor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class FileHttpCallBack extends BaseHttpCallBack<File> {

    private final String mFileDir;
    private final String mFileName;

    public FileHttpCallBack(String fileDir, String fileName) {
        mFileDir = fileDir;
        mFileName = fileName;
    }

    public abstract void onMainProgress(float progress, long total);

    @Override
    public File OnBackground(HttpResponse httpResponse) {
        InputStream is = null;
        byte[] buf = new byte[1024 * 8];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = httpResponse.inputStream;
            final long total = httpResponse.contentLength;

            long sum = 0;

            File dir = new File(mFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, mFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                TinyTaskExecutor.postToMainThread(new Runnable() {
                    @Override
                    public void run() {
                        onMainProgress(finalSum * 100.0f / total, total);
                    }
                });
            }
            fos.flush();

            return file;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                if (is != null) {
                    is.close();
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
        return null;
    }
}
