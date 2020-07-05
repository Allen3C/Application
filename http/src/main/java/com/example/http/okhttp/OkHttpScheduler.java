package com.example.http.okhttp;

import com.example.http.HttpScheduler;
import com.example.http.annotation.RequestMethod;
import com.example.http.request.IRequest;
import com.example.http.request.call.ICall;

import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * （封装了okhttp的代码）
 * 构建自定义的call
 * 封装思想是：把自定义的request转换为okhttp的request，然后用okhttp的代码client.newcall(request)构建出
 * okhttp的call，再通过OkHttpCall类将okhttp的call转换为自定义的call
 */
public class OkHttpScheduler extends HttpScheduler {
    private OkHttpClient client;

    @Override
    public ICall newCall(IRequest request) {
        Map<String, Object> params = request.getParams();
        int requestMethod = request.getRequestMethod();
        Request.Builder requestBuilder = new Request.Builder();
        switch (requestMethod){
            case RequestMethod.Get:
                //拼接Get请求的URL     host + path
                StringBuilder urlStrBuilder = new StringBuilder(request.getHost().getHost());
                urlStrBuilder.append(request.getPath());
                HttpUrl.Builder urlBuilder = HttpUrl.parse(urlStrBuilder.toString()).newBuilder();
                if(params != null && params.size() > 0){
                    Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
                    while(iterator.hasNext()){
                        Map.Entry<String, Object> next = iterator.next();
                        // TODO: 20-4-21  Object 转 String
                        urlBuilder.addQueryParameter(next.getKey(), String.valueOf(next.getValue()));
                    }
                }
                requestBuilder.get().url(urlBuilder.build());
                break;
            case RequestMethod.Post:
                // TODO: 20-4-21 框架完成再写
                break;
        }
        Request okHttpRequest = requestBuilder.build();
        Call call = getClient().newCall(okHttpRequest);
        //这个call是OKHttp的call，函数需要返回的是自定义的ICall
        OkHttpCall okHttpCall = new OkHttpCall(request, call);
        return okHttpCall;
    }

    private OkHttpClient getClient() {
        if(client == null){
            client = new OkHttpClient();
        }
        return client;
    }
}
