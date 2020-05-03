package com.example.player.source;


//播放源接口
public interface IPlayerSource {

    void setUrl(String url);

    String getUrl();

    int getResId();
}
