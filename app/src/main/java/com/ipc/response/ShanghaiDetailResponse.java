package com.ipc.response;

import android.os.RemoteException;

import com.example.ipc.IClientInterface;
import com.example.ipc.response.BaseResponse;

public class ShanghaiDetailResponse extends BaseResponse {
    public ShanghaiDetailResponse(String requestKey, String params, IClientInterface mClientInterface) {
        super(requestKey, params, mClientInterface);
    }

    public void shanghaiDetail(){
        try {
            mClientInterface.callBack(mRequestKey, "来自远方的祝福");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
