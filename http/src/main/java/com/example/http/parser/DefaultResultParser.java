package com.example.http.parser;

import com.example.http.request.IRequest;
import com.example.http.response.IResponse;
import com.example.http.result.IResult;
import com.example.http.result.Result;
import com.google.gson.Gson;

import java.lang.reflect.Type;

//单例类
public class DefaultResultParser implements IParser {

    private static DefaultResultParser mInstance;
    private final Gson mGson;

    private DefaultResultParser(){
        mGson = new Gson();

    }

    public static IParser getInstance() {
        if(mInstance == null){
            mInstance = new DefaultResultParser();
        }
        return mInstance;
    }

    //使用json解析三方库将 String字符串（json） 解析为 Type（就是定义的Bean）
    @Override
    public IResult parseResponse(IRequest iRequest, IResponse iResponse) {
        Type type = iRequest.getType();
        String bodyStr = iResponse.getBodyStr();
        Object object = mGson.fromJson(bodyStr, type);
        return Result.success(object);
    }
}
