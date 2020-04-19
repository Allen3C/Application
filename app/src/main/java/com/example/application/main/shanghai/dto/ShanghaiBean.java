package com.example.application.main.shanghai.dto;

import java.util.ArrayList;

public class ShanghaiBean {
    private int mItemType = IShanghaiItemType.VERTICAL;
    private String mDec;
    private boolean isShowImg;
    //data是用于横向列表的
    //横向的RecyclerView是最外层纵向RecyclerView的一行条目，横向条目里面的条目跟外层纵向RecyclerView的纵向条目一样
    private ArrayList<ShanghaiBean> data;

    public int getmItemType() {
        return mItemType;
    }

    public ShanghaiBean setmItemType(int mItemType) {
        this.mItemType = mItemType;
        return this;
    }

    public String getmDec() {
        return mDec;
    }

    public ShanghaiBean setmDec(String mDec) {
        this.mDec = mDec;
        return this;
    }

    public boolean isShowImg() {
        return isShowImg;
    }

    //之所以返回this，是因为可以连续set:shanghaiBean.setShowImg(false).setmDec("上海欢迎您");
    public ShanghaiBean setShowImg(boolean showImg) {
        isShowImg = showImg;
        return this;
    }

    public ArrayList<ShanghaiBean> getData() {
        return data;
    }

    public ShanghaiBean setData(ArrayList<ShanghaiBean> data) {
        this.data = data;
        return this;
    }

    public interface IShanghaiItemType{
        int VERTICAL = 0;
        int HORIZONTAL = 1;
    }
}
