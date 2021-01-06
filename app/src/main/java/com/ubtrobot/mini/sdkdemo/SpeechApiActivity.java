package com.ubtrobot.mini.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.ubtrobot.speech.SpeechApi;
import com.ubtrobot.speech.parcelable.ASRState;
import com.ubtrobot.speech.parcelable.InitResult;
import com.ubtrobot.speech.parcelable.MicrophoneWakeupAngle;
import com.ubtrobot.speech.protos.Speech;
import com.ubtrobot.speech.receivers.AsrStateReceiver;
import com.ubtrobot.speech.receivers.InitResultReceiver;
import com.ubtrobot.speech.receivers.WakeupAngelReceiver;
import com.ubtrobot.speech.receivers.WakeupReceiver;

import com.ubtrobot.teach.receivers.WakeupAngleReceiver;

/**
 * SpeechApi的测试方法
 *
 * 韩国版没有 唤醒角度，asr状态测试项。
 *
 */

public class SpeechApiActivity extends Activity {
    private static final String TAG = DemoApp.DEBUG_TAG;
    private SpeechApi speechApi;
    private WakeupReceiver wakeupReceiver;
    private com.ubtrobot.teach.receivers.WakeupReceiver oWakeupReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speech_api_layout);

        initRobot();
    }

    /**
     * 初始化接口类实例
     */
    private void initRobot() {
        speechApi = SpeechApi.get();
    }

    /**
     * 订阅唤醒事件
     *
     * @param view
     */
    public void subscribeWakeupEvent(View view) {
        wakeupReceiver = new WakeupReceiver() {
            @Override
            public void onWakeup(Speech.WakeupParam data) {
                Log.i(TAG, "监听到唤醒事件!");
                Log.i(TAG, "唤醒getAngle======" + data.getAngle());
                Log.i(TAG, "唤醒getMicType name======" + data.getMicType().name());
                Log.i(TAG, "唤醒getSerializedSize======" + data.getSerializedSize());
            }
        };

        speechApi.subscribeEvent(wakeupReceiver);
        Log.i(TAG, "subscribeEvent订阅唤醒事件接口调用成功！");
    }

    /**
     * 订阅唤醒事件
     *
     * @param view
     */
    public void subscribeWakeupAngelEvent(View view) {
        speechApi.subscribeEvent(new WakeupAngelReceiver() {
            @Override
            public void onWakeupAngel(Speech.WakeupParam data) {
                Log.i(TAG, "监听到唤醒角度事件!");
                Log.i(TAG, "唤醒getAngle======" + data.getAngle());
                Log.i(TAG, "唤醒getMicType name======" + data.getMicType().name());
                Log.i(TAG, "唤醒getSerializedSize======" + data.getSerializedSize());
            }
        });
        Log.i(TAG, "subscribeEvent订阅唤醒事件接口调用成功!");
    }

    /**
     * 订阅ASR状态事件
     *
     * @param view
     */
    public void subscribeAsrStateEvent(View view) {
        speechApi.subscribeEvent(new AsrStateReceiver() {
            @Override
            public void onStateChange(Speech.ASRState state, int errCode) {
                Log.i(TAG, "监听到状态变化事件!");
                Log.i(TAG, "errCode======" + errCode);
                Log.i(TAG, "状态name======" + state.name());
                Log.i(TAG, "状态Number======" + state.getNumber());
            }
        });
        Log.i(TAG, "subscribeEvent订阅ASR状态事件接口调用成功!");
    }

    /**
     * 订阅事件
     *
     * @param view
     */
    public void subscribeInitResultEvent(View view) {
        speechApi.subscribeEvent(new InitResultReceiver() {
            @Override
            public void onResult(Speech.InitResult data) {
                Log.i(TAG, "监听到初始化结果事件!");
                Log.i(TAG, "getResultCode======" + data.getResultCode());
                Log.i(TAG, "isInitialized======" + data.isInitialized());
                Log.i(TAG, "getInitializationErrorString======" + data.getInitializationErrorString());
            }
        });
        Log.i(TAG, "subscribeEvent监听初始化结果事件接口调用成功!");
    }

    /**
     * 取消事件订阅
     *
     * @param view
     */
    public void unsubscribeEvent(View view) {
        speechApi.unsubscribeEvent(wakeupReceiver);
        Log.i(TAG, "unsubscribeEvent取消事件订阅!");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void oSubscribeWakeupEvent(View view) {
        oWakeupReceiver = new com.ubtrobot.teach.receivers.WakeupReceiver() {
            @Override
            protected void onWakeup(com.ubtrobot.teach.protos.Speech.WakeupParam wakeupParam) {
                Log.i(TAG, "监听到唤醒事件: " + wakeupParam);
            }
        };
        speechApi.oSubscribeEvent(oWakeupReceiver);

    }

    public void oSubscribeWakeupAngelEvent(View view) {
        speechApi.oSubscribeEvent(new WakeupAngleReceiver() {
            @Override
            protected void onWakeup(MicrophoneWakeupAngle microphoneWakeupAngle) {
                Log.i(TAG, "监听到唤醒角度事件: " + microphoneWakeupAngle);
            }
        });
    }

    public void oSubscribeAsrStateEvent(View view) {
        speechApi.oSubscribeEvent(new com.ubtrobot.teach.receivers.AsrStateReceiver() {
            @Override
            protected void onStateChange(ASRState asrState) {
                Log.i(TAG, "监听到状态变化事件: " + asrState);
            }
        });
    }

    public void oSubscribeInitResultEvent(View view) {
        speechApi.oSubscribeEvent(new com.ubtrobot.teach.receivers.InitResultReceiver() {
            @Override
            protected void onResult(InitResult initResult) {
                Log.i(TAG, "监听到初始化结果事件: " + initResult);
            }
        });
    }

    public void oUnsubscribeEvent(View view) {
        Log.i(TAG, "oUnsubscribeEvent 取消事件订阅");
        speechApi.oUnsubscribeEvent(oWakeupReceiver);
    }
}
