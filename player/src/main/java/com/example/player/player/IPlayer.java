package com.example.player.player;

import android.content.Context;


//播放器接口
public interface IPlayer {

    //播放器释放
    void relese();


    void prepare(Context context, String url);

    //设置监听
    void setPlayingListener(IPlayerListener listener);

    void paused();

    void reStart();
}
