package com.ubtrobot.mini.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ubtechinc.sauron.api.TakePicApi;
import com.ubtrobot.commons.ResponseListener;

/**
 * TakePicApi的测试方法
 */

public class TakePicApiActivity extends Activity {
    private static final String TAG = DemoApp.DEBUG_TAG;
    private TakePicApi takePicApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_pic_api_layout);
        initRobot();
    }

    /**
     * 初始化接口类实例
     */
    private void initRobot() {
        takePicApi = TakePicApi.get();
    }

    /**
     * 拍照(立即)
     *
     * @param view
     */
    public void takePicImmediately(View view) {
        takePicApi.takePicImmediately(new ResponseListener<String>() {

            @Override
            public void onResponseSuccess(String string) {
                Log.i(TAG, "takePicImmediately接口调用成功！");
                Toast.makeText(getApplicationContext(), "saving " + string, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int errorCode, @NonNull String errorMsg) {
                Log.i(TAG, "takePicImmediately接口调用失败,errorCode======" + errorCode + ",errorMsg======" + errorMsg);
            }
        });

    }

    /**
     * 拍照(寻找人脸)
     *
     * @param view
     */
    public void takePicWithFaceDetect(View view) {
        takePicApi.takePicWithFaceDetect(new ResponseListener<String>() {
            @Override
            public void onResponseSuccess(String string) {
                Log.i(TAG, "takePicWithFaceDetect接口调用成功！");
                Toast.makeText(getApplicationContext(), "saving " + string, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int errorCode, @NonNull String errorMsg) {
                Log.i(TAG, "takePicWithFaceDetect接口调用失败,errorCode======" + errorCode + ",errorMsg======" + errorMsg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
