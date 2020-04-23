package com.example.http.result;

//所有的IResponse 解析后的类型
public interface IResult<T> {

    boolean isSuccess();

    int getCode();

    T data();
}
