package com.example.player;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.player.player.IPlayer;
import com.example.player.player.IPlayerListener;
import com.example.player.player.PlayerFactory;
import com.example.player.source.IPlayerSource;
import com.example.player.state.PlayerState;

public class PlayerService extends Service implements IPlayerListener {

    private PlayerState mState = PlayerState.IDLE;
    private IPlayer mPlayer;
    private PlayerFactory mPlayerFactory;

    //获取 播放器状态
    @Override
    public void playerStateChanged(PlayerState state) {
        this.mState = state;
    }

    //Binder实现了IBinder接口
    public class PlayerBinder extends Binder {
        public PlayerService getService(){
            return  PlayerService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PlayerBinder();
    }

    //bindService不走，startService一次这个方法走一次（可以带一些数据,只能给Service传）
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    //bindService只走一次，startService也只走一次，常用来做全局初始化操作
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void playOrPause(IPlayerSource playerSorece, Context mContext) {
        switch (mState){
            case IDLE:
                //初始化播放器 去播放
                if(mPlayer != null){
                    mPlayer.relese();
                }
                if(mPlayerFactory == null){
                    mPlayerFactory = new PlayerFactory();
                }
                if(mPlayer == null){
                    mPlayer = mPlayerFactory.createPlayer(mContext);
                }
                if(mPlayer == null){
                    //播放器创建失败
                    return;
                }
                //先设置监听，再prepare，不然ExoPlayer的prepare里状态改变之后这边回调不了
                //设置监听
                mPlayer.setPlayingListener(this);
                //拿到播放器去播
                mPlayer.prepare(mContext, playerSorece);
                break;
            case STARTED:
                //去暂停
                if(mPlayer != null){
                    mPlayer.paused();
                }
                break;
            case PAUSED:
                //继续播放
                if(mPlayer != null){
                    mPlayer.reStart();
                }
                break;
            case PREPARING:
                break;
            default:
                break;
        }
    }
}
