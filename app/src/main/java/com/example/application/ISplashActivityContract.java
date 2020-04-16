package com.example.application;

import com.example.application.mvp.ILifeCircle;
import com.example.application.mvp.IMvpView;
import com.example.application.mvp.MvpControler;

//作用是SplashActivity和SplashTimerPresenter互相拿到引用（不再是强引用，改成了弱引用）
//为了扩展性更好，以后每一对v和p都会有一个contract类，
// 因为每个的功能（如 void settvSplashTimer(String timer);）不同，不能把所有的功能都写到IMvpView中
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
