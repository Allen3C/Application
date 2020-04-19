package com.example.application.main.shanghai.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import android.view.View;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;

import com.example.application.R;
import com.example.application.base.BaseActivity;
import com.example.application.base.ViewInject;

import butterknife.BindView;

@ViewInject(mainlayoutid = R.layout.acrivity_shanghai_detail)
public class ShanghaiDetailActivity extends BaseActivity {
    public static String mActivityOptionsCompat = "ActivityOptionsCompat";
    @BindView(R.id.iv_shanghai_detail)
    ImageView ivShanghaiDetail;

    @Override
    public void afterBindView() {
        initAnima();

    }

    private void initAnima() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //setTransitionName也可以在xml里配
            //ViewCompat.setTransitionName(ivShanghaiDetail, mActivityOptionsCompat);

            //延时加载
            //postponeEnterTransition();
            //开启专场动画
            startPostponedEnterTransition();
        }
    }

    //用于Android 5.0 系统的 界面转场动画  ，  共享元素动画
    public static void start_5_0(Activity activity, View view){
        //如果Android版本大于等于 5.0
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Intent intent = new Intent(activity, ShanghaiDetailActivity.class);
            Pair pair = new Pair(view, mActivityOptionsCompat);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair);
            ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
        }
    }
}
