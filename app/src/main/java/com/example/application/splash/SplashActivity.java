package com.example.application.splash;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.example.application.base.BaseActivity;
import com.example.application.main.MainActivity;
import com.example.application.R;
import com.example.application.base.ViewInject;

import java.io.File;

import butterknife.BindView;

@ViewInject(mainlayoutid = R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements ISplashActivityContract.Iview {
    @BindView(R.id.vv_play)
    FullScreeVideoView vvPlay;
    @BindView(R.id.tv_splash_timer)
    TextView tvSplashTimer;

    private ISplashActivityContract.IPresenter timerPresenter;

    //模板方法设计模式
    @Override
    public void afterBindView() {

        //大函数拆分，把不同功能的代码抽离到不同的函数中
        initListener();
        initVideo();
        initTimerPresenter();
    }

    private void initTimerPresenter() {
        //会执行SplashTimerPresenter的构造方法，进而继承LifeCircleMvpPresenter的构造方法来实现生命周期的关联
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
