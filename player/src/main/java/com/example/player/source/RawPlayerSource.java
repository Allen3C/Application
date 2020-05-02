package com.example.player.source;

import java.io.File;

public class RawPlayerSource implements IPlayerSouece {

    private String url;


    //"android.resource://" + getPackageName() + File.separator + R.raw.splash
    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    public IPlayerSouece setPath(String packageName, int rawId){
        setUrl("android.resource://" + packageName + File.separator + rawId);
        return this;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
