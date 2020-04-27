package com.example.application.main.hangzhou.design;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

public class BottomShowBehavior extends CoordinatorLayout.Behavior<TextView> {
    //用到xml文件中必须复写有参构造
    public BottomShowBehavior(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    //这个方法的回调时机：即将发生嵌套滚动
    //axes用于判断滑动的方向  设置竖向滑动时返回true  返回true时下面方法才会调用
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull TextView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    //发生嵌套滚动的时候回调
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull TextView child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        //y轴消费的数值加上没被消费的数值大于0，说明(recyclerview)向下滚动了
        if(dyConsumed + dyUnconsumed > 0){
            //隐藏child
            if(child.getVisibility() == View.VISIBLE){
                BottomAnim.hide(child);
            }
        //向上滑动
        }else {
            //展示child
            if(child.getVisibility() != View.VISIBLE){
                BottomAnim.show(child);
            }
        }
    }
}
