package com.example.application.main.hangzhou.jike;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.application.R;
import com.example.application.main.tools.SystemUtil;

public class LikeClickView extends View {
    private boolean isLike;
    private Bitmap likeBitmap;
    private Bitmap unLikeBitmap;
    private Bitmap shiningBitmap;
    private Paint bitmPaint;
    private int left;
    private int top;
    private float handScale = 1.0f;
    private float centerX;
    private float centerY;

    public LikeClickView(Context context) {
        this(context, null, 0);
        init();
    }

    public LikeClickView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
        init();
    }

    public LikeClickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //自定义属性的获取，写法固定
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JiKeLikeView);
        isLike = typedArray.getBoolean(R.styleable.JiKeLikeView_is_like,false);
        typedArray.recycle();
        init();
    }

    private void init() {
        Resources resources = getResources();
        //将图片转为Bitmap供drawBitmap()调用
        likeBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_message_like);
        unLikeBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_message_unlike);
        shiningBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_message_like_shining);
        //Paint在Android中是画笔的意思
        bitmPaint = new Paint();
    }

    //控件大小会根据用户传进来的大小和测量模式做计算（也可以不根据用户输入，直接写死）
    //还需要定义个最小宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = 0;
        int measureHeight = 0;
        //最大高度是图片高度 加上 上下空白的共20dp
        int maxHeight = likeBitmap.getHeight() + SystemUtil.dp2px(getContext(), 20);
        int maxWidth = likeBitmap.getWidth() + SystemUtil.dp2px(getContext(), 30);
        //拿到当前控件的测量模式
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        //拿到当前控件的测量宽度和高度(用户传进来的:android:layout_width="wrap_content"）
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //相当于用户没有具体指定自定义View的大小的模式（见onMeasure()测量模式笔记）
        if(mode != MeasureSpec.EXACTLY){
            //测量模式未指定控件大小 如果有背景，背景有多大，我们自定义的控件就有多大
            //拿到背景的高度和宽度
            int suggestedMinimumWidth = getSuggestedMinimumWidth();
            int suggestedMinimumHeight = getSuggestedMinimumHeight();
            if(suggestedMinimumWidth == 0){
                measureWidth = maxWidth;
            }else {
                measureWidth = Math.min(suggestedMinimumWidth, maxWidth);
            }
            if(suggestedMinimumHeight == 0){
                measureHeight = maxHeight;
            }else {
                measureHeight = Math.min(suggestedMinimumHeight, maxHeight);
            }
        }else {
            //测量模式指定大小时 根据用户定义的大小来判断
            measureWidth = Math.min(widthSize, maxWidth);
            measureHeight = Math.min(heightSize, maxHeight);
        }
        setMeasuredDimension(measureWidth, measureHeight);
        setPading(measureWidth, measureHeight);
    }

    private void setPading(int measureWidth, int measureHeight) {
        //拿到图片的宽高
        int bitmapWidth = likeBitmap.getWidth();
        int bitmapHeight = likeBitmap.getHeight();
        //measureWidth是拿到当前控件的宽高
        left = (measureWidth - bitmapWidth)/2;
        top = (measureHeight - bitmapHeight)/2;
        //下面动画缩放canvas.scale要用
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        centerX = width/2;
        centerY = height/2;
    }

    //Canvas在Android里是画布的意思
    //onDraw()方法会多次调用，不要在里面有大量计算
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap handBitmap = isLike ? likeBitmap:unLikeBitmap;
        //使用 canvas.scale()及其他效果方法时，必须先调用 canvas.save()  然后再调用canvas.restore() 这两个方法成对出现
        canvas.save();
        //自定义View 动画 的缩放   参数是比例和中心点
        canvas.scale(handScale, handScale, centerX, centerY);
        canvas.drawBitmap(handBitmap, left, top, bitmPaint);
        canvas.restore();
        if(isLike){
            canvas.drawBitmap(shiningBitmap, left + 10, 0, bitmPaint);
        }
    }

    //当自定义View从界面脱离消失的时候调用,可以在里面进行资源回收：Bitmap用完最好recycle()一下
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        likeBitmap.recycle();
        unLikeBitmap.recycle();
        shiningBitmap.recycle();
    }

    //这个方法能获取用户的点击事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                onClick();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void onClick() {
        isLike = !isLike;
        //设置属性动画
        ObjectAnimator handScale = ObjectAnimator.ofFloat(this, "handScale", 1.0f, 0.8f, 1.0f);
        handScale.setDuration(250);
        handScale.start();

//        //用ValueAnimator写法
//        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, 0.8f, 1.0f);
//        valueAnimator.setDuration(250);
//        valueAnimator.start();
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float animatedValue = (float) animation.getAnimatedValue();
//                handScale = animatedValue;
//                invalidate();
//            }
//        });
    }
    //"handScale"属性随便起的，但是使用ObjectAnimator，该属性必须有set方法  系统会自动调用该set方法
    public void setHandScale(float value){
        this.handScale = value;
        //这个方法会去调onDraw()方法
        invalidate();
    }
}

