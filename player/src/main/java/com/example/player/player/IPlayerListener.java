package com.example.player.player;

import com.example.player.state.PlayerState;

//用于设置和获取 播放器 状态
public interface IPlayerListener {

    void playerStateChanged(PlayerState state);
}
