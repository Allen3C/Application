package com.example.application.mvp.presenter;

import com.example.application.mvp.ILifeCircle;
import com.example.application.mvp.IMvpView;
import com.example.application.mvp.MvpControler;

import java.lang.ref.WeakReference;

//基类  P层
//用到泛型约束，传进来的对象必须继承IMvpView
public abstract class LifeCircleMvpPresenter<T extends IMvpView> implements ILifeCircle {

    protected WeakReference<T> weakReference;

    protected LifeCircleMvpPresenter(){
        super();
    }

    public LifeCircleMvpPresenter(IMvpView iMvpView){
        super();
        attachView(iMvpView);
        MvpControler mvpControler = iMvpView.getMvpControler();
        mvpControler.savePresenter(this);
    }

    //实现弱引用
    @Override
    public void attachView(IMvpView iMvpView) {
        if(weakReference == null){
            weakReference = new WeakReference(iMvpView);
        }else{
            T view = (T)weakReference.get();
            if(view != iMvpView){
                weakReference = new WeakReference(iMvpView);
            }
        }

    }

    @Override
    public void onDestroy() {
        weakReference = null;
    }

    protected T getView(){
        T view = weakReference != null ? (T) weakReference.get() : null;
        if(view == null){
            return getEmptyView();
        }
        return view;
    }

    protected abstract T getEmptyView();
}
