package com.zs.demo.jpushdemo;

import android.app.Application;

import com.zs.demo.jpushdemo.util.Logger;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zs
 * Date：2018年 11月 19日
 * Time：13:44
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class MyApplication extends Application{

    private static final String TAG = "JIGUANG-Example";
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(TAG, "[ExampleApplication] onCreate");
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

    }
}
