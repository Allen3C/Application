package com.example.application.main.shanghai.If;

import androidx.fragment.app.Fragment;

import com.example.application.main.IMainActivityContract;
import com.example.mvp.mvp.ILifeCircle;
import com.example.mvp.mvp.IMvpView;
import com.example.mvp.mvp.MvpControler;

public interface IShanghaiDetailContract {
    interface Iview extends IMvpView {



    }
    interface IPresenter extends ILifeCircle {


        void getNetData();
    }

    IShanghaiDetailContract.Iview emptyView = new IShanghaiDetailContract.Iview() {

        @Override
        public MvpControler getMvpControler() {
            return null;
        }
    };
}
