package com.example.application.base.crash;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashProtectManager {
    private static CrashProtectManager mInstance;
    private static Context mContext;

    private CrashProtectManager(){

    }

    public static CrashProtectManager getInstance(Context context){
        if(mInstance == null){
            //context传到单例里，为防止内存泄露，要做保护context.getApplicationContext()
            mContext = context.getApplicationContext();
            mInstance = new CrashProtectManager();
        }
        return mInstance;
    }

    public void init(){
        //崩溃保护
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            //没有被try...catch住的异常都会调用这个方法
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                handleFileException(e);
                if(t == Looper.getMainLooper().getThread()){
                    handleMainThread(e);
                }
            }

            //日志文件系统
            private void handleFileException(Throwable e) {
                //通过Throwble 生成字符串
                Writer writer = new StringWriter();
                PrintWriter printWriter = new PrintWriter(writer);
                e.printStackTrace(printWriter);
                printWriter.close();
                String result = writer.toString();
                //定义文件名
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
                String time = dateFormat.format(new Date());
                String fileName = "crash-"+ time + ".txt";
                try {
                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        File cacheDir = mContext.getCacheDir();
                        if(cacheDir.exists()){
                            cacheDir.mkdirs();
                        }
                        File cacheFile = new File(cacheDir.getAbsolutePath(), fileName);
                        if(!cacheDir.exists()){
                            cacheFile.createNewFile();
                        }
                        //把字符串写入到文件
                        FileOutputStream outputStream = new FileOutputStream(cacheFile);
                        outputStream.write(result.getBytes());
                        outputStream.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            private void handleMainThread(Throwable e) {
                while(true){
                    try{
                        //Looper.loop()卡死外面还有while死循环，所以不会崩溃
                        Looper.loop();
                    }catch (Throwable e1){
                        handleFileException(e);
                    }
                }
            }
        });
    }
}
