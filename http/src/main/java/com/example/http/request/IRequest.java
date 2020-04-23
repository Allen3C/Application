package com.example.http.request;

import com.example.http.parser.IParser;
import com.example.http.request.host.IHost;

import java.lang.reflect.Type;
import java.util.Map;

public interface IRequest {

    void setParams(Map<String, Object> params);

    Map<String, Object> getParams();

    int getRequestMethod();

    IHost getHost();

    String getPath();

    IParser getParser();

    Type getType();
}
