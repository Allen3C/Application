package com.example.application.main.hangzhou;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.application.R;
import com.example.application.base.BaseFragment;
import com.example.application.base.ViewInject;
import com.example.application.main.hangzhou.adapter.HangzhouViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

@ViewInject(mainlayoutid = R.layout.fragment_hangzhou)
public class HangZhouFragment extends BaseFragment {
    @BindView(R.id.tl_tablayout)
    TabLayout tlTablayout;
    @BindView(R.id.vp_viewpager)
    ViewPager vpViewpager;

    @Override
    public void afterBindView() {
        //TabLayout 和 ViewPager 进行绑定
        tlTablayout.setupWithViewPager(vpViewpager);
        //不传getFragmentManager()，因为涉及到Fragment的嵌套，getChildFragmentManager()对大小Fragment的生命周期进行了绑定
        vpViewpager.setAdapter(new HangzhouViewPagerAdapter(getChildFragmentManager()));

    }
}
