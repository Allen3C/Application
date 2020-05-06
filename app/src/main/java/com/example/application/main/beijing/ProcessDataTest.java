package com.example.application.main.beijing;

import android.util.Log;

public class ProcessDataTest {
    private static ProcessDataTest mInstance;

    private String processDec;

    private ProcessDataTest(){
//        Log.e("ActivityOptionsCompat", "PID = " + android.os.Process.myPid());
    }

    public static synchronized  ProcessDataTest getInstance(){
        if(mInstance == null){
            mInstance = new ProcessDataTest();
        }
        return mInstance;
    }

    public String getProcessDec() {
        return processDec;
    }

    public ProcessDataTest setProcessDec(String processDec) {
        this.processDec = processDec;
        return this;
    }
}
