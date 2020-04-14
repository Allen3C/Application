package com.example.application;

import com.example.application.mvp.ILifeCircle;
import com.example.application.mvp.IMvpView;
import com.example.application.mvp.MvpControler;

//作用是SplashActivity和SplashTimerPresenter互相拿到引用（不再是强引用，改成了弱引用）
public interface ISplashActivityContract {
    interface Iview extends IMvpView{
        void settvSplashTimer(String timer);

    }
    interface IPresenter extends ILifeCircle{
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
