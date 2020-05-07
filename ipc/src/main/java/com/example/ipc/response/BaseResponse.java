package com.example.ipc.response;

import com.example.ipc.IClientInterface;

public class BaseResponse {
    public final String mRequestKey;
    public final String mParams;
    public final IClientInterface mClientInterface;

    public BaseResponse(String requestKey, String params, IClientInterface mClientInterface){
        this.mRequestKey = requestKey;
        this.mParams = params;
        this.mClientInterface = mClientInterface;
    }
}
