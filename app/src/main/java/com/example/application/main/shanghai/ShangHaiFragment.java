package com.example.application.main.shanghai;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.base.tools.AnimatorUtil;
import com.example.application.base.BaseFragment;
import com.example.application.base.ViewInject;
import com.example.application.base.tools.DoubleClickListener;
import com.example.application.main.shanghai.adapter.ShanghaiAdapter2;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import butterknife.BindView;

@ViewInject(mainlayoutid = R.layout.fragment_shanghai)
public class ShangHaiFragment extends BaseFragment {


    @BindView(R.id.tv_shanghai_welcome)
    TextView tvShanghaiWelcome;
    @BindView(R.id.shanghai_collapsingtoolbarlayout)
    CollapsingToolbarLayout shanghaiCollapsingtoolbarlayout;
    @BindView(R.id.shanghai_app_barlayout)
    AppBarLayout shanghaiAppBarlayout;
    @BindView(R.id.shanghai_recyclerview)
    RecyclerView shanghaiRecyclerview;
    @BindView(R.id.tv_marquee_title)
    TextView tvMarqueeTitle;
    private boolean mIsPlaying;

    @Override
    public void afterBindView() {
        initRecyclerView();
        initListener();
    }

    private void initRecyclerView() {
        //参数mContext是父类BaseFragment里的，拿过来直接用
        //还有两个参数：默认线性，倒置
        shanghaiRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));

        //getActivity()返回一个和此fragment绑定的FragmentActivity或者其子类的实例
//        shanghaiRecyclerview.setAdapter(new ShanghaiAdapter(getActivity(), ShanghaiDataManager.getData()));
        shanghaiRecyclerview.setAdapter(new ShanghaiAdapter2());
    }

    private void initListener() {
        shanghaiAppBarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            //两个参数：AppBarLayout对象本身， 滑动的距离
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //Log.e("shanghaiAppBarlayout", "verticalOffset = " + verticalOffset + "appBarLayout = " + appBarLayout.getMeasuredHeight());
                if (-verticalOffset < appBarLayout.getMeasuredHeight() / 2) {
                    tvShanghaiWelcome.setVisibility(View.INVISIBLE);
                    tvMarqueeTitle.setVisibility(View.INVISIBLE);
                } else {
                    tvShanghaiWelcome.setVisibility(View.VISIBLE);
                    if(mIsPlaying){
                        tvMarqueeTitle.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        tvShanghaiWelcome.setOnClickListener(new DoubleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvMarqueeTitle.clearAnimation();
                tvShanghaiWelcome.clearAnimation();
                if(mIsPlaying){
                    //关闭音视频动画
                    AnimatorUtil.startTranslationXAnim(tvShanghaiWelcome, tvShanghaiWelcome.getTranslationX(), tvShanghaiWelcome.getTranslationX() + 150, null);
                    tvMarqueeTitle.setVisibility(View.GONE);
                    AnimatorUtil.startTranslationXAnim(tvMarqueeTitle, tvMarqueeTitle.getTranslationX(), tvMarqueeTitle.getTranslationX() + 150, null);
                }else {
                    //播放音视频动画
                    AnimatorUtil.startTranslationXAnim(tvShanghaiWelcome, tvShanghaiWelcome.getTranslationX(), tvShanghaiWelcome.getTranslationX() - 150, null);
//                    //不用AnimatorListenerAdapter()也行
//                    AnimatorUtil.startTranslationXAnim(tvMarqueeTitle, tvMarqueeTitle.getTranslationX(), tvMarqueeTitle.getTranslationX() - 150, null);
//                    tvMarqueeTitle.setVisibility(View.VISIBLE);
                    AnimatorUtil.startTranslationXAnim(tvMarqueeTitle, tvMarqueeTitle.getTranslationX(), tvMarqueeTitle.getTranslationX() - 150, new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            tvMarqueeTitle.setVisibility(View.VISIBLE);
                        }
                    });
                }
                mIsPlaying = !mIsPlaying;
            }
        }));
    }
}
