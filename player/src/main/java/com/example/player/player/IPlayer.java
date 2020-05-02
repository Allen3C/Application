package com.example.player.player;

import android.content.Context;

public interface IPlayer {

    //播放器释放
    void relese();


    void prepare(Context context, String url);

    void setPlayingListener(IPlayerListener listener);

    void paused();

    void reStart();
}
