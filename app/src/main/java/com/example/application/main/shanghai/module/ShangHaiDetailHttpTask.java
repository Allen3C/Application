package com.example.application.main.shanghai.module;

import com.example.http.LfHttpServer;
import com.example.http.result.IResult;

import java.util.HashMap;
import java.util.Map;

/**
 * m层 用于请求网络数据
 * 负责封装参数 网络请求
 * @param <T>
 */
public class ShangHaiDetailHttpTask<T> extends LfHttpServer {
    public IResult<T> getXiaoHuaList(String sort, String page, String pagesize){
        Map<String, Object> params = new HashMap<>();
        params.put("sort", sort);
        params.put("page", page);
        params.put("pagesize", pagesize);
        params.put("time", "" + System.currentTimeMillis()/1000);
        params.put("key", "c3445bee630adef5e31bf1a0231b2efe");
        return super.execute(ShangHaiDetailRequest.xiaoHuaRequest,params);
    }
}
