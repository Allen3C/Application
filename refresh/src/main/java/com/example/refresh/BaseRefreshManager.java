package com.example.refresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public abstract class BaseRefreshManager {
    public LayoutInflater mLayoutInflater;

    public BaseRefreshManager(Context context){
        mLayoutInflater = LayoutInflater.from(context);
    }

    public abstract View getHeaderView();

    public abstract void downRefresh();

    public abstract void releaseRefresh();

    public abstract void iddRefresh();

    public abstract void refreshing();

    public abstract void downRefreshPercent(float percent);
}
