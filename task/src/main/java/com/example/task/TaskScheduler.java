package com.example.task;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.task.tools.ThreadUtil;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 线程池管理类
 */
public class TaskScheduler {
    private final PriorityThreadPoolExecutor executor;

    interface ITaskSchedulerType{
        int SUBMIT_TASK = 0;
    }
    private static TaskScheduler instance;
    private final Handler handler;
    private int COREPOOLSIZE = ThreadUtil.CPU_NUM + 1;
    private int MAXIMUMPOOLSIZE = COREPOOLSIZE * 3;
    private int KEEPALIVETIME = 1; //1分钟


    private TaskScheduler(){
        //用于消息调度的线程
        HandlerThread handlerThread = new HandlerThread("task-thread");
        handlerThread.start();
        this.handler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            //handlerThread handleMessage 运行在子线程
            //线程里的Looper，不传handlerThread.getLooper() 就运行在主线程  这样才能实现主线程给子线程发消息
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case ITaskSchedulerType.SUBMIT_TASK:
                        //向线程池提交任务
                        doSubmitTask((AsyncTaskInstance) msg.obj);
                        break;
                }
                return false;
            }
        });
        //创建一个线程池
        BlockingQueue<Runnable> poolQueue = new LinkedBlockingQueue<>();//无大小限制的队列  FutureTask就是一个Runnable
        //参照ForkJoinPool，根据cpu创建线程池
        this.executor = new PriorityThreadPoolExecutor(
                COREPOOLSIZE,
                MAXIMUMPOOLSIZE,
                KEEPALIVETIME,
                TimeUnit.MINUTES, //分钟
                poolQueue,
                new TaskThreadFactory());
    }


    /**
     * 向线程池提交任务
     * @param taskInstance
     */
    private void doSubmitTask(AsyncTaskInstance taskInstance) {
        executor.submit(taskInstance);
    }

    public static TaskScheduler getInstance(){
        if(instance == null){
            instance = new TaskScheduler();
        }
        return instance;
    }

    public void submit(AsyncTaskInstance instance) {
        //主线程给子线程发消息  发送的消息是instance，也就是外面传进来的FutureTask
        handler.sendMessage(handler.obtainMessage(ITaskSchedulerType.SUBMIT_TASK, instance));

    }
}
