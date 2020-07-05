package com.example.task;

import com.example.task.tools.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * FutureTask就是一个Runnable
 * FutureTask里面运行的是Callable接口里的call方法
 * 实现的功能是将自己定义的onBackground方法运行在子线程，将onComplete和onException运行在主线程
 * 这是得到一个FutureTask，想执行异步任务还得放到显线程池执行
 * @param <Result>
 */
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
            //这一步使得onBackground返回的结果可以作为onComplete的参数
            final Object object = get();
            if(object != null){
                //回到主线程  因为这个FutureTask要放在线程池也就是子线程执行，而这两个方法要放在主线程，所以要再手动抛回到主线程
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
