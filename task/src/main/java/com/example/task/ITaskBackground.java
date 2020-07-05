package com.example.task;

/**
 * 接口  定义运行在子线程的方法
 * @param <Result>
 */
public interface ITaskBackground<Result> {
    /**
     * 运行在子线程的方法
     * @return
     */
    Result onBackground();


}
