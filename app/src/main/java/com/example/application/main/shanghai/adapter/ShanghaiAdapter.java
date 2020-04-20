package com.example.application.main.shanghai.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.main.shanghai.dto.ShanghaiBean;
import com.example.application.main.shanghai.view.ShanghaiDetailActivity;

import java.util.ArrayList;

public class ShanghaiAdapter extends RecyclerView.Adapter {

    private final ArrayList<ShanghaiBean> mdata;
    private final Activity mContext;
    private final RecyclerView.RecycledViewPool recycledViewPool;

    public ShanghaiAdapter(Activity context, ArrayList<ShanghaiBean> data){
        recycledViewPool = new RecyclerView.RecycledViewPool();
        mdata = data;
        mContext = context;
    }

    //创建view 然后进行缓存（缓存是因为屏幕滚动，一个条目的view会进行复用，节省内存）
    //执行几次，然后onBindViewHolder()去缓存取view，onCreateViewHolder()就不再执行了
    //点击事件要写在这里
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ShanghaiBean.IShanghaiItemType.VERTICAL){
            //把RecyclerView子项最外层布局生成view
            //LinearLayout时需要传入parent
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shanghai_fragment, parent, false);
            //获得子项的控件并进行缓存
            ShanghaiViewHolder viewHolder = new ShanghaiViewHolder(inflate);
//            viewHolder.mTv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = (int)v.getTag();
//                    Toast.makeText(mContext, "我是子条目中的文字  +  position = " + position, Toast.LENGTH_LONG ).show();
//                }
//            });
            return viewHolder;
        } else if(viewType == ShanghaiBean.IShanghaiItemType.HORIZONTAL){
            //把RecyclerView子项最外层布局生成view
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shanghai_fragment_rv, null);
            //获得子项的控件并进行缓存
            ShanghaiViewHolderRv viewHolder = new ShanghaiViewHolderRv(inflate);
            return viewHolder;
        }
        return null;

    }

    //绑定数据
    //有多少数据执行多少次
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ShanghaiBean shanghaiBean = mdata.get(position);
        if(holder instanceof ShanghaiViewHolder){
            //可以让ShanghaiAdapter传入泛型<ShanghaiAdapter.ShanghaiViewHolder>就不用强转了
            ((ShanghaiViewHolder)holder).mTv.setText(shanghaiBean.getmDec());
            ((ShanghaiViewHolder)holder).mTv.setTag(position);
            ((ShanghaiViewHolder)holder).mIv.setVisibility(shanghaiBean.isShowImg()?View.VISIBLE:View.GONE);

            //相当于设置标记，因为onCreateViewHolder()里拿不到position
            ((ShanghaiViewHolder)holder).mIv.setTag(position);
        }else if(holder instanceof ShanghaiViewHolderRv){

            //是shanghaiBean.getData()，不是ShanghaiDataManager.getData()
            ((ShanghaiViewHolderRv)holder).mRecyclerView.setAdapter(new ShanghaiAdapter(mContext, shanghaiBean.getData()));
        }
    }


    //条目的数量
    @Override
    public int getItemCount() {
        return mdata.size();
    }

    //制作复杂条目时，必须重写这个回调方法
    @Override
    public int getItemViewType(int position) {
        //拿到条目类型
        return mdata.get(position).getmItemType();
    }

    //缓存view 内存友好设计
    public class ShanghaiViewHolder extends RecyclerView.ViewHolder{


        public TextView mTv;
        public ImageView mIv;

        public ShanghaiViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.item_shanghai_tv);
            mIv = itemView.findViewById(R.id.item_shanghai_iv);
            //转场动画应该给图片设置点击事件
            this.mIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int position = (int)v.getTag();
//                    Toast.makeText(mContext, "我是子条目  +  position = " + position, Toast.LENGTH_LONG ).show();
                    ShanghaiDetailActivity.start_5_0(mContext, mIv);
                }
            });
        }
    }
    public class ShanghaiViewHolderRv extends RecyclerView.ViewHolder{


        public RecyclerView mRecyclerView;

        public ShanghaiViewHolderRv(@NonNull View itemView) {
            super(itemView);
            mRecyclerView = itemView.findViewById(R.id.item_shanghai_rv);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            linearLayoutManager.setRecycleChildrenOnDetach(true);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setRecycledViewPool(recycledViewPool);
        }
    }
}
