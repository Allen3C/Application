package com.example.application.base;

import com.example.mvp.mvp.IMvpView;
import com.example.mvp.mvp.base.BaseMvpPresenter;
import com.example.task.LfTask;
import com.example.task.TaskHelper;

//桥接模块 集成mvp和网络请求的快捷方式
public abstract class BasePresenter<T extends IMvpView> extends BaseMvpPresenter<T> {

    public BasePresenter(T view) {
        super(view);
    }

    public void submitTask(LfTask task){
        TaskHelper.submitTask(task, task);

    }
}

