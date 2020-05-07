package com.example.ipc.result;

public interface IResult {

    boolean isSuccess();

    int getCode();

    String data();
}
