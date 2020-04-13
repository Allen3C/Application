package com.example.application;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class FullScreeVideoView extends VideoView {
    //主要用于直接new出来的对象
    public FullScreeVideoView(Context context) {
        super(context);
    }
    //主要用于xml文件中，支持自定义属性
    public FullScreeVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //主要用于xml文件中，支持自定义属性，同时支持style样式（就是将xml文件中控件的属性抽到style文件中，复用方便）
    public FullScreeVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //videoview中原始onMeasure方法会为了兼容性，根据不同模式调整width和height，所有无法全屏，自定义就直接用原始width和height所以能实现全屏
    }
}
