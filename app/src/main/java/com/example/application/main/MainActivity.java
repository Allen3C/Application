package com.example.application.main;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.application.R;
import com.example.application.base.BaseActivity;
import com.example.application.base.ViewInject;
import com.example.application.main.tools.MainConstantTool;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

@ViewInject(mainlayoutid = R.layout.activity_main)
public class MainActivity extends BaseActivity implements IMainActivityContract.Iview{

    IMainActivityContract.IPresenter mPresenter = new MainActivityPresenter(this);

    @BindView(R.id.fac_main_home)
    FloatingActionButton facMainHome;
    @BindView(R.id.rb_main_shanghai)
    RadioButton rbMainShanghai;
    @BindView(R.id.rb_main_hangzhou)
    RadioButton rbMainHangzhou;
    @BindView(R.id.rg_main_top)
    RadioGroup rgMainTop;
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

    }


    //源码用不了，换成ButterKnife的OnCheckedChangeListener
    @OnCheckedChanged({R.id.rb_main_shanghai,R.id.rb_main_hangzhou,R.id.rb_main_beijing,R.id.rb_main_shenzhen})
    public void OnCheckedChangeListener(CompoundButton view, boolean ischanged ){
        if (view.getId() == mPresenter.getCurrentCheckedId()) {
            return;
        }
        switch (view.getId()) {
            case R.id.rb_main_shanghai:
                if (ischanged) {
                    mPresenter.replaceFragment(MainConstantTool.SHANGHAI);
                }
                break;
            case R.id.rb_main_hangzhou:
                if (ischanged) {
                    mPresenter.replaceFragment(MainConstantTool.HANGZHOU);
                }
                break;
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
            rbMainShanghai.setChecked(true);
        }else {
            mPresenter.replaceFragment(MainConstantTool.HANGZHOU);
            rbMainHangzhou.setChecked(true);
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

    private void changeAnima(RadioGroup gone, RadioGroup show) {
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
