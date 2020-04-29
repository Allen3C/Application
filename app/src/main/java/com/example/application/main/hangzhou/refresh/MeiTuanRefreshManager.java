package com.example.application.main.hangzhou.refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.application.R;
import com.example.refresh.BaseRefreshManager;

//用户自定义下拉效果
public class MeiTuanRefreshManager extends BaseRefreshManager {
    private ImageView mImageView;

    public MeiTuanRefreshManager(Context context) {
        super(context);
    }

    @Override
    public View getHeaderView() {
        View view = mLayoutInflater.inflate(R.layout.meituan_header_refresh_layout, null, false);
        mImageView = view.findViewById(R.id.loading);
        return view;
    }

    //只会触发一次
    @Override
    public void downRefresh() {

    }

    //释放更新时会变成美团吉祥物,帧动画
    @Override
    public void releaseRefresh() {
        mImageView.setImageResource(R.drawable.mei_tuan_loading_pre);
        AnimationDrawable mAnimationDrawable = (AnimationDrawable)mImageView.getDrawable();
        mAnimationDrawable.start();
    }

    @Override
    public void iddRefresh() {
        mImageView.clearAnimation();
        mImageView.setImageResource(R.mipmap.pull_image);
        mImageView.setScaleX(0);
        mImageView.setScaleY(0);
    }

    //正在刷新的状态也是一个帧动画
    @Override
    public void refreshing() {
        mImageView.setImageResource(R.drawable.mei_tuan_loading);
        AnimationDrawable mAnimationDrawable = (AnimationDrawable)mImageView.getDrawable();
        mAnimationDrawable.start();
    }

    @Override
    public void downRefreshPercent(float percent) {
        Log.e("downRefreshPercent", "percent" + percent);
        mImageView.setScaleX(percent);
        mImageView.setScaleY(percent);
    }
}
