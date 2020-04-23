package com.example.application.base;

import com.example.http.parser.DefaultResultParser;
import com.example.http.request.IRequest;
import com.example.http.annotation.RequestMethod;
import com.example.http.request.LfRequest;

import java.lang.reflect.Type;

public class JHRequest extends LfRequest {
    public static IRequest sendHttp(String path, @RequestMethod int requestMethod, Type type){
        JHRequest request = new JHRequest();
        request.host = HostManager.jhHost;
        request.path = path;
        request.requestMethod = requestMethod;
        request.type = type;
        request.resultParser = DefaultResultParser.getInstance();
        return request;
    }
}
