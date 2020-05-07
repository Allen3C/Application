package com.example.ipc.result;

public class IpcResult implements IResult {

    public String data;
    public int code;
    public boolean success;

    public static IResult getErrorResult(int code){
        IpcResult result = new IpcResult();
        result.success = false;
        result.code = code;
        return result;
    }
    public static IResult getSuccessResult(String data){
        IpcResult result = new IpcResult();
        result.success = true;
        result.data = data;
        return result;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String data() {
        return data;
    }
}
