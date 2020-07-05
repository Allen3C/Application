package com.example.http.result;

/**
 * onComplete只代表执行完  还要区分成功或失败
 * @param <T>
 */
public interface IResultCallBack<T> {

    void onSuccess(IResult<T> t);

    void onFailed(IResult t);
}
