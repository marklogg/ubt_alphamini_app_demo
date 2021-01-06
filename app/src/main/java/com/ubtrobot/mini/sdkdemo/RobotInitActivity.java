package com.ubtrobot.mini.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ubtrobot.mini.voice.VoiceListener;
import com.ubtrobot.robotinit.BootMediaHelper;
import com.ubtrobot.robotinit.PowerKeyManager;
import com.ubtrobot.robotinit.RobotInitApi;
import com.ubtrobot.robotinit.VolumeManager;


public class RobotInitActivity extends Activity {
    private static final String TAG = DemoApp.DEBUG_TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_init);


    }

    public void OnRobotInit(View view){
        Log.i(TAG,"OnRobotInit====");
        RobotInitApi.get().init(new RobotInitApi.InitCompleteListener(){

            @Override
            public void onComplete() {
                RobotInitApi.get().initCompleteBehavior("", new VoiceListener() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG,"OnRobotInit onCompleted====");
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.e(TAG,"OnRobotInit: "+"i====" + i + ";;s===" + s);
                    }
                });
            }
        });
    }

    public void onSubscirsPower(View view){
        Log.i(TAG,"onSubscirsPower onSuccess");
        PowerKeyManager.get().subscribePowkeyEvent();
    }

    public void onSubscirsVolume(View view){
        Log.i(TAG,"onSubscirsVolume onSuccess");
        VolumeManager.get().subscribeVolumeEvent();
    }

    public void updateBootAudio(View view) {
        //replace your audio path
        BootMediaHelper.get().updateBootAudio("/data/files/music/custom_029.mp3", new BootMediaHelper.Callback() {
            @Override
            public void onComplete() {
                Log.i(TAG,"updateBootAudio onComplete");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i(TAG,"updateBootAudio onFailure: "+ i + " ====== "+s);
            }
        });
        //then reboot your robot
    }

    public void deleteBootAudio(View view) {
        BootMediaHelper.get().deleteBootAudio(new BootMediaHelper.Callback() {
            @Override
            public void onComplete() {
                Log.i(TAG,"deleteBootAudio onComplete");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i(TAG,"deleteBootAudio onFailure: "+ i + " ====== "+s);
            }
        });
        //then reboot your robot
    }
}
