package com.example.application;

import android.os.Handler;

public class CustomCountDownTimer implements Runnable {
    private int time;
    private int countDowntime;
    private final ICountDownHandler countDownHandler;
    private final Handler handler;
    private boolean isRun;
    //1.时时去回调此时是什么时间，倒计时到几秒(用观察者设计模式)
    //2.支持动态传入总时间
    //3.没过一秒，总秒数减一
    //4.总时间倒计时为0时，要回调完成的状态

    public CustomCountDownTimer(int time, ICountDownHandler countDownHandler)
    {
        handler = new Handler();
        this.time = time;
        this.countDowntime = time;
        this.countDownHandler = countDownHandler;
    }
    //实现的具体逻辑
    @Override
    public void run() {
        if(isRun) {
            if(countDownHandler != null){
                //回调
                countDownHandler.onTicker(countDowntime);
            }
            if(countDowntime == 0){
                cancel();
                if(countDownHandler != null){
                    countDownHandler.onFinish();
                }
            }else {
                countDowntime = time --;
                handler.postDelayed(this, 1000);
            }
        }
    }
    //开启倒计时
    public void start(){
        isRun = true;
        //调用run()
        handler.post(this);
    }
    //跳出循环 终止
    public void cancel(){
        isRun = false;
        handler.removeCallbacks(this);
    }
    //观察者回调接口（IOC数据回调）
    public interface ICountDownHandler
    {
        //倒计时回调
        void onTicker(int time);
        //完成时回调
        void onFinish();
    }
}