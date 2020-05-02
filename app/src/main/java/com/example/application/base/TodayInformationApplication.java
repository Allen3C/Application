package com.example.application.base;

import android.app.Application;

import com.example.application.base.crash.CrashProtectManager;
import com.example.application.base.helper.ContextHelper;

//app进程什么时候销毁掉，TodayInformationApplication对象什么时候销毁掉，生命周期和app一致
public class TodayInformationApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        //开启程序死活不崩溃,自己调试开发时要关闭
//        CrashProtectManager.getInstance(this).init();
        //全局Context获取
        ContextHelper.getInstance().init(this);
    }
}
