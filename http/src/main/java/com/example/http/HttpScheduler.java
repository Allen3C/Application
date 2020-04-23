package com.example.http;

import com.example.http.parser.IParser;
import com.example.http.request.IRequest;
import com.example.http.request.call.ICall;
import com.example.http.response.IResponse;
import com.example.http.result.IResult;

public abstract class HttpScheduler {
    public abstract ICall newCall(IRequest request);

    public IResult execute(ICall call){
        //IResponse 和 IResult 进行一个转换
        IResponse iResponse = call.excute();
        IRequest request = call.getRequest();//request里有一开始发送网络请求时穿进去的要解析成的数据的类型
        IParser parser = request.getParser();
        return parser.parseResponse(request, iResponse);
    }
}
