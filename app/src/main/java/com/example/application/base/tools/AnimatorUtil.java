package com.example.application.base.tools;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

public class AnimatorUtil {

    //X轴方向的属性动画
    public static void startTranslationXAnim(View target, float positionStart, float positionEnd, Animator.AnimatorListener listener){
        ObjectAnimator titleAnim = ObjectAnimator.ofFloat(target, "translationX", positionStart, positionEnd);
        titleAnim.setDuration(1000);
        titleAnim.start();
        if(listener != null){
            titleAnim.addListener(listener);
        }
    }
}
