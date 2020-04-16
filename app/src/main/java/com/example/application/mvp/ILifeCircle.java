package com.example.application.mvp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

//抽象中介者  接口
//包括Activity和Fragment生命周期的方法，Fragment后边讲
public interface ILifeCircle {
    //Bundle可以先不用了解
    void onCreate(Bundle savedInstanceState, Intent intent, Bundle getArguments);

    void onActivityCreated(Bundle savedInstanceState, Intent intent, Bundle getArguments);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void destroyView();

    void onViewDestroy();

    void onNewIntent(Intent intent);

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

    void onSaveInstanceState(Bundle bundle);

    void attachView(IMvpView iMvpView);
}
