package com.example.ipc.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.example.ipc.IClientInterface;
import com.example.ipc.IServerInterface;


public class IpcService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IServerInterface.Stub() {
            public IClientInterface mClientInterface;

            @Override
            public void excuteAsync(String requestKey, String requestParams) throws RemoteException {
                switch (requestKey){
                    case "shanghai_detail":
                        if(mClientInterface != null){
                            mClientInterface.callBack(requestKey, "来自远方的祝福");
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public String excuteSync(String requestKey, String requestParams) throws RemoteException {
                String result = "";
                switch (requestKey){
                    case "shanghai_detail":
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
