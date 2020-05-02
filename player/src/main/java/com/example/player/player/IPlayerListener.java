package com.example.player.player;

import com.example.player.state.PlayerState;

public interface IPlayerListener {

    void playerStateChanged(PlayerState state);
}
