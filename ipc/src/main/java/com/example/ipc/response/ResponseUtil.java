package com.example.ipc.response;

import com.example.ipc.IClientInterface;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ResponseUtil {

    //反射调用的类 必须在com.ipc.response 包名下面
    public static void getAsyncResponse(String requestKey, String requestParams, IClientInterface mClientInterface) {
        StringBuilder stringBuilder = new StringBuilder("com.ipc.response");
        String first = requestKey.substring(0, 1);
        String second = requestKey.substring(1, requestKey.length());
        stringBuilder.append(first.toUpperCase()).append(second).append("Response");
        //通过反射方式调用到 宿主（app）的类里面
        try {
            Class<?> clazz = Class.forName(stringBuilder.toString());
            Constructor<?> constructor = clazz.getConstructor(String.class, String.class, IClientInterface.class);
            Object o = constructor.newInstance(requestKey, requestParams, mClientInterface);
            Method method = clazz.getMethod(requestKey);
            method.invoke(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
