package com.example.application.main.shanghai.presenter;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.application.R;
import com.example.application.base.BasePresenter;
import com.example.application.base.JHTask;
import com.example.application.base.helper.ContextHelper;
import com.example.application.main.shanghai.If.IPlayerServiceContract;
import com.example.application.main.shanghai.If.IShanghaiDetailContract;
import com.example.application.main.shanghai.dto.ShanghaiDetailBean;
import com.example.application.main.shanghai.module.ShangHaiDetailHttpTask;
import com.example.http.result.IResult;
import com.example.player.PlayerService;
import com.example.player.source.RawPlayerSource;


public class PlayerServicePresenter extends BasePresenter<IPlayerServiceContract.Iview> implements IPlayerServiceContract.IPresenter {

    private PlayerService playerService;

    private ServiceConnection mConnection = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //IOC 数据回调  和Service连接成功后调用
            PlayerService.PlayerBinder binder = (PlayerService.PlayerBinder) service;
            playerService = binder.getService();
            playOrPaused();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //IOC 数据回调  和Service连接断开后调用
            if(playerService != null){
                //关闭Service
                playerService.unbindService(mConnection);
                playerService = null;
            }
        }
    };


    public PlayerServicePresenter(IPlayerServiceContract.Iview view) {
        super(view);
    }


    @Override
    public void bindService(Context context) {
        if(playerService != null){
            playOrPaused();
        }else {
            Intent intent = new Intent(context, PlayerService.class);
            context.bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void playOrPaused() {
        if(playerService != null){
            //开启播放音乐
            playerService.playOrPause(new RawPlayerSource().setPath(ContextHelper.getInstance().getApplicationContext().getPackageName(), R.raw.minyao), ContextHelper.getInstance().getApplicationContext());
        }
    }
}
