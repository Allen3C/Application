package com.example.application.base;

import com.example.http.parser.DefaultResultParser;
import com.example.http.request.IRequest;
import com.example.http.annotation.RequestMethod;
import com.example.http.request.LfRequest;

import java.lang.reflect.Type;

/**
 * 构建一个聚合网络的Request
 */
public class JHRequest extends LfRequest {
    public static IRequest sendHttp(String path, @RequestMethod int requestMethod, Type type){
        JHRequest request = new JHRequest();
        request.host = HostManager.jhHost;
        request.path = path;
        request.requestMethod = requestMethod;
        //最终要解析成的JavaBean
        request.type = type;
        //解析器
        request.resultParser = DefaultResultParser.getInstance();
        return request;
    }
}
