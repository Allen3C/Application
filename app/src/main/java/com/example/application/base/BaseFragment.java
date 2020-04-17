package com.example.application.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.application.mvp.view.LifeCircleMvpActivity;
import com.example.application.mvp.view.LifeCircleMvpFragment;

import butterknife.ButterKnife;

public abstract class BaseFragment extends LifeCircleMvpFragment {

    private Context mContext;

    //获取context
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    //Fragment不同与Activity，不在onCreate中初始化view，而是在onCreateView中
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = null;
        ViewInject annotation = this.getClass().getAnnotation(ViewInject.class);
        if(annotation != null){
            int mainlayoutid = annotation.mainlayoutid();
            if(mainlayoutid > 0){
                mView = initFragmentView(inflater, mainlayoutid);
                bindView(mView);
                afterBindView();
            }else{
                throw new RuntimeException("mainlayoutid < 0");
            }
        } else {
            throw new RuntimeException("annotation = null");
        }
        return mView;
    }

    //Activity封装好了setContentView，但是Fragment么有，算是半成品
    //Fragment必须自己把id生成一个view传递给onCreateView
    private View initFragmentView(LayoutInflater inflater, int mainlayoutid) {
        //null参数代表不需要父布局，就是讲当前view直接传给Fragment
        return inflater.from(mContext).inflate(mainlayoutid, null);

    }


    //一种设计模式  模板方法
    public abstract void afterBindView();

    //View的依赖注入绑定
    //跟Activity参数不同
    private void bindView(View mView) {
        ButterKnife.bind(this, mView);
    }
}
