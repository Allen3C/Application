package com.example.application.main.shenzhen;

import android.opengl.GLSurfaceView;
import android.widget.TextView;

import com.example.application.R;
import com.example.application.base.BaseFragment;
import com.example.application.base.ViewInject;
import com.example.applicationndk.MainActivity;
import com.example.opengl.OpenGlManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import butterknife.BindView;

@ViewInject(mainlayoutid = R.layout.fragment_shenzhen)
public class ShenZhenFragment extends BaseFragment {

    @BindView(R.id.gl_surfce_view)
    GLSurfaceView glSurfceView;


    @Override
    public void afterBindView() {
       glSurfceView.setRenderer(new GLSurfaceView.Renderer() {
           @Override
           public void onSurfaceCreated(GL10 gl, EGLConfig config) {
               //为缓冲区 设置清除颜色的值 相当于初始化
//               gl.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
               OpenGlManager.onSurfaceCreated();
           }

           @Override
           public void onSurfaceChanged(GL10 gl, int width, int height) {
               //设置视图大小
//                gl.glViewport(0, 0, width, height);
               OpenGlManager.onSurfaceChanged(width, height);
           }

           //绘制时 每一帧 都会被系统调用 在Android中 默认最高绘制效率 为 1秒 60帧
           @Override
           public void onDrawFrame(GL10 gl) {
               //设置色彩
//               gl.glClear(gl.GL_COLOR_BUFFER_BIT);
               OpenGlManager.onDrawFrame();
           }
       });
    }

}
