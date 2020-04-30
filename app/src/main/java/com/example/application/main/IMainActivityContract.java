package com.example.application.main;

import androidx.fragment.app.Fragment;

import com.example.annotation.MvpEmptyViewFactory;
import com.example.mvp.mvp.ILifeCircle;
import com.example.mvp.mvp.IMvpView;
import com.example.mvp.mvp.MvpControler;


public interface IMainActivityContract {
    @MvpEmptyViewFactory
    interface Iview extends IMvpView{


        void showFragment(Fragment mFragment);

        void addFragment(Fragment mFragment);

        void hideFragment(Fragment mFragment);
    }
    interface IPresenter extends ILifeCircle{


        void initHomeFragment();

        int getCurrentCheckedIndex();

        void replaceFragment(int mCurrentFragmentIndex);

        int getTopPosition();

        int getBottomPosition();

        int getCurrentCheckedId();
    }

//    Iview emptyView = new Iview() {
//
//
//        @Override
//        public void showFragment(Fragment mFragment) {
//
//        }
//
//        @Override
//        public void addFragment(Fragment mFragment) {
//
//        }
//
//        @Override
//        public void hideFragment(Fragment mFragment) {
//
//        }
//
//        @Override
//        public MvpControler getMvpControler() {
//            return null;
//        }
//    };
}
