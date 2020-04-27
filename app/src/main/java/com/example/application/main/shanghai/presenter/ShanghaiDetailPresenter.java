package com.example.application.main.shanghai.presenter;

import android.util.Log;

import com.example.application.base.BasePresenter;
import com.example.application.base.JHTask;
import com.example.application.main.shanghai.If.IShanghaiDetailContract;
import com.example.application.main.shanghai.dto.ShanghaiDetailBean;
import com.example.application.main.shanghai.module.ShangHaiDetailHttpTask;
import com.example.http.result.IResult;
import com.google.gson.Gson;


public class ShanghaiDetailPresenter extends BasePresenter<IShanghaiDetailContract.Iview> implements IShanghaiDetailContract.IPresenter {
    public ShanghaiDetailPresenter(IShanghaiDetailContract.Iview view) {
        super(view);
    }

    @Override
    protected IShanghaiDetailContract.Iview getEmptyView() {
        return IShanghaiDetailContract.emptyView;
    }

    @Override
    public void getNetData(String pagesize) {
//        submitTask(new LfTask() {
//            //一定要回调到主线程
//            @Override
//            public void onSuccess(Object o) {
//                //获取网络请求结果
//                Log.e("getNetData", o.toString());
//            }
//
//            @Override
//            public void onException(Throwable throwable) {
//                Log.e("getNetData", throwable.toString());
//
//            }
//
//            //运行子线程
//            @Override
//            public Object onBackground() {
//                IResult desc = new ShangHaiDetailHttpTask().getXiaoHuaList("desc", "1", "2");
//                return desc;
//            }
//        });

        //架构师的必备条件
        //1.合理利用继承关系
        //2.合理利用抽象编程
        //3.合理利用泛型传递数据
        //4.合理利用一些设计模式
        submitTask(new JHTask<ShanghaiDetailBean>() {

            @Override
            public IResult<ShanghaiDetailBean> onBackground() {
                return new ShangHaiDetailHttpTask<ShanghaiDetailBean>().getXiaoHuaList("desc", "1", pagesize + "");
            }

            @Override
            public void onSuccess(IResult<ShanghaiDetailBean> t) {
                ShanghaiDetailBean data = t.data();
//                //仅限演示
//                Gson gson = new Gson();
//                String s = gson.toJson(data);
//                Log.e("ShanghaiDetailPresenter", s);
                getView().showData(data);
            }
        });

    }
}
