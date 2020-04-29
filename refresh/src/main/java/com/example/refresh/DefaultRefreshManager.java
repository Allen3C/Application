package com.example.refresh;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class DefaultRefreshManager extends BaseRefreshManager {
    private TextView mTvRefresh;

    public DefaultRefreshManager(Context context) {
        super(context);
    }

    @Override
    public View getHeaderView() {
        //把ulti_header_layout这个布局转成view
        View view = mLayoutInflater.inflate(R.layout.ulti_header_layout, null, false);
        mTvRefresh = view.findViewById(R.id.header_text);
        return view;
    }

    @Override
    public void downRefresh() {
        mTvRefresh.setText("下拉刷新");
    }

    @Override
    public void releaseRefresh() {
        mTvRefresh.setText("释放更新");
    }

    @Override
    public void iddRefresh() {
        mTvRefresh.setText("下拉刷新");
    }

    @Override
    public void refreshing() {
        mTvRefresh.setText("刷新中...");
    }

    @Override
    public void downRefreshPercent(float percent) {

    }
}
