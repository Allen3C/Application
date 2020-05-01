package com.example.application.base;

import android.app.Application;

import com.example.application.base.crash.CrashProtectManager;

//app进程什么时候销毁掉，TodayInformationApplication对象什么时候销毁掉，生命周期和app一致
public class TodayInformationApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashProtectManager.getInstance(this).init();
    }
}
