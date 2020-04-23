package com.example.http.okhttp;

import com.example.http.request.IRequest;
import com.example.http.request.call.ICall;
import com.example.http.response.IResponse;
import com.example.http.result.IResult;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

//静态代理模式 代理类
//将第三方库的call  转为  自定义的ICall
public class OkHttpCall implements ICall {
    private final Call call;
    private IRequest request;

    public OkHttpCall(IRequest request, Call call) {
        this.call = call;
        this.request = request;
    }

    @Override
    public IResponse excute() {
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OkHttpResponse okHttpResponse = new OkHttpResponse(response);
        return okHttpResponse;
    }

    @Override
    public IRequest getRequest() {
        return request;
    }
}
