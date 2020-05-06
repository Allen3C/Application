package com.example.application.main.beijing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.Button;

import com.example.application.R;
import com.example.application.base.BaseFragment;
import com.example.application.base.ViewInject;
import com.example.application.main.shanghai.view.ShanghaiDetailActivity;

import butterknife.BindView;

@ViewInject(mainlayoutid = R.layout.fragment_beijing)
public class BeiJingFragment extends BaseFragment {
    @BindView(R.id.bt_play)
    Button btPlay;
//    private ProcessDataReceiver processDataReceiver;

    @Override
    public void afterBindView() {
        mContext.startService(new Intent(mContext, MainProcessService.class));
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProcessDataTest.getInstance().setProcessDec("你好，我来自北京");
                //去上海 跳到子进程Activity
                ShanghaiDetailActivity.start_5_0(getActivity(), btPlay);
            }
        });
//        processDataReceiver = new ProcessDataReceiver();
//        //注册广播 接受信息
//        getActivity().registerReceiver(processDataReceiver, new IntentFilter("shanghai_get_process_data"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.stopService(new Intent(mContext, MainProcessService.class));
        //广播注册之后就得反注册，防止内存泄漏
//        getActivity().unregisterReceiver(processDataReceiver);
    }

//    private class ProcessDataReceiver  extends BroadcastReceiver{
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            //接受信息
//            String processDec = ProcessDataTest.getInstance().getProcessDec();
//            Intent postIntent = new Intent("beijing_post_process_data");
//            postIntent.putExtra("processDec", processDec);
//            getActivity().sendBroadcast(postIntent);
//        }
//    }
}
