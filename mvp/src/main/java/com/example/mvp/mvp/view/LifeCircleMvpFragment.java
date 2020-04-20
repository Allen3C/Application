package com.example.mvp.mvp.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mvp.mvp.IMvpView;
import com.example.mvp.mvp.MvpControler;

//抽象同事  维护p层的生命周期
//MVP底层框架V层的基类，跟LifeCircleMvpPresenter相对应
//代理模式的目标对象是LifeCircleMvpActivity,不直接与LifeCircleMvpPresenter接触，而是通过MvpControler代理
public class LifeCircleMvpFragment extends Fragment implements IMvpView {
    private MvpControler mvpControler;

    //LifeCircleMvpActivity对应一个MvpControler，MvpControler会持有p层（代理p层）
    @Override
    public MvpControler getMvpControler() {
        if(this.mvpControler == null){
            this.mvpControler = new MvpControler();
        }
        return this.mvpControler;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle == null){
            bundle = new Bundle();
        }
        MvpControler mvpControler = this.getMvpControler();
        if(mvpControler != null){
            mvpControler.onCreate(savedInstanceState, null, bundle);
            mvpControler.onActivityCreated(savedInstanceState, null, bundle);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle == null){
            bundle = new Bundle();
        }
        MvpControler mvpControler = this.getMvpControler();
        if(mvpControler != null){
            mvpControler.onActivityCreated(savedInstanceState, null, bundle);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MvpControler mvpControler = this.getMvpControler();
        if(mvpControler != null){
            mvpControler.onViewDestroy();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        MvpControler mvpControler = this.getMvpControler();
        if(mvpControler != null){
            mvpControler.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MvpControler mvpControler = this.getMvpControler();
        if(mvpControler != null){
            mvpControler.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MvpControler mvpControler = this.getMvpControler();
        if(mvpControler != null){
            mvpControler.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        MvpControler mvpControler = this.getMvpControler();
        if(mvpControler != null){
            mvpControler.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MvpControler mvpControler = this.getMvpControler();
        if(mvpControler != null){
            mvpControler.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MvpControler mvpControler = this.getMvpControler();
        if(mvpControler != null){
            mvpControler.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MvpControler mvpControler = this.getMvpControler();
        if(mvpControler != null){
            mvpControler.onActivityResult(requestCode, resultCode, data);
        }
    }
}

