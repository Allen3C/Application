package com.example.ipc;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.example.ipc.request.IRequest;
import com.example.ipc.result.IResult;
import com.example.ipc.result.IpcResult;
import com.example.ipc.server.IpcService;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class IpcManager {

    private static IpcManager mInstance;
    private final Context mContext;

    //TreeSet 有序 不允许重复  用于去重（连续点击）  只针对异步请求
    private Set<IRequest> mRequests = new TreeSet<IRequest>();
    private Set<IRequest> mWaitRequests = new TreeSet<IRequest>();

    private int mConnectStatus = IConnectStatus.STATUS_UNBIND;
    private ServiceConnection mConnection;
    private IServerInterface mServer;
    private IBinder.DeathRecipient mDeathRecipient;
    private IClientInterface.Stub mClientInterface;

    interface IConnectStatus{
        int STATUS_UNBIND = 0;
        int STATUS_BINDING = 1;
        int STATUS_BIND = 2;
    }

    private IpcManager(Context context){
        this.mContext = context.getApplicationContext();
    }

    public synchronized static IpcManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new IpcManager(context);
        }
        return mInstance;
    }

    //同步跨进程通信
    public IResult excuteSync(IRequest request){
        if(TextUtils.isEmpty(request.getRequestKey()) || mRequests.contains(request)){
            return IpcResult.getErrorResult(11);
        }
        //判断服务是否已经连接成功
        if(mConnectStatus != IConnectStatus.STATUS_BIND){
            connectService();
            return IpcResult.getErrorResult(12);
        }
        return excute(request);
    }

    // TODO: 20-5-7 提供给客户端建立连接的方法 (同步请求需要）
    public void initConnect(){

    }

    //异步跨进程的功能通信
    public void excuteAsync(IRequest request, CallBack callBack){
        if(TextUtils.isEmpty(request.getRequestKey()) || mRequests.contains(request)){
            callBack.callBack(IpcResult.getErrorResult(21));
            return;
        }
        request.addCallBack(callBack);
        //保存这个Request
        mRequests.add(request);
        //判断服务是否已经连接成功
        if(mConnectStatus != IConnectStatus.STATUS_BIND){
            connectService();
            mWaitRequests.add(request);
            return;
        }
        excute(request);
    }

    //建立IPC 通信连接
    private void connectService() {
        if(mConnection == null){
            mConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    mConnectStatus = IConnectStatus.STATUS_BIND;
                    mServer = IServerInterface.Stub.asInterface(service);
                    //往服务端注入接口
                    try {
                        mServer.registerCallBack(mClientInterface);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    //Binder  通信的死亡通知  有重启逻辑
                    try {
                        service.linkToDeath(mDeathRecipient, 0);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    //连接成功之后 去把等待的数据请求 发送
                    for (IRequest request : mWaitRequests){
                        excute(request);
                    }
                    mWaitRequests.clear();
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    mConnectStatus = IConnectStatus.STATUS_UNBIND;
                }
            };
        }

        if(mDeathRecipient == null){
            mDeathRecipient = new IBinder.DeathRecipient() {
                @Override
                public void binderDied() {
                    mConnectStatus = IConnectStatus.STATUS_UNBIND;
                    //针对异步请求做CallBack处理
                    for(IRequest request : mRequests){
                        request.getCallBack().callBack(IpcResult.getErrorResult(22));
                    }
                    mRequests.clear();
                }
            };
        }
        if(mClientInterface == null){
            mClientInterface = new IClientInterface.Stub() {
                @Override
                public void callBack(String requestKey, String resulstStr) throws RemoteException {
                    Iterator<IRequest> iterator = mRequests.iterator();
                    while (iterator.hasNext()){
                        IRequest next = iterator.next();
                        if(TextUtils.equals(next.getRequestKey(), requestKey)){
                            next.getCallBack().callBack(IpcResult.getSuccessResult(resulstStr));
                            mRequests.remove(next);
                            return;
                        }
                    }
                }
            };
        }
        Intent intent = new Intent(mContext, IpcService.class);
        mContext.bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
        mConnectStatus = IConnectStatus.STATUS_BINDING;
    }

    //实际 跨进程通信方法
    private IResult excute(IRequest request) {
        if(request.getCallBack() != null){
            try {
                mServer.excuteAsync(request.getRequestKey(), request.getParams());
            } catch (RemoteException e) {
                request.getCallBack().callBack(IpcResult.getErrorResult(23));
            }
        }else {
            try {
                String result = mServer.excuteSync(request.getRequestKey(), request.getParams());
                return IpcResult.getSuccessResult(result);
            } catch (RemoteException e) {
                return IpcResult.getErrorResult(13);
            }
        }

        return IpcResult.getErrorResult(404);
    }
}
