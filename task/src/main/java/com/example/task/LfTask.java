package com.example.task;

/**
 * 用于执行异步任务的接口
 * 不一定非得用于执行网络请求，就是不一定要跟okhttp库结合使用，也可以用于干别的，作用就是将任务放在子线程执行
 * @param <Result>
 */
public abstract class LfTask<Result> implements ITaskBackground<Result>, ITaskCallback<Result> {
}
