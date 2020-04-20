package com.example.application.main.shanghai;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.base.BaseFragment;
import com.example.application.base.ViewInject;
import com.example.application.main.shanghai.adapter.ShanghaiAdapter;
import com.example.application.main.shanghai.dto.ShanghaiDataManager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

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
        shanghaiRecyclerview.setAdapter(new ShanghaiAdapter(getActivity(), ShanghaiDataManager.getData()));
    }

    private void initListener() {
        shanghaiAppBarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            //两个参数：AppBarLayout对象本身， 滑动的距离
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.e("shanghaiAppBarlayout", "verticalOffset = " + verticalOffset + "appBarLayout = " + appBarLayout.getMeasuredHeight());
                if (-verticalOffset < appBarLayout.getMeasuredHeight() / 2) {
                    tvShanghaiWelcome.setVisibility(View.INVISIBLE);
                } else {
                    tvShanghaiWelcome.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
