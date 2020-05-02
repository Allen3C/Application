package com.example.player.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.example.player.state.PlayerState;

import java.io.IOException;

//MediaPlayer播放器
public class GoogleMediaPlayer implements IPlayer, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnInfoListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnBufferingUpdateListener {

    private MediaPlayer mMediaPlayer;
    private IPlayerListener mPlayerListener;

    public GoogleMediaPlayer(){
        //创建一个MediaPlayer
        mMediaPlayer = new MediaPlayer();
        //知道有这些监听，用到去百度
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnInfoListener(this);
        mMediaPlayer.setOnVideoSizeChangedListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
    }

    @Override
    public void relese() {

    }

    @Override
    public void paused() {
        if(mMediaPlayer != null){
            mMediaPlayer.pause();
            setPlayerState(PlayerState.PAUSED);
        }
    }

    @Override
    public void reStart() {
        if(mMediaPlayer != null){
            mMediaPlayer.start();
            setPlayerState(PlayerState.STARTED);
        }
    }

    @Override
    public void prepare(Context context, String url) {
        //操作MediaPlayer
        //MediaPlayer 播放系统资源时只能用 uri的方式
        try {
            mMediaPlayer.setDataSource(context, Uri.parse(url));
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    //播放器准备好后的回调
    @Override
    public void onPrepared(MediaPlayer mp) {
        //初始化成功 且 播放器准备好后回调
        mp.start();
        setPlayerState(PlayerState.STARTED);
    }

    //播放器如果遇到错误会回调到Java层
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e("GoogleMediaPlayer", "what = " + what + " extra = " + extra);
        return false;
    }

    //播放完成后的监听
    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.stop();
        mp.release();
        setPlayerState(PlayerState.IDLE);
    }

    //播放卡顿的一些信息
    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    //横竖屏切换 视频大小改变回调
    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }

    //播放器的缓冲监听用与一些UI展示 缓冲进度条
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }
}
