package com.example.application.splash;

import com.example.annotation.MvpEmptyViewFactory;
import com.example.mvp.mvp.ILifeCircle;
import com.example.mvp.mvp.IMvpView;
import com.example.mvp.mvp.MvpControler;

//作用是SplashActivity和SplashTimerPresenter互相拿到引用（不再是强引用，改成了弱引用）
//为了扩展性更好，以后每一对v和p都会有一个contract类，
// 因为每个的功能（如 void settvSplashTimer(String timer);）不同，不能把所有的功能都写到IMvpView中
public interface ISplashActivityContract {
    @MvpEmptyViewFactory
    interface Iview extends IMvpView{
        //在SplashActivity里具体实现，供SplashTimerPresenter调用
        void settvSplashTimer(String timer);

    }
    interface IPresenter extends ILifeCircle{
        //在SplashTimerPresenter里实现，供SplashActivity调用
        //这就实现了SplashTimerPresenter和SplashActivity交互，互相调用方法
        void initTimer();

    }

    Iview emptyView = new Iview() {
        @Override
        public void settvSplashTimer(String timer) {

        }

        @Override
        public MvpControler getMvpControler() {
            return null;
        }
    };
}
