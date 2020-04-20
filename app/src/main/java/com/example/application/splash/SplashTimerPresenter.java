package com.example.application.splash;

import android.util.Log;

import com.example.mvp.mvp.base.BaseMvpPresenter;


class SplashTimerPresenter extends BaseMvpPresenter<ISplashActivityContract.Iview> implements ISplashActivityContract.IPresenter{

    private CustomCountDownTimer timer;

    //可以传入IMvpView,ISplashActivityContract.Iview为了扩展性更好
    public SplashTimerPresenter(ISplashActivityContract.Iview view) {
        super(view);
    }


    //构造方法传入SplashActivity是因为本P层需要获得SplashActivity这个V层的控件


    public void initTimer() {
        timer = new CustomCountDownTimer(5, new CustomCountDownTimer.ICountDownHandler() {
            @Override
            public void onTicker(int time) {
                //因为这是控件操作，要放到V层，也就是放到SplashActivity中，所以在SplashActivity中创建settvSplashTimer()方法
                getView().settvSplashTimer(time + "秒");
            }

            @Override
            public void onFinish() {
                getView().settvSplashTimer("跳过");
            }
        });
        timer.start();
    }

    public void cancel() {
        timer.cancel();
    }




    //通过静态代理将v层和p层的生命周期相关联，当SplashActivity生命周期走到onDestroy，
    // 就会自动调SplashTimerPresenter的onDestroy，进而调用cancel()，所以不再需要在
    //SplashActivity的onDestroy中手动调用cancel()
    @Override
    public void onDestroy() {
        super.onDestroy();
        cancel();
        //打印了日志，说明跟随了SplashActivity的生命周期
        Log.e("SplashTimerPresenter", "onDestroy");
    }

    //防止空指针异常
    @Override
    protected ISplashActivityContract.Iview getEmptyView() {
        return ISplashActivityContract.emptyView;
    }
}
