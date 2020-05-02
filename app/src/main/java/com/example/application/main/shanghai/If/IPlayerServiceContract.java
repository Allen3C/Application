package com.example.application.main.shanghai.If;

import android.content.Context;

import com.example.annotation.MvpEmptyViewFactory;
import com.example.application.main.shanghai.dto.ShanghaiDetailBean;
import com.example.mvp.mvp.ILifeCircle;
import com.example.mvp.mvp.IMvpView;

public interface IPlayerServiceContract {
    @MvpEmptyViewFactory
    interface Iview extends IMvpView {

    }
    interface IPresenter extends ILifeCircle {
        void bindService(Context context);

        void playOrPaused();
    }
}
