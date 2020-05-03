package com.example.application.main.shanghai.view;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;

import com.example.application.R;
import com.example.application.base.BaseActivity;
import com.example.application.base.ViewInject;
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
    @BindView(R.id.glsurfaceview)
    GLSurfaceView glsurfaceview;
//    @BindView(R.id.tv_crash)
//    TextView mTvCrash;

    @Override
    public void afterBindView() {
        glsurfaceview.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                //都是子线程回调
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {

            }

            @Override
            public void onDrawFrame(GL10 gl) {
                //循环调用  进行渲染
            }
        });
        initAnima();
        initGetNetData();
//        ivShanghaiDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String s = null;
//                s.toString();
//            }
//        });
        //initPostNetData();
//        mTvCrash.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String s = null;
//                        s.toString();
//
//                    }
//                }).start();
//            }
//        });
    }

//    private void initPostNetData() {
//        OkHttpClient client = new OkHttpClient();
//        FormBody.Builder builder = new FormBody.Builder();
//        builder.add("key", "6c08233485a6d4ab085b215046fe2170");
//        Request request = new Request.Builder().url("http://apis.juhe.cn/lottery/types").post(builder.build()).build();
//        Call call = client.newCall(request);
//        //异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                Log.e("initGetNetData", "onFailure" + e);
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                Log.e("initGetNetData", "onResponse" + response.body().string());
//            }
//        });
//    }

    //发送网络请求数据
    private void initGetNetData() {

        mPresenter.getNetData("1");


//        GetXiaoHuaTask task = new GetXiaoHuaTask();
//        task.execute("desc", "1", "2");

//        Object desc = new ShangHaiDetailHttpTask().getXiaoHuaList("desc", "1", "2");
//        if(desc instanceof Response){
//            Response response = (Response)desc;
//            Log.e("initGetNetData", response.body().toString());
//        }


//        //1.可以隔离
//        OkHttpClient client = new OkHttpClient();
//        //2.可以隔离  1）构建url 2）构建参数
//        HttpUrl.Builder builder = HttpUrl.parse("http://v.juhe.cn/joke/content/list.php").newBuilder();
//        builder.addQueryParameter("sort", "desc");
//        builder.addQueryParameter("page", "1");
//        builder.addQueryParameter("pagesize", "2");
//        //System.currentTimeMillis()  13位毫秒数
//        builder.addQueryParameter("time", "" + System.currentTimeMillis()/1000);
//        builder.addQueryParameter("key", "c3445bee630adef5e31bf1a0231b2efe");
//        //3.可以隔离 构建request
//        Request request = new Request.Builder().url(builder.build()).get().build();
//        //4.可以隔离  构建call
//        Call call = client.newCall(request);
//        //异步请求
//        //5.发送网络请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                Log.e("initGetNetData", "onFailure" + e);
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                Log.e("initGetNetData", "onResponse" + response.body().string());
//            }
//        });
    }

    private void initAnima() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //setTransitionName也可以在xml里配
            ViewCompat.setTransitionName(ivShanghaiDetail, mActivityOptionsCompat);

            //延时加载
            //postponeEnterTransition();
            //开启专场动画
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
