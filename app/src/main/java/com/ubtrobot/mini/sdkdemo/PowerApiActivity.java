package com.ubtrobot.mini.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.ubt.power.PowerApi;


public class PowerApiActivity extends Activity {
    private static final String TAG = DemoApp.DEBUG_TAG;
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.power_api_layout);

    }

    public void powerOnMicArray(View view) {
        boolean isPowerOn = PowerApi.get().isMicArrayPowerOn();
        if (isPowerOn) {
            PowerApi.get().powerOffMicArray();
        } else {
            PowerApi.get().powerOnMicArray();
        }
        isPowerOn = PowerApi.get().isMicArrayPowerOn();
        Log.d(TAG,"isPowerOn = "+isPowerOn);
    }

    public void powerOnEyes(View view) {
        boolean isPowerOn = PowerApi.get().isEyesPowerOn();
        if (isPowerOn) {
            PowerApi.get().powerOffEyes();
        } else {
            PowerApi.get().powerOnEyes();
        }
        isPowerOn = PowerApi.get().isEyesPowerOn();
        Log.d(TAG,"isPowerOn = "+isPowerOn);
    }

    public void setEyesBrightness(View view) {
        int currBright = PowerApi.get().getEyesBrightness();
        PowerApi.get().setEyesBrightness(currBright - 1);
    }
}
