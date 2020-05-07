// IServerInterface.aidl
package com.example.ipc;

import com.example.ipc.IClientInterface;

// Declare any non-default types here with import statements

interface IServerInterface {

    void excuteAsync(String requestKey, String requestParams);

    String excuteSync(String requestKey, String requestParams);

    void registerCallBack(IClientInterface iClientInterface);
}
