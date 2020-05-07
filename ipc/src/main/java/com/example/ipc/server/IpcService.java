package com.example.ipc.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.example.ipc.IClientInterface;
import com.example.ipc.IServerInterface;
import com.example.ipc.response.ResponseUtil;


public class IpcService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IServerInterface.Stub() {
            public IClientInterface mClientInterface;

            @Override
            public void excuteAsync(String requestKey, String requestParams) throws RemoteException {
                ResponseUtil.getAsyncResponse(requestKey, requestParams, mClientInterface);
//                switch (requestKey){
//                    case "shanghaiDetail":
//                        if(mClientInterface != null){
//                            mClientInterface.callBack(requestKey, "来自远方的祝福");
//                        }
//                        break;
//                    default:
//                        break;
//                }
            }

            // TODO: 20-5-7 反射处理同步方式 待完善
            @Override
            public String excuteSync(String requestKey, String requestParams) throws RemoteException {
                String result = "";
                switch (requestKey){
                    case "shanghaiDetail":
                        result = "来自远方的祝福";
                        break;
                    default:
                        break;
                }
                return result;
            }

            @Override
            public void registerCallBack(IClientInterface iClientInterface) throws RemoteException {
                this.mClientInterface = iClientInterface;
            }
        };
    }
}
