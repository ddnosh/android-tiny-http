# android-tiny-http
[![Download](https://api.bintray.com/packages/ddnosh/maven/tinyhttp/images/download.svg) ](https://bintray.com/ddnosh/maven/tinyhttp/_latestVersion)  
a tiny http library for android.

# Solution
1. Encapsulation HttpUrlConnection;
2. One line code to make a http request;

# Function
1. support http "get" and "post" request;
2. one request with params, request type and callback;
3. support String, Bitmap, Image and File request;

# Technology
1. Desing Pattern
    1. Builder

# Usage
As String type:
``` 
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
```
