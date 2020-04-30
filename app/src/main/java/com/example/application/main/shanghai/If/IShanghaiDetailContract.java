package com.example.application.main.shanghai.If;

import com.example.annotation.MvpEmptyViewFactory;
import com.example.application.main.shanghai.dto.ShanghaiDetailBean;
import com.example.mvp.mvp.ILifeCircle;
import com.example.mvp.mvp.IMvpView;
import com.example.mvp.mvp.MvpControler;

public interface IShanghaiDetailContract {
    @MvpEmptyViewFactory
    interface Iview extends IMvpView {


        void showData(ShanghaiDetailBean data);
    }
    interface IPresenter extends ILifeCircle {


        void getNetData(String pagesize);
    }


//    //外面增加一个方法这个就得重新生成，用APT可以解决
//    IShanghaiDetailContract.Iview emptyView = new IShanghaiDetailContract.Iview() {
//
//        @Override
//        public void showData(ShanghaiDetailBean data) {
//
//        }
//
//        @Override
//        public MvpControler getMvpControler() {
//            return null;
//        }
//    };
}
