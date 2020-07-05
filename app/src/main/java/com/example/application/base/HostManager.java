package com.example.application.base;

import com.example.http.request.host.IHost;

/**
 * 获取host  和默认path
 */
public interface HostManager {
    IHost jhHost = new IHost() {

        @Override
        public String getHost() {
            return "http://v.juhe.cn";
        }

        @Override
        public String getDefaultPath() {
            return "/joke/content/list.php";
        }
    };
}
