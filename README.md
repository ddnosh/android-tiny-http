# android-tiny-task
a tiny http library for android.

# Solution
1. Encapsulation HttpUrlConnection;
2. One line code to make a http request;

# Function
1. support http "get" and "post" request;
2. one request with params, request type and callback;

# Technology
1. Desing Pattern
    1. Builder

# Usage
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
                        
# TODO
1. support file transfer;
