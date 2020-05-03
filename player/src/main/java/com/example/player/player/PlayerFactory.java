package com.example.player.player;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.player.tool.DataSourceUtil;

public class PlayerFactory {

    //一般工厂设计模式  可以创建用户想要的播放器
    public IPlayer createPlayer(Context context) {
        //读取配置
        int playertype = DataSourceUtil.getMetaDataFromApp(context);
        switch (playertype){
            case IPlayerType.MEDIAPLAYERTYPE:
                return new GoogleMediaPlayer();
            case IPlayerType.EXOPLAYER:
                return new ExoPlayer(context);
            default:
                break;
        }
        return null;
    }
}
