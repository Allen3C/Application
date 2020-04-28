package com.example.application.main;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.application.R;
import com.example.application.base.BaseActivity;
import com.example.application.base.ViewInject;
import com.example.application.main.tools.MainConstantTool;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

@ViewInject(mainlayoutid = R.layout.activity_main)
public class MainActivity extends BaseActivity implements IMainActivityContract.Iview{

    IMainActivityContract.IPresenter mPresenter = new MainActivityPresenter(this);

    @BindView(R.id.fac_main_home)
    FloatingActionButton facMainHome;
    @BindView(R.id.rb_main_shanghai)
    LottieAnimationView rbMainShanghai;
    @BindView(R.id.rb_main_hangzhou)
    LottieAnimationView rbMainHangzhou;
    @BindView(R.id.rg_main_top)
    LinearLayout rgMainTop;
    @BindView(R.id.fm_main_bottom)
    FrameLayout fmMainBottom;
    @BindView(R.id.rb_main_beijing)
    RadioButton rbMainBeijing;
    @BindView(R.id.rb_main_shenzhen)
    RadioButton rbMainShenzhen;
    @BindView(R.id.rg_main_bottom)
    RadioGroup rgMainBottom;

    private boolean isChangeTopOrBottom;


    @Override
    public void afterBindView() {
        initHomeFragment();
        changeAnima(rgMainBottom, rgMainTop);
        initCheckListener();
    }

    //使用LottieAnimationView得每个控件都设置一个点击事件
    private void initCheckListener() {
        rbMainShanghai.playAnimation();
        rbMainShanghai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbMainShanghai.getId() == mPresenter.getCurrentCheckedId()) {
                    return;
                }
                mPresenter.replaceFragment(MainConstantTool.SHANGHAI);
                //根据json文件从头到尾执行动画
                rbMainShanghai.playAnimation();
                //根据json文件从尾到头执行动画（把杭州按钮恢复过来）
                rbMainHangzhou.reverseAnimation();
            }
        });

        rbMainHangzhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbMainHangzhou.getId() == mPresenter.getCurrentCheckedId()) {
                    return;
                }
                mPresenter.replaceFragment(MainConstantTool.HANGZHOU);
                rbMainHangzhou.playAnimation();
                rbMainShanghai.reverseAnimation();
            }
        });
    }


    //源码用不了，换成ButterKnife的OnCheckedChangeListener
    @OnCheckedChanged({R.id.rb_main_beijing,R.id.rb_main_shenzhen})
    public void OnCheckedChangeListener(CompoundButton view, boolean ischanged ){
        if (view.getId() == mPresenter.getCurrentCheckedId()) {
            return;
        }
        switch (view.getId()) {
            case R.id.rb_main_beijing:
                if (ischanged) {
                    mPresenter.replaceFragment(MainConstantTool.BEIJING);
                }
                break;
            case R.id.rb_main_shenzhen:
                if (ischanged) {
                    mPresenter.replaceFragment(MainConstantTool.SHENZHEN);
                }
                break;
            default:
                break;
        }
    }

    //初始化Fragment
    private void initHomeFragment() {
        mPresenter.initHomeFragment();

    }

    @OnClick(R.id.fac_main_home)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fac_main_home:
                isChangeTopOrBottom = !isChangeTopOrBottom;
                if(isChangeTopOrBottom){
                    changeAnima(rgMainTop,rgMainBottom);
                    handleTopPosition();
                }else{
                    changeAnima(rgMainBottom, rgMainTop);
                    handleBottomPosition();
                }
                break;
        }
    }

    //北京 深圳
    private void handleBottomPosition() {
        if(mPresenter.getTopPosition() != 1){
            //四个Fragment都在，只是隐藏了
            //TopPosition记录了上海杭州页面切走时候显示的是上海还是杭州，
            //切回来继续显示
            mPresenter.replaceFragment(MainConstantTool.SHANGHAI);
            rbMainShanghai.playAnimation();
            //rbMainShanghai.setChecked(true);
        }else {
            mPresenter.replaceFragment(MainConstantTool.HANGZHOU);
            rbMainHangzhou.playAnimation();
            //rbMainHangzhou.setChecked(true);
        }
    }

    //上海 杭州
    private void handleTopPosition() {
        if(mPresenter.getBottomPosition() != 3){
            mPresenter.replaceFragment(MainConstantTool.BEIJING);
            rbMainBeijing.setChecked(true);
        }else {
            mPresenter.replaceFragment(MainConstantTool.SHENZHEN);
            rbMainShenzhen.setChecked(true);
        }
    }

    private void changeAnima(ViewGroup gone, ViewGroup show) {
        //消失的动画
        gone.clearAnimation();//清除自身动画
        Animation animationGone = AnimationUtils.loadAnimation(this, R.anim.main_tab_translate_hide);
        gone.startAnimation(animationGone);
        gone.setVisibility(View.GONE);
        //展示的动画
        show.clearAnimation();
        Animation animationShow = AnimationUtils.loadAnimation(this, R.anim.main_tab_translate_show);
        show.startAnimation(animationShow);
        show.setVisibility(View.VISIBLE);

    }

    @Override
    public void showFragment(Fragment mFragment) {

        getSupportFragmentManager().beginTransaction().show(mFragment).commit();
    }

    @Override
    public void addFragment(Fragment mFragment) {
        //将Fragment添加到上边的FrameLayout中
        getSupportFragmentManager().beginTransaction().add(R.id.fl_main_content, mFragment).commit();

    }

    @Override
    public void hideFragment(Fragment mFragment) {

        getSupportFragmentManager().beginTransaction().hide(mFragment).commit();
    }
}
