package com.example.task;

//这是仿照AsyncTask源码自己构建线程池管理网络请求，不是封装AsyncTask
public class TaskHelper {
    public static void submitTask(ITaskBackground iTaskBackground, ITaskCallback iTaskCallback){
        //拿到FutureTask,仿照FutureTask源码AsyncTask.java中exec.execute(mFuture) 拿到线程池去执行FutureTask
        AsyncTaskInstance instance = AsyncTaskInstance.getInstance(iTaskBackground, iTaskCallback);
        //构建线程池管理器
        TaskScheduler.getInstance().submit(instance);

    }
}
