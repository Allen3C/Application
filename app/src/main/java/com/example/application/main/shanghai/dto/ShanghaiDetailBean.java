package com.example.application.main.shanghai.dto;

import java.util.ArrayList;

//对照聚合数据《笑话大全》json返回示例定义变量
//json中 大括号表示对象，在这里需要定义一个类  中括号表示数组
public class ShanghaiDetailBean {
    public int error_code;
    public String reason;
    public XiaoHuaListBean result;

    public static class XiaoHuaListBean{
        public ArrayList<XiaoHuaBean> data;
    }

    public static class XiaoHuaBean{
        public String content;
        public String hashId;
        public String unixtime;
        public String updatetime;
    }
}
