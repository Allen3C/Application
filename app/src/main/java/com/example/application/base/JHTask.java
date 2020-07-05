package com.example.application.base;

import com.example.http.result.IResult;
import com.example.http.result.IResultCallBack;
import com.example.http.result.Result;
import com.example.task.LfTask;


//Task Module 的作用是用来运行子线程以及线程管理的
//JHTask 作用是借助泛型来衔接 Task Module 和Http Module的
public abstract class JHTask<T> extends LfTask<IResult<T>> implements IResultCallBack<T> {


    /**
     * 运行在主线程
     * @param iResult
     */
    @Override
    public void onComplete(IResult<T> iResult) {

        if(iResult != null){
            if(iResult.isSuccess()){
                onSuccess(iResult);
            }else {
                onFailed(Result.failed(Result.CODE_505));
            }
        }else {
            onFailed(Result.failed(Result.CODE_404));
        }
    }

    /**
     * 运行在主线程
     * @param t
     */
    @Override
    public void onFailed(IResult t) {
        switch (t.getCode()){
            //可以做成统一错误码处理
            case Result.CODE_404:
                break;
            case Result.CODE_504:
                break;
            case Result.CODE_505:
                break;
        }
    }

    @Override
    public void onException(Throwable throwable) {
        onFailed(Result.failed(Result.CODE_504));
    }
}
