package com.ubtrobot.mini.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.ubtrobot.motion.protos.Sys;
import com.ubtrobot.motor.receiver.SubsystemErrorReceiver;
import com.ubtrobot.sys.SysApi;

/**
 * SysApi的测试方法
 */

public class SysApiActivity extends Activity {
    private static final String TAG = DemoApp.DEBUG_TAG;
    private SysApi sysApi;
    private SubsystemErrorReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sys_api_layout);

        initRobot();
    }

    /**
     * 初始化接口类实例
     */
    private void initRobot() {
        sysApi = SysApi.get();
    }

    public void readRobotId(View view) {
        Log.i(TAG, "readRobotId接口调用成功,readRobotId======" + sysApi.readRobotSid());
    }

    /**
     * 订阅Sys错误事件
     *
     * @param view
     */
    public void subscribeSysErrorEvent(View view) {
        receiver = new SubsystemErrorReceiver() {
            @Override
            protected void onSubsystemCommandError(Sys.SubsystemCommandError subsystemCommandError) {
                Log.i(TAG, "subscribeSysErrorEvent接口调用失败！");
            }
        };
        sysApi.subscribeSubsystemErrorEvent(receiver);
        Log.i(TAG, "subscribeSysErrorEvent接口调用成功！");
    }

    /**
     * 取消订阅Sys错误事件
     *
     * @param view
     */
    public void unsubscribeSysErrorEvent(View view) {
        if (receiver != null) {
            sysApi.unsubscribeSubsystemErrorEvent(receiver);
            Log.i(TAG, "unsubscribeSysErrorEvent接口调用成功！");
        } else {
            Log.i(TAG, "receiver为null,不调用接口！");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
