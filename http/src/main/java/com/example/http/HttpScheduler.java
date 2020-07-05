package com.example.http;

import com.example.http.parser.IParser;
import com.example.http.request.IRequest;
import com.example.http.request.call.ICall;
import com.example.http.response.IResponse;
import com.example.http.result.IResult;

/**
 * 构建自定义的call
 * 并用自定义的call请求网络，然后解析成自定义的IResult
 */
public abstract class HttpScheduler {
    public abstract ICall newCall(IRequest request);

    /**
     * 执行网络请求 并返回自定义的IResult
     * @param call
     * @return
     */
    public IResult execute(ICall call){
        //请求网络返回结果 结果类型是IResponse
        IResponse iResponse = call.excute();
        //request里有一开始发送网络请求时传进去的要解析成的数据的类型
        IRequest request = call.getRequest();
        IParser parser = request.getParser();
        //将IResponse转换为IResult 并返回
        return parser.parseResponse(request, iResponse);
    }
}
