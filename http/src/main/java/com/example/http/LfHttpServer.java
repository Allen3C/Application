package com.example.http;

import com.example.http.request.IRequest;
import com.example.http.result.IResult;

import java.util.Map;

//属于HttpHelper的快捷方式，重头戏在HttpHelper里
public class LfHttpServer<T> {
    protected IResult<T> execute(IRequest request, Map<String, Object> params){
        return HttpHelper.execute(request, params);

    }
}
//另一种写法
//public class LfHttpServer {
//    protected <T> IResult<T> execute(IRequest request, Map<String, Object> params){
//        return HttpHelper.execute(request, params);
//
//    }
//}
