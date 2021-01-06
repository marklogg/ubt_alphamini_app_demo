package com.ubtrobot.mini.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.ubtechinc.sauron.api.ObjectDetectApi;
import com.ubtechinc.sauron.api.ObjectType;
import com.ubtrobot.commons.Priority;
import com.ubtrobot.commons.ResponseListener;
import com.ubtrobot.mini.voice.VoicePool;

import java.util.List;

/**
 * ObjectDetectApi的测试方法
 */

public class ObjectDetectApiActivity extends Activity {
    private static final String TAG = DemoApp.DEBUG_TAG;
    private ObjectDetectApi objectDetectApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_detect_api_layout);

        initRobot();
    }

    /**
     * 初始化接口类实例
     */
    private void initRobot() {
        objectDetectApi = ObjectDetectApi.get();
    }

    /**
     * 开始物体识别
     *
     * @param view
     */
    public void objectDetect(View view) {
        objectDetectApi.objectDetect(0x00,ObjectType.ALL, 15000, new ResponseListener<List<String>>() {
            @Override
            public void onResponseSuccess(List<String> resultList) {
                for (String result : resultList) {
                    Log.i(TAG, "objectDetect接口调用返回======" + result);
                    VoicePool.get().playTTs("This is: " + result + " .", Priority.HIGH, null);
                }

            }

            @Override
            public void onFailure(int errorCode, @NonNull String errorMsg) {
                Log.i(TAG, "objectDetect接口调用失败,errorCode======" + errorCode + ",errorMsg======" + errorMsg);
            }
        });
        Log.i(TAG, "objectDetect接口调用成功!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
