package com.zs.demo.jpushdemo;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class MainActivity extends AppCompatActivity {

    private static final int TAG = 1000;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                setAlias("aaa");
            }
        }
    };
    private JPushMessageReceiver jPushMessageReceiver = new JPushMessageReceiver() {
        @Override
        public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
            super.onAliasOperatorResult(context, jPushMessage);
            if (jPushMessage.getErrorCode() == 6002) {//超时处理
                Message obtain = Message.obtain();
                obtain.what = 100;
                mHandler.sendMessageDelayed(obtain, 1000 * 60);//60秒后重新验证
            }else {
                Log.e("onAliasOperatorResult: ", jPushMessage.getErrorCode()+"");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // sharepreference 中记录一下，如果设置过可以不再设置
        setAlias("aaa");
    }

    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";

    public void registerMessageReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(jPushMessageReceiver, filter);
    }

    private void setAlias(String alias) {
        if ( !alias.isEmpty()) {
            JPushInterface.setAlias(getApplicationContext(), TAG, alias);//设置别名或标签
            registerMessageReceiver();
        }

    }
}
