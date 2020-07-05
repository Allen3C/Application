package com.example.application.main.shanghai.presenter;

import android.util.Log;

import com.example.application.base.BasePresenter;
import com.example.application.base.JHTask;
import com.example.application.main.shanghai.If.IShanghaiDetailContract;
import com.example.application.main.shanghai.dto.ShanghaiDetailBean;
import com.example.application.main.shanghai.module.ShangHaiDetailHttpTask;
import com.example.http.result.IResult;
import com.google.gson.Gson;

/**
 * p层 请求网络
 */
public class ShanghaiDetailPresenter extends BasePresenter<IShanghaiDetailContract.Iview> implements IShanghaiDetailContract.IPresenter {
    public ShanghaiDetailPresenter(IShanghaiDetailContract.Iview view) {
        super(view);
    }

//    @Override
//    protected IShanghaiDetailContract.Iview getEmptyView() {
//        return IShanghaiDetailContract.emptyView;
//    }

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





        /**
         * 这是task模块的功能
         * 执行异步任务（http模块的网络请求）
         */
        submitTask(new JHTask<ShanghaiDetailBean>() {

            /**
             * 运行在子线程  执行网络请求
             * AsyncTaskInstance这个类里实现将这个方法运行在子线程
             * @return
             */
            @Override
            public IResult<ShanghaiDetailBean> onBackground() {
                //这是http模块的功能
                //执行网络请求并返回结果 返回的结果作为onComplete的参数，也就是下边onSuccess的参数
                return new ShangHaiDetailHttpTask<ShanghaiDetailBean>().getXiaoHuaList("desc", "1", pagesize + "");
            }


            /**
             * 运行在主线程  表示请求成功
             * AsyncTaskInstance这个类里实现将这个方法运行在主线程
             * @param t
             */
            @Override
            public void onSuccess(IResult<ShanghaiDetailBean> t) {
                //t是Result  t.data()是Result里边的JavaBean，也就是网络返回的数据
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
