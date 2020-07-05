package com.example.http.okhttp;

import com.example.http.response.IResponse;

import java.io.IOException;

import okhttp3.Response;

//静态代理，将三方库okhttp返回的Response 转换为 我们定义的IResponse类型
public class OkHttpResponse implements IResponse {

    private final Response response;

    public OkHttpResponse(Response response) {
        this.response = response;

    }

    @Override
    public String getBodyStr() {
        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
