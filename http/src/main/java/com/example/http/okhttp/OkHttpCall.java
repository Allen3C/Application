package com.example.http.okhttp;

import com.example.http.request.IRequest;
import com.example.http.request.call.ICall;
import com.example.http.response.IResponse;
import com.example.http.result.IResult;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * （封装了okhttp的代码）
 * 静态代理模式 代理类  代理OKHttp的call
 * 将okhttp的call转换为自定义call，就是包装了一下okhttp的call，说的专业点就是代理了okhttp的call
 */
public class OkHttpCall implements ICall {
    private final Call call;
    private IRequest request;

    public OkHttpCall(IRequest request, Call call) {
        this.call = call;
        this.request = request;
    }

    /**
     * 同步执行网络请求
     * @return
     */
    @Override
    public IResponse excute() {
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //静态代理
        OkHttpResponse okHttpResponse = new OkHttpResponse(response);
        return okHttpResponse;
    }

    @Override
    public IRequest getRequest() {
        return request;
    }
}
