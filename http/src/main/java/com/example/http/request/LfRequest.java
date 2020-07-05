package com.example.http.request;

import com.example.http.parser.IParser;
import com.example.http.annotation.RequestMethod;
import com.example.http.request.host.IHost;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 包括 host path params  请求方法
 */
public class LfRequest implements IRequest {

    protected String path;

    protected IHost host;

    @RequestMethod
    protected int requestMethod;

    protected Map<String, Object> params;

    protected Type type;

    protected IParser resultParser;

    @Override
    public void setParams(Map<String, Object> params) {

        this.params = params;
    }

    @Override
    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public int getRequestMethod() {
        return requestMethod;
    }

    @Override
    public IHost getHost() {
        return host;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public IParser getParser() {
        return resultParser;
    }

    @Override
    public Type getType() {
        return type;
    }
}
