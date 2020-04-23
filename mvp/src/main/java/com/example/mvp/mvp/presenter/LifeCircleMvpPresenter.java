package com.example.mvp.mvp.presenter;

import com.example.mvp.mvp.ILifeCircle;
import com.example.mvp.mvp.IMvpView;
import com.example.mvp.mvp.MvpControler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

//抽象中介者，保护和获取v层引用
//基类  P层
//用到泛型约束，传进来的对象必须继承IMvpView
//抽象类可以不用全部实现接口中的方法
public abstract class LifeCircleMvpPresenter<T extends IMvpView> implements ILifeCircle {

    protected WeakReference<T> weakReference;

    protected LifeCircleMvpPresenter(){
        super();
    }

    //在构造方法里进行生命周期的关联
    //T 也行
    public LifeCircleMvpPresenter( IMvpView iMvpView){
        super();
        attachView(iMvpView);
        //使代理类持有p层
        MvpControler mvpControler = iMvpView.getMvpControler();
        mvpControler.savePresenter(this);
    }

    //实现弱引用（只是实现弱引用，没有获取到v层），通过代理模式把v层和p层的生命周期相关联时调用
    @Override
    public void attachView(IMvpView iMvpView) {
        if(weakReference == null){
            weakReference = new WeakReference(iMvpView);
        }else{
            //如果存在弱引用，把弱引用的对象获取出来
            //因为protected WeakReference<T> weakReference;所以可以不用强转
            T view = (T)weakReference.get();  //T view得到的是实际类型
            //如果和当前传进来的对象不一致，就再重新创建一次，确保数据的同步
            if(view != iMvpView){
                weakReference = new WeakReference(iMvpView);
            }
        }

    }

    @Override
    public void onDestroy() {
        weakReference = null;
    }

    //获取v层
    protected T getView(){
        //因为protected WeakReference<T> weakReference;所以可以不用强转
        T view = weakReference != null ? (T) weakReference.get() : null;
        if(view == null){
            return getEmptyView();
        }
        return view;
    }

    protected abstract T getEmptyView();
}
