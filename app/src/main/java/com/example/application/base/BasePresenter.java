package com.example.application.base;


import com.example.mvp.mvp.IMvpView;
import com.example.mvp.mvp.base.BaseMvpPresenter;
import com.example.task.LfTask;
import com.example.task.TaskHelper;

import today.information.mvp.MvpEmptyViewFactory;

//桥接模块 集成mvp和网络请求的快捷方式
public class BasePresenter<T extends IMvpView> extends BaseMvpPresenter<T> {

    public BasePresenter(T view) {
        super(view);
    }

    public void submitTask(LfTask task){
        TaskHelper.submitTask(task, task);

    }

    @Override
    protected T getEmptyView() {
        T t = null;
        Class superClassGenricType = GenericsUtils.getSuperClassGenricType(this.getClass(), 0);
        try {
            t = (T) MvpEmptyViewFactory.create(superClassGenricType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}

