package com.example.task;

/**
 * 接口  定义运行在主线程的方法
 * @param <Result>
 */
public interface ITaskCallback<Result> {

    void onComplete(Result o);

    void onException(Throwable throwable);
}
