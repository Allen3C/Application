package com.example.application;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;

@ViewInject(mainlayoutid = R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements ISplashActivityContract.Iview{
    @BindView(R.id.vv_play)
    FullScreeVideoView vvPlay;
    @BindView(R.id.tv_splash_timer)
    TextView tvSplashTimer;

    private ISplashActivityContract.IPresenter timerPresenter;

    @Override
    public void afterBindView() {

        initListener();
        initVideo();
        initTimerPresenter();
    }

    private void initTimerPresenter() {
        //持有P层强引用
        timerPresenter = new SplashTimerPresenter(this);
        timerPresenter.initTimer();
    }

    private void initVideo() {
        vvPlay.setVideoURI(Uri.parse("android.resource://" + getPackageName() + File.separator + R.raw.splash));
        vvPlay.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
    }

    private void initListener() {
        tvSplashTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                //把当前界面关闭掉
                finish();
            }
        });

        vvPlay.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
    }

    @Override
    public void settvSplashTimer(String s) {
        tvSplashTimer.setText(s);
    }
}
