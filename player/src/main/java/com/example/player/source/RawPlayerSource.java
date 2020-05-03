package com.example.player.source;

import java.io.File;


//Raw文件播放源
public class RawPlayerSource implements IPlayerSource {

    private String url;
    private int resId;


    //"android.resource://" + getPackageName() + File.separator + R.raw.splash
    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    public IPlayerSource setPath(String packageName, int rawId){
        setUrl("android.resource://" + packageName + File.separator + rawId);
        setResId(rawId);
        return this;
    }

    private void setResId(int rawId) {
        this.resId = rawId;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public int getResId() {
        return resId;
    }
}
