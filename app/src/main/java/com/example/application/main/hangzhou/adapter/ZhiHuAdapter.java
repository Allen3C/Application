package com.example.application.main.hangzhou.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.main.shanghai.dto.ShanghaiBean;
import com.example.application.main.shanghai.dto.ShanghaiDetailBean;
import com.example.application.main.shanghai.view.ShanghaiDetailActivity;

import java.util.ArrayList;

public class ZhiHuAdapter extends RecyclerView.Adapter {


    private final ArrayList<ShanghaiDetailBean.XiaoHuaBean> mData;

    public ZhiHuAdapter(ArrayList<ShanghaiDetailBean.XiaoHuaBean> data) {

        mData = data;
    }

    //创建view 然后进行缓存（缓存是因为屏幕滚动，一个条目的view会进行复用，节省内存）
    //执行几次，然后onBindViewHolder()去缓存取view，onCreateViewHolder()就不再执行了
    //点击事件要写在这里
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //子条目布局item_shanghai_fragment通用
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shanghai_fragment, parent, false);
        ShanghaiViewHolder viewHolder = new ShanghaiViewHolder(inflate);
        return viewHolder;

    }

    //绑定数据
    //有多少数据执行多少次
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ShanghaiDetailBean.XiaoHuaBean xiaoHuaBean = mData.get(position);
        //可以让ShanghaiAdapter传入泛型<ShanghaiAdapter.ShanghaiViewHolder>就不用强转了
        ((ShanghaiViewHolder) holder).mTv.setText(xiaoHuaBean.content);
        ((ShanghaiViewHolder) holder).mTv.setTag(position);
        ((ShanghaiViewHolder) holder).mIv.setVisibility(View.GONE);
    }


    //条目的数量
    @Override
    public int getItemCount() {
        return mData.size();
    }


    //缓存view 内存友好设计
    public class ShanghaiViewHolder extends RecyclerView.ViewHolder {


        public TextView mTv;
        public ImageView mIv;

        public ShanghaiViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.item_shanghai_tv);
            mIv = itemView.findViewById(R.id.item_shanghai_iv);
            //转场动画应该给图片设置点击事件

        }
    }
}
