package com.example.application.main.shanghai.manager;

import android.os.AsyncTask;
import android.util.Log;

import com.example.application.main.shanghai.module.ShangHaiDetailHttpTask;

import java.io.IOException;

import okhttp3.Response;

//同步请求不能放到主线程
//三个参数意义分别为:传进的参数,处理过程产生的变量,线程执行完返回的结果
public class GetXiaoHuaTask extends AsyncTask<Object, Object, Object> {

    //运行在子线程中
    @Override
    protected Object doInBackground(Object... objects) {
        Object desc = new ShangHaiDetailHttpTask().getXiaoHuaList((String)objects[0],(String)objects[1], (String)objects[2]);
        Response response = (Response)desc;
        try {
            Log.e("GetXiaoHuaTask", "onResponse" + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return desc;
    }

    //运行在主线程,用于更新数据
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
//        Response response = (Response)o;
//        try {
//            Log.e("GetXiaoHuaTask", "onResponse" + response.body().string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
