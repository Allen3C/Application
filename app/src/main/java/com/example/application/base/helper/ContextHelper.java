package com.example.application.base.helper;

import android.app.Application;
import android.content.Context;

//全局Context获取
public class ContextHelper {

    private static ContextHelper mInstance;
    private Application mApplication;

    public static ContextHelper getInstance(){
        if(mInstance == null){
            mInstance = new ContextHelper();
        }
        return mInstance;
    }

    public void init(Application application){
        this.mApplication = application;
    }

    public Context getApplicationContext(){
        return mApplication.getApplicationContext();
    }
}
