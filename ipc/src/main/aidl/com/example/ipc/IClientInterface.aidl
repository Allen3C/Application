// IClientInterface.aidl
package com.example.ipc;

// Declare any non-default types here with import statements

interface IClientInterface {
    void callBack(String requestKey, String resulstStr);
//    void callBacks(String requestKey, Bundle bundle);
}
