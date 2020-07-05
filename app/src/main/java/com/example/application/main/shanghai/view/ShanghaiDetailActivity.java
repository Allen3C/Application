package com.example.application.main.shanghai.view;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;

import com.example.application.R;
import com.example.application.base.BaseActivity;
import com.example.application.base.ViewInject;
import com.example.application.main.beijing.MainProcessService;
import com.example.application.main.beijing.ProcessDataTest;
import com.example.application.main.shanghai.If.IShanghaiDetailContract;
import com.example.application.main.shanghai.dto.ShanghaiDetailBean;
import com.example.application.main.shanghai.presenter.ShanghaiDetailPresenter;
import com.example.ipc.CallBack;
import com.example.ipc.IpcManager;
import com.example.ipc.request.IpcRequest;
import com.example.ipc.result.IResult;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * v层
 */
@ViewInject(mainlayoutid = R.layout.acrivity_shanghai_detail)
public class ShanghaiDetailActivity extends BaseActivity implements IShanghaiDetailContract.Iview {
    IShanghaiDetailContract.IPresenter mPresenter = new ShanghaiDetailPresenter(this);

    public static String mActivityOptionsCompat = "ActivityOptionsCompat";
    @BindView(R.id.iv_shanghai_detail)
    ImageView ivShanghaiDetail;

    @Override
    public void afterBindView() {
        initAnima();
        initGetNetData();
        initIpc();
    }

    private void initIpc() {
        IpcRequest request = new IpcRequest("shanghaiDetail");
        IpcManager.getInstance(this).excuteAsync(request, new CallBack() {
            @Override
            public void callBack(IResult iResult) {
                boolean success = iResult.isSuccess();
                String data = iResult.data();
                int code = iResult.getCode();
                Log.e("数据请求", success + "  " + code);
            }
        });
//        IResult result = IpcManager.getInstance(this).excuteSync(request);
//        Log.e("数据请求", result.data());
    }


    //发送网络请求数据
    private void initGetNetData() {
        mPresenter.getNetData("1");
    }

    private void initAnima() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //setTransitionName也可以在xml里配
            ViewCompat.setTransitionName(ivShanghaiDetail, mActivityOptionsCompat);

            //延时加载
            //postponeEnterTransition();
            //开启转场动画
            startPostponedEnterTransition();
        }
    }

    //用于Android 5.0 系统的 界面转场动画  ，  共享元素动画
    public static void start_5_0(Activity activity, View view) {
        //如果Android版本大于等于 5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(activity, ShanghaiDetailActivity.class);
            Pair pair = new Pair(view, mActivityOptionsCompat);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair);
            ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
        }
    }

    @Override
    public void showData(ShanghaiDetailBean data) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
