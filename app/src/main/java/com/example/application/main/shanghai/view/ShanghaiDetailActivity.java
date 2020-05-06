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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import butterknife.BindView;
import butterknife.ButterKnife;


@ViewInject(mainlayoutid = R.layout.acrivity_shanghai_detail)
public class ShanghaiDetailActivity extends BaseActivity implements IShanghaiDetailContract.Iview {
    IShanghaiDetailContract.IPresenter mPresenter = new ShanghaiDetailPresenter(this);

    public static String mActivityOptionsCompat = "ActivityOptionsCompat";
    @BindView(R.id.iv_shanghai_detail)
    ImageView ivShanghaiDetail;
//    private GetProcessReceiver getProcessReceiver;
//    @BindView(R.id.glsurfaceview)
//    GLSurfaceView glsurfaceview;
    private ServiceConnection mConnection = new ServiceConnection() {
    private Messenger messenger;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle data = msg.getData();
            Log.e("ActivityOptionsCompat", "processDec = " + data.getString("process"));
        }
    };
    private Messenger messengerClient = new Messenger(handler);
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        messenger = new Messenger(service);
        Message message = new Message();
        message.what = MainProcessService.SHANGHAI_DETAIL;
        message.replyTo = messengerClient;
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
};

    @Override
    public void afterBindView() {
//        glsurfaceview.setRenderer(new GLSurfaceView.Renderer() {
//            @Override
//            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//                //都是子线程回调
//            }
//
//            @Override
//            public void onSurfaceChanged(GL10 gl, int width, int height) {
//
//            }
//
//            @Override
//            public void onDrawFrame(GL10 gl) {
//                //循环调用  进行渲染
//            }
//        });
        initAnima();
//        initReceiver();
//        initProcessData();
        initGetNetData();
//        initProviderData();
        initProcessService();
    }

    private void initProcessService() {
        Intent intent = new Intent(this, MainProcessService.class);
        bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
    }

//    private void initProviderData() {
//        Uri insert = getContentResolver().insert(Uri.parse("content://com.news.today.process.data"), new ContentValues());
//        Log.e("ActivityOptionsCompat", "processDec = " + insert.toString());
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(getProcessReceiver);
    }

//    private void initReceiver() {
//        getProcessReceiver = new GetProcessReceiver();
//        registerReceiver(getProcessReceiver, new IntentFilter("beijing_post_process_data"));
//    }
//
//    private void initProcessData() {
//        Intent intent = new Intent("shanghai_get_process_data");
//        //发送广播
//        sendBroadcast(intent);
//    }


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
//    private class GetProcessReceiver extends BroadcastReceiver{
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String processDec = intent.getStringExtra("processDec");
//            Log.e("ActivityOptionsCompat", "processDec = " + processDec);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
