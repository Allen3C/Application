package com.example.application.main;

import androidx.fragment.app.Fragment;

import com.example.application.R;
import com.example.application.main.beijing.BeiJingFragment;
import com.example.application.main.hangzhou.HangZhouFragment;
import com.example.application.main.shanghai.ShangHaiFragment;
import com.example.application.main.shenzhen.ShenZhenFragment;
import com.example.application.mvp.base.BaseMvpPresenter;

public class MainActivityPresenter extends BaseMvpPresenter<IMainActivityContract.Iview> implements IMainActivityContract.IPresenter {
    //当前Fragment的 角标
    private int mCurrentFragmentIndex;
    //用数组存放Fragment实例
    //因为这四个Fragment要复用的，不用销毁，所以可以保存实例
    private Fragment[] mFragments = new Fragment[4];
    private int mCurrentCheckedId;
    private int mTopPosition;
    private int mBottomPosition;

    public MainActivityPresenter(IMainActivityContract.Iview view) {
        super(view);
    }

    @Override
    protected IMainActivityContract.Iview getEmptyView() {
        return IMainActivityContract.emptyView;
    }

    @Override
    public void initHomeFragment() {

        mCurrentFragmentIndex = 0;
        replaceFragment(mCurrentFragmentIndex);
    }

    @Override
    public int getCurrentCheckedIndex() {
        return mCurrentFragmentIndex;
    }

    //切换Fragment的方法
    public void replaceFragment(int mCurrentFragmentIndex) {
        for(int i = 0; i < mFragments.length; i++){
            if(mCurrentFragmentIndex != i){
                if(mFragments[i] != null){
                    hideFragment(mFragments[i]);
                }
            }
        }

        Fragment mFragment = mFragments[mCurrentFragmentIndex];
        if(mFragment != null){
            addAndShowFrament(mFragment);
            setCurChecked(mCurrentFragmentIndex);
        }else{
            newCurrentFragment(mCurrentFragmentIndex);
            setCurChecked(mCurrentFragmentIndex);
        }
    }

    @Override
    public int getTopPosition() {
        return mTopPosition;
    }

    @Override
    public int getBottomPosition() {
        return mBottomPosition;
    }

    @Override
    public int getCurrentCheckedId() {
        return mCurrentCheckedId;
    }

    //记录当前角标
    private void setCurChecked(int mCurrentFragmentIndex) {
        this.mCurrentFragmentIndex = mCurrentFragmentIndex;
        switch (mCurrentFragmentIndex){
            case 0:
                mCurrentCheckedId = R.id.rb_main_shanghai;
                mTopPosition = 0;
                break;
            case 1:
                mCurrentCheckedId = R.id.rb_main_hangzhou;
                mTopPosition = 1;
                break;
            case 2:
                mCurrentCheckedId = R.id.rb_main_beijing;
                mBottomPosition = 2;
                break;
            case 3:
                mCurrentCheckedId = R.id.rb_main_shenzhen;
                mBottomPosition = 3;
                break;
        }

    }

    //创建当前Fragment
    private void newCurrentFragment(int mCurrentFragmentIndex) {

        Fragment fragment = null;
        switch (mCurrentFragmentIndex){
            case 0:
                fragment = new ShangHaiFragment();
                break;
            case 1:
                fragment = new HangZhouFragment();
                break;
            case 2:
                fragment = new BeiJingFragment();
                break;
            case 3:
                fragment = new ShenZhenFragment();
                break;

        }
        mFragments[mCurrentFragmentIndex] = fragment;
        addAndShowFrament(fragment);
    }

    //显示Fragment
    private void addAndShowFrament(Fragment mFragment) {
        if(mFragment.isAdded()){
            //在view层操作
            getView().showFragment(mFragment);
        }else {
            //add()方法也能显示，不能重复添加
            getView().addFragment(mFragment);
        }

    }

    //隐藏当前不要展示的Fragment
    private void hideFragment(Fragment mFragment) {
        if(mFragment != null && mFragment.isVisible()){
            getView().hideFragment(mFragment);
        }

    }
}
