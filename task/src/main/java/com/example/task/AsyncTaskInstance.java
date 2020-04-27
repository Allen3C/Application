package com.example.task;

import com.example.task.tools.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

//FutureTask就是一个Runnable
public class AsyncTaskInstance<Result> extends FutureTask<Result> {
    private final ITaskBackground iTaskBackground;
    private final ITaskCallback iTaskCallback;

    //不在构造方法里传Callable，在super()里new。只向外暴露ITaskBackground iTaskBackground, ITaskCallback iTaskCallback两个参数
    public AsyncTaskInstance(final ITaskBackground<Result> iTaskBackground, ITaskCallback<Result> iTaskCallback) {
        super(new Callable<Result>() {
            //运行在子线程
            @Override
            public Result call() throws Exception {
                return iTaskBackground.onBackground();
            }
        });
        this.iTaskBackground = iTaskBackground;
        this.iTaskCallback = iTaskCallback;
    }

    //FutureTask执行完会走到这个方法
    @Override
    protected void done() {
        if(iTaskCallback != null){
            onComplete();
        }
    }

    //获取 FutureTask 执行过程中的异常
    @Override
    protected void setException(final Throwable t) {
        super.setException(t);
        if(iTaskCallback != null){
            ThreadUtil.postMainThread(new Runnable() {
                @Override
                public void run() {
                    iTaskCallback.onException(t);
                }
            });
        }
    }

    private void onComplete() {
        try {
            final Object object = get();
            if(object != null){
                ThreadUtil.postMainThread(new Runnable() {
                    @Override
                    public void run() {
                        //一定要回调到主线程
                        iTaskCallback.onComplete(object);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AsyncTaskInstance getInstance(ITaskBackground iTaskBackground, ITaskCallback iTaskCallback){
        return new AsyncTaskInstance(iTaskBackground, iTaskCallback);
    }
}
