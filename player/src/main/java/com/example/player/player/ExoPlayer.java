package com.example.player.player;

import android.content.Context;
import android.net.Uri;

import com.example.player.source.IPlayerSource;
import com.example.player.state.PlayerState;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.util.Util;

public class ExoPlayer implements IPlayer{
    private final SimpleExoPlayer mExoPlayer;
    private IPlayerListener mPlayerListener;


    public ExoPlayer(Context context){
        //创建播放器
        mExoPlayer = new SimpleExoPlayer.Builder(context).build();
    }

    @Override
    public void relese() {

    }

    @Override
    public void prepare(Context context, IPlayerSource iPlayerSource) {
        //准备资源 去播放

        //网上解决方案
        DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(iPlayerSource.getResId()));
        RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(context);
        try {
            rawResourceDataSource.open(dataSpec);
        } catch (RawResourceDataSource.RawResourceDataSourceException e) {
            e.printStackTrace();
        }
        Uri uri = rawResourceDataSource.getUri();
        //github教程
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, ""));
        MediaSource videoSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(uri);
        mExoPlayer.prepare(videoSource);
        mExoPlayer.setPlayWhenReady(true);
        setPlayerState(PlayerState.STARTED);
    }

    //设置监听
    @Override
    public void setPlayingListener(IPlayerListener listener) {
        this.mPlayerListener = listener;
    }
    private void setPlayerState(PlayerState state) {
        if(mPlayerListener != null){
            //把播放器状态传进去
            mPlayerListener.playerStateChanged(state);
        }
    }

    @Override
    public void paused() {
        mExoPlayer.setPlayWhenReady(false);
        setPlayerState(PlayerState.PAUSED);
    }

    @Override
    public void reStart() {
        mExoPlayer.setPlayWhenReady(true);
        setPlayerState(PlayerState.STARTED);
    }


}
