package com.example.mvp.mvp.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.mvp.mvp.IMvpView;
import com.example.mvp.mvp.presenter.LifeCircleMvpPresenter;

//抽象中介者  空实现ILifeCircle中的接口
//因为SplashTimerPresenter直接继承LifeCircleMvpPresenter需要实现太多方法，这些方法有的对来说是没用的
//所以要用BaseMvpPresenter来起到桥接作用
//P层中间类
public abstract class BaseMvpPresenter<T extends IMvpView> extends LifeCircleMvpPresenter<T> {
    public BaseMvpPresenter(T view){
        super(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, Intent intent, Bundle getArguments) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState, Intent intent, Bundle getArguments) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void destroyView() {

    }

    @Override
    public void onViewDestroy() {

    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {

    }
}
