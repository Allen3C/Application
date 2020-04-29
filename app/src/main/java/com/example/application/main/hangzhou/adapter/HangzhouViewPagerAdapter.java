package com.example.application.main.hangzhou.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.application.main.hangzhou.view.JiKeFragment;
import com.example.application.main.hangzhou.view.RefreshFragment;
import com.example.application.main.hangzhou.view.ZhiHuFragment;

import java.util.ArrayList;

public class HangzhouViewPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<String> titleList = new ArrayList<>();

    public HangzhouViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        titleList.add("知乎");
        titleList.add("即刻");
        titleList.add("下拉刷新");
    }

    //不要一开始把Fragment都new出来
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
               return new ZhiHuFragment();
            case 1:
                return new JiKeFragment();
            case 2:
                return new RefreshFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    //设置标题栏，也就是TabLayout显示的内容
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
