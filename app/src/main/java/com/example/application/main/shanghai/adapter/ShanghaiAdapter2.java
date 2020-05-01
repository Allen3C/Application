package com.example.application.main.shanghai.adapter;

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
import com.example.application.main.shanghai.view.ShanghaiDetailActivity;

import java.util.ArrayList;

public class ShanghaiAdapter2 extends RecyclerView.Adapter {

    public ShanghaiAdapter2() {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shanghai_cardview, parent, false);
        ShanghaiViewHolder viewHolder = new ShanghaiViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }


    //条目的数量
    @Override
    public int getItemCount() {
        return 15;
    }

    public class ShanghaiViewHolder extends RecyclerView.ViewHolder {
        public ShanghaiViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
