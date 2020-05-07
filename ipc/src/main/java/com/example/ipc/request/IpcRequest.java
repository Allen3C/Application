package com.example.ipc.request;

import androidx.annotation.NonNull;

import com.example.ipc.CallBack;

public class IpcRequest implements IRequest {

    private long mTime;
    private String mRequestKey;
    private String mParams;
    private CallBack mCallBack;

    public IpcRequest(){
        mTime = System.currentTimeMillis();
    }

    public IpcRequest(@NonNull String requestKey){
        this.mRequestKey = requestKey;

    }
    @Override
    public void setParams(@NonNull String params) {
        this.mParams = params;
    }

    @Override
    public String getParams() {
        return mParams;
    }

    @Override
    public String getRequestKey() {
        return mRequestKey;
    }

    @Override
    public void addCallBack(CallBack callBack) {
        this.mCallBack = callBack;
    }

    @Override
    public CallBack getCallBack() {
        return mCallBack;
    }

    @Override
    public long getAddTime() {
        return mTime;
    }


    @Override
    public int compareTo(@NonNull IRequest o) {

        return (int) (this.getAddTime() - o.getAddTime());
    }
}
