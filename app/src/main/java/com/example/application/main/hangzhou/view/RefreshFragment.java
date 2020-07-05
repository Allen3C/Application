package com.example.application.main.hangzhou.view;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.base.BaseFragment;
import com.example.application.base.ViewInject;
import com.example.application.main.hangzhou.adapter.ZhiHuAdapter;
import com.example.application.main.hangzhou.refresh.MeiTuanRefreshManager;
import com.example.application.main.shanghai.If.IShanghaiDetailContract;
import com.example.application.main.shanghai.dto.ShanghaiDetailBean;
import com.example.application.main.shanghai.presenter.ShanghaiDetailPresenter;
import com.example.refresh.GodRefreshLayout;

import butterknife.BindView;

@ViewInject(mainlayoutid = R.layout.fragment_refresh)
public class RefreshFragment extends BaseFragment implements IShanghaiDetailContract.Iview{
    IShanghaiDetailContract.IPresenter mPresenter = new ShanghaiDetailPresenter(this);
    @BindView(R.id.god_refresh)
    GodRefreshLayout godRefresh;
    @BindView(R.id.refresh_recyclerview)
    RecyclerView refreshRecyclerview;

    @Override
    public void afterBindView() {
        initRecyclerView();
        initRefreshLayout();
    }

    private void initRefreshLayout(){
        //1.采用默认下拉效果
        //godRefresh.setRefreshManager();
        //2.用户自定义下拉效果
        godRefresh.setRefreshManager(new MeiTuanRefreshManager(mContext));
        godRefresh.setRefreshListener(new GodRefreshLayout.RefreshingListener() {
            @Override
            public void onRefreshing() {
                mPresenter.getNetData("20");
//                godRefresh.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        godRefresh.refreshOver();
//                    }
//                }, 2000);
            }
        });
    }


    private void initRecyclerView() {
        refreshRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mPresenter.getNetData("20");
    }

    @Override
    public void showData(ShanghaiDetailBean data) {
        if(refreshRecyclerview.getAdapter() == null){
            ZhiHuAdapter adapter = new ZhiHuAdapter(data.result.data);
            refreshRecyclerview.setAdapter(adapter);
        }
        //数据请求成功，隐藏下拉刷新头部
        godRefresh.refreshOver();
    }
}
