package com.example.application.main.shenzhen;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.application.R;
import com.example.application.base.BaseFragment;
import com.example.application.base.ViewInject;
import com.example.application.main.shenzhen.view.OpenGlActivity;
import com.example.application.main.shenzhen.view.WebViewActivity;
import com.example.application.main.shenzhen.view.WeiXinActivity;
import com.example.applicationndk.MainActivity;
import com.example.opengl.OpenGlManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import butterknife.BindView;

@ViewInject(mainlayoutid = R.layout.fragment_shenzhen)
public class ShenZhenFragment extends BaseFragment {

    @BindView(R.id.bt_open_gl)
    Button openGL;

    @BindView(R.id.bt_webview)
    Button webView;

    @BindView(R.id.bt_wx)
    Button weixin;

    @Override
    public void afterBindView() {
        openGL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OpenGlActivity.class));
            }
        });

        webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WebViewActivity.class));
            }
        });

        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WeiXinActivity.class));
            }
        });

    }

}
