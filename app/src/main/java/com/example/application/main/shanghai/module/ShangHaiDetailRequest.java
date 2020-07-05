package com.example.application.main.shanghai.module;

import com.example.application.base.JHRequest;
import com.example.application.main.shanghai.dto.ShanghaiDetailBean;
import com.example.http.request.IRequest;
import com.example.http.annotation.RequestMethod;

/**
 * 获得IRequest
 */
public interface ShangHaiDetailRequest {
    IRequest xiaoHuaRequest = JHRequest.sendHttp("/joke/content/list.php", RequestMethod.Get, ShanghaiDetailBean.class);
}
