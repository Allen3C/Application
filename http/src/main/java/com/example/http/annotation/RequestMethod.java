package com.example.http.annotation;

import androidx.annotation.IntDef;

import static com.example.http.annotation.RequestMethod.Get;
import static com.example.http.annotation.RequestMethod.Post;

/**
 * 请求方式  注解
 */
@IntDef({Get, Post})
public @interface RequestMethod {
    int Get = 1;
    int Post = 2;
}
