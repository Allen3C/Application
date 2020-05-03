package com.example.player.player;

import android.content.Context;

import com.example.player.source.IPlayerSource;


//播放器接口
public interface IPlayer {

    //播放器释放
    void relese();


    void prepare(Context context, IPlayerSource iPlayerSource);

    //设置监听
    void setPlayingListener(IPlayerListener listener);

    void paused();

    void reStart();
}
